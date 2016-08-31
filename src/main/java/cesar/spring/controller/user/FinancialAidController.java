package cesar.spring.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import cesar.model.ExtraCurriculumActivity;
import cesar.model.FinancialAid;
import cesar.model.FinancialAidType;
import cesar.model.User;
import cesar.model.dao.FinancialAidDao;
import cesar.model.dao.FinancialAidTypeDao;
import cesar.model.dao.UserDao;
import cesar.spring.controller.storedQuery.StoredQueryController;
import cesar.spring.security.SecurityUtils;

@Controller
@SessionAttributes("financialAid")
public class FinancialAidController {

    @Autowired
    private UserDao             userDao;
    @Autowired
    private FinancialAidDao     financialAidDao;
    @Autowired
    private FinancialAidTypeDao financialAidTypeDao;

    private Logger              logger = LoggerFactory
                                           .getLogger( FinancialAidController.class );

    @RequestMapping(value = ("/addFinancialAid.html"),
        method = RequestMethod.POST)
    public String handleRequestInternal( Integer userId, Integer typeId,
        String details, Boolean forProfile )
    {
        if( StringUtils.hasText( details ) )
        {
            User student = userDao.getUserById( userId );
            FinancialAidType financialAidType = financialAidTypeDao
                .getFinancialAidTypeById( typeId );
            FinancialAid financialAid = new FinancialAid( student,
                financialAidType, details );
            financialAidDao.saveFinancialAid( financialAid );
            logger.info( "User: "
                + userDao.getUserByUsername( SecurityUtils.getUsername() )
                    .getName() + " added a financial aid information ." );
        }

        if( forProfile == null )
        {
            return "redirect:/user/display.html?userId=" + userId
                + "&#tab-other";
        }
        else
        {
            return "redirect:/profile.html?#tab-other";
        }
    }

    @RequestMapping(value = ("/{path}/editFinancialAid.html"),
        method = RequestMethod.GET)
    protected String formBackingObject1( Integer financialAidId,
        @PathVariable("path") String path, ModelMap model ) throws Exception
    {
        Boolean forProfile = true;
        if( path.equals( "user" ) ) forProfile = false;

        FinancialAid financialAid = financialAidDao
            .getFinancialAidById( financialAidId );
        model.addAttribute( financialAid );
        model.addAttribute( "forProfile", forProfile );
        return "others/editFinancialAid";
    }

    @RequestMapping(value = ("/{path}/editFinancialAid.html"),
        method = RequestMethod.POST)
    protected String onSubmit( FinancialAid financialAid,
        @PathVariable("path") String path, SessionStatus status )
    {

        financialAidDao.saveFinancialAid( financialAid );

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " edited a financial aid information ." );

        if( path.equals( "user" ) )
            return "redirect:/user/display.html?userId="
                + financialAid.getStudent().getId() + "&#tab-other";
        return "redirect:/profile.html?#tab-other";
    }

    @RequestMapping(value = ("/{path}/deleteFinancialAid.html"),
        method = RequestMethod.GET)
    protected String deleteFinancialAidGETMethod( Integer financialAidId,
        @PathVariable("path") String path, ModelMap model )
    {
        FinancialAid financialAid = financialAidDao
            .getFinancialAidById( financialAidId );

        Boolean forProfile = true;

        if( path.equals( "user" ) ) forProfile = false;

        financialAidDao.deleteFinancialAid( financialAid );

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " deleted a financial aid information ." );

        if( forProfile == true )
        {
            return "redirect:/profile.html?#tab-other";
        }
        else
        {
            return "redirect:/user/display.html?userId="
                + financialAid.getStudent().getId() + "&#tab-other";
        }
    }

}
