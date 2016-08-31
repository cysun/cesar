package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.ServiceType;
import cesar.model.dao.ServiceTypeDao;

@Component
public class ServiceTypePropertyEditor extends PropertyEditorSupport {

    @Autowired
    private ServiceTypeDao ServiceTypeDao;

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( ServiceTypeDao.getServiceTypeById( Integer.valueOf( text ) ) );
    }

    @Override
    public String getAsText()
    {
        ServiceType serviceType = (ServiceType) getValue();
        return serviceType != null ? serviceType.getId().toString() : "";
    }

}
