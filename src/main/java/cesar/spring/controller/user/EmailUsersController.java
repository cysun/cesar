package cesar.spring.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cesar.model.Advisement;
import cesar.model.Email;
import cesar.model.User;
import cesar.model.dao.AdvisementDao;
import cesar.model.dao.UserDao;

import cesar.spring.security.SecurityUtils;

@Controller
public class EmailUsersController {

    @Autowired
    private UserDao           userDao;
    @Autowired
    private MailSender        mailSender;
    @Autowired
    private AdvisementDao     advisementDao;
    @Autowired
    private SimpleMailMessage mailMessage;

    @RequestMapping(value = "/user/email.html", method = RequestMethod.GET)
    public String setupForm( Integer[] userId, String subject, String content,
        ModelMap model )
    {
        Email email = new Email();
        email.setSubject( subject );
        email.setContent( content );
        email.setRecipients( userDao.getUsersById( userId ) );
        model.addAttribute( "email", email );
        return "user/email";
    }

    @RequestMapping(value = "/user/advisement/emailAdvisement.html",
        method = RequestMethod.GET)
    public String formSetup( Integer advisementId, ModelMap model )
    {
        Advisement advisement = advisementDao.getAdvisementById( advisementId );
        Email email = new Email();
        email
            .setAuthor( userDao.getUserByUsername( SecurityUtils.getUsername() ) );
        List<User> recipients = email.getRecipients();
        recipients.add( advisement.getStudent() );
        email.setRecipients( recipients );
        email.setSubject( "CESAR Advisement Record #" + advisement.getId() );

        StringBuffer sb = new StringBuffer();
        sb.append( "Advisor: " ).append( advisement.getAdvisor().getName() );
        if( advisement.getEditDate() == null )
        {
            sb.append( "\nTime: " ).append( advisement.getCreateDate() );
        }
        else
        {
            sb.append( "\nTime: " ).append( advisement.getEditDate() );
        }
        sb.append( "\n\n" ).append( advisement.getComment() );
        email.setContent( sb.toString() );

        model.addAttribute( "email", email );
        model.addAttribute( "advisement", advisement );

        return "/user/advisement/emailAdvisement";

    }

    @RequestMapping(value = { "/user/email.html" }, method = RequestMethod.POST)
    public String emailProcessFrom( Email email, Integer[] userId,
        ModelMap model )
    {
        User user = userDao.getUserByUsername( SecurityUtils.getUsername() );

        email.setRecipients( userDao.getUsersById( userId ) );

        mailMessage.setSubject( email.getSubject() );
        mailMessage.setText( email.getContent() );
        mailMessage.setFrom( user.getEmail() );

        boolean errorOccurred = false;
        List<String> failedAddresses = new ArrayList<String>();
        for( User recipient : email.getRecipients() )
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
            }
        }

        if( !errorOccurred )
        {
            model.addAttribute( "msgTitle", "Status" );
            model.addAttribute( "msg", "The email is sent." );
            model.addAttribute( "backUrl", "/user/search/student/search.html" );
            return "status";
        }
        else
        {
            model.addAttribute( "failedAddresses", failedAddresses );
            return "exception/email";
        }
    }

    @RequestMapping(value = { "/user/advisement/emailAdvisement.html" },
        method = RequestMethod.POST)
    public String processForm( Email email, Integer sendTo, ModelMap model )
    {
        User user = userDao.getUserByUsername( SecurityUtils.getUsername() );

        email.getRecipients().add( userDao.getUserById( sendTo ) );

        mailMessage.setSubject( email.getSubject() );
        mailMessage.setText( email.getContent() );
        mailMessage.setFrom( user.getEmail() );

        boolean errorOccurred = false;
        List<String> failedAddresses = new ArrayList<String>();
        for( User recipient : email.getRecipients() )
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
            }
        }

        if( !errorOccurred )
        {
            model.addAttribute( "msgTitle", "Status" );
            model.addAttribute( "msg", "The email is sent." );
            model.addAttribute( "backUrl", "/user/display.html?userId="
                + email.getRecipients().get( 0 ).getId() );
            return "status";
        }
        else
        {
            model.addAttribute( "failedAddresses", failedAddresses );
            return "exception/email";
        }
    }

}
