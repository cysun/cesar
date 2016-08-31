package cesar.model.dao;

import java.util.List;

import cesar.model.ReasonForAppointment;

public interface ReasonForAppointmentDao {

    public ReasonForAppointment getReasonForAppointmentById( Integer id );

    public List<ReasonForAppointment> getReasonsForAppointment();

}
