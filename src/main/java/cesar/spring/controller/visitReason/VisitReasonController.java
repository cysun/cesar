package cesar.spring.controller.visitReason;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import cesar.model.VisitReason;
import cesar.model.VisitReasonType;
import cesar.model.User;
import cesar.model.dao.VisitReasonDao;
import cesar.model.dao.VisitReasonTypeDao;
import cesar.model.dao.UserDao;
import cesar.spring.controller.user.RegisterController;
import cesar.spring.editor.VisitReasonTypePropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
public class VisitReasonController {

    @Autowired
    private VisitReasonDao                visitReasonDao;

    @Autowired
    private VisitReasonTypeDao            visitReasonTypeDao;

    @Autowired
    private UserDao                       userDao;

    @Autowired
    private VisitReasonTypePropertyEditor visitReasonTypePropertyEditor;

    private Logger                        logger = LoggerFactory
                                                     .getLogger( VisitReasonController.class );

    @RequestMapping(value = { "visitReason/addVisitReason.html" },
        method = RequestMethod.GET)
    protected String addVisitReasonGet( ModelMap model )
    {
        List<VisitReasonType> visitReasonTypes = visitReasonTypeDao
            .getVisitReasonTypes();
        VisitReason visitReason = new VisitReason();

        model.addAttribute( "visitReasonTypes", visitReasonTypes );
        model.addAttribute( "visitReason", visitReason );

        return "visitReason/addVisitReason";
    }

    @RequestMapping(value = { "visitReason/addVisitReason.html" },
        method = RequestMethod.POST)
    protected String addVisitReasonPOST( ModelMap model,
        VisitReason visitReason, String cin )
    {

        visitReasonDao.saveVisitReason( visitReason );

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " add a visit reason." );
        model.addAttribute( "msgTitle", "Status" );
        model.addAttribute( "msg", "Visit reason record has been added" );
        model.addAttribute( "backUrl", "/" );
        return "status";

    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( VisitReasonType.class,
            visitReasonTypePropertyEditor );
    }
}
