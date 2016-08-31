package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Major;
import cesar.model.Quarter;
import cesar.model.Schedule;
import cesar.model.dao.ScheduleDao;

@Repository("scheduleDao")
@SuppressWarnings({ "rawtypes" })
public class ScheduleDaoImpl implements ScheduleDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public Schedule getScheduleById( Integer id )
    {
        return (Schedule) hibernateTemplate.get( Schedule.class, id );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Schedule> getSchedulesByMajor( Major major )
    {
        return (List<Schedule>) hibernateTemplate.find( "from Schedule where"
            + " major= ? order by quarter asc", major );

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Schedule> getSchedulesByQuarter( Quarter quarter )
    {
        return (List<Schedule>) hibernateTemplate.find( "from Schedule where"
            + " quarter= ? ", quarter );

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveSchedule( Schedule schedule )
    {

        hibernateTemplate.merge( schedule );

    }

    public Schedule getScheduleByMajorAndQuarter( Quarter quarter, Major major )
    {
        List results = hibernateTemplate.find(
            "from Schedule where quarter = ?  and major = ?", quarter, major );
        return results.size() == 0 ? null : (Schedule) results.get( 0 );
    }
}
