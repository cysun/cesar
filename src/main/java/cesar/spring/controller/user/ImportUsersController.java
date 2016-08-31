package cesar.spring.controller.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import cesar.model.HighSchool;
import cesar.model.Major;
import cesar.model.Quarter;
import cesar.model.Role;
import cesar.model.User;
import cesar.model.dao.HighSchoolDao;
import cesar.model.dao.MajorDao;
import cesar.model.dao.RoleDao;
import cesar.model.dao.UserDao;
import cesar.spring.command.ImportUsersCommand;
import cesar.spring.controller.storedQuery.StoredQueryController;
import cesar.spring.editor.HighSchoolPropertyEditor;
import cesar.spring.editor.MajorPropertyEditor;
import cesar.spring.editor.QuarterPropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
@SessionAttributes("importUsersCommand")
public class ImportUsersController {

    @Autowired
    private PasswordEncoder          passwordEncoder;
    @Autowired
    private UserDao                  userDao;
    @Autowired
    private RoleDao                  roleDao;
    @Autowired
    private HighSchoolDao            highSchoolDao;
    @Autowired
    private MajorDao                 majorDao;

    @Autowired
    private MajorPropertyEditor      majorPropertyEditor;
    @Autowired
    private QuarterPropertyEditor    quarterPropertyEditor;
    @Autowired
    private HighSchoolPropertyEditor highSchoolPropertyEditor;

    private Logger                   logger = LoggerFactory
                                                .getLogger( ImportUsersController.class );

    @RequestMapping(value = { "/user/importUsers.html" },
        method = RequestMethod.GET)
    public String SetupForm( ModelMap modelMap )
    {
        ImportUsersCommand importUsersCommand = new ImportUsersCommand();
        modelMap.addAttribute( "importUsersCommand", importUsersCommand );

        return "user/importUsersForm";
    }

    @RequestMapping(value = { "/user/importUsers.html" },
        method = RequestMethod.POST)
    public String submitForm(
        HttpServletRequest request,
        HttpServletResponse response,
        @ModelAttribute("importUsersCommand") ImportUsersCommand importUsersCommand,
        BindingResult result, SessionStatus status,
        @RequestParam("_page") int currentPage, ModelMap modelMap )
    {

        Map<Integer, String> pageForms = new HashMap<Integer, String>();

        pageForms.put( 0, "user/importUsersForm" );
        pageForms.put( 1, "user/importUsersFinish" );

        pageForms.put( 2, "status" );
        if( request.getParameter( "_cancel" ) != null )
        {
            // return to current page view, since user clicked cancel
            status.setComplete();
            return (String) pageForms.get( currentPage );
        }
        else if( request.getParameter( "_finish" ) != null )
        {
            // User is finished
            Role userRole = roleDao.getRoleByName( "ROLE_USER" );
            Role newUserRole = roleDao.getRoleByName( "ROLE_NEWUSER" );
            Role studentRole = roleDao.getRoleByName( "ROLE_STUDENT" );

            List<User> importedUsers = importUsersCommand.getImportedUsers();

            for( int i = 0; i < importedUsers.size(); ++i )
            {
                // check cin
                String cin = importedUsers.get( i ).getCin();
                User user = userDao.getUserByCin( cin );
                if( user != null )
                {
                    importedUsers.set( i, user );
                    continue;
                }
                user = importedUsers.get( i );

                user.setUsername( cin );
                user.setPassword( passwordEncoder.encodePassword( cin, null ) );
                user.setEnabled( false );

                user.getRoles().add( userRole );
                user.getRoles().add( newUserRole );
                user.getRoles().add( studentRole );

                // check high school, if null add new high school
                String highSchoolName = user.getHighSchool().getName();
                if( highSchoolDao.getHighSchoolByName( highSchoolName ) == null )
                {
                    highSchoolDao.saveHighSchool( user.getHighSchool() );
                }
                user.setHighSchool( highSchoolDao
                    .getHighSchoolByName( highSchoolName ) );
                // check major, if null add new major
                Major major = user.getMajor();
                String majorSymbol = major.getSymbol();
                if( majorDao.getMajorBySymbol( majorSymbol ) == null )
                {
                    major.setName( majorSymbol );
                    majorDao.saveMajor( major );
                }
                user.setMajor( majorDao.getMajorBySymbol( majorSymbol ) );

                userDao.saveUser( user );

                logger.info( "User: "
                    + userDao.getUserByUsername( SecurityUtils.getUsername() )
                        .getName() + " imported users" );
            }

            modelMap.addAttribute( "msgTitle", "Import Completed" );
            modelMap.addAttribute( "msg", importedUsers.size()
                + " users imported. " );

            Integer directory = (Integer) request.getSession().getAttribute(
                "directory" );
            if( directory.intValue() == 0 )
            {
                modelMap.addAttribute( "backUrl",
                    "/user/search/student/search.html" );
            }

            if( directory.intValue() == 1 )
            {
                modelMap.addAttribute( "backUrl",
                    "/user/search/advisor/search.html" );
            }
            if( directory.intValue() == 2 )
            {
                modelMap.addAttribute( "backUrl",
                    "/user/search/staff/search.html" );
            }
            if( directory.intValue() == 3 )
            {
                modelMap.addAttribute( "backUrl",
                    "/user/search/user/search.html" );
            }

            status.setComplete();

            return "status";
        }
        else
        {
            // User clicked Next or Back(_target)
            // Extract target page
            int targetPage = WebUtils.getTargetPage( request, "_target",
                currentPage );
            // If targetPage is lesser than current Page , user clicked "back"
            if( targetPage < currentPage ){ return (String) pageForms
                .get( targetPage ); }
            // user clicked 'Next', return target page
            modelMap.addAttribute( importUsersCommand );
            return (String) pageForms.get( targetPage );
        }

    }

    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
            new SimpleDateFormat( "MM/dd/yyyy" ), true ) );
        binder.registerCustomEditor( Major.class, majorPropertyEditor );
        binder.registerCustomEditor( Quarter.class, quarterPropertyEditor );
        binder
            .registerCustomEditor( HighSchool.class, highSchoolPropertyEditor );
    }

}
