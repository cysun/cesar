package cesar.model.dao;

import java.util.List;

import cesar.model.Quarter;
import cesar.model.VisitReason;

public interface VisitReasonDao {

    public VisitReason getVisitReasonById( Integer id );

    public void saveVisitReason( VisitReason visitReason );
    
    public List<VisitReason> getVisitReasonsByQuarter(Quarter fromQuarter, Quarter toQuarter);

}
