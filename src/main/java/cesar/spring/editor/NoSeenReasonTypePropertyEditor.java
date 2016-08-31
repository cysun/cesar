package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.NoSeenReasonType;
import cesar.model.dao.NoSeenReasonTypeDao;

@Component
public class NoSeenReasonTypePropertyEditor extends PropertyEditorSupport {

    @Autowired
    private NoSeenReasonTypeDao noSeenReasonTypeDao;

    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( noSeenReasonTypeDao.getNoSeenReasonTypeById( Integer
            .valueOf( text ) ) );
    }

    public String getAsText()
    {
        NoSeenReasonType noSeenReasonType = (NoSeenReasonType) getValue();
        return noSeenReasonType != null ? noSeenReasonType.getId().toString()
            : "";
    }
}
