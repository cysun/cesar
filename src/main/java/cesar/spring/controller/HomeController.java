package cesar.spring.controller;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import cesar.model.Role;
import cesar.model.User;
import cesar.model.dao.UserDao;
import cesar.spring.controller.advisement.AdvisementController;
import cesar.spring.security.SecurityUtils;

@Controller
public class HomeController {

    @Autowired
    private UserDao userDao;

    private Logger  logger = LoggerFactory.getLogger( HomeController.class );

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String handleRequestInternal( ModelMap model )
    {
        if( !SecurityUtils.isAnonymousUser() )
        {
            String username = SecurityUtils.getUsername();
            User user = userDao.getUserByUsername( username );

            logger.info( "User " + user.getName() + " logged in." );

            Set<Role> roles = user.getRoles();
            Iterator<Role> roleIterator = roles.iterator();
            while( roleIterator.hasNext() )
            {
                Role role = roleIterator.next();
                if( role.getName().equals( "ROLE_NEWUSER" ) )
                {
                    model.addAttribute( "user", user );
                    user.getRoles().remove( role );
                    userDao.saveUser( user );
                    return "redirect:/profile.html";
                }
            }
        }

        return "home";
    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
            new SimpleDateFormat( "MM/dd/yyyy" ), true ) );
    }

}
