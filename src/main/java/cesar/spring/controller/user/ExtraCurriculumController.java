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
import cesar.model.ExtraCurriculumActivityType;
import cesar.model.User;
import cesar.model.dao.ExtraCurriculumActivityDao;
import cesar.model.dao.ExtraCurriculumActivityTypeDao;

import cesar.model.dao.UserDao;
import cesar.spring.controller.storedQuery.StoredQueryController;
import cesar.spring.security.SecurityUtils;

@Controller
@SessionAttributes("extraCurriculum")
public class ExtraCurriculumController {

    @Autowired
    private UserDao                        userDao;
    @Autowired
    private ExtraCurriculumActivityDao     extraCurriculumActivityDao;
    @Autowired
    private ExtraCurriculumActivityTypeDao extraCurriculumActivityTypeDao;

    private Logger                         logger = LoggerFactory
                                                      .getLogger( StoredQueryController.class );

    @RequestMapping(value = ("/addExtraCurriculums.html"),
        method = RequestMethod.POST)
    public String handleRequestInternal( Integer userId, Integer typeId,
        String description, Boolean forProfile )
    {
        if( StringUtils.hasText( description ) )
        {
            User student = userDao.getUserById( userId );
            ExtraCurriculumActivityType extraCurriculumActivityType = extraCurriculumActivityTypeDao
                .getExtraCurriculumActivityTypeById( typeId );
            ExtraCurriculumActivity extraCurriculumActivity = new ExtraCurriculumActivity(
                student, extraCurriculumActivityType, description );
            extraCurriculumActivityDao
                .saveExtraCurriculumActivity( extraCurriculumActivity );

            logger.info( "User: "
                + userDao.getUserByUsername( SecurityUtils.getUsername() )
                    .getName() + "added an extra curriculum." );
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

    @RequestMapping(value = ("/{path}/editExtraCurriculum.html"),
        method = RequestMethod.GET)
    protected String formBackingObject1( Integer extraCurriculumId,
        @PathVariable("path") String path, ModelMap model )
    {
        Boolean forProfile = true;
        if( path.equals( "user" ) ) forProfile = false;
        ExtraCurriculumActivity extraCurriculum = extraCurriculumActivityDao
            .getExtraCurriculumActivityById( extraCurriculumId );
        model.addAttribute( "extraCurriculum", extraCurriculum );
        model.addAttribute( "forProfile", forProfile );

        return "others/editExtraCurriculum";
    }

    @RequestMapping(value = ("/{path}/editExtraCurriculum.html"),
        method = RequestMethod.POST)
    protected String editExtraCurriculumPOSTMETHOD(
        ExtraCurriculumActivity extraCurriculum,
        @PathVariable("path") String path, SessionStatus status )
    {
        Boolean forProfile = true;
        if( path.equals( "user" ) ) forProfile = false;
        ExtraCurriculumActivity updatedExtraCurriculumActivity = extraCurriculumActivityDao
            .getExtraCurriculumActivityById( extraCurriculum.getId() );
        updatedExtraCurriculumActivity.setDescription( extraCurriculum
            .getDescription() );
        extraCurriculumActivityDao
            .saveExtraCurriculumActivity( updatedExtraCurriculumActivity );
        status.setComplete();

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " edited an extra curriculum info." );
        if( forProfile == true )
        {
            return "redirect:/profile.html?#tab-other";
        }
        else
        {
            return "redirect:/user/display.html?userId="
                + updatedExtraCurriculumActivity.getStudent().getId()
                + "&#tab-other";
        }

    }

    @RequestMapping(value = ("/{path}/deleteExtraCurriculum.html"),
        method = RequestMethod.GET)
    protected String deleteExtraCurriculumGETMethod( Integer extraCurriculumId,
        @PathVariable("path") String path, ModelMap model )
    {
        ExtraCurriculumActivity extraCurriculumActivity = extraCurriculumActivityDao
            .getExtraCurriculumActivityById( extraCurriculumId );

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " deleted an extra curriculum information." );

        Boolean forProfile = true;

        if( path.equals( "user" ) )
        {
            forProfile = false;
        }
        extraCurriculumActivityDao
            .deleteExtraCurriculumActivity( extraCurriculumActivity );

        if( forProfile == true )
        {
            return "redirect:/profile.html?#tab-other";
        }
        else
        {
            return "redirect:/user/display.html?userId="
                + extraCurriculumActivity.getStudent().getId() + "&#tab-other";
        }

    }
}
