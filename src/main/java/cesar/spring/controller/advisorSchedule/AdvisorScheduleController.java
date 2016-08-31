package cesar.spring.controller.advisorSchedule;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cesar.model.AdvisorScheduleRecord;
import cesar.model.AdvisorScheduleSection;
import cesar.model.Block;
import cesar.model.Quarter;
import cesar.model.ScheduleTable;
import cesar.model.ScheduleTableSection;
import cesar.model.User;
import cesar.model.dao.AdvisorScheduleRecordDao;
import cesar.model.dao.BlockDao;
import cesar.model.dao.ScheduleTableDao;

import cesar.model.dao.UserDao;
import cesar.spring.security.SecurityUtils;

@Controller
public class AdvisorScheduleController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ScheduleTableDao scheduleTableDao;

    @Autowired
    private AdvisorScheduleRecordDao advisorScheduleRecordDao;

    @Autowired
    private BlockDao blockDao;

    private Logger logger = LoggerFactory.getLogger( AdvisorScheduleController.class );

    @RequestMapping(value = ("/{path}/advisorSchedule/advisorsScheduleTable.html"),
        method = RequestMethod.GET)
    public String viewScheduleTable( @PathVariable("path") String path,
        Integer studentId, ModelMap model, Integer id )
    {

        if( path.equals( "user" ) || path.equals( "student" ) )
        {
            ScheduleTable scheduleTable = scheduleTableDao.getLatestScheduleTable();

            if( scheduleTable == null )
            {
                scheduleTable = new ScheduleTable();
                scheduleTableDao.save( scheduleTable );
            }

            model.addAttribute( "scheduleTable", scheduleTable );
            if( path.equals( "student" ) )
            {
                if( studentId != null )
                {
                    User student = userDao.getUserById( studentId );
                    model.addAttribute( "student", student );
                }
                model.addAttribute( "studentId", studentId );
                model.addAttribute( "isForAdvisor", false );
                model.addAttribute( "id", id );
            }
            else
            {
                model.addAttribute( "isForAdvisor", true );
                model.addAttribute( "id", id );
            }

            return "advisorSchedule/advisorsScheduleTable";
        }
        else
        {
            model.addAttribute( "msgTitle", "Bad Request" );
            model.addAttribute( "msg", "The URL you requested is invalid" );
            return "status";
        }
    }

    @RequestMapping(value = ("/user/advisorSchedule/advisorsScheduleTable.html"),
        method = RequestMethod.POST)
    public String editScheduleTable( ModelMap model, Integer id,
        Boolean isRegisterPeriod )
    {
        User user = userDao.getUserByUsername( SecurityUtils.getUsername() );
        ScheduleTable scheduleTable = scheduleTableDao.getScheduleTableById( id );
        scheduleTable.setIsRegisterPeriod( isRegisterPeriod );
        scheduleTableDao.save( scheduleTable );

        AdvisorScheduleRecord record = new AdvisorScheduleRecord();
        record.setCreatedDate( new Date() );
        if( isRegisterPeriod )
        {
            logger.info( "User: " + user.getName()
                + " changed advisors schedule table to register period mode" );

        }
        else
        {
            logger.info( "User: " + user.getName()
                + " changed advisors schedule table is changed to regular mode" );
        }
        advisorScheduleRecordDao.saveAdvisorScheduleRecord( record );

        model.addAttribute( "user", user );
        model.addAttribute( "scheduleTable", scheduleTable );
        model.addAttribute( "isForAdvisor", true );

        return "advisorSchedule/advisorsScheduleTable";
    }

    @RequestMapping(value = ("/user/advisorSchedule/editScheduleTable.html"),
        method = RequestMethod.GET)
    public String editScheduleTable( ModelMap model, Integer startTime )
    {
        ScheduleTable scheduleTable = scheduleTableDao.getLatestScheduleTable();
        User currentUser = userDao.getUserByUsername( SecurityUtils.getUsername() );

        for( ScheduleTableSection section : scheduleTable.getScheduleTableSections() )
        {
            if( section.getStartTime().intValue() == startTime.intValue() )
            {
                Boolean isAdd = true;

                int weekDayInt = section.getStartTime() / 1000;

                String startHour = null;
                String endHour = null;

                if( ((section.getStartTime() % 1000) % 100) % 10 == 0 )
                {
                    startHour = (section.getStartTime() % 1000) / 10 + ":00";
                    endHour = (section.getStartTime() % 1000) / 10 + ":30";
                }
                else if( ((section.getStartTime() % 1000) % 100) % 10 == 1 )
                {
                    startHour = (section.getStartTime() % 1000) / 10 + ":30";
                    endHour = (section.getStartTime() % 1000 + 10) / 10 + ":00";
                }

                if( startHour == null || endHour == null )
                {
                    model.addAttribute( "msgTitle", "Status" );
                    model.addAttribute( "msg", "Appointment has been cancelled" );
                    model.addAttribute( "backUrl",
                        "/user/advisorSchedule/advisorsScheduleTable.html" );
                }
                String weekDay = null;
                switch( weekDayInt )
                {
                    case 1:
                        weekDay = "Monday";
                        break;
                    case 2:
                        weekDay = "Tuesday";
                        break;
                    case 3:
                        weekDay = "Wednesday";
                        break;
                    case 4:
                        weekDay = "Thursday";
                        break;
                    case 5:
                        weekDay = "Friday";
                        break;
                }

                for( User user : section.getAdvisors() )
                {
                    if( user.getUsername().equals( currentUser.getUsername() ) )
                    {
                        isAdd = false;
                        break;
                    }
                }

                model.addAttribute( "user", currentUser );
                model.addAttribute( "weekDay", weekDay );
                model.addAttribute( "startHour", startHour );
                model.addAttribute( "endHour", endHour );
                model.addAttribute( "section", section );
                model.addAttribute( "isAdd", isAdd );

                break;
            }
        }

        return "advisorSchedule/editScheduleTableSection";
    }

    @RequestMapping(value = ("/user/advisorSchedule/addScheduleTableSection.html"),
        method = RequestMethod.POST)
    public String saveScheduleTable( ModelMap model, Integer id )
    {
        ScheduleTable scheduleTable = scheduleTableDao.getLatestScheduleTable();

        User currentUser = userDao.getUserByUsername( SecurityUtils.getUsername() );

        Set<AdvisorScheduleSection> advisorScheduleSections = currentUser.getAdvisorScheduleSections();

        AdvisorScheduleRecord record = new AdvisorScheduleRecord();
        record.setCreatedDate( new Date() );
        for( ScheduleTableSection tmpSection : scheduleTable.getScheduleTableSections() )
        {

            if( tmpSection.getId().equals( id ) )
            {
                tmpSection.getAdvisors().add( currentUser );
                AdvisorScheduleSection advisorScheduleSection = new AdvisorScheduleSection();
                advisorScheduleSection.setStartTime( tmpSection.getStartTime() );
                advisorScheduleSections.add( advisorScheduleSection );

                record.setLog( currentUser.getUsername()
                    + " add schedule section with start time: "
                    + tmpSection.getStartTime() );

                logger.info( currentUser.getUsername()
                    + " add schedule section with start time:"
                    + tmpSection.getStartTime() );
                break;
            }

        }
        scheduleTable.setUpdatedDate( new Date() );
        Quarter currentQuarter = new Quarter( new Date() );
        if( scheduleTable.getQuarter().getCode() != currentQuarter.getCode() )
        {
            scheduleTable.setQuarter( currentQuarter );
        }
        scheduleTableDao.save( scheduleTable );
        userDao.saveUser( currentUser );
        advisorScheduleRecordDao.saveAdvisorScheduleRecord( record );

        logger.info( "User " + currentUser.getName()
            + " saved a schedule session" );

        return "redirect:advisorsScheduleTable.html";
    }

    @RequestMapping(value = ("/user/advisorSchedule/deleteScheduleTableSection.html"),
        method = RequestMethod.POST)
    public String deleteScheduleTableSection( ModelMap model, Integer id )
    {
        ScheduleTable scheduleTable = scheduleTableDao.getLatestScheduleTable();

        User currentUser = userDao.getUserByUsername( SecurityUtils.getUsername() );

        Set<AdvisorScheduleSection> advisorScheduleSections = currentUser.getAdvisorScheduleSections();

        AdvisorScheduleRecord record = new AdvisorScheduleRecord();
        record.setCreatedDate( new Date() );

        outer: for( ScheduleTableSection tmpSection : scheduleTable.getScheduleTableSections() )
        {

            if( tmpSection.getId().equals( id ) )
            {
                for( User advisor : tmpSection.getAdvisors() )
                {
                    if( advisor.getId().equals( currentUser.getId() ) )
                    {
                        tmpSection.getAdvisors().remove( advisor );
                        record.setLog( currentUser.getUsername()
                            + " delete schedule section with start time: "
                            + tmpSection.getStartTime() );
                        break;

                    }
                }

                for( AdvisorScheduleSection advisorScheduleSection : advisorScheduleSections )
                {
                    if( tmpSection.getStartTime().equals(
                        advisorScheduleSection.getStartTime() ) )
                    {
                        advisorScheduleSections.remove( advisorScheduleSection );
                        break outer;
                    }
                }

            }

        }
        scheduleTable.setUpdatedDate( new Date() );
        Quarter currentQuarter = new Quarter( new Date() );
        if( scheduleTable.getQuarter().getCode() != currentQuarter.getCode() )
        {
            scheduleTable.setQuarter( currentQuarter );
        }
        scheduleTableDao.save( scheduleTable );
        userDao.saveUser( currentUser );
        advisorScheduleRecordDao.saveAdvisorScheduleRecord( record );
        logger.info( "User: " + currentUser.getUsername()
            + " deleted a schedule section " );

        return "redirect:advisorsScheduleTable.html";
    }

    @RequestMapping(value = ("/user/advisorSchedule/activeBlock.html"),
        method = RequestMethod.GET)
    public String block( ModelMap model )
    {
        List<Block> blocks = blockDao.getBlock();
        Calendar currentTime = Calendar.getInstance();
        List<Block> activeBlocks = new ArrayList<Block>();
        for( int i = 0; i < blocks.size(); i++ )
        {
            if( blocks.get( i ).getEndDateTime().after( currentTime ) )
            {
                activeBlocks.add( blocks.get( i ) );
            }
        }
        model.addAttribute( "advisors",
            userDao.getUsersByRoleName( "ROLE_ADVISOR" ) );
        model.addAttribute( "blocks", activeBlocks );

        return "advisorSchedule/activeBlocks";
    }

    @RequestMapping(value = ("/user/advisorSchedule/block.html"))
    public String block( ModelMap model, Integer[] advisorsId,
        String startDate, String endDate, String startTime, String endTime )
        throws ParseException
    {
        if( advisorsId != null && startDate != null && endDate != null
            && startTime != null && endTime != null )
        {
            for( int i = 0; i < advisorsId.length; i++ )
            {
                String[] sd = startDate.split( "/" );
                String[] s = startTime.split( " " );
                String[] st = s[0].split( ":" );
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.set( Integer.parseInt( sd[2] ),
                    Integer.parseInt( sd[0] ) - 1, Integer.parseInt( sd[1] ),
                    Integer.parseInt( st[0] ), Integer.parseInt( st[1] ), 0 );
                startCalendar.set( startCalendar.MILLISECOND, 0 );

                String[] ed = endDate.split( "/" );
                String[] e = endTime.split( " " );
                String[] et = e[0].split( ":" );
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.set( Integer.parseInt( ed[2] ),
                    Integer.parseInt( ed[0] ) - 1, Integer.parseInt( ed[1] ),
                    Integer.parseInt( et[0] ), Integer.parseInt( et[1] ), 0 );
                endCalendar.set( startCalendar.MILLISECOND, 0 );

                User advisor = userDao.getUserById( advisorsId[i] );
                Block block = new Block();
                block.setAdvisor( advisor );
                block.setStartDateTime( startCalendar );
                block.setEndDateTime( endCalendar );
                blockDao.saveBlock( block );
            }

            List<Block> blocks = blockDao.getBlock();
            Calendar currentTime = Calendar.getInstance();
            List<Block> activeBlocks = new ArrayList<Block>();
            for( int i = 0; i < blocks.size(); i++ )
            {
                if( blocks.get( i ).getEndDateTime().after( currentTime ) )
                {
                    activeBlocks.add( blocks.get( i ) );
                }
            }
            model.addAttribute( "blocks", activeBlocks );

            return "redirect:activeBlock.html";
        }
        else
        {
            return "advisorSchedule/activeBlocks";
        }

    }

    @RequestMapping(value = "/user/advisorSchedule/deleteBlock.html")
    public String deleteBlock( Integer id )
    {
        blockDao.deleteBlock( blockDao.getBlockById( id ) );
        logger.info( SecurityUtils.getUsername() + " deleted block " + id );
        return "redirect:activeBlock.html";
    }

}
