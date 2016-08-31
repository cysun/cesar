package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.AppointmentType;
import cesar.model.dao.AppointmentTypeDao;

@Component
public class AppointmentTypePropertyEditor extends PropertyEditorSupport {

    @Autowired
    private AppointmentTypeDao appointmentTypeDao;

    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( appointmentTypeDao.getAppointmentTypeById( Integer
            .valueOf( text ) ) );
    }

    public String getAsText()
    {
        AppointmentType appSection = (AppointmentType) getValue();
        return appSection != null ? appSection.getId().toString() : "";
    }
}
