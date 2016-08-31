package cesar.spring.command;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

import cesar.model.HighSchool;
import cesar.model.Major;
import cesar.model.Quarter;
import cesar.model.User;

public class ImportUsersCommand {

    private String       text;
    private List<User>   importedUsers;
    private List<String> invalidLines;

    public ImportUsersCommand()
    {
        importedUsers = new ArrayList<User>();
        invalidLines = new ArrayList<String>();
    }

    public void setText( String text )
    {
        this.text = text;
        this.parseText();
    }

    public void parseText()
    {
        if( !StringUtils.hasText( text ) )
        {
            return;
        }
        else
        {
            parseList();
        }

    }

    public void parseList()
    {
        importedUsers.clear();
        invalidLines.clear();

        String[] lines = text.split( "\\r\\n|\\r|\\n" );

        for( int i = 0; i < lines.length; i++ )
        {
            if( !StringUtils.hasText( lines[i] ) ) continue;
            String[] items = lines[i].split( "\t" );
            int length = items.length;
            if( items[0].matches( "\\d{4}" ) && items[8].matches( "\\d{9}" ) )
            {
                User user = new User();
                // set admin Term
                user.setQuarterAdmitted( new Quarter( Integer
                    .parseInt( items[0] ) ) );

                // Set major
                String[] AcadPlan = items[1].split( " " );
                Major major = new Major();
                major.setSymbol( AcadPlan[0] );
                user.setMajor( major );

                // Set highSchool
                String highSchoolName = items[7];
                HighSchool highSchool = new HighSchool();
                highSchool.setName( highSchoolName );
                user.setHighSchool( highSchool );

                user.setCin( items[8] );
                user.setLastName( items[9] );
                user.setFirstName( items[10] );
                user.setMiddleName( items[11] );
                user.setCountry( items[12] );
                user.setAddress1( items[13] );
                user.setAddress2( items[14] );
                user.setCity( items[15] );
                user.setState( items[16] );
                user.setZip( items[17] );

                if( length > 18 )
                {
                    user.setGender( items[18] );
                }
                if( length > 19 )
                {
                    user.setEmail( items[19] );
                }
                if( length > 20 )
                {
                    user.setCellPhone( items[20] );
                }
                importedUsers.add( user );
            }
            else
            {
                invalidLines.add( lines[i] );
            }

        }
    }

    public String getText()
    {
        return text;
    }

    public List<User> getImportedUsers()
    {
        return importedUsers;
    }

    public List<String> getInvalidLines()
    {
        return invalidLines;
    }

}
