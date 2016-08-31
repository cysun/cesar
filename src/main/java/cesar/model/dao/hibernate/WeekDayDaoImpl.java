package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cesar.model.AppointmentSection;
import cesar.model.WeekDay;
import cesar.model.dao.WeekDayDao;

@Repository("weekDayDao")
public class WeekDayDaoImpl implements WeekDayDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public WeekDay getWeekDayById( Integer id )
    {
        return (WeekDay) hibernateTemplate.get( WeekDay.class, id );
    }

    @Override
    public WeekDay getWeekDayByCode( Integer code )
    {
        List<WeekDay> results = hibernateTemplate.find(
            "from WeekDay where code = ?", code );
        return results.size() == 0 ? null : (WeekDay) results.get( 0 );
    }

    @Override
    public WeekDay getWeekDayByName( String name )
    {
        List<WeekDay> results = hibernateTemplate.find(
            "from WeekDay where name = ?", name );
        return results.size() == 0 ? null : (WeekDay) results.get( 0 );
    }

    @Override
    public WeekDay getWeekDayBySymbol( String symbol )
    {
        List<WeekDay> results = hibernateTemplate.find(
            "from WeekDay where symbol = ? ", symbol );
        return results.size() == 0 ? null : (WeekDay) results.get( 0 );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WeekDay> getWeekdays()
    {
        return (List<WeekDay>) hibernateTemplate
            .find( "from WeekDay order by code asc" );
    }

}
