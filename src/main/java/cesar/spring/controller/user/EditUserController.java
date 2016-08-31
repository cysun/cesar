package cesar.spring.controller.user;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import cesar.model.Ethnicity;
import cesar.model.HighSchool;
import cesar.model.HighSchoolProgram;
import cesar.model.Major;
import cesar.model.Quarter;
import cesar.model.Role;
import cesar.model.User;
import cesar.model.dao.EthnicityDao;
import cesar.model.dao.HighSchoolDao;
import cesar.model.dao.HighSchoolProgramDao;
import cesar.model.dao.MajorDao;
import cesar.model.dao.RoleDao;
import cesar.model.dao.UserDao;
import cesar.spring.controller.storedQuery.StoredQueryController;
import cesar.spring.editor.EthnicityPropertyEditor;
import cesar.spring.editor.HighSchoolProgramPropertyEditor;
import cesar.spring.editor.HighSchoolPropertyEditor;
import cesar.spring.editor.MajorPropertyEditor;
import cesar.spring.editor.QuarterPropertyEditor;
import cesar.spring.editor.RolePropertyEditor;
import cesar.spring.editor.UserPropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
@SessionAttributes("user")
public class EditUserController {

    @Autowired
    private UserDao                         userDao;
    @Autowired
    private RoleDao                         roleDao;
    @Autowired
    private EthnicityDao                    ethnicityDao;
    @Autowired
    private MajorDao                        majorDao;
    @Autowired
    private HighSchoolDao                   highSchoolDao;
    @Autowired
    private HighSchoolProgramDao            highSchoolProgramDao;
    @Autowired
    private PasswordEncoder                 passwordEncoder;

    @Autowired
    private RolePropertyEditor              rolePropertyEditor;
    @Autowired
    private EthnicityPropertyEditor         ethnicityPropertyEditor;
    @Autowired
    private MajorPropertyEditor             majorPropertyEditor;
    @Autowired
    private QuarterPropertyEditor           quarterPropertyEditor;
    @Autowired
    private HighSchoolPropertyEditor        highSchoolPropertyEditor;
    @Autowired
    private HighSchoolProgramPropertyEditor highSchoolProgramPropertyEditor;
    @Autowired
    private UserPropertyEditor              userPropertyEditor;

    private Logger                          logger = LoggerFactory
                                                       .getLogger( EditUserController.class );

    @RequestMapping(value = { "/{charactor}/edit/{info}.html" },
        method = RequestMethod.GET)
    public String setupForm( Integer userId, @PathVariable("info") String info,
        @PathVariable("charactor") String charactor, ModelMap model )
    {

        User user = null;

        boolean forProfile = false;
        boolean forStaffOnly = false;
        if( charactor.equals( "user" ) )
        {
            // if advisor or staff view profile
            if( userId == null )
            {
                user = userDao.getUserByUsername( SecurityUtils.getUsername() );
                forProfile = true;
            }
            else
            {
                user = userDao.getUserById( userId );
            }
            
           
            if(SecurityUtils.isUserInRole( "ROLE_STAFF" ))
            {
                forStaffOnly = true;
               
            }else
            {
                forStaffOnly = false;
            }
            
            

        }
        else if( charactor.equals( "profile" ) )
        {
            forProfile = true;
            user = userDao.getUserByUsername( SecurityUtils.getUsername() );
        }

        model.addAttribute( "user", user );

        if( info.equals( "account" ) )
        {
            model.addAttribute( "roles", roleDao.getAllRoles() );
            model.addAttribute( "advisors",
                userDao.getUsersByRoleName( "ROLE_ADVISOR" ) );
        }

        if( info.equals( "demographic" ) )
            model.addAttribute( "ethnicities", ethnicityDao.getEthnicities() );

        if( info.equals( "program" ) )
        {
            model.addAttribute( "majors", majorDao.getRegularMajors() );
        }

        if( info.equals( "highSchool" ) )
        {
            model.addAttribute( "highSchools", highSchoolDao.getHighSchools() );
            model.addAttribute( "highSchoolPrograms",
                highSchoolProgramDao.getHighSchoolPrograms() );
        }
        model.addAttribute( "forProfile", forProfile );
        model.addAttribute( "forStaffOnly", forStaffOnly );
        return "/user/edit/" + info;

    }

    @RequestMapping(value = { "/{charactor}/edit/{info}.html" },
        method = RequestMethod.POST)
    public String processForm( Integer id, User user,
        @PathVariable("info") String info,
        @PathVariable("charactor") String charactor, SessionStatus status,
        ModelMap model )
    {
        if( info.equals( "account" ) )
        {
            boolean hasError = false;

            String cinError = null;
            String usernameError = null;

            User cinUser = userDao.getUserByCin( user.getCin() );
          
            if( cinUser != null
                && user.getId().intValue() != cinUser.getId().intValue() )
            {

                hasError = true;
                cinError = "The CIN : " + user.getCin()
                    + " has been registered.";

            }

            User usernameUser = userDao.getUserByUsername( user.getUsername() );
            if( usernameUser != null
                && usernameUser.getId().intValue() != user.getId().intValue() )
            {
                hasError = true;
                usernameError = "The user name: " + user.getUsername()
                    + " has been registered.";

            }
            if( hasError )
            {
                model.addAttribute( "roles", roleDao.getAllRoles() );
                model.addAttribute( "advisors",
                    userDao.getUsersByRoleName( "ROLE_ADVISOR" ) );
                model.addAttribute( "usernameError", usernameError );
                model.addAttribute( "cinError", cinError );

                model.addAttribute( "forProfile", false );

                return "/user/edit/" + info;

            }

            String password = user.getPassword1();
            if( StringUtils.hasText( password ) )
                user.setPassword( passwordEncoder.encodePassword( password,
                    null ) );
        }

        userDao.saveUser( user );

        status.setComplete();

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " edit the user information." );
        if( charactor.equals( "user" ) )
        {
            if( id == null )
            {
                return "redirect:/profile.html?#tab-" + info;
            }
            else
            {
                return "redirect:../display.html?userId=" + user.getId()
                    + "&#tab-" + info;
            }
        }
        else
        {
            return "redirect:/profile.html#tab-" + info;
        }
    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
            new SimpleDateFormat( "MM/dd/yyyy" ), true ) );
        binder.registerCustomEditor( Role.class, rolePropertyEditor );
        binder.registerCustomEditor( Ethnicity.class, ethnicityPropertyEditor );
        binder.registerCustomEditor( Major.class, majorPropertyEditor );
        binder.registerCustomEditor( Quarter.class, quarterPropertyEditor );
        binder
            .registerCustomEditor( HighSchool.class, highSchoolPropertyEditor );
        binder.registerCustomEditor( HighSchoolProgram.class,
            highSchoolProgramPropertyEditor );
        binder.registerCustomEditor( User.class, userPropertyEditor );
    }

}
