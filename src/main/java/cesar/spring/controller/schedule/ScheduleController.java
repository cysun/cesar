package cesar.spring.controller.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint.StaticPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import cesar.model.Course;

import cesar.model.CoursePlan;
import cesar.model.CourseTaken;
import cesar.model.CourseTransferred;
import cesar.model.CourseWaived;
import cesar.model.Major;
import cesar.model.Prerequisite;
import cesar.model.Quarter;
import cesar.model.QuarterPlan;
import cesar.model.Schedule;
import cesar.model.Section;
import cesar.model.User;

import cesar.model.WeekDay;
import cesar.model.dao.CourseDao;
import cesar.model.dao.CoursePlanDao;
import cesar.model.dao.CourseTakenDao;
import cesar.model.dao.CourseTransferredDao;
import cesar.model.dao.CourseWaivedDao;
import cesar.model.dao.MajorDao;
import cesar.model.dao.QuarterPlanDao;
import cesar.model.dao.ScheduleDao;
import cesar.model.dao.SectionDao;
import cesar.model.dao.UserDao;
import cesar.model.dao.WeekDayDao;

import cesar.spring.editor.CourseGetCodePropertyEditor;
import cesar.spring.editor.MajorPropertyEditor;
import cesar.spring.editor.QuarterPropertyEditor;
import cesar.spring.editor.WeekDayPropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
public class ScheduleController {

    @Autowired
    private MajorDao                    majorDao;
    @Autowired
    private ScheduleDao                 scheduleDao;
    @Autowired
    private SectionDao                  sectionDao;
    @Autowired
    private CourseDao                   courseDao;
    @Autowired
    private WeekDayDao                  weekDayDao;
    @Autowired
    private UserDao                     userDao;
    @Autowired
    private CourseTakenDao              courseTakenDao;
    @Autowired
    private CourseTransferredDao        courseTransferredDao;
    @Autowired
    private CourseWaivedDao             courseWaivedDao;

    @Autowired
    private MajorPropertyEditor         majorPropertyEditor;
    @Autowired
    private QuarterPropertyEditor       quarterPropertyEditor;
    @Autowired
    private CourseGetCodePropertyEditor courseGetSymbolPropertyEditor;
    @Autowired
    private WeekDayPropertyEditor       weekDayPropertyEditor;
    
    @Autowired
    private QuarterPlanDao                   quarterPlanDao;
    @Autowired
    private CoursePlanDao                   coursePlanDao;

    private Logger                      logger = LoggerFactory
                                                   .getLogger( ScheduleController.class );

    private String                      tabs[] = { "CS", "CE", "EE", "ME",
        "TECH", "MATH", "PHYS", "GENERAL"     };

    int getTabIndex( String major )
    {
        for( int i = 0; i < tabs.length; ++i )
            if( tabs[i].equals( major ) ) return i + 1;

        return 0;
    }

    @RequestMapping(value = ("/schedule/displaySchedules.html"),
        method = RequestMethod.GET)
    protected String displaySchedules( String major,
        HttpServletRequest request, ModelMap model )
    {
        if(SecurityUtils.isAdministrator())
        {
            model.addAttribute("staffOnly", true);
        }else
        {
            model.addAttribute("staffOnly", false);
        }
        
        if( major == null ) return "schedule/displaySchedules";
        HttpSession httpSession = request.getSession();

        Schedule schedule = (Schedule) httpSession
            .getAttribute( "selectSchedule" );

        model.addAttribute( "major", major );

        List<Schedule> schedules = scheduleDao.getSchedulesByMajor( majorDao
            .getMajorBySymbol( major ) );
        List<Quarter> quarterList = new ArrayList<Quarter>();

        Integer quarterRange = 6;
        quarterList.clear();

        Quarter quarter = new Quarter( new Date() );

        for( int i = 0; i < quarterRange; i++ )
        {
            quarterList.add( quarter );
            quarter = quarter.next();
        }
        model.addAttribute( "quarterList", quarterList );

        List<Quarter> quarters = new ArrayList<Quarter>();

        List<Schedule> newSchedules = new ArrayList<Schedule>();
        
        
        if( schedules != null && schedules.size() > 0 )
        {
            for( Schedule scheduleTmp : schedules )
            {
                if(scheduleTmp.getQuarter().before(new Quarter()))
                {
                    continue;
                }
                else{
                    quarters.add( scheduleTmp.getQuarter() );
                    
                    if(scheduleTmp.getSections().size()>0)
                    {
                        newSchedules.add( scheduleTmp );
                    }
                }
            }
            model.addAttribute( "quarters", quarters );

            if( schedule != null )
            {
                if( schedule.getMajor().getSymbol().equals( major ) )
                {
                    model.addAttribute(
                        "schedule",
                        scheduleDao.getScheduleByMajorAndQuarter(
                            schedule.getQuarter(), schedule.getMajor() ) );
                }
                else
                {
                    
                    model.addAttribute( "schedule", newSchedules.get( 0 ) );
                }
            }
            else
            {
                if(newSchedules.size()>0)
                {
                    model.addAttribute( "schedule", newSchedules.get( 0 ) );
                }
              
            }

        }

        model.addAttribute( "weekDays", weekDayDao.getWeekdays() );
        model.addAttribute( "section", new Section() );
        model.addAttribute( "schedules", newSchedules );

        
        
        httpSession.removeAttribute( "selectSchedule" );
        return "schedule/displaySchedulesTab";
    }

