package cesar.spring.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cesar.model.User;
import cesar.model.dao.UserDao;

@Controller
public class ResetPasswordController {

    @Autowired
    private UserDao           userDao;
    @Autowired
    private PasswordEncoder   passwordEncoder;
    @Autowired
    private MailSender        mailSender;
    @Autowired
    private SimpleMailMessage mailMessage;

    private Logger            logger = LoggerFactory
                                         .getLogger( ResetPasswordController.class );

    @RequestMapping(value = "/forgotPassword.html", method = RequestMethod.GET)
    protected String forgetPasswordGET( ModelMap model )
    {
        return "login";
    }

    @RequestMapping(value = "/resetPassword.html", method = RequestMethod.POST)
    protected String onSubmit( ModelMap model, String username, String cin,
        String email ) throws Exception
    {
        User user;
        if( StringUtils.hasText( cin ) )
            user = userDao.getUserByCin( cin );
        else if( StringUtils.hasText( username ) )
            user = userDao.getUserByUsername( username );
        else
            user = userDao.getUserByEmail( email );

        if( user == null )
        {
            model.addAttribute( "error", "Password Reset Failed" );
            model.addAttribute( "errorCause", "The user is not in the system." );
            model.addAttribute( "backUrl", "/login.html" );
            return "error";
        }

        if( user.isEnabled() == false )
        {
            model.addAttribute( "error", "Password Reset Failed" );
            model.addAttribute( "errorCause", "Please register using your CIN" );
            model.addAttribute( "backUrl", "/login.html" );
            return "error";
        }
        String newPassword = "" + (int) (Math.random() * 1000000);
        user.setPassword( passwordEncoder.encodePassword( newPassword, null ) );

        userDao.saveUser( user );
        logger.info( "User: " + user.getName() + " reset the password. " );

        List<User> recipients = new ArrayList<User>();
        recipients.add( user );

        mailMessage.setSubject( "CESAR Password Reset" );
        mailMessage.setText( "username: " + user.getUsername() + "\npassword: "
            + newPassword );

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
            String msg = "Your password has been reset. You will receive your "
                + "new password by email shortly.";
            model.addAttribute( "msgTitle", "Status" );
            model.addAttribute( "msg", msg );
            model.addAttribute( "backUrl", "/" );
            return "status";
        }
        else
        {
            model.addAttribute( "failedAddresses", failedAddresses );
            return "exception/email";
        }
    }

}
