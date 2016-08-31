package cesar.model.dao;

import java.util.List;

import cesar.model.Quarter;
import cesar.model.ScheduleTable;

public interface ScheduleTableDao {

    public ScheduleTable getScheduleTableById( Integer id );

    public void save( ScheduleTable scheduleTable );

    public List<ScheduleTable> getAllScheduleTables();

    public List<ScheduleTable> getScheduleTablesByQuarter( Quarter quarter );

    public ScheduleTable getLatestScheduleTable();

}
