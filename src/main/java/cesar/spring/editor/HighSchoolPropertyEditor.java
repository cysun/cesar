package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.HighSchool;
import cesar.model.dao.HighSchoolDao;

@Component
public class HighSchoolPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private HighSchoolDao highSchoolDao;

    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( highSchoolDao.getHighSchoolById( Integer.valueOf( text ) ) );
    }

    public String getAsText()
    {
        HighSchool highSchool = (HighSchool) getValue();
        return highSchool != null ? highSchool.getId().toString() : "";
    }
}
