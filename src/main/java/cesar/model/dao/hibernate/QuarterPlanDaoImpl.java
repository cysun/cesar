package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Quarter;
import cesar.model.QuarterPlan;
import cesar.model.dao.QuarterPlanDao;

@Repository("QuarterPlanDao")
public class QuarterPlanDaoImpl implements QuarterPlanDao {

	@Autowired
    private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
    @Override
    public List<QuarterPlan> getQuarterPlanByQuarter( Quarter quarter )
    {
        return (List<QuarterPlan>) hibernateTemplate.find(
            "from QuarterPlan where quarter = ?", quarter );
        
    }
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveQuarterPlan( QuarterPlan quarterPlan )
    {
        hibernateTemplate.merge( quarterPlan );

    }
	
}
