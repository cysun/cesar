package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.Ethnicity;
import cesar.model.dao.EthnicityDao;

@Component
public class EthnicityPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private EthnicityDao ethnicityDao;

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( ethnicityDao.getEthnicityById( Integer.valueOf( text ) ) );
    }

    @Override
    public String getAsText()
    {
        Ethnicity ethnicity = (Ethnicity) getValue();
        return ethnicity != null ? ethnicity.getId().toString() : "";
    }
}
