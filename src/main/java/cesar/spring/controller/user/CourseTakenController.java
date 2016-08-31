package cesar.spring.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import cesar.model.Course;
import cesar.model.CourseTaken;
import cesar.model.User;
import cesar.model.dao.CourseDao;
import cesar.model.dao.CourseTakenDao;
import cesar.model.dao.UserDao;
import cesar.spring.editor.CourseGetCodePropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
@SessionAttributes("courseTaken")
public class CourseTakenController {

    @Autowired
    private UserDao                     userDao;
    @Autowired
    private CourseDao                   courseDao;
    @Autowired
    private CourseTakenDao              courseTakenDao;
    @Autowired
    private CourseGetCodePropertyEditor courseGetCodePropertyEditor;

    private Logger                      logger = LoggerFactory
                                                   .getLogger( CourseTakenController.class );

    @RequestMapping(value = ("/addCourseTaken.html"),
        method = RequestMethod.POST)
    public String handleRequestInternal( Integer userId, String courseCode,
        String grade, String quarter, String year, Boolean forProfile,
        ModelMap model, HttpServletRequest request )
    {
        String courseTakenError = null;
        HttpSession httpSession = request.getSession();
        User student = userDao.getUserById( userId );
        Course course = courseDao.getCourseByCode( courseCode );
        if( course == null && forProfile == null )
        {
            courseTakenError = "Course entered does not exsit";
            httpSession.setAttribute( "courseTakenError", courseTakenError );
            return "redirect:/user/display.html?userId=" + userId
                + "&#tab-courses";
        }
        else if( course == null && forProfile != null )
        {
            courseTakenError = "Course entered does not exsit";
            httpSession.setAttribute( "courseTakenError", courseTakenError );
            return "redirect:/profile.html?#tab-courses";
        }
        courseTakenError = null;
        httpSession.setAttribute( "courseTakenError", courseTakenError );
        CourseTaken courseTaken = new CourseTaken();
        courseTaken.setCourse( course );
        courseTaken.setStudent( student );
        courseTaken.setGrade( grade );
        courseTaken.setQuarter( quarter );
        courseTaken.setYear( year );

        courseTakenDao.saveCourseTaken( courseTaken );

        logger.info( "User : "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + "add a course that he/she has taken" );
        if( forProfile == null )
        {
            return "redirect:/user/display.html?userId=" + userId
                + "&#tab-courses";
        }
        else
        {
            return "redirect:/profile.html?#tab-courses";
        }

    }

    @RequestMapping(value = { "/{path}/editCourseTaken.html" },
        method = RequestMethod.GET)
    protected String formBackingObject( Integer courseTakenId,
        @PathVariable("path") String path, ModelMap model )
    {
        Boolean forProfile = true;
        if( path.equals( "user" ) ) forProfile = false;
        CourseTaken courseTaken = courseTakenDao
            .getCourseTakenById( courseTakenId );
        model.addAttribute( "courseTaken", courseTaken );
        model.addAttribute( "forProfile", forProfile );
        return "others/editCourseTaken";
    }

    @RequestMapping(value = { "/{path}/editCourseTaken.html" },
        method = RequestMethod.POST)
    protected String onSubmit( CourseTaken courseTaken,
        @PathVariable("path") String path, ModelMap model, SessionStatus status )
    {
        String courseTakenError = null;
        Course course = courseTaken.getCourse();
        if( course == null )
        {
            Boolean forProfile = false;
            courseTakenError = "course entered does not exsit";
            model.addAttribute( "courseTakenError", courseTakenError );
            model.addAttribute( "courseTaken", courseTaken );
            if( !path.equals( "user" ) ) forProfile = true;
            model.addAttribute( "forProfile", forProfile );
            return "/others/editCourseTaken";
        }
        courseTakenDao.saveCourseTaken( courseTaken );
        status.setComplete();

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " edited a course : " + course.getCode()
            + " that he/she has taken " );
        if( path.equals( "user" ) )
            return "redirect:/user/display.html?userId="
                + courseTaken.getStudent().getId() + "&#tab-courses";
        return "redirect:/profile.html?#tab-courses";
    }

    @RequestMapping(value = { "/{path}/deleteCourseTaken.html" },
        method = RequestMethod.GET)
    public String deleteCourseTaken( Integer id,
        @PathVariable("path") String path, ModelMap model )
    {
        CourseTaken courseTaken = courseTakenDao.getCourseTakenById( id );

        courseTakenDao.deleteCourseTaken( courseTaken );

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " deleted the course:"
            + courseTaken.getCourse().getCode() + " that he/she has taken" );

        if( path.equals( "user" ) )
            return "redirect:/user/display.html?userId="
                + courseTaken.getStudent().getId() + "&#tab-courses";
        return "redirect:/profile.html?#tab-courses";

    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Course.class, courseGetCodePropertyEditor );
    }

}
