package cesar.spring.controller.user;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import cesar.model.Course;
import cesar.model.CourseWaived;
import cesar.model.User;
import cesar.model.dao.CourseDao;
import cesar.model.dao.CourseWaivedDao;
import cesar.model.dao.UserDao;
import cesar.spring.editor.CourseGetCodePropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
@SessionAttributes("courseWaived")
public class CourseWaivedController {

    @Autowired
    private UserDao                     userDao;
    @Autowired
    private CourseDao                   courseDao;
    @Autowired
    private CourseWaivedDao             courseWaivedDao;
    @Autowired
    private CourseGetCodePropertyEditor courseGetCodePropertyEditor;

    private Logger                      logger = LoggerFactory
                                                   .getLogger( CourseWaivedController.class );

    @RequestMapping(value = ("/user/addCourseWaived.html"),
        method = RequestMethod.POST)
    public String addCourseWaivedGetHandler( Integer userId, String courseCode,
        String comment, HttpServletRequest request )
    {
        String courseWaivedError = null;
        HttpSession session = request.getSession();
        User student = userDao.getUserById( userId );
        User advisor = userDao.getUserByUsername( SecurityUtils.getUsername() );
        Course course = courseDao.getCourseByCode( courseCode );

        if( course == null )
        {
            courseWaivedError = "Course entered does not exsit";
            session.setAttribute( "courseWaivedError", courseWaivedError );
            return "redirect:/user/display.html?userId=" + userId
                + "&#tab-courses";
        }

        courseWaivedError = null;
        session.setAttribute( "courseWaivedError", courseWaivedError );

        CourseWaived courseWaived = new CourseWaived();
        courseWaived.setCourse( course );
        courseWaived.setStudent( student );
        courseWaived.setComment( comment );
        courseWaived.setAdvisor( advisor );

        courseWaivedDao.saveCourseWaived( courseWaived );

        logger.info( "User : "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " added a waived course: " + course.getCode() );

        return "redirect:/user/display.html?userId=" + userId + "&#tab-courses";
    }

    @RequestMapping(value = { "/user/editCourseWaived.html" },
        method = RequestMethod.GET)
    public String editCourseWaivedGetHandler( Integer courseWaivedId,
        ModelMap model )
    {
        CourseWaived courseWaived = courseWaivedDao
            .getCourseWaivedById( courseWaivedId );
        model.addAttribute( "courseWaived", courseWaived );
        return "others/editCourseWaived";
    }

    @RequestMapping(value = { "/user/editCourseWaived.html" },
        method = RequestMethod.POST)
    protected String onSubmit( CourseWaived courseWaived, ModelMap model,
        SessionStatus status )
    {
        String courseWaivedError = null;
        Course course = courseWaived.getCourse();

        if( course == null )
        {
            courseWaivedError = "course entered does not exsit";
            model.addAttribute( "courseWaivedError", courseWaivedError );
            model.addAttribute( "courseWaived", courseWaived );
            return "/others/editCourseWaived";
        }

        courseWaived.setAdvisor( userDao.getUserByUsername( SecurityUtils
            .getUsername() ) );
        courseWaived.setDate( new Date() );

        courseWaivedDao.saveCourseWaived( courseWaived );
        status.setComplete();

        logger.info( "User : "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " edited a waived course: " + course.getCode() );

        return "redirect:/user/display.html?userId="
            + courseWaived.getStudent().getId() + "&#tab-courses";
    }

    @RequestMapping(value = { "/user/deleteCourseWaived.html" },
        method = RequestMethod.GET)
    public String deleteCourseWaived( Integer id, ModelMap model )
    {
        CourseWaived courseWaived = courseWaivedDao.getCourseWaivedById( id );
        courseWaivedDao.deleteCourseWaiver( courseWaived );

        logger.info( "User : "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " deleted a wavied course: "
            + courseWaived.getCourse().getCode() );

        return "redirect:/user/display.html?userId="
            + courseWaived.getStudent().getId() + "&#tab-courses";
    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Course.class, courseGetCodePropertyEditor );
    }
}
