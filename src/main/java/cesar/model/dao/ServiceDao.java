package cesar.model.dao;

import java.util.List;

import cesar.model.Quarter;
import cesar.model.Service;

public interface ServiceDao {

    public Service getServiceById( Integer id );

    public void saveService( Service service );
    
    public List<Service> getServicesByQuarter(Quarter fromQuarter, Quarter toQuarter);

}
