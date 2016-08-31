package cesar.model.dao;

import java.util.List;

import cesar.model.VisitReasonType;

public interface VisitReasonTypeDao {

    public VisitReasonType getVisitReasonTypeById( Integer id );

    public List<VisitReasonType> getVisitReasonTypes();

    public void addVisitReasonType( VisitReasonType visitReasonType );

    public void deleteVisitReasonType( VisitReasonType visitReasonType );

}
