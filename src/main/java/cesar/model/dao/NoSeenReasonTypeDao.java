package cesar.model.dao;

import java.util.List;

import cesar.model.NoSeenReasonType;

public interface NoSeenReasonTypeDao {

    public NoSeenReasonType getNoSeenReasonTypeById( Integer id );

    public List<NoSeenReasonType> getNoSeenReasonTypes();

    public void addNoSeenReasonType( NoSeenReasonType noSeenReasonType );

    public void deleteNoSeenReasonType( NoSeenReasonType noSeenReasonType );
}
