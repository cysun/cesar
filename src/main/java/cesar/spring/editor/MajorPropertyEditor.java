package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.Major;
import cesar.model.dao.MajorDao;

@Component
public class MajorPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private MajorDao majorDao;

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( majorDao.getMajorById( Integer.valueOf( text ) ) );
    }

    @Override
    public String getAsText()
    {
        Major major = (Major) getValue();
        return major != null ? major.getId().toString() : "";
    }

}
