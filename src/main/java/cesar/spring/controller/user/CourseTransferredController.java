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
import cesar.model.CourseTransferred;
import cesar.model.User;
import cesar.model.dao.CourseDao;
import cesar.model.dao.CourseTransferredDao;
import cesar.model.dao.UserDao;
import cesar.spring.editor.CourseGetCodePropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
@SessionAttributes("courseTransferred")
public class CourseTransferredController {

    @Autowired
    private UserDao                     userDao;
    @Autowired
    private CourseDao                   courseDao;
    @Autowired
    private CourseTransferredDao        courseTransferredDao;
    @Autowired
    private CourseGetCodePropertyEditor courseGetCodePropertyEditor;

    private Logger                      logger = LoggerFactory
                                                   .getLogger( CourseTransferredController.class );

    @RequestMapping(value = ("/user/addCourseTransferred.html"),
        method = RequestMethod.POST)
    public String addCourseWaivedGetHandler( Integer userId, String courseCode,
        String comment, HttpServletRequest request )
    {
        String courseTransferredError = null;
        HttpSession session = request.getSession();
        User student = userDao.getUserById( userId );
        User advisor = userDao.getUserByUsername( SecurityUtils.getUsername() );
        Course course = courseDao.getCourseByCode( courseCode );

        if( course == null )
        {
            courseTransferredError = "Course entered does not exsit";
            session.setAttribute( "courseTransferredError",
                courseTransferredError );
            return "redirect:/user/display.html?userId=" + userId
                + "&#tab-courses";
        }

        courseTransferredError = null;
        session.setAttribute( "courseTransferredError", courseTransferredError );

        CourseTransferred courseTransferred = new CourseTransferred();
        courseTransferred.setCourse( course );
        courseTransferred.setStudent( student );
        courseTransferred.setComment( comment );
        courseTransferred.setAdvisor( advisor );

        courseTransferredDao.saveCourseTransfer( courseTransferred );

        logger
            .info( "User : "
                + userDao.getUserByUsername( SecurityUtils.getUsername() )
                    .getName() + " added a transferred course: "
                + course.getCode() );
        return "redirect:/user/display.html?userId=" + userId + "&#tab-courses";
    }

    @RequestMapping(value = { "/user/editCourseTransferred.html" },
        method = RequestMethod.GET)
    public String editCourseTransferredGetHandler( Integer courseTransferredId,
        ModelMap model )
    {
        CourseTransferred courseTransferred = courseTransferredDao
            .getCourseTransferById( courseTransferredId );
        model.addAttribute( "courseTransferred", courseTransferred );

        return "others/editCourseTransferred";
    }

    @RequestMapping(value = { "/user/editCourseTransferred.html" },
        method = RequestMethod.POST)
    protected String onSubmit( CourseTransferred courseTransferred,
        ModelMap model, SessionStatus status )
    {
        String courseTransferredError = null;
        Course course = courseTransferred.getCourse();

        if( course == null )
        {
            courseTransferredError = "course entered does not exsit";
            model.addAttribute( "courseTransferredError",
                courseTransferredError );
            model.addAttribute( "courseTransferred", courseTransferred );
            return "/others/editCourseTransferred";
        }
        courseTransferred.setDate( new Date() );
        courseTransferred.setAdvisor( userDao.getUserByUsername( SecurityUtils
            .getUsername() ) );

        courseTransferredDao.saveCourseTransfer( courseTransferred );
        status.setComplete();

        logger.info( "User : "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " edited a transferred course: "
            + course.getCode() );

        return "redirect:/user/display.html?userId="
            + courseTransferred.getStudent().getId() + "&#tab-courses";
    }

    @RequestMapping(value = { "/user/deleteCourseTransferred.html" },
        method = RequestMethod.GET)
    public String deleteCourseTransferred( Integer id, ModelMap model )
    {
        CourseTransferred courseTransferred = courseTransferredDao
            .getCourseTransferById( id );
        courseTransferredDao.deleteCourseTransfer( courseTransferred );

        logger.info( "User : "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " deleted a transferred course: "
            + courseTransferred.getCourse().getCode() );

        return "redirect:/user/display.html?userId="
            + courseTransferred.getStudent().getId() + "&#tab-courses";
    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Course.class, courseGetCodePropertyEditor );
    }
}
