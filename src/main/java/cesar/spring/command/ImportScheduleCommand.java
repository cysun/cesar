package cesar.spring.command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cesar.model.Major;
import cesar.model.Quarter;
import cesar.model.Section;

public class ImportScheduleCommand {

    private String       text;
    private Major        major;
    private Quarter      quarter;
    private Set<Section> importedSections;
    private List<String> invalidCourseLines;
    private List<String> invalidWeekDayLines;
    private List<String> invalidTimeLines;
    private List<String> invalidUnitsLines;

    public ImportScheduleCommand()
    {
        importedSections = new HashSet<Section>();
        invalidCourseLines = new ArrayList<String>();
        invalidWeekDayLines = new ArrayList<String>();
        invalidTimeLines = new ArrayList<String>();
        invalidUnitsLines = new ArrayList<String>();
    }

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    public Major getMajor()
    {
        return major;
    }

    public void setMajor( Major major )
    {
        this.major = major;
    }

    public Quarter getQuarter()
    {
        return quarter;
    }

    public void setQuarter( Quarter quarter )
    {
        this.quarter = quarter;
    }

    public Set<Section> getImportedSections()
    {
        return importedSections;
    }

    public void setImportedSections( Set<Section> importedSections )
    {
        this.importedSections = importedSections;
    }

    public List<String> getInvalidCourseLines()
    {
        return invalidCourseLines;
    }

    public void setInvalidCourseLines( List<String> invalidCourseLines )
    {
        this.invalidCourseLines = invalidCourseLines;
    }

    public List<String> getInvalidWeekDayLines()
    {
        return invalidWeekDayLines;
    }

    public void setInvalidWeekDayLines( List<String> invalidWeekDayLines )
    {
        this.invalidWeekDayLines = invalidWeekDayLines;
    }

    public List<String> getInvalidTimeLines()
    {
        return invalidTimeLines;
    }

    public void setInvalidTimeLines( List<String> invalidTimeLines )
    {
        this.invalidTimeLines = invalidTimeLines;
    }

    public List<String> getInvalidUnitsLines()
    {
        return invalidUnitsLines;
    }

    public void setInvalidUnitsLines( List<String> invalidUnitsLines )
    {
        this.invalidUnitsLines = invalidUnitsLines;
    }

}
