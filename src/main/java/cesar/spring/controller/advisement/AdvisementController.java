package cesar.spring.controller.advisement;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cesar.model.Advisement;
import cesar.model.User;
import cesar.model.dao.AdvisementDao;
import cesar.model.dao.UserDao;
import cesar.spring.security.SecurityUtils;

@Controller
public class AdvisementController {

    @Autowired
    private UserDao       userDao;
    @Autowired
    private AdvisementDao advisementDao;

    private Logger        logger = LoggerFactory
                                     .getLogger( AdvisementController.class );

    @RequestMapping(value = ("/user/advisement/addAdvisement.html"),
        method = RequestMethod.POST)
    protected String handleRequestInternal( Integer userId, String comment,
        Boolean forAdvisorOnly )
    {

        if( StringUtils.hasText( comment ) )
        {
            User advisor = userDao.getUserByUsername( SecurityUtils
                .getUsername() );
            User student = userDao.getUserById( Integer.valueOf( userId ) );
            Advisement advisement = new Advisement( student, advisor, comment );
            if( forAdvisorOnly != null ) advisement.setForAdvisorOnly( true );
            advisementDao.saveAdvisement( advisement );
            logger.info( "User : " + advisor.getName()
                + " added an advisement." );

        }

        return "redirect:/user/display.html?userId=" + userId;
    }

    @RequestMapping(value = ("/user/advisement/editAdvisement.html"),
        method = RequestMethod.GET)
    protected String formBackingObject( Integer advisementId, ModelMap model )
        throws Exception
    {
        Advisement advisement = advisementDao.getAdvisementById( advisementId );
        model.addAttribute( advisement );

        return "user/advisement/editAdvisement";

    }

    @RequestMapping(value = ("/user/advisement/editAdvisement.html"),
        method = RequestMethod.POST)
    protected String onSubmit( Advisement advisement, Integer id )
    {
        Advisement updatedAdvisement = advisementDao.getAdvisementById( id );
        User user = userDao.getUserByUsername( SecurityUtils.getUsername() );
        updatedAdvisement.setEditedBy( user );
        updatedAdvisement.setEditDate( new Date() );
        updatedAdvisement.setComment( advisement.getComment() );
        updatedAdvisement.setForAdvisorOnly( advisement.isForAdvisorOnly() );
        advisementDao.saveAdvisement( updatedAdvisement );

        logger.info( "User + " + user.getName() + " edited an advisement" );

        return "redirect:/user/display.html?userId="
            + updatedAdvisement.getStudent().getId();
    }

    @RequestMapping(value = ("/user/advisement/deleteAdvisement.html"),
        method = RequestMethod.GET)
    protected String deleteAdvisement( Integer advisementId )
    {
        Advisement advisement = advisementDao.getAdvisementById( advisementId );
        advisement.setDeleted( true );
        advisementDao.saveAdvisement( advisement );
        logger.info( "User + "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " deleted an advisement" );
        return "redirect:/user/display.html?userId="
            + advisement.getStudent().getId();
    }
}
