package cesar.model.dao;

import java.util.List;

import cesar.model.ServiceType;

public interface ServiceTypeDao {

    public ServiceType getServiceTypeById( Integer id );

    public List<ServiceType> getServiceTypes();

    public void addServiceType( ServiceType serviceType );

    public void deleteServiceType( ServiceType serviceType );

}
