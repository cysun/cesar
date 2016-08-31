package cesar.spring.controller.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import cesar.model.Course;
import cesar.model.Major;
import cesar.model.Quarter;
import cesar.model.Schedule;
import cesar.model.Section;
import cesar.model.WeekDay;
import cesar.model.dao.CourseDao;
import cesar.model.dao.MajorDao;
import cesar.model.dao.ScheduleDao;
import cesar.model.dao.UserDao;
import cesar.model.dao.WeekDayDao;
import cesar.spring.command.ImportScheduleCommand;
import cesar.spring.controller.HomeController;
import cesar.spring.editor.MajorPropertyEditor;
import cesar.spring.editor.QuarterPropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
@SessionAttributes("importScheduleCommand")
class ImportScheduleController {

    @Autowired
    private CourseDao             courseDao;

    @Autowired
    private MajorDao              majorDao;

    @Autowired
    private ScheduleDao           scheduleDao;

    @Autowired
    private WeekDayDao            weekDayDao;

    @Autowired
    private QuarterPropertyEditor quarterPropertyEditor;

    @Autowired
    private MajorPropertyEditor   majorPropertyEditor;

    @Autowired
    private UserDao               userDao;

    List<Quarter>                 quarters = new ArrayList<Quarter>();

    private Logger                logger   = LoggerFactory
                                               .getLogger( ImportScheduleController.class );

    @RequestMapping(value = ("/schedule/importSchedule.html"),
        method = RequestMethod.GET)
    protected String importScheduleSetupForm( ModelMap model )
    {
        Integer quarterRange = 6;
        ImportScheduleCommand importScheduleCommand = new ImportScheduleCommand();

        quarters.clear();
        Quarter quarter = new Quarter( new Date() );

        for( int i = 0; i < quarterRange; i++ )
        {
            quarters.add( quarter );
            quarter = quarter.next();
        }
        model.addAttribute( "importScheduleCommand", importScheduleCommand );
        model.addAttribute( "majors", majorDao.getMajors() );
        model.addAttribute( "quarters", quarters );
        return "schedule/importScheduleForm";
    }