    @RequestMapping(value = ("/schedule/displaySchedules.html"),
        method = RequestMethod.POST)
    protected String scheduleQuery( String major, Integer quarter,
        HttpServletRequest request, ModelMap model )
    {
        Schedule schedule = scheduleDao.getScheduleByMajorAndQuarter(
            new Quarter( quarter ), majorDao.getMajorBySymbol( major ) );

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute( "selectSchedule", schedule );
        return "redirect:displaySchedules.html#ui-tabs-" + getTabIndex( major );

    }

    @RequestMapping(value = { "/schedule/section/addSection.html" },
        method = RequestMethod.GET)
    protected String addSection( String major, Integer _page, Integer _target,
        Integer quarterCode1, Boolean fromCoursePlan, Boolean cancelSection,
        HttpServletRequest request, ModelMap model )
    {
        boolean addSection = true;
        List<Quarter> quarterList = new ArrayList<Quarter>();
        Section section = new Section();

        Integer quarterRange = 6;
        quarterList.clear();

        Quarter quarter = new Quarter( new Date() );

        for( int i = 0; i < quarterRange; i++ )
        {
            quarterList.add( quarter );
            quarter = quarter.next();
        }
        if( fromCoursePlan != null )
        {
            if( cancelSection == null )
            {
                List<Major> allMajors = majorDao.getMajors();
                model.addAttribute( "_page", _page );
                model.addAttribute( "_target", _target );
                model.addAttribute( "quarterCode1", quarterCode1 );
                model.addAttribute( "fromCoursePlan", fromCoursePlan );
                model.addAttribute( "allMajors", allMajors );
            }
            else
            {

                HttpSession httpSession = request.getSession();
                CoursePlan coursePlan = (CoursePlan) httpSession
                    .getAttribute( "coursePlan" );

                List<Section> yellowSections = new ArrayList<Section>();
                List<Course> redCourses = new ArrayList<Course>();
                List<Course> blueCourses = new ArrayList<Course>();
                List<Section> greenSections = new ArrayList<Section>();
                List<Course> greyCourses = new ArrayList<Course>();

                User currentUser = coursePlan.getStudent();
                Set<Course> allCourses = new HashSet<Course>();
                Set<Course> GECourses = null;
                List<Course> coursesNeedToTake = new ArrayList<Course>();
                List<CourseTaken> coursesTaken = null;
                List<CourseWaived> coursesWaived = null;
                List<CourseTransferred> coursesTransferred = null;
                List<Course> takenCourses = new ArrayList<Course>();
                if( !coursePlan.getIsTemplate() )
                {
                    int majorId = currentUser.getMajor().getId();
                    Major currentMajor = majorDao.getMajorById( majorId );

                    allCourses.addAll( currentMajor.getCourses() );

                    GECourses = majorDao.getMajorBySymbol( "GENERAL" )
                        .getCourses();

                    allCourses.addAll( GECourses );

                    coursesTaken = courseTakenDao
                        .getNonrepeatableCourseTakenByStudent( currentUser );
                    coursesWaived = courseWaivedDao
                        .getNonrepeatableCourseWaivedsByStudent( currentUser );
                    coursesTransferred = courseTransferredDao
                        .getNonrepeatableCourseTransferredsByStudent( currentUser );

                }
                else
                {
                    Major selectedMajor = majorDao.getMajorById( coursePlan
                        .getMajor().getId() );
                    allCourses.addAll( selectedMajor.getCourses() );
                    GECourses = majorDao.getMajorBySymbol( "GENERAL" )
                        .getCourses();
                    allCourses.addAll( GECourses );

                    coursesTaken = new ArrayList<CourseTaken>();
                    coursesWaived = new ArrayList<CourseWaived>();
                    coursesTransferred = new ArrayList<CourseTransferred>();
                }
                for( CourseTaken courseTaken : coursesTaken )
                {
                    takenCourses.add( courseTaken.getCourse() );
                }

                for( CourseWaived courseWaived : coursesWaived )
                {
                    takenCourses.add( courseWaived.getCourse() );
                }

                for( CourseTransferred courseTransferred : coursesTransferred )
                {
                    takenCourses.add( courseTransferred.getCourse() );
                }

                List<String> allCoursesStr = new ArrayList<String>();
                for( Course course1 : allCourses )
                {
                    allCoursesStr.add( course1.getCode() );
                }

                // compare to the courses that current user has taken, get the
                // CoursesNeedToTake
                List<String> courseNeedToTakeStr = new ArrayList<String>();
                courseNeedToTakeStr.addAll( allCoursesStr );
                for( Course takenCourse : takenCourses )
                {
                    String code = takenCourse.getCode();
                    if( allCoursesStr.contains( code ) )
                        courseNeedToTakeStr.remove( code );
                }

                // preCoursesStr stores the name of courses that user has taken
                Set<String> preCoursesStr = new HashSet<String>();
                for( Course takenCourse : takenCourses )
                {
                    preCoursesStr.add( takenCourse.getCode() );
                }

                // get quaterPlans's courses
                Set<QuarterPlan> quarterPlanList = coursePlan.getQuarterPlans();
                if( quarterPlanList != null && quarterCode1 != null )
                {
                    for( QuarterPlan quarterPlanTmp : quarterPlanList )
                    {
                        if( quarterPlanTmp.getQuarter().getCode() < quarterCode1 )
                        {

                            Set<Section> selectedSectionList = quarterPlanTmp
                                .getSections();
                            Set<Course> selectedCourseList = quarterPlanTmp
                                .getCourses();

                            if( selectedSectionList == null )
                                selectedSectionList = new HashSet<Section>();
                            if( selectedCourseList == null )
                                selectedCourseList = new HashSet<Course>();

                            for( Section selectedSection : selectedSectionList )
                            {
                                if( selectedSection.getCourse().isRepeatable() == false )
                                    preCoursesStr.add( selectedSection
                                        .getCourse().getCode() );
                                if( courseNeedToTakeStr
                                    .contains( selectedSection.getCourse()
                                        .getCode() )
                                    && selectedSection.getCourse()
                                        .isRepeatable() == false )
                                {
                                    courseNeedToTakeStr.remove( selectedSection
                                        .getCourse().getCode() );
                                }
                            }

                            for( Course selectedCourse : selectedCourseList )
                            {
                                if( selectedCourse.isRepeatable() == false )
                                    preCoursesStr
                                        .add( selectedCourse.getCode() );
                                if( courseNeedToTakeStr
                                    .contains( selectedCourse.getCode() )
                                    && selectedCourse.isRepeatable() == false )
                                {
                                    courseNeedToTakeStr.remove( selectedCourse
                                        .getCode() );
                                }
                            }
                        }
                    }
                }

                // get coursesNeedToTaken
                for( String courseCode1 : courseNeedToTakeStr )
                {
                    coursesNeedToTake.add( courseDao
                        .getCourseByCode( courseCode1 ) );
                }

                // compare the preCourses to coursesNeedToTaken, get result
                List<String> meetPreCoursesStr = new ArrayList<String>();
                HashMap<String, String> notMeetPreCoursesStr = new HashMap<String, String>();

                for( Course courseTmp : coursesNeedToTake )
                {
                    StringBuffer courseToTake = new StringBuffer();

                    // if preCourses has section course, then continue
                    if( preCoursesStr.contains( courseTmp.getCode() ) )
                        continue;
                    else
                    {

                        // get prerequisites course from course
                        Set<Prerequisite> prerequisites = courseTmp
                            .getPrerequisites();
                        Boolean isSatisfied = true;

                        for( Prerequisite prerequisite : prerequisites )
                        {

                            Set<Course> prereCourses = prerequisite
                                .getCourses();

                            // if prerequisites has form like course1/course2

                            if( prerequisite.getCourses().size() > 1 )
                            {
                                Boolean forPartialSat = false;
                                Integer preCounter = 1;

                                String sb = new String();
                                for( Course prereCourse1 : prereCourses )
                                {
                                    if( preCoursesStr.contains( prereCourse1
                                        .getCode() ) )
                                    {
                                        forPartialSat = true;

                                        break;
                                    }
                                    else
                                    {

                                        if( preCounter < prereCourses.size() )
                                            sb = sb + prereCourse1 + "/";
                                        else
                                            sb = sb + prereCourse1 + " ";
                                    }
                                    preCounter++;
                                }
                                if( forPartialSat == false )
                                {
                                    isSatisfied = false;
                                    courseToTake.append( sb );
                                }
                            }
                            else
                            {
                                for( Course prereCourse2 : prereCourses )
                                {
                                    if( !preCoursesStr.contains( prereCourse2
                                        .getCode() ) )
                                    {
                                        courseToTake
                                            .append( prereCourse2 + " " );
                                        isSatisfied = false;
                                    }
                                }

                            }

                        }
                        if( isSatisfied == true )
                        {
                            meetPreCoursesStr.add( courseTmp.getCode() );
                        }
                        else
                        {
                            notMeetPreCoursesStr.put( courseTmp.getCode(),
                                courseToTake.toString() );
                        }

                    }
                }

                Boolean isEmptySections = true;

                // get quarter plan
                QuarterPlan quarterPlan = (QuarterPlan) httpSession
                    .getAttribute( "quarterPlan" );
                // get next schedule

                Set<Section> availSections = new HashSet<Section>();

                List<Major> majors = majorDao.getMajors();

                for( Major major2 : majors )
                {
                    Schedule schedule1 = scheduleDao
                        .getScheduleByMajorAndQuarter(
                            quarterPlan.getQuarter(), major2 );
                    if( schedule1 != null )
                        availSections.addAll( schedule1.getSections() );
                }

                if( availSections.size() > 0 )
                {
                    isEmptySections = false;
                }

                if( isEmptySections == true )
                {
                    String warning = "WARNING: The courses to be offered in "
                        + quarter
                        + " are not available, You are suggested"
                        + " to take following courses based on the course prerequisites and the courses you have or would have taken by this quarter. ";
                    model.addAttribute( "warning", warning );

                    // green courses
                    for( String meetCourseStr : meetPreCoursesStr )
                    {
                        Course course1 = courseDao
                            .getCourseByCode( meetCourseStr );
                        blueCourses.add( course1 );
                    }

                    // grey courses
                    Set<String> courseKeys = notMeetPreCoursesStr.keySet();

                    for( String courseCode1 : courseKeys )
                    {
                        String notMeetPres = notMeetPreCoursesStr
                            .get( courseCode1 );
                        Course course1 = courseDao
                            .getCourseByCode( courseCode1 );
                        course1.setExtraInfo( notMeetPres );
                        greyCourses.add( course1 );
                    }
                    model.addAttribute( "blueCourses", blueCourses );
                    model.addAttribute( "greyCourses", greyCourses );
                }

                // has schedule
                else
                {
                    // reclassify meetPreCourses to grey and red

                    List<String> unavailCoursesStr = new ArrayList<String>();
                    unavailCoursesStr.addAll( meetPreCoursesStr );

                    HashMap<String, String> notMeetNUnavailPreCourseStr = new HashMap<String, String>();
                    notMeetNUnavailPreCourseStr.putAll( notMeetPreCoursesStr );

                    for( Section sectionTmp : availSections )
                    {
                        // green or blue
                        if( meetPreCoursesStr.contains( sectionTmp.getCourse()
                            .getCode() ) )
                        {

                            greenSections.add( sectionTmp );

                            // yellow(Str)
                            unavailCoursesStr.remove( sectionTmp.getCourse()
                                .getCode() );
                        }

                        // reclassify notMeetPreCourse into Grey and yellow
                        // in schedule: red and grey(Str)
                        String notMeetPreCourseStr = notMeetPreCoursesStr
                            .get( sectionTmp.getCourse().getCode() );
                        if( notMeetPreCourseStr != null )
                        {
                            // red sections
                            sectionTmp.setExtraInfo( notMeetPreCourseStr );
                            // newSection.setExtraInfo( notMeetPreCourseStr );
                            yellowSections.add( sectionTmp );

                            // grey(Str)
                            notMeetNUnavailPreCourseStr.remove( sectionTmp
                                .getCourse().getCode() );
                        }
                    }

                    // red courses
                    for( String courseCode1 : unavailCoursesStr )
                    {
                        redCourses
                            .add( courseDao.getCourseByCode( courseCode1 ) );
                    }

                    // grey courses
                    Set<String> courseKeys = notMeetNUnavailPreCourseStr
                        .keySet();

                    for( String courseCode1 : courseKeys )
                    {
                        String notMeetPres = notMeetNUnavailPreCourseStr
                            .get( courseCode1 );
                        Course course1 = new Course();
                        course1.setCode( courseDao
                            .getCourseByCode( courseCode1 ).getCode() );
                        course1.setName( courseDao
                            .getCourseByCode( courseCode1 ).getName() );
                        course1.setExtraInfo( notMeetPres );
                        greyCourses.add( course1 );
                    }

                    model.addAttribute( "yellowSections", yellowSections );
                    model.addAttribute( "greenSections", greenSections );
                    model.addAttribute( "redCourses", redCourses );
                    model.addAttribute( "blueCourses", blueCourses );
                    model.addAttribute( "greyCourses", greyCourses );

                }

                if( courseNeedToTakeStr.size() == 0 )
                {
                    model.addAttribute( "currentPage", -1 );
                }
                else
                {
                    model.addAttribute( "currentPage", _page );
                }

                model.addAttribute( "targetPage", _target );
                model.addAttribute( "availSections", availSections );
                model.addAttribute( "quarterPlan", quarterPlan );
                model.addAttribute( "previousPage", _page - 1 );

                return "coursePlan/coursePlanForm";

            }
        }

        model.addAttribute( "weekDays", weekDayDao.getWeekdays() );
        model.addAttribute( "section", section );
        model.addAttribute( "major", major );
        model.addAttribute( "addSection", addSection );
        model.addAttribute( "quarterList", quarterList );
        model.addAttribute( "addSection", true );

        return "schedule/editSection";
    }

