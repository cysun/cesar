package cesar.spring.controller.visitReason;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import cesar.model.VisitReasonType;
import cesar.model.dao.VisitReasonTypeDao;

@Controller
public class VisitReasonTypeController {

    @Autowired
    private VisitReasonTypeDao visitReasonTypeDao;

    @RequestMapping(value = { "visitReason/viewVisitReasonTypes.html" },
        method = RequestMethod.GET)
    protected String viewVisitReasonTypes( ModelMap model )
    {
        List<VisitReasonType> visitReasonTypes = visitReasonTypeDao
            .getVisitReasonTypes();

        model.addAttribute( "visitReasonTypes", visitReasonTypes );

        return "visitReason/viewVisitReasonTypes";
    }

    @RequestMapping(value = { "visitReason/addVisitReasonType.html" },
        method = RequestMethod.POST)
    protected String addVisitReasonType( ModelMap model,
        String visitReasonTypeName )
    {
        VisitReasonType st = new VisitReasonType();
        st.setName( visitReasonTypeName );

        visitReasonTypeDao.addVisitReasonType( st );

        return "redirect:viewVisitReasonTypes.html";
    }

    @RequestMapping(value = { "visitReason/deleteVisitReasonType.html" },
        method = RequestMethod.GET)
    protected String deleteVisitReasonType( ModelMap model, Integer id )
    {
        VisitReasonType nsrt = visitReasonTypeDao.getVisitReasonTypeById( id );

        nsrt.setDeleted( true );

        visitReasonTypeDao.addVisitReasonType( nsrt );

        return "redirect:viewVisitReasonTypes.html";
    }
}
