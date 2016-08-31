package cesar.spring.controller.appointment;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import cesar.model.AdvisorScheduleSection;
import cesar.model.AppointmentSection;
import cesar.model.AppointmentType;
import cesar.model.Block;
import cesar.model.ReasonForAppointment;
import cesar.model.Role;
import cesar.model.User;
import cesar.model.dao.AppointmentSectionDao;
import cesar.model.dao.AppointmentTypeDao;
import cesar.model.dao.BlockDao;
import cesar.model.dao.ReasonForAppointmentDao;
import cesar.model.dao.UserDao;
import cesar.spring.editor.AppointmentTypePropertyEditor;
import cesar.spring.editor.ReasonForAppointmentPropertyEditor;
import cesar.spring.editor.UserPropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentSectionDao              appointmentSectionDao;

    @Autowired
    private AppointmentTypeDao                 appointmentTypeDao;

    @Autowired
    private ReasonForAppointmentDao            reasonForAppointmentDao;

    @Autowired
    private AppointmentTypePropertyEditor      appointmentTypePropertyEditor;

    @Autowired
    private ReasonForAppointmentPropertyEditor reasonForAppointmentPropertyEditor;

    @Autowired
    private UserPropertyEditor                 userPropertyEditor;

    @Autowired
    private UserDao                            userDao;

    @Autowired
    private MailSender                         mailSender;

    @Autowired
    private SimpleMailMessage                  mailMessage;
    
    @Autowired
    private BlockDao                            blockDao;

    private Logger                             logger = LoggerFactory
                                                          .getLogger( AppointmentController.class );

    @RequestMapping(value = ("/appointment/{path}/viewAppointment.html"),
        method = RequestMethod.GET)
    public String formHandler( ModelMap model,
        @PathVariable("path") String path, Integer studentId )
    {
        User user = null;
        if( studentId == null )
            user = userDao.getUserByUsername( SecurityUtils.getUsername() );
        else
            user = userDao.getUserById( studentId );

        if( user == null )
        {
            return "redirect:/";
        }
        else
        {
            if( path.equals( "student" ) || path.equals( "advisor" ) )
            {
                Calendar currentDay = Calendar.getInstance();
                Date currentDate = currentDay.getTime();
                
                if( path.equals( "student" ) )
                {
                    List<AppointmentSection> futureAppointmentSections = appointmentSectionDao
                            .getAppointmentSectionsByStudentWithSpeDay( user,
                                currentDate );

                	Map<String, List<AppointmentSection>> appSections = new HashMap<String, List<AppointmentSection>>();
                	appSections.put("Future Appointments", futureAppointmentSections );
                    
                    model.addAttribute( "appointmentSections", appSections );
                	model.addAttribute( "isAdvisor", false );

                    if( studentId == null )
                        model.addAttribute( "advisorView", false );
                    else
                        model.addAttribute( "advisorView", true );
                }
                else if( path.equals( "advisor" ) )
                {
                    Date currentTime = new Date();
                    List<AppointmentSection> appointmentSections = new ArrayList<AppointmentSection>();
                    
                    if (studentId == null) {
                    	appointmentSections = appointmentSectionDao.getAvailableAppointmentSectionsByAdvisor( user );
                    } else {
                    	appointmentSections = appointmentSectionDao.getAvailableAppointmentSectionsByStudent(user);
                    }
                    
                    List<AppointmentSection> pastAppointmentSections = new ArrayList<AppointmentSection>();
                    List<AppointmentSection> futureAppointmentSections = new ArrayList<AppointmentSection>();
                    
                    for( AppointmentSection as : appointmentSections )
                    {
                        if( !as.getEndTime().after( currentTime ) )
                        	pastAppointmentSections.add( as );
                        else
                        	futureAppointmentSections.add( as );

                    }

                    if( appointmentSections.size() == 0 )
                    {
                        appointmentSections = null;
                    }
                    
                    Map<String, List<AppointmentSection>> appSections = new TreeMap<String, List<AppointmentSection>>();
                    appSections.put("Past Appointments", pastAppointmentSections );
                    appSections.put("Future Appointments", futureAppointmentSections );
                    
                    model.addAttribute( "appointmentSections", appSections );
                    model.addAttribute( "isAdvisor", true );
                }

                return "appointment/viewAppointment";
            }
            else
            {
                return "redirect:/cesar";
            }
        }
    }

    @RequestMapping(
        value = ("appointment/advisor/initialMakeAppointment.html"),
        method = RequestMethod.GET)
    public String initialMakeAppointmentHandler()
    {
        return "appointment/searchAppStudent";
    }

    @RequestMapping("/appointment/advisor/searchStudentForApp.html")
    public String search( String term, ModelMap model )
    {
        List<User> users = null;

        if( StringUtils.hasText( term ) )
            users = userDao.searchStudents( term.toLowerCase() );

        model.addAttribute( "term", term );
        model.addAttribute( "users", users );

        return "appointment/searchAppStudent";
    }

    @RequestMapping("/appointment/advisor/ajaxSearch.html")
    public void ajaxSearch( String term, HttpServletResponse response )
        throws JSONException, IOException
    {
        List<User> users = new ArrayList<User>();
        if( StringUtils.hasText( term ) )
            users = userDao.searchStudentsByPrefix( term.toLowerCase() );

        if( users.size() > 15 ) users.clear();

        response.setContentType( "application/json" );
        JSONArray jsonArray = new JSONArray();
        for( User user : users )
        {
            Map<String, String> json = new HashMap<String, String>();
            json.put( "label", user.getFirstName() + " " + user.getLastName() );
            json.put( "userId", user.getId().toString() );
            jsonArray.put( json );
        }
        jsonArray.write( response.getWriter() );
    }

    @RequestMapping(
        value = ("appointment/student/initialMakeAppointment.html"),
        method = RequestMethod.GET)
    public String AppHandler( ModelMap model, Integer startTime,
        Integer advisorId, Integer studentId, Integer id )
    {
        User student = null;
        if( studentId == null )
            student = userDao.getUserByUsername( SecurityUtils.getUsername() );
        else
            student = userDao.getUserById( studentId );

        User advisor = userDao.getUserById( advisorId );

        List<AppointmentSection> calAppSections = new ArrayList<AppointmentSection>();

        if( advisor == null )
        {
            model.addAttribute( "msgTitle", "Bad Request" );
            model.addAttribute( "msg",
                "The appointment you requested is invalid" );
            model.addAttribute( "backUrl",
                "/student/advisorSchedule/advisorsScheduleTable.html" );
            return "status";
        }
        else
        {
            Boolean validStartTime = false;
            Set<AdvisorScheduleSection> advisorScheduleSections = advisor
                .getAdvisorScheduleSections();
            for( AdvisorScheduleSection ass : advisorScheduleSections )
            {
                if( ass.getStartTime().intValue() == startTime.intValue() )
                {
                    validStartTime = true;
                    break;
                }
            }
            if( !validStartTime )
            {
                model.addAttribute( "msgTitle", "Bad Request" );
                model.addAttribute( "msg",
                    "The appointment you requested is invalid" );
                model.addAttribute( "backUrl",
                    "/student/advisorSchedule/advisorsScheduleTable.html" );
                return "status";
            }
            else
            {
            	List<Block> blocks = blockDao.getBlocksByAdvisor(advisor);
                
                Calendar currentCalendar = Calendar.getInstance();
                Calendar parsedCalendar = Calendar.getInstance();
                int parsedWeekDay = startTime / 1000;
                Integer parsedHour = startTime % 1000 / 10;
                Integer parsedMinute = startTime%10;
                if(parsedMinute == 1)
                {
                    parsedCalendar.set( Calendar.MINUTE, 30 );
                }else
                {
                    parsedCalendar.set( Calendar.MINUTE, 0 );
                }
                parsedCalendar.set( Calendar.HOUR_OF_DAY, parsedHour );
                

                parsedCalendar.set( Calendar.SECOND, 0 );
                parsedCalendar.set( Calendar.MILLISECOND, 0 );
                
                boolean flag = false;
                if( currentCalendar.get( Calendar.DAY_OF_WEEK ) - 1 == parsedWeekDay
                    && currentCalendar.before( parsedCalendar ) )
                {
                	
                	for (int i = 0; i < blocks.size(); i++) {
                		if ((parsedCalendar.equals(blocks.get(i).getStartDateTime()) || parsedCalendar.after(blocks.get(i).getStartDateTime())) && parsedCalendar.before(blocks.get(i).getEndDateTime())) {
                			flag = true;
                			break;
                		}
                	}
                	
                	if (flag == false) {
                		
                		List<AppointmentSection> as1 = this.findSpeStartTimeSchedule( startTime, parsedCalendar, advisor, student );
                    
                    	calAppSections.addAll( as1 );
                	}
                }
            
                Calendar tmpCurrentCalendar = (Calendar) parsedCalendar.clone();
                tmpCurrentCalendar.add( Calendar.DAY_OF_MONTH, 1 );

                Calendar sixtyDaysAfterCalendar = Calendar.getInstance();
                sixtyDaysAfterCalendar.add( Calendar.DAY_OF_MONTH, 60 );
                sixtyDaysAfterCalendar.set( Calendar.HOUR_OF_DAY, 22 );
                
                while( tmpCurrentCalendar.before( sixtyDaysAfterCalendar ) )
                {
                	boolean flag2 = false;
                	
                    int tmpWeekDay = tmpCurrentCalendar
                        .get( Calendar.DAY_OF_WEEK ) - 1;
                    if( tmpWeekDay == parsedWeekDay )
                    {
                    	for (int i = 0; i < blocks.size(); i++) {
                    		if ((tmpCurrentCalendar.equals(blocks.get(i).getStartDateTime()) || tmpCurrentCalendar.after(blocks.get(i).getStartDateTime())) && tmpCurrentCalendar.before(blocks.get(i).getEndDateTime())) {
                    			flag2 = true;
                    			break;
                    		}
                    	}
                    	
                    	if (flag2 == false) {
                    		
                        List<AppointmentSection> as2 = this.findSpeStartTimeSchedule( startTime, tmpCurrentCalendar, advisor, student );
                        
                        calAppSections.addAll( as2 );
                    	}
                      
                    }
                    tmpCurrentCalendar.add( Calendar.DAY_OF_MONTH, 1 );
                    
                }
                
                Boolean isChangeAdvisor = false;
                model.addAttribute( "isChangeAdvisor", isChangeAdvisor );
                model.addAttribute( "isFirstTime", true );
                model.addAttribute( "advisor", advisor );
                model.addAttribute( "appointmentSections", calAppSections );
                model.addAttribute( "studentId", studentId );
                model.addAttribute( "id", id );

                return "/appointment/initialMakeAppResult";

            }

        }
    }

    public List<AppointmentSection> findSpeStartTimeSchedule(
        Integer startTimeInt, Calendar calendar, User advisor, User student )
    {
        Calendar startCalendar = (Calendar) calendar.clone();
        Calendar endCalendar = (Calendar) calendar.clone();
        if( startCalendar.get( Calendar.MINUTE ) >= 30 )
        {
            endCalendar.add( Calendar.HOUR_OF_DAY, 1 );
            endCalendar.set( Calendar.MINUTE, 0 );
        }
        else
        {
            endCalendar.set( Calendar.MINUTE, 30 );
        }

        List<AppointmentSection> appointmentSections = appointmentSectionDao
            .getAppointmentSectionByAdvisorAndBetweenSpeDates( advisor,
                startCalendar.getTime(), endCalendar.getTime() );

        List<AppointmentSection> calculatedAppSections = new ArrayList<AppointmentSection>();
        if( appointmentSections.size() == 0 )
        {
            AppointmentSection section = new AppointmentSection();
            section.setAdvisor( advisor );
            section.setStudent( student );
            section.setStartTimeInt( startTimeInt );
            section.setStartTime( startCalendar.getTime() );

            Calendar tmpEndCal = (Calendar) startCalendar.clone();
            tmpEndCal.add( Calendar.MINUTE, 30 );
            section.setEndTime( tmpEndCal.getTime() );

            calculatedAppSections.add( section );

        }
        else
        {

            Calendar tmpStartCalendar = (Calendar) startCalendar.clone();

            Calendar tmpEndCalendar = (Calendar) endCalendar.clone();

            Boolean isAvailable = true;
            for( AppointmentSection as : appointmentSections )
            {

                if( ((as.getStartTime().compareTo( tmpStartCalendar.getTime() ) >= 0) && as
                    .getStartTime().before( tmpEndCalendar.getTime() ))
                    || (as.getEndTime().after( tmpStartCalendar.getTime() ) && (as
                        .getEndTime().before( tmpEndCalendar.getTime() ))) )
                {
                    isAvailable = false;
                    break;
                }
            }

            if( isAvailable == true )
            {
                AppointmentSection section = new AppointmentSection();
                section.setAdvisor( advisor );
                section.setStudent( student );
                section.setStartTimeInt( startTimeInt );
                section.setStartTime( startCalendar.getTime() );

                Calendar tmpEndCal = (Calendar) startCalendar.clone();
                tmpEndCal.add( Calendar.MINUTE, 30 );
                section.setEndTime( tmpEndCal.getTime() );

                calculatedAppSections.add( section );

            }

        }

        return calculatedAppSections;

    }

    @RequestMapping(value = ("appointment/makeAppointment.html"),
        method = RequestMethod.GET)
    public String formAppHandler( ModelMap model, Integer studentId )
    {
        User student = null;
        if( studentId == null )
            student = userDao.getUserByUsername( SecurityUtils.getUsername() );
        else
            student = userDao.getUserById( studentId );
        if( student.getCurrentAdvisor() == null )
        {
            if( studentId == null )
                return "redirect:/student/advisorSchedule/advisorsScheduleTable.html";
            else
                return "redirect:/student/advisorSchedule/advisorsScheduleTable.html?studentId="
                    + studentId;
        }
        else
        {
            if( studentId != null ){ return "redirect:/student/advisorSchedule/advisorsScheduleTable.html?studentId="
                + studentId; }
            User advisor = student.getCurrentAdvisor();

            List<AppointmentSection> sections = getAvailableSchedule( advisor,
                student );
            if( sections.size() == 0 )
            {
                List<User> advisors = userDao
                    .getUsersByRoleName( "ROLE_ADVISOR" );
                advisors.remove( advisor );
                Collections.sort( advisors, new Comparator<Object>() {

                    public int compare( Object a, Object b )
                    {
                        int orderA = (userDao.getUsersByAdvisor( (User) a ))
                            .size();
                        int orderB = (userDao.getUsersByAdvisor( (User) b ))
                            .size();
                        return orderA - orderB;
                    }
                } );

                for( User ad : advisors )
                {
                    List<AppointmentSection> sections1 = getAvailableSchedule(
                        ad, student );

                    if( sections1.size() > 0 )
                    {

                        Boolean isChangeAdvisor = true;
                        model.addAttribute( "isChangeAdvisor", isChangeAdvisor );
                        model.addAttribute( "isFirstTime", false );
                        model.addAttribute( "advisor", advisor );
                        model.addAttribute( "newAdvisor", ad );
                        model.addAttribute( "appointmentSections", sections1 );
                        
                        if( studentId != null )
                            model.addAttribute( "studentId", studentId );

                        return "/appointment/initialMakeAppResult";
                    }
                }
                model.addAttribute( "isChangeAdvisor", false );
                model.addAttribute( "isFirstTime", false );
                model.addAttribute( "advisor", advisor );
                model.addAttribute( "studentId", studentId );
                
                return "/appointment/initialMakeAppResult";
            }
            else
            {
                model.addAttribute( "appointmentSections", sections );
                model.addAttribute( "isChangeAdvisor", false );
                model.addAttribute( "isFirstTime", false );
                model.addAttribute( "studentId", studentId );
                
                return "/appointment/initialMakeAppResult";
            }

        }

    }

    public List<AppointmentSection> getAvailableSchedule( User advisor,
        User student )
    {
        Set<AdvisorScheduleSection> advisorScheduleSections = advisor
            .getAdvisorScheduleSections();
        List<AppointmentSection> appointmentSections = new ArrayList<AppointmentSection>();

        Calendar currentCalendar = Calendar.getInstance();
        Calendar endCalendar = (Calendar) currentCalendar.clone();
        endCalendar.add( Calendar.DAY_OF_MONTH, 14 );
        endCalendar.add( Calendar.HOUR_OF_DAY, 22 );

        List<Block> blocks = blockDao.getBlocksByAdvisor(advisor);
        
        while( currentCalendar.before( endCalendar ) )
        {
            Integer weekDayInt = currentCalendar.get( Calendar.DAY_OF_WEEK ) - 1;
            for( AdvisorScheduleSection asSection : advisorScheduleSections )
            {
                if( (asSection.getStartTime().intValue()) / 1000 == weekDayInt
                    .intValue() )
                {
                    Integer hour = (asSection.getStartTime().intValue() % 1000) / 10;
                    Integer minute = asSection.getStartTime().intValue() % 1000 % 100 % 10;
                    Calendar tmpCurrentCalendar = (Calendar) currentCalendar
                        .clone();
                    tmpCurrentCalendar.set( Calendar.HOUR_OF_DAY, hour );
                    if( minute.intValue() == 1 )
                    {
                        tmpCurrentCalendar.set( Calendar.MINUTE, 30 );
                    }
                    else
                    {
                        tmpCurrentCalendar.set( Calendar.MINUTE, 0 );
                    }
                    tmpCurrentCalendar.set( Calendar.SECOND, 0 );
                    tmpCurrentCalendar.set( Calendar.MILLISECOND, 0 );

                    boolean flag = false;

                    if( tmpCurrentCalendar.after( Calendar.getInstance() ) )
                    {
                    	for (int i = 0; i < blocks.size(); i++) {
                    		if ((tmpCurrentCalendar.equals(blocks.get(i).getStartDateTime()) || tmpCurrentCalendar.after(blocks.get(i).getStartDateTime())) && tmpCurrentCalendar.before(blocks.get(i).getEndDateTime())) {
                    			flag = true;
                    			break;
                    		}
                    	}
                    	
                    	if (flag == false) {
                    		List<AppointmentSection> as1 = this
                    				.findSpeStartTimeSchedule(
                    						asSection.getStartTime(), tmpCurrentCalendar,
                    						advisor, student );
                    		appointmentSections.addAll( as1 );
                    	}
                    }
                }
            }

            currentCalendar.add( Calendar.DAY_OF_YEAR, 1 );

        }
        Collections.sort( appointmentSections, new Comparator<Object>() {

            public int compare( Object a, Object b )
            {
                Date orderA = ((AppointmentSection) a).getStartTime();
                Date orderB = ((AppointmentSection) b).getStartTime();
                return orderA.compareTo( orderB );
            }
        } );
        return appointmentSections;
    }

    @SuppressWarnings("deprecation")
    @RequestMapping(value = ("appointment/initialMakeAppointment.html"),
        method = RequestMethod.POST)
    public String makeAppointmentHandler( ModelMap model, String date,
        Boolean isFirstTime )
    {
        Date currentDate = new Date();

        SimpleDateFormat df = new SimpleDateFormat( "MM/dd/yyyy" );
        String importDateForm = date;
        Date startTime = null;
        Calendar selectedCalendar = Calendar.getInstance();
        try
        {
            startTime = df.parse( importDateForm );
            if( currentDate.getDate() == startTime.getDate()
                && currentDate.getMonth() == startTime.getMonth()
                && currentDate.getYear() == startTime.getYear() )
            {
                startTime.setHours( currentDate.getHours() );
                startTime.setMinutes( currentDate.getMinutes() );
                startTime.setSeconds( currentDate.getSeconds() + 1 );
            }
            else
            {
                startTime.setHours( 0 );
                startTime.setMinutes( 0 );
                startTime.setSeconds( 0 );

            }
            selectedCalendar.setTime( startTime );
        }
        catch( Exception e1 )
        {
            model.addAttribute( "beginError", "The format of " + date
                + " is invalid!" );
            model.addAttribute( "date", date );
            model.addAttribute( "isFirstTime", false );
            return "appointment/initialMakeAppointment";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.DAY_OF_MONTH, 13 );
        calendar.set( Calendar.HOUR_OF_DAY, 21 );
        calendar.set( Calendar.MINUTE, 0 );
        calendar.set( Calendar.SECOND, 0 );

        if( startTime.compareTo( currentDate ) < 0
            || startTime.after( calendar.getTime() ) )
        {
            model.addAttribute( "beginError",
                "Date and begin time should between '" + currentDate.toString()
                    + "' and '" + calendar.getTime() + " ';" );
            model.addAttribute( "date", date );
            model.addAttribute( "isFirstTime", false );
            return "appointment/initialMakeAppointment";
        }

        Integer selectedWeekInt = selectedCalendar.get( Calendar.DAY_OF_WEEK );
        if( selectedWeekInt > 6 || selectedWeekInt < 2 )
        {

            model.addAttribute( "beginError", "Date should be a week day" );
            model.addAttribute( "date", date );
            model.addAttribute( "isFirstTime", false );
            return "appointment/initialMakeAppointment";
        }

        Integer parsedStartDay = (selectedWeekInt - 1) * 1000;

        List<User> advisors = (List<User>) userDao
            .getUsersByRoleName( "ROLE_ADVISOR" );

        List<AppointmentSection> appointmentSections = new ArrayList<AppointmentSection>();

        for( User advisor : advisors )
        {
            Set<AdvisorScheduleSection> advisorScheduleSections = advisor
                .getAdvisorScheduleSections();
            List<AppointmentSection> appSections = appointmentSectionDao
                .getAvailableAppointmentSectionsByAdvisor( advisor );

            for( AdvisorScheduleSection asSection : advisorScheduleSections )
            {
                if( asSection.getStartTime() > parsedStartDay
                    && asSection.getStartTime() < parsedStartDay + 1000 )
                {
                    Boolean isAvailable = true;
                    Boolean isNextAvailable = true;
                    for( AppointmentSection appSection : appSections )
                    {
                        if( appSection.getStartTimeInt().intValue() == asSection
                            .getStartTime().intValue()
                            && appSection.getStartTime().getDate() == startTime
                                .getDate()
                            && appSection.getStartTime().getMonth() == startTime
                                .getMonth()
                            && appSection.getStartTime().getYear() == startTime
                                .getYear() )
                        {
                            isAvailable = false;
                            break;
                        }

                    }
                    if( isAvailable == true )
                    {
                        AppointmentSection appointmentSection = new AppointmentSection();
                        appointmentSection.setAdvisor( advisor );

                        appointmentSection.setStartTimeInt( asSection
                            .getStartTime() );
                        Calendar tmpCalendar = Calendar.getInstance();
                        tmpCalendar = (Calendar) selectedCalendar.clone();
                        tmpCalendar.set( Calendar.HOUR_OF_DAY,
                            (asSection.getStartTime() % 1000) / 10 );
                        tmpCalendar.set( Calendar.MINUTE, 0 );
                        tmpCalendar.set( Calendar.SECOND, 0 );

                        appointmentSection.setStartTime( tmpCalendar.getTime() );

                        tmpCalendar.add( Calendar.MINUTE, 30 );
                        appointmentSection.setEndTime( tmpCalendar.getTime() );
                        appointmentSection.setIsAvailable( true );
                        appointmentSection.setIsWalkInAppointment( false );

                        if( tmpCalendar.getTime().after( currentDate ) )
                        {
                            appointmentSections.add( appointmentSection );
                        }

                    }

                    for( AppointmentSection appSection : appSections )
                    {
                        if( appSection.getStartTimeInt().intValue() == asSection
                            .getStartTime().intValue() + 1
                            && appSection.getStartTime().getDate() == startTime
                                .getDate()
                            && appSection.getStartTime().getMonth() == startTime
                                .getMonth()
                            && appSection.getStartTime().getYear() == startTime
                                .getYear() )
                        {
                            isNextAvailable = false;
                            break;
                        }

                    }

                    if( isNextAvailable == true )
                    {
                        AppointmentSection appointmentSection = new AppointmentSection();
                        appointmentSection.setAdvisor( advisor );

                        appointmentSection.setStartTimeInt( asSection
                            .getStartTime() + 1 );
                        Calendar tmpCalendar = Calendar.getInstance();
                        tmpCalendar = (Calendar) selectedCalendar.clone();
                        tmpCalendar.set( Calendar.HOUR_OF_DAY,
                            (asSection.getStartTime() % 1000) / 10 );
                        tmpCalendar.set( Calendar.MINUTE, 30 );
                        tmpCalendar.set( Calendar.SECOND, 0 );

                        appointmentSection.setStartTime( tmpCalendar.getTime() );

                        tmpCalendar.add( Calendar.MINUTE, 30 );
                        appointmentSection.setEndTime( tmpCalendar.getTime() );
                        appointmentSection.setIsAvailable( true );
                        appointmentSection.setIsWalkInAppointment( false );

                        if( tmpCalendar.getTime().after( currentDate ) )
                        {
                            appointmentSections.add( appointmentSection );
                        }

                    }

                }

            }
        }
        if( appointmentSections.size() == 0 )
        {
            appointmentSections = null;
        }
        else
        {
            Collections.sort( appointmentSections, new Comparator<Object>() {

                public int compare( Object a, Object b )
                {
                    Date orderA = ((AppointmentSection) a).getStartTime();
                    Date orderB = ((AppointmentSection) b).getStartTime();
                    return orderA.compareTo( orderB );
                }
            } );
        }
        model.addAttribute( "appointmentSections", appointmentSections );
        model.addAttribute( "isChangeAdvisor", false );
        model.addAttribute( "isFirstTime", isFirstTime );
        return "/appointment/initialMakeAppResult";
    }

    @RequestMapping(value = ("/appointment/{path}/cancelAppointment.html"))
    public String deleteAppointment( Integer id,
        @PathVariable("path") String path, Boolean advisorView, ModelMap model )
    {
        AppointmentSection as = appointmentSectionDao.getAppointmentById( id );
        User user = userDao.getUserByUsername( SecurityUtils.getUsername() );

        as.setIsAvailable( false );

        if( path.equals( "advisor" ) )
        {

            if( !(SecurityUtils.isUserInRole( "ROLE_ADVISOR" ) || SecurityUtils
                .isUserInRole( "ROLE_STAFF" )) )
            {
                model.addAttribute( "msgTitle", "Bad Request" );
                model.addAttribute( "msg",
                    "The appointment you requested is invalid" );
                return "status";
            }

            as.setCancelledByAdvisor( true );

        }

        if( path.equals( "student" ) )
        {

            if( user.getId().intValue() != as.getStudent().getId().intValue() )
            {
                model.addAttribute( "msgTitle", "Bad Request" );
                model.addAttribute( "msg",
                    "The appointment you requested is invalid" );
                return "status";
            }

            as.setCancelledByStudent( true );
        }

        appointmentSectionDao.saveAppointmentSection( as );
        logger.info( "User " + user.getName() + " cancelled an appointment" );

        List<User> recipients = new ArrayList<User>();
        recipients.add( as.getAdvisor() );
        recipients.add( as.getStudent() );
        mailMessage.setSubject( "CESAR appointment" );
        mailMessage.setText( "The appointment starts from " + as.getStartTime()
                + " to " + as.getEndTime() + " with advisor :"
                + as.getAdvisor().getFirstName() + " " + as.getAdvisor().getLastName() + " and student: "
                + as.getStudent().getFirstName() + " " + as.getStudent().getLastName() + " has been cancelled." );

        boolean errorOccurred = false;
        List<String> failedAddresses = new ArrayList<String>();
        for( User recipient : recipients )
        {
            mailMessage.setTo( recipient.getEmail() );
            try
            {
                mailSender.send( mailMessage );
            }
            catch( MailException e )
            {
                errorOccurred = true;
                failedAddresses.add( recipient.getEmail() + " ("
                    + recipient.getName() + ")" );
                e.printStackTrace();
            }
        }

        if( !errorOccurred )
        {
            model.addAttribute( "msgTitle", "Status" );
            model.addAttribute( "msg", "Appointment has been cancelled" );
            if( path.equals( "advisor" ) )
                if( advisorView != null )
                {
                    if( advisorView )
                    {
                        model.addAttribute( "backUrl",
                            "/appointment/student/viewAppointment.html?studentId="
                                + as.getStudent().getId() );
                    }
                    else
                    {
                        model.addAttribute( "backUrl",
                            "/appointment/advisor/viewAppointment.html" );
                    }
                }
                else
                {
                    model.addAttribute( "backUrl",
                        "/appointment/advisor/viewAppointment.html" );
                }
            if( path.equals( "student" ) )
                model.addAttribute( "backUrl",
                    "/appointment/student/viewAppointment.html" );
            
            if( path.equals( "staff" ) )
                model.addAttribute( "backUrl",
                    "/appointment/staff/viewAppointmentWithSpecificDate.html" );
            return "status";

        }
        else
        {
            model.addAttribute( "failedAddresses", failedAddresses );
            return "exception/email";
        }

    }

    @RequestMapping(value = ("/appointment/initialMakeAppointmentSubmit.html"),
        method = RequestMethod.POST)
    public String submitInitialMakeAppointment( Integer startTimeInt,
        Integer advisorId, Integer studentId, String startTime, String endTime,
        Boolean isChangeAdvisor, Boolean isWalkIn, ModelMap model, Integer id )
    {
        User student;
        if( studentId == null )
            student = userDao.getUserByUsername( SecurityUtils.getUsername() );
        else
            student = userDao.getUserById( studentId );

        User advisor = userDao.getUserById( advisorId );

        SimpleDateFormat df = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );
        Date startDate = null;
        Date endDate = null;
        try
        {
            startDate = df.parse( startTime );
        }
        catch( Exception e )
        {
            model.addAttribute( "msgTitle", "Request Error" );
            model
                .addAttribute( "msg", "Start date and time format are invalid" );
            return "status";
        }

        try
        {
            endDate = df.parse( endTime );
        }
        catch( Exception e )
        {
            model.addAttribute( "msgTitle", "Request Error" );
            model.addAttribute( "msg", "End date and time format are invalid" );
            return "status";
        }
        
        AppointmentSection as = null;
        if (id == null) {
        	as = new AppointmentSection();
        	as.setAdvisor( advisor );
        	as.setStartTimeInt( startTimeInt );
        	as.setStudent( student );
        	as.setIsAvailable( true );
        	as.setIsShowUp( true );
        	as.setIsWalkInAppointment( false );
        	as.setEndTime( endDate );
        	as.setStartTime( startDate );
        } else {
        	as = appointmentSectionDao.getAppointmentById(id);
            as.setAdvisor( advisor );
            as.setStartTimeInt( startTimeInt );
            as.setStudent( student );
            as.setIsAvailable( true );
            as.setIsShowUp( true );
            as.setIsWalkInAppointment( false );
            as.setEndTime( endDate );
            as.setStartTime( startDate );
        }

        model.addAttribute( "as", as );
        if( studentId != null )
            model.addAttribute( "isAdvisor", true );
        else
            model.addAttribute( "isAdvisor", false );

        model.addAttribute( "appointmentTypes",
            appointmentTypeDao.getAppointmentTypes() );
        model.addAttribute( "reasonsForAppointment",
            reasonForAppointmentDao.getReasonsForAppointment() );
        model.addAttribute( "saveApp", true );
        return "/appointment/editStudentAppointment";
    }

    @RequestMapping(value = ("/appointment/student/editAppointment.html"),
        method = RequestMethod.POST)
    public String editAppointmentFormStudent( AppointmentSection as,
        Boolean isAdvisor, ModelMap model )
    {
        Boolean isNewAppSection = null;
        if( as.getId() == null )
            isNewAppSection = true;
        else
            isNewAppSection = false;

        if( isAdvisor == null )
        {
            AppointmentType appType = appointmentTypeDao
                .getAppointmentTypeById( 200 );
            as.setAppointmentType( appType );

        }
        else
        {
            if( !isAdvisor )
            {
                AppointmentType appType = appointmentTypeDao
                    .getAppointmentTypeById( 200 );
                as.setAppointmentType( appType );
            }
        }
        as.setTitle( "Appointment" );
        appointmentSectionDao.saveAppointmentSection( as );

        User student = as.getStudent();
        User advisor = as.getAdvisor();

        List<AppointmentSection> studentAss = student.getAppointmentSections();
        if (!studentAss.contains(as))
        	studentAss.add( as );
        
        List<AppointmentSection> advisorAss = advisor.getAppointmentSections();
        if (!advisorAss.contains(as))
        	advisorAss.add( as );

        if( as.getStudent().getCurrentAdvisor() == null )
        {
            as.getStudent().setCurrentAdvisor( advisor );
        }
        userDao.saveUser( student );
        userDao.saveUser( advisor );

        logger.info( "User "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " updated an appointment" );

        if( isNewAppSection == false )
        {
            model.addAttribute( "msgTitle", "Status" );
            model.addAttribute( "msg", "Appointment has been updated" );
            model.addAttribute( "backUrl",
                "/appointment/student/viewAppointment.html" );
            return "status";

        }

        List<User> recipients = new ArrayList<User>();
        recipients.add( as.getAdvisor() );
        recipients.add( as.getStudent() );
        mailMessage.setSubject( "CESAR Appointment" );
        mailMessage.setText( "The appointment start from " + as.getStartTime()
                + " to " + as.getEndTime() + " with advisor :"
                + as.getAdvisor().getFirstName() + " " + as.getAdvisor().getLastName() + " and student: "
                + as.getStudent().getFirstName() + " " + as.getStudent().getLastName() 
                + ", CIN: " + as.getStudent().getCin() + ", and Quarter Admitted: " 
                + as.getStudent().getQuarterAdmitted() + " has been added." );

        boolean errorOccurred = false;
        List<String> failedAddresses = new ArrayList<String>();
        for( User recipient : recipients )
        {
            mailMessage.setTo( recipient.getEmail() );
            try
            {
                mailSender.send( mailMessage );
            }
            catch( MailException e )
            {
                errorOccurred = true;
                failedAddresses.add( recipient.getEmail() + " ("
                    + recipient.getName() + ")" );
                e.printStackTrace();
            }
        }

        if( !errorOccurred )
        {
            model.addAttribute( "msgTitle", "Status" );
            model.addAttribute( "msg", "Appointment has been added" );
            if( isAdvisor )
            {
                model.addAttribute( "backUrl",
                    "/appointment/student/viewAppointment.html?studentId="
                        + as.getStudent().getId() );
            }
            else
            {
                model.addAttribute( "backUrl",
                    "/appointment/student/viewAppointment.html" );
            }
            return "status";
        }
        else
        {
            model.addAttribute( "failedAddresses", failedAddresses );
            return "exception/email";
        }

    }

    @RequestMapping(value = ("/appointment/{path}/updateAppSchedule.html"),
        method = RequestMethod.GET)
    public String updateAppointmentForm( Integer id, Boolean advisorView,
        @PathVariable("path") String path, ModelMap model )
    {
        AppointmentSection as = appointmentSectionDao.getAppointmentById( id );
        if( as == null )
        {
            model.addAttribute( "msgTitle", "Request Error" );
            model.addAttribute( "msg",
                "The appointment you request does not exist" );
            return "status";
        }
        else
        {
            User currentUser = userDao.getUserByUsername( SecurityUtils
                .getUsername() );

            if( path.equals( "advisor" ) )
            {

                model.addAttribute( "as", as );
                model.addAttribute( "appointmentTypes",
                    appointmentTypeDao.getAppointmentTypes() );
                model.addAttribute( "reasonsForAppointment",
                    reasonForAppointmentDao.getReasonsForAppointment() );
                if( advisorView != null )
                {
                    if( advisorView == true )
                        model.addAttribute( "advisorView", advisorView );
                }

                return "/appointment/editAdvisorAppointment";

            }

            if( path.equals( "student" ) )
            {
                User asStudent = as.getStudent();
                Set<Role> roles = currentUser.getRoles();
                Boolean isAdvisor = false;
                for( Role role : roles )
                {
                    if( role.getName().equals( "ROLE_ADVISOR" )
                        || role.getName().equals( "ROLE_STAFF" ) )
                        isAdvisor = true;
                }
                if( currentUser.getId().intValue() == asStudent.getId()
                    .intValue() || isAdvisor )
                {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add( Calendar.MINUTE, -10 );

                    if( as.getStartTime().before( calendar.getTime() ) )
                    {
                        model.addAttribute( "isOldOne", true );
                    }
                    else
                    {
                        model.addAttribute( "isOldOne", false );
                    }
                    model.addAttribute( "as", as );
                    model.addAttribute( "reasonsForAppointment",
                        reasonForAppointmentDao.getReasonsForAppointment() );
                    
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String[] startTime = df.format(as.getStartTime()).split(" ");
                    String[] startDate = startTime[0].split("/");
                    Integer month = Integer.parseInt(startDate[0]);
                    Integer day = Integer.parseInt(startDate[1]);
                    Integer year = Integer.parseInt(startDate[2]);
                    model.addAttribute( "month", month );
                    model.addAttribute( "day", day );
                    model.addAttribute( "year", year );
                    
                    return "/appointment/editStudentAppointment";
                }
            }
            model.addAttribute( "msgTitle", "Bad Request" );
            model.addAttribute( "msg",
                "The appointment you requested is invalid" );
            return "status";
        }

    }

    @RequestMapping(value = ("/appointment/advisor/updateAppSchedule.html"),
        method = RequestMethod.POST)
    public String updateAppointmentFormSubmit( Integer id, String startDate1,
        String endDate1, String startTime1, String endTime1,
        AppointmentSection as, Boolean advisorView, ModelMap model, String notes )
        throws IOException
    {

        AppointmentSection as1 = appointmentSectionDao.getAppointmentById( id );
        Boolean hasError = false;
        Date start = null;
        Date end = null;
        SimpleDateFormat df = new SimpleDateFormat( "MM/dd/yyyy HH:mm" );
        try
        {
            start = df.parse( startDate1 + " " + startTime1 );
        }
        catch( Exception e1 )
        {
            hasError = true;
            model.addAttribute( "startError", "The format of " + startDate1
                + " " + startTime1 + " is invalid!" );
        }

        try
        {
            end = df.parse( endDate1 + " " + endTime1 );
        }
        catch( Exception e )
        {
            hasError = true;
            model.addAttribute( "endError", "The format of " + endDate1 + " "
                + endTime1 + " is invalid!" );
        }

        if( hasError == false )
        {
            if( start.after( end ) )
            {
                hasError = true;
                model.addAttribute( "error", "Start time: " + start
                    + " can not after end time: " + end );
            }
        }

        if( hasError == true )
        {
            model.addAttribute( "as", as );
            model.addAttribute( "appointmentTypes",
                appointmentTypeDao.getAppointmentTypes() );
            model.addAttribute( "reasonsForAppointment",
                reasonForAppointmentDao.getReasonsForAppointment() );

            return "/appointment/editAdvisorAppointment";
        }
        else
        {
            Boolean timeChanged = false;
            if( as1.getStartTime().compareTo( start ) != 0 )
                timeChanged = true;
            if( as1.getEndTime().compareTo( end ) != 0 ) timeChanged = true;

            as1.setAppointmentType( as.getAppointmentType() );
            as1.setReasonForAppointment( as.getReasonForAppointment() );
            as1.setStartTime( start );
            as1.setEndTime( end );
            as1.setNotes(notes);
            Boolean isShowUp = as.getIsShowUp();
            as1.setIsShowUp( isShowUp );
            
          //no show up means not available, which means not on the list anymore!

            if( !isShowUp ) as1.setIsAvailable( false );

            appointmentSectionDao.saveAppointmentSection( as1 );

            logger
                .info( "User: "
                    + userDao.getUserByUsername( SecurityUtils.getUsername() )
                        .getName()
                    + " cancelled an appointment due to the absence of the student " );

            boolean errorOccurred = false;

            if( timeChanged || !isShowUp )
            {

                List<User> recipients = new ArrayList<User>();
                recipients.add( as1.getAdvisor() );
                recipients.add( as1.getStudent() );
                mailMessage.setSubject( "CESAR Appointment" );
                if( !isShowUp )
                {
                	mailMessage
                    .setText( "The appointment with "
                        + as1.getAdvisor().getFirstName() + " " + as1.getAdvisor().getLastName() 
                        + " has been cancelled due to your absence."
                        + " It is important that you make another appointment." 
                    	+ " Please stop by the ECST Advisement & Recruitment Center (A-125)" 
                    	+ " to make another appointment or call 323-343-4574.");
                }
                else if( timeChanged )
                {
                	mailMessage
                    .setText( "The time of appointment with advisor :"
                        + as1.getAdvisor().getFirstName() + " " + as1.getAdvisor().getLastName()
                        + " and student: "
                        + as1.getStudent().getFirstName() + " " + as1.getStudent().getLastName()
                        + " has been changed to : start from "
                        + as1.getStartTime() + " to " + as1.getEndTime()
                        + "." );

                }

                List<String> failedAddresses = new ArrayList<String>();
                for( User recipient : recipients )
                {
                    mailMessage.setTo( recipient.getEmail() );
                    try
                    {
                        mailSender.send( mailMessage );
                    }
                    catch( MailException e )
                    {
                        errorOccurred = true;
                        failedAddresses.add( recipient.getEmail() + " ("
                            + recipient.getName() + ")" );
                        e.printStackTrace();
                    }
                }

                if( errorOccurred )
                {
                    model.addAttribute( "failedAddresses", failedAddresses );
                    return "exception/email";
                }

            }

            if( advisorView != null )
            {
                if( advisorView == true ){ return "redirect:/appointment/student/viewAppointment.html?studentId="
                    + as1.getStudent().getId(); }
            }
            return "redirect:viewAppointment.html";

        }
    }

    /*************************
     * walk in appointment****
     *************************/

    @RequestMapping(value = ("/appointment/advisor/walkInAppointment.html"),
        method = RequestMethod.GET)
    public String walkInAppMethod( ModelMap model )
    {
        Calendar currentCalendar = Calendar.getInstance();

        User currentUser = userDao.getUserByUsername( SecurityUtils
            .getUsername() );
        Set<Role> roles = currentUser.getRoles();
        Integer parsedWeekDayInt = currentCalendar.get( Calendar.DAY_OF_WEEK ) - 1;
        Integer parsedHourInt = currentCalendar.get( Calendar.HOUR_OF_DAY );
        Integer parsedMinuteInt = currentCalendar.get( Calendar.MINUTE );
        Map<String, List<AppointmentSection>> walkInAppSections = new HashMap<String, List<AppointmentSection>>();

        for( Role role : roles )
        {
            if( role.getName().equals( "ROLE_STAFF" ) )
            {
                List<User> advisors = userDao
                    .getUsersByRoleName( "ROLE_ADVISOR" );

                for( User advisor : advisors )
                {
                    Set<AdvisorScheduleSection> asSections = advisor
                        .getAdvisorScheduleSections();
                    List<AppointmentSection> appSectionsForMap = new ArrayList<AppointmentSection>();
                    for( AdvisorScheduleSection ass : asSections )
                    {
                        if( ass.getStartTime().intValue() / 1000 == parsedWeekDayInt
                            .intValue() )
                        {
                            if( parsedHourInt.intValue() * 10
                                + (parsedMinuteInt / 30) == ass.getStartTime()
                                .intValue() % 1000 )
                            {
                                List<AppointmentSection> calAppSections = getWalkInAppointments(
                                    advisor, currentCalendar, ass );
                                if( calAppSections.size() == 0 )
                                {
                                    calAppSections = null;
                                }
                                else
                                {
                                    appSectionsForMap.addAll( calAppSections );
                                }

                            }
                            else if( parsedHourInt.intValue() * 10
                                + (parsedMinuteInt / 30) < ass.getStartTime()
                                .intValue() % 1000 )
                            {
                                Calendar startCalendar = Calendar.getInstance();
                                startCalendar.set( Calendar.HOUR_OF_DAY,
                                    ass.getStartTime() % 1000 / 10 );

                                if( ass.getStartTime() % 1000 % 100 % 10 == 1 )
                                {
                                    startCalendar.set( Calendar.MINUTE, 30 );
                                }
                                else
                                {
                                    startCalendar.set( Calendar.MINUTE, 0 );
                                }

                                startCalendar.set( Calendar.SECOND, 0 );
                                startCalendar.set( Calendar.MILLISECOND, 0 );
                                List<AppointmentSection> calAppSections = getWalkInAppointments(
                                    advisor, startCalendar, ass );

                                appSectionsForMap.addAll( calAppSections );

                            }
                        }
                    }
                    Collections.sort( appSectionsForMap,
                        new Comparator<Object>() {

                            public int compare( Object a, Object b )
                            {
                                Date orderA = ((AppointmentSection) a)
                                    .getStartTime();
                                Date orderB = ((AppointmentSection) b)
                                    .getStartTime();
                                return orderA.compareTo( orderB );
                            }
                        } );

                    if( appSectionsForMap.size() != 0 )
                    {
                        walkInAppSections.put( advisor.getFirstName() + " "
                            + advisor.getLastName(), appSectionsForMap );
                    }

                }
                model.addAttribute( "appointmentSections", walkInAppSections );
                model.addAttribute( "isChangeAdvisor", false );
                model.addAttribute( "isStaff", true );

                return "/appointment/displayWalkInAppointments";
            }
        }

        Set<AdvisorScheduleSection> asSections = currentUser
            .getAdvisorScheduleSections();
        List<AppointmentSection> appSectionsForMap = new ArrayList<AppointmentSection>();
        for( AdvisorScheduleSection ass : asSections )
        {
            if( ass.getStartTime().intValue() / 1000 == parsedWeekDayInt
                .intValue() )
            {
                if( parsedHourInt.intValue() * 10 + (parsedMinuteInt / 30) == ass
                    .getStartTime().intValue() % 1000 )
                {
                    List<AppointmentSection> calAppSections = getWalkInAppointments(
                        currentUser, currentCalendar, ass );
                    if( calAppSections.size() == 0 )
                    {
                        calAppSections = null;
                    }
                    else
                    {
                        appSectionsForMap.addAll( calAppSections );
                    }

                }
                else if( parsedHourInt.intValue() * 10 + (parsedMinuteInt / 30) < ass
                    .getStartTime().intValue() % 1000 )
                {
                    Calendar startCalendar = Calendar.getInstance();
                    startCalendar.set( Calendar.HOUR_OF_DAY,
                        ass.getStartTime() % 1000 / 10 );

                    if( ass.getStartTime() % 1000 % 100 % 10 == 1 )
                    {
                        startCalendar.set( Calendar.MINUTE, 30 );
                    }
                    else
                    {
                        startCalendar.set( Calendar.MINUTE, 0 );
                    }

                    startCalendar.set( Calendar.SECOND, 0 );
                    startCalendar.set( Calendar.MILLISECOND, 0 );
                    List<AppointmentSection> calAppSections = getWalkInAppointments(
                        currentUser, startCalendar, ass );
                    if( calAppSections.size() == 0 )
                    {
                        calAppSections = null;
                    }
                    else
                    {
                        appSectionsForMap.addAll( calAppSections );
                    }
                }
            }
        }
        Collections.sort( appSectionsForMap, new Comparator<Object>() {

            public int compare( Object a, Object b )
            {
                Date orderA = ((AppointmentSection) a).getStartTime();
                Date orderB = ((AppointmentSection) b).getStartTime();
                return orderA.compareTo( orderB );
            }
        } );

        walkInAppSections.put(
            currentUser.getFirstName() + " " + currentUser.getLastName(),
            appSectionsForMap );

        model.addAttribute( "appointmentSections", walkInAppSections );
        model.addAttribute( "isChangeAdvisor", false );
        model.addAttribute( "isStaff", false );

        return "/appointment/displayWalkInAppointments";

    }

    @SuppressWarnings("deprecation")
    public List<AppointmentSection> getWalkInAppointments( User advisor,
        Calendar calendar, AdvisorScheduleSection ass )
    {
        Calendar startCalendar = (Calendar) calendar.clone();
        Calendar endCalendar = (Calendar) calendar.clone();

        Integer parsedMinInt = calendar.get( Calendar.MINUTE );
        Integer startHour = startCalendar.get( Calendar.HOUR_OF_DAY );

        if( parsedMinInt.intValue() >= 30 )
        {
            endCalendar.set( Calendar.HOUR_OF_DAY, startHour + 1 );
            endCalendar.set( Calendar.MINUTE, 0 );
        }
        else
        {
            endCalendar.set( Calendar.HOUR_OF_DAY, startHour );
            endCalendar.set( Calendar.MINUTE, 30 );
        }

        endCalendar.set( Calendar.SECOND, 0 );
        endCalendar.set( Calendar.MILLISECOND, 0 );

        long startMilliSecond = startCalendar.getTimeInMillis();
        long endMilliSecond = endCalendar.getTimeInMillis();
        List<AppointmentSection> appointmentSections = appointmentSectionDao
            .getAppointmentSectionByAdvisorAndBetweenSpeDates( advisor,
                startCalendar.getTime(), endCalendar.getTime() );
        List<AppointmentSection> calculatedAppSections = new ArrayList<AppointmentSection>();
        if( appointmentSections.size() == 0
            && (endMilliSecond - startMilliSecond) >= (1000 * 60 * 2) )
        {
            AppointmentSection section = new AppointmentSection();
            section.setAdvisor( advisor );

            section.setStartTime( startCalendar.getTime() );
            section.setEndTime( endCalendar.getTime() );
            if( startCalendar.get( Calendar.MINUTE ) >= 30 )
            {
                Integer startTimeInt = ass.getStartTime() + 1;
                section.setStartTimeInt( startTimeInt );
            }
            else
            {
                section.setStartTimeInt( ass.getStartTime() );
            }

            calculatedAppSections.add( section );

        }

        Date startTime = startCalendar.getTime();
        Date endTime = endCalendar.getTime();

        for( int i = 0; i < appointmentSections.size(); i++ )
        {
            AppointmentSection tmpAppSection = appointmentSections.get( i );
            if( startTime.before( tmpAppSection.getStartTime() ) )
            {
                Calendar tmpStartTimeCalendar = Calendar.getInstance();
                Calendar tmpAppStartTimeCalendar = Calendar.getInstance();
                tmpStartTimeCalendar.setTime( startTime );
                tmpAppStartTimeCalendar.setTime( tmpAppSection.getStartTime() );
                long milliseconds1 = tmpStartTimeCalendar.getTimeInMillis();
                long milliseconds2 = tmpAppStartTimeCalendar.getTimeInMillis();
                if( milliseconds2 - milliseconds1 > (1000 * 60 * 2) )
                {
                    AppointmentSection section = new AppointmentSection();
                    section.setAdvisor( advisor );

                    if( startTime.getMinutes() < 30 )
                    {
                        section.setStartTimeInt( startTime.getDay() * 1000
                            + startTime.getHours() * 10 );
                    }
                    else
                    {
                        section.setStartTimeInt( startTime.getDay() * 1000
                            + startTime.getHours() * 10 + 1 );
                    }
                    section.setStartTime( startTime );
                    section.setEndTime( appointmentSections.get( i )
                        .getStartTime() );
                    calculatedAppSections.add( section );

                }

                startTime = (Date) tmpAppSection.getEndTime().clone();

                if( startTime.after( endTime ) )
                {
                    break;
                }

            }
            if( startTime.after( tmpAppSection.getStartTime() )
                && tmpAppSection.getEndTime().after( startTime ) )
            {
                if( tmpAppSection.getEndTime().before( endTime ) )
                {
                    startTime = (Date) tmpAppSection.getEndTime().clone();
                }
                else
                {
                    break;
                }
            }
            if( startTime.equals( tmpAppSection.getStartTime() ) )
            {
                if( tmpAppSection.getEndTime().before( endTime ) )
                {
                    startTime = (Date) tmpAppSection.getEndTime().clone();
                }
                else
                {
                    break;
                }
            }

            if( i + 1 == appointmentSections.size()
                && tmpAppSection.getEndTime().before( endTime ) )
            {
                long millisecond3 = endCalendar.getTimeInMillis();
                Calendar appEndCalendar = Calendar.getInstance();
                appEndCalendar.setTime( tmpAppSection.getEndTime() );
                long millisecond4 = appEndCalendar.getTimeInMillis();

                if( (millisecond3 - millisecond4) > (1000 * 60 * 2) )
                {
                    AppointmentSection section = new AppointmentSection();
                    section.setAdvisor( advisor );

                    if( startTime.getMinutes() < 30 )
                    {
                        section.setStartTimeInt( ass.getStartTime() );
                    }
                    else
                    {
                        section.setStartTimeInt( ass.getStartTime() + 1 );
                    }
                    section.setStartTime( tmpAppSection.getEndTime() );
                    section.setEndTime( endTime );
                    calculatedAppSections.add( section );
                }

            }
        }

        return calculatedAppSections;
    }

    @RequestMapping(
        value = ("/appointment/advisor/walkInAppointmentConfirm.html"))
    public String walkInAppConfirm( Integer advisorId, String startTime,
        Boolean fromStaff, String endTime, ModelMap model )
        throws ParseException
    {

        User advisor = userDao.getUserById( advisorId );
        SimpleDateFormat df = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );
        Date start = df.parse( startTime );
        Date end = df.parse( endTime );

        model.addAttribute( "as", new AppointmentSection() );
        model.addAttribute( "reasonsForAppointment",
            reasonForAppointmentDao.getReasonsForAppointment() );
        model.addAttribute( "advisor", advisor );
        model.addAttribute( "startTime", start );
        model.addAttribute( "fromStaff", fromStaff );
        model.addAttribute( "endTime", end );
        model.addAttribute( "isUpdate", false );

        return "appointment/addWalkInAppointment";
    }

    @SuppressWarnings("deprecation")
    @RequestMapping(value = ("/appointment/advisor/addWalkInAppointment.html"),
        method = RequestMethod.POST)
    public String addWalkInApp( Integer asId, Integer advisorId,
        String startDate, String endDate, String startTime1, String endTime1,
        String cin, ModelMap model, Boolean advisorView, AppointmentSection as )
    {
        AppointmentSection savedAs = null;
        Boolean isShowUp = true;
        Boolean isTimeChanged = false;

        if( asId != null )
        {
            savedAs = appointmentSectionDao.getAppointmentById( asId );
        }
        User advisor = userDao.getUserById( advisorId );
        Boolean hasError = false;
        Date start = null;
        Date end = null;
        SimpleDateFormat df = new SimpleDateFormat( "MM/dd/yyyy HH:mm" );

        try
        {
            start = df.parse( startDate + " " + startTime1 );
        }
        catch( Exception e1 )
        {
            hasError = true;
            model.addAttribute( "startError", "The format of " + startDate
                + " " + startTime1 + " is invalid!" );
        }

        try
        {
            end = df.parse( endDate + " " + endTime1 );
        }
        catch( Exception e )
        {
            hasError = true;
            model.addAttribute( "endError", "The format of " + endDate + " "
                + endTime1 + " is invalid!" );
        }

        User student = userDao.getUserByCin( cin );

        if( student == null )
        {
            hasError = true;
            model.addAttribute( "cinError", "The cin : " + cin + " is invalid" );
        }

        if( hasError == false )
        {
            if( start.after( end ) )
            {
                hasError = true;
                model.addAttribute( "error", "Start time: " + start
                    + " can not after end time: " + end );
            }
        }

        if( hasError == true )
        {
            model.addAttribute( "as", as );
            model.addAttribute( "advisor", advisor );
            model.addAttribute( "startTime", start );
            model.addAttribute( "endTime", end );
            model.addAttribute( "cin", cin );
            model.addAttribute( "reasonsForAppointment",
                reasonForAppointmentDao.getReasonsForAppointment() );
            model.addAttribute( "isUpdate", false );
            model.addAttribute( "advisorView", advisorView );
            return "appointment/addWalkInAppointment";
        }
        else
        {
            if( asId == null )
            {
                Integer startTimeInt = (start.getDay()) * 1000
                    + start.getHours() * 10;
                if( start.getMinutes() < 30 )
                {
                    as.setStartTimeInt( startTimeInt );
                }
                else
                {
                    as.setStartTimeInt( startTimeInt + 1 );
                }

                as.setAdvisor( advisor );

                if( student.getCurrentAdvisor() == null )
                    student.setCurrentAdvisor( advisor );

                as.setStudent( student );
                as.setIsAvailable( true );
                as.setIsWalkInAppointment( true );
                as.setEndTime( end );
                as.setAppointmentType( appointmentTypeDao
                    .getAppointmentTypeById( 201 ) );
                as.setStartTime( start );
                as.setTitle( " Walk In Appointment " );
                appointmentSectionDao.saveAppointmentSection( as );

                List<AppointmentSection> studentAss = student.getAppointmentSections();
                if (!studentAss.contains(as))
                	studentAss.add( as );
                
                List<AppointmentSection> advisorAss = advisor.getAppointmentSections();
                if (!advisorAss.contains(as))
                	advisorAss.add( as );

                userDao.saveUser( student );
                userDao.saveUser( advisor );

                logger.info( "User: "
                    + userDao.getUserByUsername( SecurityUtils.getUsername() )
                        .getName() + " added a walk in appointment." );
            }
            else
            {

                if( start.compareTo( savedAs.getStartTime() ) != 0 )
                    isTimeChanged = true;
                if( end.compareTo( savedAs.getEndTime() ) != 0 )
                    isTimeChanged = true;

                Integer startTimeInt = (start.getDay()) * 1000
                    + start.getHours() * 10;
                if( start.getMinutes() < 30 )
                {
                    savedAs.setStartTimeInt( startTimeInt );
                }
                else
                {
                    savedAs.setStartTimeInt( startTimeInt + 1 );
                }
                savedAs.setStartTime( start );
                savedAs.setEndTime( end );
                savedAs.setStudent( student );
                savedAs.setReasonForAppointment( as.getReasonForAppointment() );

                if( as.getIsShowUp() == false )
                {
                    savedAs.setIsShowUp( false );
                    savedAs.setIsAvailable( false );
                    isShowUp = false;
                }
                appointmentSectionDao.saveAppointmentSection( savedAs );

                logger
                    .info( "User: "
                        + userDao.getUserByUsername(
                            SecurityUtils.getUsername() ).getName()
                        + " cancelled a walk in appointment due to the absence of student." );

            }

        }

        if( asId == null )
        {

            List<User> recipients = new ArrayList<User>();
            recipients.add( as.getAdvisor() );
            recipients.add( as.getStudent() );
            mailMessage.setSubject( "CESAR Appointment" );
            mailMessage.setText( "The walk in appointment starts from "
                    + as.getStartTime() + " to " + as.getEndTime()
                    + " with advisor :" + as.getAdvisor().getFirstName() + " " + as.getAdvisor().getLastName()
                    + " and student: " + as.getStudent().getFirstName() + " " + as.getStudent().getLastName() 
         			+ ", CIN: " + as.getStudent().getCin() + ", and Quarter Admitted: " 
         			+ as.getStudent().getQuarterAdmitted() + " has been added." );

            boolean errorOccurred = false;
            List<String> failedAddresses = new ArrayList<String>();
            for( User recipient : recipients )
            {
                mailMessage.setTo( recipient.getEmail() );
                try
                {
                    mailSender.send( mailMessage );
                }
                catch( MailException e )
                {
                    errorOccurred = true;
                    failedAddresses.add( recipient.getEmail() + " ("
                        + recipient.getName() + ")" );
                    e.printStackTrace();
                }
            }

            if( errorOccurred )
            {

                model.addAttribute( "failedAddresses", failedAddresses );
                return "exception/email";

            }

        }
        else
        {

            boolean errorOccurred = false;

            if( isTimeChanged || !isShowUp )
            {

                List<User> recipients = new ArrayList<User>();
                recipients.add( savedAs.getAdvisor() );
                recipients.add( savedAs.getStudent() );
                mailMessage.setSubject( "CESAR Appointment" );
                if( !isShowUp )
                {
                	mailMessage.setText( "The appointment with "
                            + savedAs.getAdvisor().getFirstName() + " " + savedAs.getAdvisor().getLastName() 
                            + " has been cancelled due to your absence."
                            + " It is important that you make another appointment." 
                        	+ " Please stop by the ECST Advisement & Recruitment Center (A-125)" 
                        	+ " to make another appointment or call 323-343-4574.");
                    
                }
                else if( isTimeChanged )
                {
                	mailMessage
                    .setText( "The time of appointment with advisor :"
                        + savedAs.getAdvisor().getFirstName() + " " + savedAs.getAdvisor().getLastName()
                        + " and student: "
                        + savedAs.getStudent().getFirstName() + " " + savedAs.getStudent().getLastName()
                        + " has been changed to : from "
                        + savedAs.getStartTime() + " to "
                        + savedAs.getEndTime() + "." );

                }

                List<String> failedAddresses = new ArrayList<String>();
                for( User recipient : recipients )
                {
                    mailMessage.setTo( recipient.getEmail() );
                    try
                    {
                        mailSender.send( mailMessage );
                    }
                    catch( MailException e )
                    {
                        errorOccurred = true;
                        failedAddresses.add( recipient.getEmail() + " ("
                            + recipient.getName() + ")" );
                        e.printStackTrace();
                    }
                }

                if( errorOccurred )
                {
                    model.addAttribute( "failedAddresses", failedAddresses );
                    return "exception/email";
                }

            }
        }

        model.addAttribute( "msgTitle", "Status" );
        if( asId == null )
            model.addAttribute( "msg",
                "The Walk In Appointment has been added." );
        else
            model.addAttribute( "msg",
                "The Walk In Appointment has been updated. " );
        User currentUser = userDao.getUserByUsername( SecurityUtils
            .getUsername() );

        if( advisorView != null )
        {
            if( advisorView )
                model.addAttribute( "backUrl",
                    "/appointment/student/viewAppointment.html?studentId="
                        + student.getId() );

        }
        else if( currentUser.getId().intValue() == advisor.getId().intValue() )
        {
            model.addAttribute( "backUrl",
                "/appointment/advisor/viewAppointment.html" );
        }
        else
        {
            model.addAttribute( "backUrl", "/" );
        }

        return "status";

    }

    @RequestMapping(
        value = ("/appointment/{path}/updateWalkInAppSchedule.html"),
        method = RequestMethod.GET)
    public String formUpdateWalkInAppView( Integer id,
        @PathVariable("path") String path, ModelMap model, Boolean advisorView )
    {
        AppointmentSection as = appointmentSectionDao.getAppointmentById( id );
        if( as == null )
        {
            model.addAttribute( "msgTitle", "Request Error" );
            model.addAttribute( "msg",
                "The appointment you request does not exist" );
            return "status";
        }
        else
        {
            User currentUser = userDao.getUserByUsername( SecurityUtils
                .getUsername() );

            if( path.equals( "advisor" ) )
            {

                model.addAttribute( "as", as );
                model.addAttribute( "advisor", as.getAdvisor() );
                model.addAttribute( "startTime", as.getStartTime() );
                model.addAttribute( "endTime", as.getEndTime() );
                model.addAttribute( "reasonsForAppointment",
                    reasonForAppointmentDao.getReasonsForAppointment() );
                model.addAttribute( "cin", as.getStudent().getCin() );
                model.addAttribute( "isUpdate", true );
                model.addAttribute( "advisorView", advisorView );
                return "/appointment/addWalkInAppointment";

            }
            if( path.equals( "student" ) )
            {
                User asStudent = as.getStudent();
                if( currentUser.getId().intValue() == asStudent.getId()
                    .intValue() )
                {
                    model.addAttribute( "as", as );
                    return "/appointment/viewStudentWalkInAppointment";
                }
            }
            model.addAttribute( "msgTitle", "Bad Request" );
            model.addAttribute( "msg",
                "The appointment you requested is invalid" );
            return "status";
        }

    }

    /*********************
     ****** reports ******
     *********************/

    @RequestMapping(
        value = ("/appointment/staff/viewAppointmentWithSpecificDate.html"),
        method = RequestMethod.GET)
    public String viewAppWithSpeDateGet( ModelMap model )
    {

        Map<String, List<AppointmentSection>> appSections = new HashMap<String, List<AppointmentSection>>();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set( Calendar.HOUR_OF_DAY, 0 );
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set( Calendar.HOUR_OF_DAY, 23 );

        List<User> advisors = userDao.getUsersByRoleName( "ROLE_ADVISOR" );

        for( User advisor : advisors )
        {

            List<AppointmentSection> appSectionsForMap = appointmentSectionDao
                .getAppointmentSectionByAdvisorAndBetweenSpeDates( advisor,
                    startCalendar.getTime(), endCalendar.getTime() );

            Collections.sort( appSectionsForMap, new Comparator<Object>() {

                public int compare( Object a, Object b )
                {
                    Date orderA = ((AppointmentSection) a).getStartTime();
                    Date orderB = ((AppointmentSection) b).getStartTime();
                    return orderA.compareTo( orderB );
                }
            } );

            if( appSectionsForMap.size() != 0 )
            {
                appSections.put(
                    advisor.getFirstName() + " " + advisor.getLastName(),
                    appSectionsForMap );
            }

        }
        model.addAttribute( "appointmentSections", appSections );
        return "appointment/viewAppointmentWithDate";
    }

    @RequestMapping(
        value = ("/appointment/staff/viewAppointmentWithSpecificDate.html"),
        method = RequestMethod.POST)
    public String viewAppWithSpeDateGet( ModelMap model, String date )
    {
    	if (date != "") {
        SimpleDateFormat df = new SimpleDateFormat( "MM/dd/yyyy" );
        String importDateForm = date;
        Date time = null;
        Calendar selectedCalendar = Calendar.getInstance();
        try
        {
            time = df.parse( importDateForm );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

        selectedCalendar.setTime( time );
        Calendar startCalendar = (Calendar) selectedCalendar.clone();
        Calendar endCalendar = (Calendar) selectedCalendar.clone();

        startCalendar.set( Calendar.HOUR_OF_DAY, 1 );
        endCalendar.set( Calendar.HOUR_OF_DAY, 23 );

        Map<String, List<AppointmentSection>> appSections = new HashMap<String, List<AppointmentSection>>();

        List<User> advisors = userDao.getUsersByRoleName( "ROLE_ADVISOR" );

        for( User advisor : advisors )
        {

            List<AppointmentSection> appSectionsForMap = appointmentSectionDao
                .getAppointmentSectionByAdvisorAndBetweenSpeDates( advisor,
                    startCalendar.getTime(), endCalendar.getTime() );

            Collections.sort( appSectionsForMap, new Comparator<Object>() {

                public int compare( Object a, Object b )
                {
                    Date orderA = ((AppointmentSection) a).getStartTime();
                    Date orderB = ((AppointmentSection) b).getStartTime();
                    return orderA.compareTo( orderB );
                }
            } );

            if( appSectionsForMap.size() != 0 )
            {
                appSections.put(
                    advisor.getFirstName() + " " + advisor.getLastName(),
                    appSectionsForMap );
            }

        }
        model.addAttribute( "appointmentSections", appSections );
        return "appointment/viewAppointmentWithDate";
    	} else {
    		return "appointment/viewAppointmentWithDate";
    	}
    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
            new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" ), true ) );
        binder.registerCustomEditor( AppointmentType.class,
            appointmentTypePropertyEditor );
        binder.registerCustomEditor( User.class, userPropertyEditor );
        binder.registerCustomEditor( ReasonForAppointment.class,
            reasonForAppointmentPropertyEditor );

    }

}
