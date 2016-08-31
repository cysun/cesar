package cesar.model.dao;

import java.util.List;

import cesar.model.NoSeenReason;
import cesar.model.Quarter;

public interface NoSeenReasonDao {

    public NoSeenReason getNoSeenReasonById( Integer id );

    public void saveNoSeenReason( NoSeenReason noSeenReason );
    
    public List<NoSeenReason> getNoSeenReasonsByQuarter(Quarter fromQuarter, Quarter toQuarter);

}
