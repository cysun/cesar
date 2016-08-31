package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import cesar.model.Major;
import cesar.model.Service;
import cesar.model.ServiceType;
import cesar.model.dao.ServiceTypeDao;

@Repository("serviceTypeDao")
public class ServiceTypeDaoImpl implements ServiceTypeDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public ServiceType getServiceTypeById( Integer id )
    {
        return (ServiceType) hibernateTemplate.get( ServiceType.class, id );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ServiceType> getServiceTypes()
    {
        return (List<ServiceType>) hibernateTemplate
            .find( "from ServiceType where deleted = false" );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addServiceType( ServiceType serviceType )
    {
        hibernateTemplate.saveOrUpdate( serviceType );

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteServiceType( ServiceType serviceType )
    {
        hibernateTemplate.delete( serviceType );

    }

}
