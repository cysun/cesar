package cesar.model.dao;

import java.util.List;

import cesar.model.AdvisorScheduleRecord;

public interface AdvisorScheduleRecordDao {

    public AdvisorScheduleRecord getAdvisorScheduleRecordById( Integer id );

    public List<AdvisorScheduleRecord> gerAdvisorSchedules();

    public void saveAdvisorScheduleRecord( AdvisorScheduleRecord record );
}
