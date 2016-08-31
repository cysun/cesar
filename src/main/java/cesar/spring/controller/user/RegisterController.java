package cesar.spring.controller.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import cesar.model.Role;
import cesar.model.User;
import cesar.model.dao.RoleDao;
import cesar.model.dao.UserDao;
import cesar.spring.controller.storedQuery.StoredQueryController;

@Controller
public class RegisterController {

    @Autowired
    private UserDao           userDao;
    @Autowired
    private RoleDao           roleDao;
    @Autowired
    private PasswordEncoder   passwordEncoder;
    @Autowired
    private MailSender        mailSender;
    @Autowired
    private SimpleMailMessage mailMessage;

    private Logger            logger = LoggerFactory
                                         .getLogger( RegisterController.class );

    @RequestMapping(value = "/register.html", method = RequestMethod.GET)
    public String setupForm( ModelMap model, String confirmId )
    {
        if( confirmId == null )
        {
            return "register/checkCIN";
        }
        else
        {
            User user = userDao.getUserByConfirmId( confirmId );
            if( user == null )
            {
                model.addAttribute( "error", "Register Failed" );
                model.addAttribute( "errorCause", "Invalid link" );
                model.addAttribute( "backUrl", "/" );
                return "error";
            }
            else
            {
                user.setEnabled( true );
                userDao.saveUser( user );

                logger.info( "User:" + user.getName()
                    + " has registered successfully( enabled)." );

                model.addAttribute( "msgTitle", "Status" );
                model.addAttribute( "msg", "Register successfully" );
                model.addAttribute( "backUrl", "/" );
                return "status";
            }
        }
    }

    @RequestMapping(value = "/register.html", method = RequestMethod.POST)
    public String submitForm( HttpServletRequest request,
        HttpServletResponse response, ModelMap model, String cin, User user,
        BindingResult result, SessionStatus status,
        @RequestParam("_page") int currentPage, Integer id )
    {
        String confirmId = "" + (int) (Math.random() * 1000000);
        if( currentPage == 0 )
        {
            User exsitUser = userDao.getUserByCin( cin );

            if( exsitUser == null )
            {
                User newUser = new User();
                newUser.setCin( cin );
                model.addAttribute( "user", newUser );
                return "register/registerWithoutAccount";
            }
            else
            {
                if( exsitUser.isEnabled() == true )
                {
                    model
                        .addAttribute( "error", "This CIN has been registered" );
                    return "register/checkCIN";
                }
                else
                {
                    model.addAttribute( "user", exsitUser );
                    return "register/registerWithAccount";
                }
            }
        }
        else if( currentPage == 1 )
        {
            User usernameUser = userDao.getUserByUsername( user.getUsername() );
            if( usernameUser != null )
            {
                String usernameError = "The user name: " + user.getUsername()
                    + " has been registered.";
                model.addAttribute( "usernameError", usernameError );
                model.addAttribute( "user", user );
                return "register/registerWithoutAccount";
            }
            String password = user.getPassword1();
            if( StringUtils.hasText( password ) )
                user.setPassword( passwordEncoder.encodePassword( password,
                    null ) );

            Role userRole = roleDao.getRoleByName( "ROLE_USER" );
            Role newUserRole = roleDao.getRoleByName( "ROLE_NEWUSER" );
            Role studentRole = roleDao.getRoleByName( "ROLE_STUDENT" );

            user.getRoles().add( userRole );
            user.getRoles().add( newUserRole );
            user.getRoles().add( studentRole );

            user.setConfirmId( confirmId );
            userDao.saveUser( user );

            logger.info( "User: " + user + " registered the user info" );

        }
        else if( currentPage == 2 )
        {
            User usernameUser = userDao.getUserByUsername( user.getUsername() );
            if( usernameUser != null )
            {
                if(usernameUser.isEnabled())
                {
                    String usernameError = "The user name: " + user.getUsername()
                        + " has been registered.";
                    model.addAttribute( "usernameError", usernameError );
                    model.addAttribute( "user", user );
                    return "register/registerWithAccount";
                }
            }
            User updatedUser = userDao.getUserById( id );
            updatedUser.setUsername( user.getUsername() );
            String password = user.getPassword1();
            if( StringUtils.hasText( password ) )
                updatedUser.setPassword( passwordEncoder.encodePassword(
                    password, null ) );
            user.setEmail( updatedUser.getEmail() );

            updatedUser.setConfirmId( confirmId );

            userDao.saveUser( updatedUser );
            logger.info( "User: " + updatedUser + " registered the user info" );

        }

        List<User> recipients = new ArrayList<User>();
        recipients.add( user );
        mailMessage.setSubject( "CESAR register" );
        mailMessage
            .setText( "Click following link to activate your account http://localhost:8080/cesar/register.html?confirmId="
                + confirmId );

        boolean errorOccurred = false;
        List<String> failedAddresses = new ArrayList<String>();
        for( User recipient : recipients )
        {
            mailMessage.setTo( recipient.getEmail() );
            try
            {
                mailSender.send( mailMessage );
            }
            catch( MailException e )
            {
                errorOccurred = true;
                failedAddresses.add( recipient.getEmail() + " ("
                    + recipient.getName() + ")" );
                e.printStackTrace();
            }
        }

        if( !errorOccurred )
        {
            model.addAttribute( "msgTitle", "Status" );
            model.addAttribute( "msg",
                "Your register request has been sent. You will receive the activate "
                    + "link by email shortly." );
            model.addAttribute( "backUrl", "/" );
            return "status";
        }
        else
        {
            model.addAttribute( "failedAddresses", failedAddresses );
            return "exception/email";
        }

    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
            new SimpleDateFormat( "MM/dd/yyyy" ), true ) );
    }
}
