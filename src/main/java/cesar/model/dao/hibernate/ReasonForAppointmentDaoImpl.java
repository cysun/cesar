package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cesar.model.ReasonForAppointment;
import cesar.model.dao.ReasonForAppointmentDao;

@Repository("reasonForAppointmentDao")
public class ReasonForAppointmentDaoImpl implements ReasonForAppointmentDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public ReasonForAppointment getReasonForAppointmentById( Integer id )
    {
        return hibernateTemplate.get( ReasonForAppointment.class, id );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ReasonForAppointment> getReasonsForAppointment()
    {
        return hibernateTemplate.find( "from ReasonForAppointment" );
    }

}
