package cesar.model.dao;

import java.util.List;

import cesar.model.AppointmentType;

public interface AppointmentTypeDao {

    public AppointmentType getAppointmentTypeById( Integer id );

    public List<AppointmentType> getAppointmentTypes();
}
