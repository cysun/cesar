package cesar.spring.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cesar.model.WeekDay;
import cesar.model.dao.WeekDayDao;

@Component
public class WeekDayPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private WeekDayDao weekDayDao;

    public void setAsText( String text ) throws IllegalArgumentException
    {
        setValue( weekDayDao.getWeekDayById( Integer.valueOf( text ) ) );
    }

    public String getAsText()
    {
        WeekDay weekDay = (WeekDay) getValue();
        return weekDay != null ? weekDay.getId().toString() : "";

    }
}