    @RequestMapping(value = { "/schedule/section/editSection.html" },
        method = RequestMethod.GET)
    protected String editSection( Integer sectionId, Integer scheduleId,
        ModelMap model )
    {
        Section section = sectionId == null ? new Section() : sectionDao
            .getSectionById( sectionId );
        Schedule schedule = scheduleDao.getScheduleById( scheduleId );
        Set<Course> courses = schedule.getMajor().getCourses();
        model.addAttribute( "weekDays", weekDayDao.getWeekdays() );
        model.addAttribute( "section", section );
        model.addAttribute( "schedule", schedule );
        model.addAttribute( "courses", courses );
        model.addAttribute( "addSection", false );
        return "schedule/editSection";
    }

    @RequestMapping(value = { "/schedule/section/deleteSection.html" },
        method = RequestMethod.GET)
    protected String deleteSection( Integer id, Integer majorId,
        Integer scheduleId )
    {
        Schedule schedule = scheduleDao.getScheduleById( scheduleId );
        Section deleteSection = sectionDao.getSectionById( id );
        Major major1 = majorDao.getMajorById( majorId );

        Quarter q = schedule.getQuarter();
        List<QuarterPlan> qp = quarterPlanDao.getQuarterPlanByQuarter(q);
        boolean noSection = true;
        for (int i = 0; i < qp.size(); i++) {
        	QuarterPlan quarterPlan = qp.get(i);
        	Set<Section> s = quarterPlan.getSections();
        	if (s.contains(deleteSection)) {
        		noSection = false;
        		break;
        	}
        }
        
        if (noSection == false) {
        	return "redirect:../displaySchedules.html#ui-tabs-"
        	+ getTabIndex( major1.getSymbol() );
        } else {
        	Set<Section> sections = schedule.getSections();
        	sections.remove( deleteSection );

        	scheduleDao.saveSchedule( schedule );

        	sectionDao.deleteSection( deleteSection );

        	logger.info( "User: "
        		+ userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " deleted a section" );

        	return "redirect:../displaySchedules.html#ui-tabs-"
            	+ getTabIndex( major1.getSymbol() );
        }

    }

