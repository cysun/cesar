package cesar.spring.controller.service;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import cesar.model.ServiceType;
import cesar.model.dao.ServiceTypeDao;

@Controller
public class ServiceTypeController {

    @Autowired
    private ServiceTypeDao serviceTypeDao;

    @RequestMapping(value = { "service/viewServiceTypes.html" },
        method = RequestMethod.GET)
    protected String viewServiceTypes( ModelMap model )
    {
        List<ServiceType> serviceTypes = serviceTypeDao.getServiceTypes();

        model.addAttribute( "serviceTypes", serviceTypes );

        return "service/viewServiceTypes";
    }

    @RequestMapping(value = { "service/addServiceType.html" },
        method = RequestMethod.POST)
    protected String addServiceType( ModelMap model, String serviceTypeName )
    {
        ServiceType st = new ServiceType();
        st.setName( serviceTypeName );

        serviceTypeDao.addServiceType( st );

        return "redirect:viewServiceTypes.html";
    }

    @RequestMapping(value = { "service/deleteServiceType.html" },
        method = RequestMethod.GET)
    protected String deleteServiceType( ModelMap model, Integer id )
    {
        ServiceType st = serviceTypeDao.getServiceTypeById( id );

        st.setDeleted( true );

        serviceTypeDao.addServiceType( st );

        return "redirect:viewServiceTypes.html";
    }
}
