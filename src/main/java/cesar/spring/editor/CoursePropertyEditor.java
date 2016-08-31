package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.Course;
import cesar.model.dao.CourseDao;

@Component
public class CoursePropertyEditor extends PropertyEditorSupport {

    @Autowired
    private CourseDao courseDao;

    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( courseDao.getCourseById( Integer.valueOf( text ) ) );
    }

    public String getAsText()
    {
        Course course = (Course) getValue();
        return course != null ? course.getId().toString() : "";
    }
}
