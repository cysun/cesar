package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.Role;
import cesar.model.dao.RoleDao;

@Component
public class RolePropertyEditor extends PropertyEditorSupport {

    @Autowired
    private RoleDao roleDao;

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( roleDao.getRoleById( Integer.valueOf( text ) ) );
    }

    @Override
    public String getAsText()
    {
        Role role = (Role) getValue();
        return role != null ? role.getId().toString() : "";
    }

}
