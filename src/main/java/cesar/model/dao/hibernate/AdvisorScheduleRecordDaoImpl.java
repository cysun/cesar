package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.AdvisorScheduleRecord;
import cesar.model.dao.AdvisorScheduleRecordDao;

@Repository("advisorScheduleRecordDao")
public class AdvisorScheduleRecordDaoImpl implements AdvisorScheduleRecordDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public AdvisorScheduleRecord getAdvisorScheduleRecordById( Integer id )
    {
        return (AdvisorScheduleRecord) hibernateTemplate.get(
            AdvisorScheduleRecord.class, id );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AdvisorScheduleRecord> gerAdvisorSchedules()
    {
        return (List<AdvisorScheduleRecord>) hibernateTemplate
            .find( "from AdvisorScheduleRecord" );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveAdvisorScheduleRecord( AdvisorScheduleRecord record )
    {
        hibernateTemplate.saveOrUpdate( record );

    }

}
