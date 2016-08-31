package cesar.model.dao;

import java.util.List;

import cesar.model.WeekDay;

public interface WeekDayDao {

    public WeekDay getWeekDayById( Integer id );

    public WeekDay getWeekDayByCode( Integer code );

    public WeekDay getWeekDayByName( String name );

    public WeekDay getWeekDayBySymbol( String symbol );

    public List<WeekDay> getWeekdays();
}
