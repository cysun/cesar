package cesar.spring.editor;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.springframework.stereotype.Component;

import cesar.model.Quarter;

@Component
public class QuarterPropertyEditor extends PropertyEditorSupport {

    @SuppressWarnings("deprecation")
    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        if( text == null || text.equals( "" ) )
        {
            // do nothing
        }
        else if( text
            .matches( "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)" ) )
        {
            setValue( new Quarter( new Date( text ) ) );
        }
        else
        {
            String[] str = text.split( " " );
            setValue( new Quarter( Integer.parseInt( str[1] ), str[0] ) );
        }
    }

    public String getAsText()
    {
        Quarter quarter = (Quarter) getValue();
        return quarter != null ? quarter.toString() : "";
    }

}
