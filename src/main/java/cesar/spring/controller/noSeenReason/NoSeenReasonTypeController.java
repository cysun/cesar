package cesar.spring.controller.noSeenReason;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import cesar.model.NoSeenReasonType;
import cesar.model.dao.NoSeenReasonTypeDao;

@Controller
public class NoSeenReasonTypeController {

    @Autowired
    private NoSeenReasonTypeDao noSeenReasonTypeDao;

    @RequestMapping(value = { "noSeenReason/viewNoSeenReasonTypes.html" },
        method = RequestMethod.GET)
    protected String viewNoSeenReasonTypes( ModelMap model )
    {
        List<NoSeenReasonType> noSeenReasonTypes = noSeenReasonTypeDao
            .getNoSeenReasonTypes();

        model.addAttribute( "noSeenReasonTypes", noSeenReasonTypes );

        return "noSeenReason/viewNoSeenReasonTypes";
    }

    @RequestMapping(value = { "noSeenReason/addNoSeenReasonType.html" },
        method = RequestMethod.POST)
    protected String addNoSeenReasonType( ModelMap model,
        String noSeenReasonTypeName )
    {
        NoSeenReasonType st = new NoSeenReasonType();
        st.setName( noSeenReasonTypeName );

        noSeenReasonTypeDao.addNoSeenReasonType( st );

        return "redirect:viewNoSeenReasonTypes.html";
    }

    @RequestMapping(value = { "noSeenReason/deleteNoSeenReasonType.html" },
        method = RequestMethod.GET)
    protected String deleteNoSeenReasonType( ModelMap model, Integer id )
    {
        NoSeenReasonType nsrt = noSeenReasonTypeDao
            .getNoSeenReasonTypeById( id );

        nsrt.setDeleted( true );

        noSeenReasonTypeDao.addNoSeenReasonType( nsrt );

        return "redirect:viewNoSeenReasonTypes.html";
    }
}
