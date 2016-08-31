package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cesar.model.AppointmentType;
import cesar.model.dao.AppointmentTypeDao;

@Repository("appointmentDao")
public class AppointmentTypeDaoImpl implements AppointmentTypeDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public AppointmentType getAppointmentTypeById( Integer id )
    {
        return (AppointmentType) hibernateTemplate.get( AppointmentType.class,
            id );

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AppointmentType> getAppointmentTypes()
    {
        return (List<AppointmentType>) hibernateTemplate
            .find( "from AppointmentType" );
    }

}
