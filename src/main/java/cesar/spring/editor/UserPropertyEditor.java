package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.User;
import cesar.model.dao.UserDao;

@Component
public class UserPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private UserDao userDao;

    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( userDao.getUserById( Integer.valueOf( text ) ) );
    }

    public String getAsText()
    {
        User user = (User) getValue();
        return user != null ? user.getId().toString() : "";
    }
}
