package cesar.model.dao;

import java.util.List;

import cesar.model.Quarter;
import cesar.model.QuarterPlan;

public interface QuarterPlanDao {

	public List<QuarterPlan> getQuarterPlanByQuarter( Quarter quarter );
	
	public void saveQuarterPlan( QuarterPlan quarterPlan );
}
