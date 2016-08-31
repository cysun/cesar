package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.VisitReasonType;
import cesar.model.dao.VisitReasonTypeDao;

@Component
public class VisitReasonTypePropertyEditor extends PropertyEditorSupport {

    @Autowired
    private VisitReasonTypeDao VisitReasonTypeDao;

    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( VisitReasonTypeDao.getVisitReasonTypeById( Integer
            .valueOf( text ) ) );
    }

    public String getAsText()
    {
        VisitReasonType visitReasonType = (VisitReasonType) getValue();
        return visitReasonType != null ? visitReasonType.getId().toString()
            : "";
    }
}
