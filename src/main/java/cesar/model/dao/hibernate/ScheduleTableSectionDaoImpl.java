package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cesar.model.ScheduleTable;
import cesar.model.ScheduleTableSection;
import cesar.model.dao.ScheduleTableSectionDao;

@Repository("ScheduleTableSectionDao")
public class ScheduleTableSectionDaoImpl implements ScheduleTableSectionDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public ScheduleTableSection getScheduleTableSectionById( Integer id )
    {
        return (ScheduleTableSection) hibernateTemplate.get(
            ScheduleTableSection.class, id );
    }

    @Override
    public ScheduleTableSection getScheduleTableSectionByStartTime(
        Integer startTime )
    {
        @SuppressWarnings("unchecked")
        List<ScheduleTableSection> result = hibernateTemplate.find(
            "from ScheduleTableSection where startTime = ? ", startTime );
        return result.size() == 0 ? null : (ScheduleTableSection) result
            .get( 0 );
    }

}
