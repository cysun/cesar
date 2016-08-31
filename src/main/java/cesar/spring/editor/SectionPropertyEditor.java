package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.Section;
import cesar.model.dao.SectionDao;

@Component
public class SectionPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private SectionDao sectionDao;

    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( sectionDao.getSectionById( Integer.valueOf( text ) ) );
    }

    public String getAsText()
    {
        Section section = (Section) getValue();
        return section != null ? section.getId().toString() : "";
    }

}
