package cesar.spring.controller.user;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import cesar.model.GeneratedCin;

import cesar.model.Role;
import cesar.model.User;
import cesar.model.dao.GeneratedCinDao;
import cesar.model.dao.RoleDao;
import cesar.model.dao.UserDao;
import cesar.spring.controller.storedQuery.StoredQueryController;
import cesar.spring.editor.RolePropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
public class AddUserController {

    @Autowired
    private UserDao            userDao;
    @Autowired
    private RoleDao            roleDao;
    @Autowired
    private PasswordEncoder    passwordEncoder;

    @Autowired
    private RolePropertyEditor rolePropertyEditor;
    @Autowired
    private GeneratedCinDao    generatedCinDao;

    private Logger             logger = LoggerFactory
                                          .getLogger( AddUserController.class );

    @RequestMapping(value = { "/user/add.html" }, method = RequestMethod.GET)
    public String setupForm( ModelMap model )
    {
        User user = new User();
        user.getRoles().add( roleDao.getRoleByName( "ROLE_NEWUSER" ) );
        user.getRoles().add( roleDao.getRoleByName( "ROLE_USER" ) );

        model.addAttribute( "user", user );
        model.addAttribute( "roles", roleDao.getAllRoles() );

        return "user/add";
    }

    @RequestMapping(value = { "/user/add.html" }, method = RequestMethod.POST)
    public String processForm( User user, ModelMap model )
    {
        String cin = user.getCin();
        String username = user.getUsername();

        boolean hasError = false;

        String cinError = null;
        String usernameError = null;

        if( userDao.getUserByCin( cin ) != null )
        {
            hasError = true;
            cinError = "The CIN : " + user.getCin() + " has been registered.";

        }

        if( userDao.getUserByUsername( username ) != null )
        {
            hasError = true;
            usernameError = "The user name: " + user.getUsername()
                + " has been registered.";

        }

        if( hasError )
        {
            model.addAttribute( "roles", roleDao.getAllRoles() );
            model.addAttribute( "usernameError", usernameError );
            model.addAttribute( "cinError", cinError );

            return "user/add";

        }

        String password = user.getPassword1();
        user.setPassword( passwordEncoder.encodePassword( password, null ) );
        userDao.saveUser( user );

        User savedUser = userDao.getUserByUsername( user.getUsername() );

        logger.info( "User: " + savedUser.getName() + " added an user" );

        return "redirect:display.html?userId=" + savedUser.getId();
    }

    @RequestMapping(value = { "/generateCin.html" })
    protected String generateCin( HttpServletRequest request,
        HttpServletResponse response ) throws Exception
    {
        GeneratedCin generatedCin = new GeneratedCin();
        generatedCinDao.saveGeneratedCin( generatedCin );

        response.setContentType( "text/plain" );
        response.getWriter().print( generatedCin.getId() );

        return null;
    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Role.class, rolePropertyEditor );
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
            new SimpleDateFormat( "MM/dd/yyyy" ), true ) );
    }

}