    @RequestMapping(value = { "/schedule/section/{path}.html" },
        method = RequestMethod.POST)
    protected String editSection( Section section, Integer scheduleId,
        @PathVariable("path") String path, String courseCode,
        Integer quarterCode, String info, String major, String major1,
        Integer _page, Integer _target, Boolean fromCoursePlan,
        HttpServletRequest request, ModelMap model )
    {

        courseCode = courseCode.toUpperCase();
        if( path.equals( "editSection" ) )
        {
            String courseError = null;
            String startTimeError = null;
            String endTimeError = null;
            String weekDayError = null;

            Schedule schedule = scheduleDao.getScheduleById( scheduleId );
            Set<Section> sections = schedule.getSections();
            Course course = courseDao.getCourseByCode( courseCode );

            Boolean hasError = false;
            if( course == null )
            {
                courseError = "Course entered does not exsit";
                hasError = true;

            }

            SimpleDateFormat df = new SimpleDateFormat( "hh:mm a" );
            try
            {
                df.parse( section.getStartTime() );
            }
            catch( ParseException e )
            {
                startTimeError = "Start time enterd is invalid";
                hasError = true;
            }

            try
            {
                df.parse( section.getEndTime() );
            }
            catch( ParseException e )
            {
                endTimeError = "End time enterd is invalid";
                hasError = true;

            }

            if( section.getWeekDays() == null )
            {
                weekDayError = "Please select week days";
                hasError = true;
            }
            if( hasError == true )
            {
                model.addAttribute( "courseError", courseError );
                model.addAttribute( "startTimeError", startTimeError );
                model.addAttribute( "endTimeError", endTimeError );
                model.addAttribute( "weekDayError", weekDayError );
                model.addAttribute( "schedule", schedule );
                model.addAttribute( "weekDays", weekDayDao.getWeekdays() );
                return "/schedule/editSection";
            }

            for( Section scheduledSection : sections )
            {
                if( scheduledSection.getId().equals( section.getId() ) )
                {
                    scheduledSection.setCourse( course );
                    scheduledSection.setOption( section.getOption() );
                    scheduledSection.setSectionNumber( section
                        .getSectionNumber() );
                    scheduledSection.setCallNumber( section.getCallNumber() );
                    scheduledSection.setLocation( section.getLocation() );
                    scheduledSection.setCapacity( section.getCapacity() );
                    scheduledSection.setUnits( section.getUnits() );
                    scheduledSection.setStartTime( section.getStartTime() );
                    scheduledSection.setEndTime( section.getEndTime() );
                    scheduledSection.setWeekDays( section.getWeekDays() );
                    scheduledSection.setInfo( section.getInfo() );
                }
            }

            scheduleDao.saveSchedule( schedule );

            logger.info( "User: "
                + userDao.getUserByUsername( SecurityUtils.getUsername() )
                    .getName() + " edited a section" );

            HttpSession httpSession = request.getSession();
            httpSession.setAttribute( "selectSchedule", schedule );

            return "redirect:../displaySchedules.html#ui-tabs-"
                + getTabIndex( schedule.getMajor().getSymbol() );
        }

        HttpSession httpSession = request.getSession();

        httpSession.removeAttribute( "weekDayError" );
        httpSession.removeAttribute( "startTimeError" );
        httpSession.removeAttribute( "endTimeError" );
        httpSession.removeAttribute( "courseError" );

        if( path.equals( "addSection" ) )
        {
            String courseError = null;
            String startTimeError = null;
            String endTimeError = null;
            String weekDayError = null;

            Quarter quarter = new Quarter( quarterCode );
            Major selectedMajor = null;
            if( fromCoursePlan != null )
            {
                selectedMajor = majorDao.getMajorBySymbol( major1 );
            }
            else
            {
                selectedMajor = majorDao.getMajorBySymbol( major );
            }
            Schedule schedule = scheduleDao.getScheduleByMajorAndQuarter(
                quarter, selectedMajor );

            Course course = courseDao.getCourseByCode( courseCode );

            Boolean hasError = false;
            if( course == null )
            {
                courseError = "Course entered does not exsit";
                hasError = true;

            }

            SimpleDateFormat df = new SimpleDateFormat( "hh:mm a" );
            try
            {
                df.parse( section.getStartTime() );
            }
            catch( ParseException e )
            {
                startTimeError = "Start time enterd is invalid";
                hasError = true;
            }

            try
            {
                df.parse( section.getEndTime() );
            }
            catch( ParseException e )
            {
                endTimeError = "End time enterd is invalid";
                hasError = true;

            }

            if( section.getWeekDays() == null )
            {
                weekDayError = "Please select week days";
                hasError = true;
            }

            if( hasError == true )
            {
                httpSession.setAttribute( "courseError", courseError );
                httpSession.setAttribute( "startTimeError", startTimeError );
                httpSession.setAttribute( "endTimeError", endTimeError );
                httpSession.setAttribute( "weekDayError", weekDayError );

                if( fromCoursePlan != null )
                {
                    return "redirect:/schedule/section/addSection.html?"
                        + "_page=" + _page + "&_target=" + _target
                        + "&quarterCode=" + quarterCode + "&fromCoursePlan="
                        + fromCoursePlan + "&major=" + major;
                }
                else
                {
                    return "redirect:/schedule/section/addSection.html?major="
                        + major;
                }
            }

            if( schedule == null )
            {
                schedule = new Schedule();
                schedule.setMajor( selectedMajor );
                schedule.setQuarter( quarter );
            }
            Set<Section> sections = schedule.getSections();
            section.setCourse( course );
            section.setInfo( info );
            sections.add( section );
            scheduleDao.saveSchedule( schedule );

            logger.info( "User: "
                + userDao.getUserByUsername( SecurityUtils.getUsername() )
                    .getName() + " added a section" );

            httpSession.setAttribute( "selectSchedule", schedule );

            if( fromCoursePlan != null )
            {

                CoursePlan coursePlan = (CoursePlan) httpSession
                    .getAttribute( "coursePlan" );

                List<Section> yellowSections = new ArrayList<Section>();
                List<Course> redCourses = new ArrayList<Course>();
                List<Course> blueCourses = new ArrayList<Course>();
                List<Section> greenSections = new ArrayList<Section>();
                List<Course> greyCourses = new ArrayList<Course>();

                User currentUser = coursePlan.getStudent();
                Set<Course> allCourses = new HashSet<Course>();
                Set<Course> GECourses = null;
                List<Course> coursesNeedToTake = new ArrayList<Course>();
                List<CourseTaken> coursesTaken = null;
                List<CourseWaived> coursesWaived = null;
                List<CourseTransferred> coursesTransferred = null;
                List<Course> takenCourses = new ArrayList<Course>();
                if( !coursePlan.getIsTemplate() )
                {

                    int majorId = currentUser.getMajor().getId();
                    Major currentMajor = majorDao.getMajorById( majorId );

                    allCourses.addAll( currentMajor.getCourses() );

                    GECourses = majorDao.getMajorBySymbol( "GENERAL" )
                        .getCourses();

                    allCourses.addAll( GECourses );

                    coursesTaken = courseTakenDao
                        .getNonrepeatableCourseTakenByStudent( currentUser );
                    coursesWaived = courseWaivedDao
                        .getNonrepeatableCourseWaivedsByStudent( currentUser );
                    coursesTransferred = courseTransferredDao
                        .getNonrepeatableCourseTransferredsByStudent( currentUser );

                }
                else
                {
                    Major selectedMajor1 = majorDao.getMajorById( coursePlan
                        .getMajor().getId() );
                    allCourses.addAll( selectedMajor1.getCourses() );
                    GECourses = majorDao.getMajorBySymbol( "GENERAL" )
                        .getCourses();
                    allCourses.addAll( GECourses );

                    coursesTaken = new ArrayList<CourseTaken>();
                    coursesWaived = new ArrayList<CourseWaived>();
                    coursesTransferred = new ArrayList<CourseTransferred>();
                }
                for( CourseTaken courseTaken : coursesTaken )
                {
                    takenCourses.add( courseTaken.getCourse() );
                }

                for( CourseWaived courseWaived : coursesWaived )
                {
                    takenCourses.add( courseWaived.getCourse() );
                }

                for( CourseTransferred courseTransferred : coursesTransferred )
                {
                    takenCourses.add( courseTransferred.getCourse() );
                }

                List<String> allCoursesStr = new ArrayList<String>();
                for( Course course1 : allCourses )
                {
                    allCoursesStr.add( course1.getCode() );
                }

                // compare to the courses that current user has taken, get the
                // CoursesNeedToTake
                List<String> courseNeedToTakeStr = new ArrayList<String>();
                courseNeedToTakeStr.addAll( allCoursesStr );
                for( Course takenCourse : takenCourses )
                {
                    String code = takenCourse.getCode();
                    if( allCoursesStr.contains( code ) )
                        courseNeedToTakeStr.remove( code );
                }

                // preCoursesStr stores the name of courses that user has taken
                Set<String> preCoursesStr = new HashSet<String>();
                for( Course takenCourse : takenCourses )
                {
                    preCoursesStr.add( takenCourse.getCode() );
                }

                // get quaterPlans's courses
                Set<QuarterPlan> quarterPlanList = coursePlan.getQuarterPlans();
                if( quarterPlanList != null && quarterCode != null )
                {
                    for( QuarterPlan quarterPlanTmp : quarterPlanList )
                    {
                        if( quarterPlanTmp.getQuarter().getCode() < quarterCode )
                        {

                            Set<Section> selectedSectionList = quarterPlanTmp
                                .getSections();
                            Set<Course> selectedCourseList = quarterPlanTmp
                                .getCourses();

                            if( selectedSectionList == null )
                                selectedSectionList = new HashSet<Section>();
                            if( selectedCourseList == null )
                                selectedCourseList = new HashSet<Course>();

                            for( Section selectedSection : selectedSectionList )
                            {
                                if( selectedSection.getCourse().isRepeatable() == false )
                                    preCoursesStr.add( selectedSection
                                        .getCourse().getCode() );
                                if( courseNeedToTakeStr
                                    .contains( selectedSection.getCourse()
                                        .getCode() )
                                    && selectedSection.getCourse()
                                        .isRepeatable() == false )
                                {
                                    courseNeedToTakeStr.remove( selectedSection
                                        .getCourse().getCode() );
                                }
                            }

                            for( Course selectedCourse : selectedCourseList )
                            {
                                if( selectedCourse.isRepeatable() == false )
                                    preCoursesStr
                                        .add( selectedCourse.getCode() );
                                if( courseNeedToTakeStr
                                    .contains( selectedCourse.getCode() )
                                    && selectedCourse.isRepeatable() == false )
                                {
                                    courseNeedToTakeStr.remove( selectedCourse
                                        .getCode() );
                                }
                            }
                        }
                    }
                }

                // get coursesNeedToTaken
                for( String courseCode1 : courseNeedToTakeStr )
                {
                    coursesNeedToTake.add( courseDao
                        .getCourseByCode( courseCode1 ) );
                }

                // compare the preCourses to coursesNeedToTaken, get result
                List<String> meetPreCoursesStr = new ArrayList<String>();
                HashMap<String, String> notMeetPreCoursesStr = new HashMap<String, String>();

                for( Course courseTmp : coursesNeedToTake )
                {
                    StringBuffer courseToTake = new StringBuffer();

                    // if preCourses has section course, then continue
                    if( preCoursesStr.contains( courseTmp.getCode() ) )
                        continue;
                    else
                    {

                        // get prerequisites course from course
                        Set<Prerequisite> prerequisites = courseTmp
                            .getPrerequisites();
                        Boolean isSatisfied = true;

                        for( Prerequisite prerequisite : prerequisites )
                        {

                            Set<Course> prereCourses = prerequisite
                                .getCourses();

                            // if prerequisites has form like course1/course2

                            if( prerequisite.getCourses().size() > 1 )
                            {
                                Boolean forPartialSat = false;
                                Integer preCounter = 1;

                                String sb = new String();
                                for( Course prereCourse1 : prereCourses )
                                {
                                    if( preCoursesStr.contains( prereCourse1
                                        .getCode() ) )
                                    {
                                        forPartialSat = true;

                                        break;
                                    }
                                    else
                                    {

                                        if( preCounter < prereCourses.size() )
                                            sb = sb + prereCourse1 + "/";
                                        else
                                            sb = sb + prereCourse1 + " ";
                                    }
                                    preCounter++;
                                }
                                if( forPartialSat == false )
                                {
                                    isSatisfied = false;
                                    courseToTake.append( sb );
                                }
                            }
                            else
                            {
                                for( Course prereCourse2 : prereCourses )
                                {
                                    if( !preCoursesStr.contains( prereCourse2
                                        .getCode() ) )
                                    {
                                        courseToTake
                                            .append( prereCourse2 + " " );
                                        isSatisfied = false;
                                    }
                                }

                            }

                        }
                        if( isSatisfied == true )
                        {
                            meetPreCoursesStr.add( courseTmp.getCode() );
                        }
                        else
                        {
                            notMeetPreCoursesStr.put( courseTmp.getCode(),
                                courseToTake.toString() );
                        }

                    }
                }

                Boolean isEmptySections = true;

                // get quarter plan
                QuarterPlan quarterPlan = (QuarterPlan) httpSession
                    .getAttribute( "quarterPlan" );
                // get next schedule

                Set<Section> availSections = new HashSet<Section>();

                List<Major> majors = majorDao.getMajors();

                for( Major major2 : majors )
                {
                    Schedule schedule1 = scheduleDao
                        .getScheduleByMajorAndQuarter( quarter, major2 );
                    if( schedule1 != null )
                        availSections.addAll( schedule1.getSections() );
                }

                if( availSections.size() > 0 )
                {
                    isEmptySections = false;
                }

                if( isEmptySections == true )
                {
                    String warning = "WARNING: The courses to be offered in "
                        + quarter
                        + " are not available, You are suggested"
                        + " to take following courses based on the course prerequisites and the courses you have or would have taken by this quarter. ";
                    model.addAttribute( "warning", warning );

                    // green courses
                    for( String meetCourseStr : meetPreCoursesStr )
                    {
                        Course course1 = courseDao
                            .getCourseByCode( meetCourseStr );
                        blueCourses.add( course1 );
                    }

                    // grey courses
                    Set<String> courseKeys = notMeetPreCoursesStr.keySet();

                    for( String courseCode1 : courseKeys )
                    {
                        String notMeetPres = notMeetPreCoursesStr
                            .get( courseCode1 );
                        Course course1 = courseDao
                            .getCourseByCode( courseCode1 );
                        course1.setExtraInfo( notMeetPres );
                        greyCourses.add( course1 );
                    }
                    model.addAttribute( "blueCourses", blueCourses );
                    model.addAttribute( "greyCourses", greyCourses );
                }

                // has schedule
                else
                {
                    // reclassify meetPreCourses to grey and red

                    List<String> unavailCoursesStr = new ArrayList<String>();
                    unavailCoursesStr.addAll( meetPreCoursesStr );
                    HashMap<String, String> notMeetNUnavailPreCourseStr = new HashMap<String, String>();
                    notMeetNUnavailPreCourseStr.putAll( notMeetPreCoursesStr );

                    for( Section sectionTmp : availSections )
                    {
                        // green or blue
                        if( meetPreCoursesStr.contains( sectionTmp.getCourse()
                            .getCode() ) )
                        {

                            greenSections.add( sectionTmp );

                            // yellow(Str)
                            unavailCoursesStr.remove( sectionTmp.getCourse()
                                .getCode() );
                        }

                        // reclassify notMeetPreCourse into Grey and yellow
                        // in schedule: red and grey(Str)
                        String notMeetPreCourseStr = notMeetPreCoursesStr
                            .get( sectionTmp.getCourse().getCode() );
                        if( notMeetPreCourseStr != null )
                        {
                            // red sections
                            sectionTmp.setExtraInfo( notMeetPreCourseStr );
                            // newSection.setExtraInfo( notMeetPreCourseStr );
                            yellowSections.add( sectionTmp );

                            // grey(Str)
                            notMeetNUnavailPreCourseStr.remove( sectionTmp
                                .getCourse().getCode() );
                        }
                    }

                    // red courses
                    for( String courseCode1 : unavailCoursesStr )
                    {
                        redCourses
                            .add( courseDao.getCourseByCode( courseCode1 ) );
                    }

                    // grey courses
                    Set<String> courseKeys = notMeetNUnavailPreCourseStr
                        .keySet();

                    for( String courseCode1 : courseKeys )
                    {
                        String notMeetPres = notMeetNUnavailPreCourseStr
                            .get( courseCode1 );
                        Course course1 = new Course();
                        course1.setCode( courseDao
                            .getCourseByCode( courseCode1 ).getCode() );
                        course1.setName( courseDao
                            .getCourseByCode( courseCode1 ).getName() );
                        course1.setExtraInfo( notMeetPres );
                        greyCourses.add( course1 );
                    }

                    model.addAttribute( "yellowSections", yellowSections );
                    model.addAttribute( "greenSections", greenSections );
                    model.addAttribute( "redCourses", redCourses );
                    model.addAttribute( "blueCourses", blueCourses );
                    model.addAttribute( "greyCourses", greyCourses );

                }

                if( courseNeedToTakeStr.size() == 0 )
                {
                    model.addAttribute( "currentPage", -1 );
                }
                else
                {
                    model.addAttribute( "currentPage", _page );
                }

                model.addAttribute( "targetPage", _target );
                model.addAttribute( "availSections", availSections );
                model.addAttribute( "quarterPlan", quarterPlan );
                model.addAttribute( "previousPage", _page - 1 );

                return "coursePlan/coursePlanForm";

            }

            return "redirect:../displaySchedules.html#ui-tabs-"
                + getTabIndex( schedule.getMajor().getSymbol() );

        }

        else
        {
            return "redirect:../displaySchedules.html";
        }

    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
            new SimpleDateFormat( "MM/dd/yyyy" ), true ) );
        binder.registerCustomEditor( Major.class, majorPropertyEditor );
        binder.registerCustomEditor( Quarter.class, quarterPropertyEditor );
        binder.registerCustomEditor( Course.class,
            courseGetSymbolPropertyEditor );
        binder.registerCustomEditor( WeekDay.class, weekDayPropertyEditor );
    }
}
