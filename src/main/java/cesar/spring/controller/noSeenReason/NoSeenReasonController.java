package cesar.spring.controller.noSeenReason;

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

import cesar.model.NoSeenReason;
import cesar.model.NoSeenReasonType;

import cesar.model.dao.NoSeenReasonDao;
import cesar.model.dao.NoSeenReasonTypeDao;
import cesar.model.dao.UserDao;
import cesar.spring.editor.NoSeenReasonTypePropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
public class NoSeenReasonController {

    @Autowired
    private NoSeenReasonDao                noSeenReasonDao;

    @Autowired
    private NoSeenReasonTypeDao            noSeenReasonTypeDao;

    @Autowired
    private UserDao                        userDao;

    private Logger                         logger = LoggerFactory
                                                      .getLogger( NoSeenReason.class );

    @Autowired
    private NoSeenReasonTypePropertyEditor noSeenReasonTypePropertyEditor;

    @RequestMapping(value = { "noSeenReason/addNoSeenReason.html" },
        method = RequestMethod.GET)
    protected String addNoSeenReasonGet( ModelMap model )
    {
        List<NoSeenReasonType> noSeenReasonTypes = noSeenReasonTypeDao
            .getNoSeenReasonTypes();
        NoSeenReason noSeenReason = new NoSeenReason();

        model.addAttribute( "noSeenReasonTypes", noSeenReasonTypes );
        model.addAttribute( "noSeenReason", noSeenReason );

        return "noSeenReason/addNoSeenReason";
    }

    @RequestMapping(value = { "noSeenReason/addNoSeenReason.html" },
        method = RequestMethod.POST)
    protected String addNoSeenReasonPOST( ModelMap model,
        NoSeenReason noSeenReason, String cin )
    {

        noSeenReasonDao.saveNoSeenReason( noSeenReason );

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " added a no seen reason record" );
        model.addAttribute( "msgTitle", "Status" );
        model.addAttribute( "msg", "NoSeenReason record has been added" );
        model.addAttribute( "backUrl", "/" );
        return "status";

    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( NoSeenReasonType.class,
            noSeenReasonTypePropertyEditor );
    }
}
