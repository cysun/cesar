package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.ReasonForAppointment;
import cesar.model.dao.ReasonForAppointmentDao;

@Component
public class ReasonForAppointmentPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private ReasonForAppointmentDao reasonForAppointmentDao;

    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( reasonForAppointmentDao.getReasonForAppointmentById( Integer
            .valueOf( text ) ) );
    }

    public String getAsText()
    {
        ReasonForAppointment reasonForAppointment = (ReasonForAppointment) getValue();
        return reasonForAppointment != null ? reasonForAppointment.getId()
            .toString() : "";
    }
}
