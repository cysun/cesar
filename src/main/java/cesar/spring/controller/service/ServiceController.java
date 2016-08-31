package cesar.spring.controller.service;

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

import cesar.model.Service;
import cesar.model.ServiceType;

import cesar.model.dao.ServiceDao;
import cesar.model.dao.ServiceTypeDao;
import cesar.model.dao.UserDao;

import cesar.spring.editor.ServiceTypePropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
public class ServiceController {

    @Autowired
    private ServiceDao                serviceDao;

    @Autowired
    private ServiceTypeDao            serviceTypeDao;

    @Autowired
    private UserDao                   userDao;

    @Autowired
    private ServiceTypePropertyEditor serviceTypePropertyEditor;

    private Logger                    logger = LoggerFactory
                                                 .getLogger( ServiceController.class );

    @RequestMapping(value = { "service/addService.html" },
        method = RequestMethod.GET)
    protected String addServiceGet( ModelMap model )
    {
        List<ServiceType> serviceTypes = serviceTypeDao.getServiceTypes();
        Service service = new Service();

        model.addAttribute( "serviceTypes", serviceTypes );
        model.addAttribute( "service", service );

        return "service/addService";
    }

    @RequestMapping(value = { "service/addService.html" },
        method = RequestMethod.POST)
    protected String addServicePOST( ModelMap model, Service service, String cin )
    {

        serviceDao.saveService( service );

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " added a service ." );

        model.addAttribute( "msgTitle", "Status" );
        model.addAttribute( "msg", "Service record has been added" );
        model.addAttribute( "backUrl", "/" );
        return "status";

    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( ServiceType.class,
            serviceTypePropertyEditor );
    }
}