    @RequestMapping(value = ("/schedule/importSchedule.html"),
        method = RequestMethod.POST)
    protected String importScheduleSubmitForm(
        HttpServletRequest request,
        HttpServletResponse response,
        @ModelAttribute("importScheduleCommand") ImportScheduleCommand importScheduleCommand,
        BindingResult result, SessionStatus status,
        @RequestParam("_page") int currentPage, ModelMap model )
    {
        Map<Integer, String> pageForms = new HashMap<Integer, String>();
        pageForms.put( 0, "schedule/importScheduleForm" );
        pageForms.put( 1, "schedule/importScheduleFinish" );
        if( request.getParameter( "_finish" ) != null )
        {
            // User is finished
            Major major = importScheduleCommand.getMajor();
            Quarter quarter = importScheduleCommand.getQuarter();

            Schedule schedule = scheduleDao.getScheduleByMajorAndQuarter(
                quarter, major );
            if( schedule != null )
            {
                Set<Section> sections = schedule.getSections();
                sections.addAll( importScheduleCommand.getImportedSections() );

                status.setComplete();
                scheduleDao.saveSchedule( schedule );

            }
            else
            {
                schedule = new Schedule();
                Set<Section> sections = importScheduleCommand
                    .getImportedSections();
                schedule.setSections( sections );
                schedule.setMajor( major );
                schedule.setQuarter( quarter );
                scheduleDao.saveSchedule( schedule );
                status.setComplete();
            }

            logger.info( "User: "
                + userDao.getUserByUsername( SecurityUtils.getUsername() )
                    .getName() + " added a schedule in :"
                + quarter.getQuarterName() + " and major :" + major.getName() );
            model.addAttribute( "msgTitle", "Imported Completed" );
            model.addAttribute( "msg", schedule.getQuarter()
                + " Schedule is imported" );
            model.addAttribute( "backUrl", "/schedule/displaySchedules.html" );

            return "status";
        }
        else
        {
            // User clicked Next or Back(_target)
            // Extract target page
            int targetPage = WebUtils.getTargetPage( request, "_target",
                currentPage );
            if( targetPage < currentPage )
            {
                if( targetPage == 0 )
                {
                    model.addAttribute( "quarters", quarters );
                    model.addAttribute( "majors", majorDao.getMajors() );
                }
                return (String) pageForms.get( targetPage );

            }
            // User clicked 'Next', return target page

            if( currentPage == 0 )
            {
                Set<Section> importedSections = importScheduleCommand
                    .getImportedSections();
                List<String> invalidCourseLines = importScheduleCommand
                    .getInvalidCourseLines();
                List<String> invalidWeekDayLines = importScheduleCommand
                    .getInvalidWeekDayLines();
                List<String> invalidTimeLines = importScheduleCommand
                    .getInvalidTimeLines();
                List<String> invalidUnitsLines = importScheduleCommand
                    .getInvalidUnitsLines();

                String text = importScheduleCommand.getText();
                if( StringUtils.hasText( text ) )
                {
                    importedSections.clear();
                    invalidCourseLines.clear();
                    invalidWeekDayLines.clear();
                    invalidTimeLines.clear();
                    invalidUnitsLines.clear();

                    String[] lines = text.split( "\\r\\n|\\r|\\n" );

                    outer: for( int i = 0; i < lines.length; i++ )
                    {
                        Section section = new Section();
                        String[] tokens = lines[i].split( "\t" );
                        int size = tokens.length;
                        if( size < 6 )
                        {
                            continue;
                        }

                        // course
                        String[] tmpTokens = tokens[0].split( "-" );

                        String[] courseTokens = tmpTokens[0].split( " " );

                        String courseName = "";
                        for( int k = 0; k < courseTokens.length; k++ )
                        {
                            courseName = courseName
                                + courseTokens[k].trim().toUpperCase();
                        }
                        Course course = courseDao.getCourseByCode( courseName );
                        if( course == null )
                        {
                            invalidCourseLines.add( lines[i] );
                            continue;
                        }
                        else
                        {
                            section.setCourse( course );
                        }
                        // option
                        if( tmpTokens.length > 1 )
                        {
                            String option = tmpTokens[1].trim();
                            section.setOption( option );
                        }

                        // section number
                        String sectionNumber = tokens[1].trim();
                        section.setSectionNumber( sectionNumber );

                        // call number
                        String callNumber = tokens[2].trim();
                        section.setCallNumber( callNumber );

                        // units
                        Integer units = null;
                        try
                        {
                            units = Integer.parseInt( tokens[3].trim() );

                        }
                        catch( Exception e )
                        {
                            invalidUnitsLines.add( lines[i] );
                            continue;
                        }

                        section.setUnits( units );

                        // days
                        String weekDaysStr = tokens[4].toUpperCase().trim();

                        weekDaysStr = weekDaysStr.replaceAll( "TH", "R" );
                        weekDaysStr = weekDaysStr.replaceAll( "SA", "S" );

                        String[] weekDaysTokens = weekDaysStr.split( " " );
                        String weekDays = "";
                        for( int j = 0; j < weekDaysTokens.length; j++ )
                        {
                            weekDays = weekDays + weekDaysTokens[j];
                        }

                        char[] weekdayChars = weekDays.toUpperCase()
                            .toCharArray();

                        Set<WeekDay> weekDaySet = section.getWeekDays();
                        for( int j = 0; j < weekdayChars.length; j++ )
                        {
                            String symbol = weekdayChars[j] + "";
                            WeekDay weekDay = weekDayDao
                                .getWeekDayBySymbol( symbol );
                            if( weekDay == null )
                            {
                                invalidWeekDayLines.add( lines[i] );
                                continue outer;
                            }
                            else
                            {
                                weekDaySet.add( weekDay );
                            }
                        }
                        section.setWeekDays( weekDaySet );

                        // times
                        String[] times = tokens[5].trim().toUpperCase()
                            .split( "-" );
                        String startTime = times[0].trim();
                        String endTime = times[1].trim();

                        SimpleDateFormat df = new SimpleDateFormat( "hh:mm a" );
                        try
                        {
                            df.parse( startTime );
                            df.parse( endTime );
                            section.setStartTime( startTime );
                            section.setEndTime( endTime );
                        }
                        catch( ParseException e )
                        {
                            invalidTimeLines.add( lines[i] );
                            continue;
                        }

                        section.setStartTime( startTime );
                        section.setEndTime( endTime );

                        // location
                        if( size > 6 )
                        {
                            String location = tokens[6].trim();
                            section.setLocation( location );
                        }
                        else
                        {
                            section.setLocation( "" );
                        }

                        // capacity
                        if( size > 7 )
                        {
                            String capacity = tokens[7].trim();
                            section.setCapacity( capacity );
                        }
                        else
                        {
                            section.setCapacity( "" );
                        }

                        importedSections.add( section );

                    }
                    importScheduleCommand
                        .setImportedSections( importedSections );
                    importScheduleCommand
                        .setInvalidCourseLines( invalidCourseLines );
                    importScheduleCommand
                        .setInvalidTimeLines( invalidTimeLines );
                    importScheduleCommand
                        .setInvalidWeekDayLines( invalidWeekDayLines );
                    importScheduleCommand
                        .setInvalidUnitsLines( invalidUnitsLines );
                }
            }
            model.addAttribute( importScheduleCommand );
            return (String) pageForms.get( targetPage );
        }
    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
            new SimpleDateFormat( "MM/dd/yyyy" ), true ) );
        binder.registerCustomEditor( Major.class, majorPropertyEditor );
        binder.registerCustomEditor( Quarter.class, quarterPropertyEditor );
    }
}
