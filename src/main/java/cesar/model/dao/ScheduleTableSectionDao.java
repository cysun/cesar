package cesar.model.dao;

import cesar.model.ScheduleTableSection;

public interface ScheduleTableSectionDao {

    public ScheduleTableSection getScheduleTableSectionById( Integer id );

    public ScheduleTableSection getScheduleTableSectionByStartTime(
        Integer startTime );

}
