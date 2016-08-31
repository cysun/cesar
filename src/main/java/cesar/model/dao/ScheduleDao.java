package cesar.model.dao;

import java.util.List;

import cesar.model.Major;
import cesar.model.Quarter;
import cesar.model.Schedule;

public interface ScheduleDao {

    public Schedule getScheduleById( Integer id );

    public List<Schedule> getSchedulesByMajor( Major major );

    public Schedule getScheduleByMajorAndQuarter( Quarter quarter, Major major );

    public List<Schedule> getSchedulesByQuarter( Quarter quarter );

    public void saveSchedule( Schedule schedule );

}
