package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Quarter;
import cesar.model.ScheduleTable;
import cesar.model.dao.ScheduleTableDao;

@Repository("scheduleTableDao")
public class ScheduleTableDaoImpl implements ScheduleTableDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public ScheduleTable getScheduleTableById( Integer id )
    {
        return (ScheduleTable) hibernateTemplate.get( ScheduleTable.class, id );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void save( ScheduleTable scheduleTable )
    {
        hibernateTemplate.saveOrUpdate( scheduleTable );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ScheduleTable> getAllScheduleTables()
    {
        return (List<ScheduleTable>) hibernateTemplate
            .find( "from ScheduleTable" );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ScheduleTable> getScheduleTablesByQuarter( Quarter quarter )
    {
        return (List<ScheduleTable>) hibernateTemplate.find(
            "from ScheduleTable where quarter = ? ", quarter );
    }

    @Override
    public ScheduleTable getLatestScheduleTable()
    {
        List results = hibernateTemplate
            .find( "from ScheduleTable order by createdDate desc" );
        return results.size() == 0 ? null : (ScheduleTable) results.get( 0 );
    }

}
