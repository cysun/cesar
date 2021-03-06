package cesar.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class Quarter {

    private Integer code;

    public Quarter( int code )
    {
        this.code = code;
    }

    public Quarter( Calendar calendar )
    {
        setCode( calendar );
    }

    public Quarter( Date date )
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        setCode( calendar );
    }

    public Quarter( int year, String term )
    {
        code = (year - 1800) * 10;

        term = term.toUpperCase();
        if( term.equals( "WINTER" ) || term.equals( "W" ) )
            code += 1;
        else if( term.equals( "SPRING" ) || term.equals( "S" ) )
            code += 3;
        else if( term.equals( "SUMMER" ) || term.equals( "X" ) )
            code += 6;
        else if( term.equals( "FALL" ) || term.equals( "F" ) )
            code += 9;
        else
        {
            code += 9;
        }
    }

    public Quarter()
    {
        setCode( Calendar.getInstance() );
    }

    private void setCode( Calendar calendar )
    {
        code = (calendar.get( Calendar.YEAR ) - 1800) * 10;

        int week = calendar.get( Calendar.WEEK_OF_YEAR );
        if( week < 4 )
            code += 1; // Winter term: week 1-3
        else if( week < 22 )
            code += 3; // Spring term: week 4-21
        else if( week < 34 )
            code += 6; // Summer term: week 22-33
        else
            code += 9; // Fall term: week 34-
    }

    public Quarter next()
    {
        int yearCode = code / 10;
        int quarterSuffix = code % 10;

        switch( quarterSuffix )
        {
            case 1:
                quarterSuffix = 3;
                break;
            case 3:
                quarterSuffix = 6;
                break;
            case 6:
                quarterSuffix = 9;
                break;
            default:
                ++yearCode;
                quarterSuffix = 1;
        }

        return new Quarter( yearCode * 10 + quarterSuffix );
    }

    public Quarter previous()
    {
        int yearCode = code / 10;
        int quarterSuffix = code % 10;

        switch( quarterSuffix )
        {
            case 9:
                quarterSuffix = 6;
                break;
            case 6:
                quarterSuffix = 3;
                break;
            case 3:
                quarterSuffix = 1;
                break;
            default:
                --yearCode;
                quarterSuffix = 9;
        }

        return new Quarter( yearCode * 10 + quarterSuffix );
    }

    @Override
    public String toString()
    {
        String s;
        switch( code % 10 )
        {
            case 1:
                s = "WINTER";
                break;
            case 3:
                s = "SPRING";
                break;
            case 6:
                s = "SUMMER";
                break;
            case 9:
                s = "FALL";
                break;
            default:
                s = "UNKNOWN";
        }

        return s + " " + (code / 10 + 1800);
    }

    public String getShortString()
    {
        String s;
        switch( code % 10 )
        {
            case 1:
                s = "W";
                break;
            case 3:
                s = "S";
                break;
            case 6:
                s = "X";
                break;
            case 9:
                s = "F";
                break;
            default:
                s = "U";
        }

        int year = ((code / 10) + 1900) % 100;
        return s + (year < 10 ? "0" + year : year);
    }

    public String getQuarterName()
    {
        String s;
        switch( code % 10 )
        {
            case 1:
                s = "WINTER";
                break;
            case 3:
                s = "SPRING";
                break;
            case 6:
                s = "SUMMER";
                break;
            case 9:
                s = "FALL";
                break;
            default:
                s = "UNKNOWN";
        }

        return s;
    }

    public boolean equals( Quarter quarter )
    {
        return quarter == null ? false : code == quarter.code;
    }

    public boolean before( Quarter quarter )
    {
        return quarter == null ? false : code < quarter.code;
    }

    public boolean after( Quarter quarter )
    {
        return quarter == null ? false : code > quarter.code;
    }

    public int getQuarterSuffix()
    {
        return code % 10;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode( int code )
    {
        this.code = code;
    }

}
