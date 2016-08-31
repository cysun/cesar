package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.AppointmentSection;
import cesar.model.dao.AppointmentSectionDao;

@Component
public class AppointmentSectionPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private AppointmentSectionDao appointmentSectionDao;

    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( appointmentSectionDao.getAppointmentById( Integer
            .valueOf( text ) ) );
    }

    public String getAsText()
    {
        AppointmentSection appSection = (AppointmentSection) getValue();
        return appSection != null ? appSection.getId().toString() : "";
    }
}
