package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.HighSchoolProgram;
import cesar.model.dao.HighSchoolProgramDao;

@Component
public class HighSchoolProgramPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private HighSchoolProgramDao highSchoolProgramDao;

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( highSchoolProgramDao.getHighSchoolProgramById( Integer
            .valueOf( text ) ) );
    }

    @Override
    public String getAsText()
    {
        HighSchoolProgram highSchoolProgram = (HighSchoolProgram) getValue();
        return highSchoolProgram != null ? highSchoolProgram.getId().toString()
            : "";
    }

}
