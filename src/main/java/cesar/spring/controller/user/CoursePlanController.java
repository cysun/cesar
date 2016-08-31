package cesar.spring.controller.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import cesar.model.Course;
import cesar.model.CoursePlan;
import cesar.model.CourseTaken;
import cesar.model.CourseTransferred;
import cesar.model.CourseWaived;
import cesar.model.Major;
import cesar.model.Prerequisite;
import cesar.model.Quarter;
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
import cesar.spring.controller.storedQuery.StoredQueryController;
import cesar.spring.editor.CoursePropertyEditor;
import cesar.spring.editor.QuarterPropertyEditor;
import cesar.spring.editor.SectionPropertyEditor;
import cesar.spring.security.SecurityUtils;
import cesar.model.QuarterPlan;

@Controller
public class CoursePlanController {

    @Autowired
    private UserDao               userDao;
    @Autowired
    private ScheduleDao           scheduleDao;
    @Autowired
    private CourseTakenDao        courseTakenDao;
    @Autowired
    private CourseDao             courseDao;
    @Autowired
    private CoursePlanDao         coursePlanDao;
    @Autowired
    private CoursePropertyEditor  coursePropertyEditor;
    @Autowired
    private MajorDao              majorDao;
    @Autowired
    private CourseTransferredDao  courseTransferredDao;
    @Autowired
    private CourseWaivedDao       courseWaivedDao;
    @Autowired
    private SectionPropertyEditor sectionPropertyEditor;
    @Autowired
    private QuarterPropertyEditor quarterPropertyEditor;
    @Autowired
    private QuarterPlanDao         quarterPlanDao;
    @Autowired
    private SectionDao         		sectionDao;

    private Logger                logger = LoggerFactory
                                             .getLogger( CoursePlanController.class );

    @RequestMapping(value = { "/{path}/coursePlan/view/viewCoursePlan.html" },
        method = RequestMethod.GET)
    protected String setupForm( Integer coursePlanId, Integer userId,
        @PathVariable("path") String path, Boolean forStudentView,
        ModelMap model )
    {

        if( path != null )
        {
            if( path.equals( "user" ) )
            {
                Boolean forProfile = false;
                model.addAttribute( "userId", userId );
                model.addAttribute( "forProfile", forProfile );
            }
        }
        boolean forView = true;
        CoursePlan coursePlan = coursePlanDao.getCoursePlanById( coursePlanId );
        
        model.addAttribute( "coursePlan", coursePlan );
        model.addAttribute( "forView", forView );

        if( forStudentView == null )
        {
            forStudentView = false;
        }

        model.addAttribute( "forStudentView", forStudentView );
        return "coursePlan/coursePlanDisplay";

    }

    @RequestMapping(
        value = { "/user/coursePlan/viewCoursePlanTemplates.html" },
        method = RequestMethod.GET)
    protected String viewCoursePlanTemplates( ModelMap model )
    {
        List<CoursePlan> coursePlanTemplates = coursePlanDao
            .getCoursePlanTemplates();

        model.addAttribute( "coursePlanTemplates", coursePlanTemplates );

        return "coursePlan/viewCoursePlanTemplates";

    }

    @RequestMapping(value = { "/user/coursePlan/view/viewCoursePlan.html" },
        method = RequestMethod.POST)
    protected String signforPlan( String signature, Integer userId,
        Integer coursePlanId, ModelMap model )
    {
        CoursePlan coursePlan = coursePlanDao.getCoursePlanById( coursePlanId );
        User student = userDao.getUserById(userId);
        if( signature != null )
        {
            User advisor = userDao.getUserByUsername( SecurityUtils
                .getUsername() );
            
            Set<QuarterPlan> quarterPlans = coursePlan.getQuarterPlans();
            for (QuarterPlan quarterPlan: quarterPlans) {
            	Set<Section> sections = quarterPlan.getSections();
            	for (Section section: sections) {
            		Set<User> students = section.getStudents();
            		if (!(students.contains(student))) {
            			section.setEnrollmentTotal(section.getEnrollmentTotal()+1);
            			students.add(student);
            			section.setStudents(students);
            			sectionDao.save(section);
            		}
            	}
            	quarterPlanDao.saveQuarterPlan(quarterPlan);
            }
            
            String advisorName = advisor.getFirstName() + " "
                + advisor.getLastName();
            coursePlan.setAdvisor( advisorName );
            coursePlan.setApprovedDate( new Date() );
            coursePlan.setApproved( true );
            coursePlanDao.saveCoursePlan( coursePlan );
            logger.info( "User: "
                + userDao.getUserByUsername( SecurityUtils.getUsername() )
                    .getName() + " approved a coursePlan" );

        }
        else
        {
        	coursePlan.setAdvisor( "" );
            coursePlan.setApprovedDate( null );
            coursePlan.setApproved( false );
            coursePlanDao.saveCoursePlan( coursePlan );
            logger.info( "User: "
                + userDao.getUserByUsername( SecurityUtils.getUsername() )
                    .getName() + " disapproved a coursePlan" );

        }

        return "redirect:/user/display.html?userId=" + userId
            + "&#tab-advisement";
    }

    @RequestMapping(value = ("/coursePlan/deleteCoursePlan.html"),
        method = RequestMethod.GET)
    protected String deleteCoursePlan( Integer coursePlanId, Integer studentId )
    {
        CoursePlan coursePlan = coursePlanDao.getCoursePlanById( coursePlanId );
        coursePlan.setDeleted( true );
        coursePlanDao.saveCoursePlan( coursePlan );

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " deleted a course plan" );
        if( coursePlan.getIsTemplate() ){ return "redirect:/user/coursePlan/viewCoursePlanTemplates.html"; }

        if( studentId == null )
        {
            return "redirect:../profile.html?#tab-advisement";
        }
        else
        {
            User student = userDao.getUserById( studentId );
            return "redirect:../user/display.html?userId=" + student.getId()
                + "#tab-advisement";
        }
    }

    @RequestMapping(value = ("/user/coursePlan/addCoursePlanTemplate.html"),
        method = RequestMethod.GET)
    protected String addCoursePlanTemplate( HttpServletRequest request,
        Integer coursePlanId, ModelMap model )
    {

        CoursePlan coursePlan = null;
        User currentUser = userDao.getUserByUsername( SecurityUtils
            .getUsername() );

        Boolean isEdit = false;
        if( coursePlanId == null )
        {
            coursePlan = new CoursePlan();
            Date currentTime = new Date();
            coursePlan.setName( "Course_plan_" + currentTime.toString() );
            coursePlan.setIsTemplate( true );

        }
        else
        {
            coursePlan = coursePlanDao.getCoursePlanById( coursePlanId );
            isEdit = true;
        }

        coursePlan.setAdvisor( currentUser.getName() );

        // create a session for course plan
        HttpSession session = request.getSession();

        Quarter currentQuarter = new Quarter( new Date() );

        model.addAttribute( "quarter", currentQuarter );

        model.addAttribute( "majors", majorDao.getRegularMajors() );

        session.setAttribute( "coursePlan", coursePlan );

        session.setAttribute( "isEdit", isEdit );
        return "coursePlan/introCoursePlanTemplate";
    }

    @RequestMapping(value = ("/coursePlan/addCoursePlanIntro.html"),
        method = RequestMethod.GET)
    protected String addCoursePlanSetupForm( HttpServletRequest request,
        ModelMap model, Integer studentId )
    {

        // create a session for course plan
        HttpSession session = request.getSession();

        User currentUser = null;
        if( studentId == null )
        {
            currentUser = userDao.getUserByUsername( SecurityUtils
                .getUsername() );
            session.setAttribute( "isAdvisor", false );
        }
        else
        {
            currentUser = userDao.getUserById( studentId );
            session.setAttribute( "isAdvisor", true );
        }

        Major major = currentUser.getMajor();

        List<CoursePlan> coursePlanTemplates = null;
        if( major == null )
        {
            String majorError = "Please select your major";
            model.addAttribute( "majorError", majorError );
            coursePlanTemplates = new ArrayList<CoursePlan>();
        }
        else
        {
            coursePlanTemplates = coursePlanDao
                .getCoursePlanTemplatesByMajor( major );
        }

        model.addAttribute( "coursePlanTemplates", coursePlanTemplates );
        model.addAttribute( "currentUser", currentUser );
        return "coursePlan/preIntroCoursePlan";
    }

    @RequestMapping(value = ("/coursePlan/addCoursePlanIntro.html"),
        method = RequestMethod.POST)
    protected String addCoursePlanSetupForm( HttpServletRequest request,
        ModelMap model, Integer studentId, Integer coursePlanId )
    {
        CoursePlan coursePlan = new CoursePlan();
        Boolean isEdit = false;
        if( coursePlanId != -1 )
        {
            CoursePlan selectedCoursePlan = coursePlanDao
                .getCoursePlanById( coursePlanId );
            isEdit = true;
            coursePlan.setIsTemplate( false );
            coursePlan.setAdvisor( selectedCoursePlan.getAdvisor() );
            Set<QuarterPlan> quarterPlans = new LinkedHashSet<QuarterPlan>();

            for( QuarterPlan selectQuarterPlan : selectedCoursePlan
                .getQuarterPlans() )
            {
                QuarterPlan quarterPlan = new QuarterPlan();

                quarterPlan.setCourses( selectQuarterPlan.getCourses() );
                quarterPlan.setSections( selectQuarterPlan.getSections() );
                quarterPlan.setQuarter( selectQuarterPlan.getQuarter() );

                quarterPlans.add( quarterPlan );
            }

            coursePlan.setQuarterPlans( quarterPlans );
        }

        HttpSession session = request.getSession();

        coursePlan.setStudent( userDao.getUserById( studentId ) );

        Quarter currentQuarter = new Quarter( new Date() );
        model.addAttribute( "quarter", currentQuarter );

        session.setAttribute( "isEdit", isEdit );
        session.setAttribute( "coursePlan", coursePlan );

        return "coursePlan/introCoursePlan";
    }

    @RequestMapping(value = ("/coursePlan/editCoursePlan.html"),
        method = RequestMethod.GET)
    protected String editCoursePlanSetupForm( HttpServletRequest request,
        ModelMap model, Integer studentId, Integer coursePlanId )
    {
        Boolean isEdit = true;
        Boolean isAdvisor = false;
        HttpSession session = request.getSession();

        CoursePlan coursePlan = coursePlanDao.getCoursePlanById( coursePlanId );

        coursePlan.setIsTemplate( false );

        if( studentId != null )
        {
            User advisor = userDao.getUserByUsername( SecurityUtils
                .getUsername() );
            coursePlan.setAdvisor( advisor.getName() );
            isAdvisor = true;
            coursePlan.setApproved( true );
            coursePlan.setApprovedDate( new Date() );
        }

        session.setAttribute( "isAdvisor", isAdvisor );

        Quarter currentQuarter = new Quarter( new Date() );
        model.addAttribute( "quarter", currentQuarter );

        session.setAttribute( "isEdit", isEdit );
        session.setAttribute( "coursePlan", coursePlan );

        return "coursePlan/introCoursePlan";
    }

	    @RequestMapping(value = { "/coursePlan/addCoursePlan.html",
	    "/user/coursePlan/addCoursePlanTemplate.html" },
	    method = RequestMethod.POST)
	protected String addCoursePlanSubmitForm( HttpServletRequest request,
	    HttpServletResponse response, QuarterPlan quarterPlan,
	    SessionStatus status, @RequestParam("_page") int currentPage,
	    String note, Integer quarterCode, Boolean firstQuarter,
	    Integer majorId, String name, ModelMap model )
	{
	
	    List<Section> yellowSections = new ArrayList<Section>();
	    List<Course> redCourses = new ArrayList<Course>();
	    List<Course> blueCourses = new ArrayList<Course>();
	    List<Section> greenSections = new ArrayList<Section>();
	    List<Course> greyCourses = new ArrayList<Course>();
	
	    // create session for CoursePlan
	    HttpSession session = request.getSession();
	    CoursePlan coursePlan = (CoursePlan) session
	        .getAttribute( "coursePlan" );
	
	    if( name != null ) coursePlan.setName( name );
	
	    if( note != null ) coursePlan.setNote( note );
	
	    Map<Integer, String> pageForms = new HashMap<Integer, String>();
	    pageForms.put( 0, "coursePlan/introCoursePlan" );
	    pageForms.put( 1, "coursePlan/coursePlanForm" );
	
	    // initiate
	    User currentUser = null;
	    if( !coursePlan.getIsTemplate() )
	    {
	        currentUser = coursePlan.getStudent();
	
	        if( currentUser.getMajor() == null )
	        {
	            String majorError = "Please select your major";
	            model.addAttribute( "majorError", majorError );
	
	            return (String) pageForms.get( 0 );
	        }
	    }
	    else
	    {
	        currentUser = userDao.getUserByUsername( SecurityUtils
	            .getUsername() );
	    }
	
	    // major for template
	    Major majorTemplate = null;
	
	    if( coursePlan.getIsTemplate() )
	    {
	        if( firstQuarter != null )
	        {
	            if( firstQuarter )
	            {
	                majorTemplate = majorDao.getMajorById( majorId );
	                coursePlan.setMajor( majorTemplate );
	            }
	
	        }
	        else
	        {
	            majorTemplate = coursePlan.getMajor();
	        }
	    }
	    // if user click save, then save the course plan
	    if( request.getParameter( "_save" ) != null )
	    {
	
	        if( coursePlan.getIsTemplate() )
	        {
	            if( coursePlan.getName().isEmpty() )
	            {
	                name = "course_plan_template_" + coursePlan.getTimeStamp();
	            }
	            coursePlan.setName( name );
	
	            coursePlan.setNote( note );
	
	            coursePlan.setApproved( false );
	
	            coursePlanDao.saveCoursePlan( coursePlan );
	            session.removeAttribute( "coursePlan" );
	            session.removeAttribute( "quarterPlan" );
	
	            logger.info( "User: "
	                + userDao.getUserByUsername( SecurityUtils.getUsername() )
	                    .getName() + " added a course plan template" );
	            return "redirect:/user/coursePlan/viewCoursePlanTemplates.html";
	
	        }
	
	        if( !coursePlan.getIsTemplate() )
	        {
	            Boolean isAdvisor = (Boolean) session
	                .getAttribute( "isAdvisor" );
	            if( isAdvisor )
	            {
	                User advisor = userDao.getUserByUsername( SecurityUtils
	                    .getUsername() );
		            coursePlan.setAdvisor( advisor.getFirstName() + " "
	                    + advisor.getLastName() );
	                coursePlan.setApprovedDate( new Date() );
	                coursePlan.setApproved( true );
	                
	                User student = coursePlan.getStudent();
	                Set<QuarterPlan> quarterPlans = coursePlan.getQuarterPlans();
	                for (QuarterPlan qp: quarterPlans) {
	                 	Set<Section> sections = qp.getSections();
	                 	if( sections != null ) {
	                   	for (Section section: sections) {
	                   		Set<User> students = section.getStudents();
	                   		boolean flag = false;
	                   		for (User s: students) {
	                   			if (student.getId().equals(s.getId())) {
	                   				flag = true;
	                   				break;
	                   			}
	                   		}
	                   		
	                   		if (flag == false) {
	                   			section.setEnrollmentTotal(section.getEnrollmentTotal()+1);
	                   			students.add(student);
	                   			section.setStudents(students);
	                   			sectionDao.save(section);
	                   		}
	                   	}
	                 	}
	                   	quarterPlanDao.saveQuarterPlan(qp);
	                }
	    	            
	            }
	            else
	            {
	                coursePlan.setAdvisor( "" );
	                coursePlan.setApprovedDate( null );
	                coursePlan.setApproved( false );
	            }
	
	            coursePlanDao.saveCoursePlan( coursePlan );
	            session.removeAttribute( "coursePlan" );
	
	            logger.info( "User: "
	                + userDao.getUserByUsername( SecurityUtils.getUsername() )
	                    .getName() + " added a course plan." );
	
	            if( isAdvisor )
	            {
	                User student = coursePlan.getStudent();
	                session.removeAttribute( "isAdvisor" );
	                return "redirect:../user/display.html?userId="
	                    + student.getId() + "#tab-advisement";
	            }
	            else if( !isAdvisor )
	            {
	                session.removeAttribute( "isAdvisor" );
	                return "redirect:../profile.html?#tab-advisement";
	            }
	
	        }
	    }
	
	    // Extract target page
	    int targetPage = WebUtils.getTargetPage( request, "_target",
	        currentPage );
	
	    // post request not from "introCoursePlan.jsp" or user does click
	    // finish button
	
	    // save quarter plan based on submitted data.
	    if( (targetPage > 1 || request.getParameter( "_finish" ) != null)
	        && request.getParameter( "_previous" ) == null )
	    {
	        Set<QuarterPlan> quarterPlanList = coursePlan.getQuarterPlans();
	        double totalUnits = 0;
	        Boolean isOld = false;
	        for( QuarterPlan quarterPlanTmp : quarterPlanList )
	        {
	            if( quarterPlanTmp.getQuarter().getCode() == quarterCode )
	            {
	                quarterPlanTmp.setCourses( quarterPlan.getCourses() );
	                quarterPlanTmp.setSections( quarterPlan.getSections() );
	                
	                if (quarterPlan.getCourses() != null) {
	                for( Course course : quarterPlan.getCourses() )
	                {
	                    totalUnits = totalUnits + course.getUnits();
	                }
	                }
	                
	                if (quarterPlan.getSections() != null) {
	                for( Section section : quarterPlan.getSections() )
	                {
	                    totalUnits = totalUnits + section.getUnits();
	                }
	                }
	                
	                quarterPlanTmp.setUnits( totalUnits );
	                quarterPlanTmp.setNotes( quarterPlan.getNotes() );
	                isOld = true;
	                break;
	            }
	        }
	
	        if( !isOld )
	        {
	            quarterPlan.setQuarter( new Quarter( quarterCode ) );
	
	            Set<Course> courses = quarterPlan.getCourses();
	            if( courses == null )
	            {
	                courses = new HashSet<Course>();
	                quarterPlan.setCourses( courses );
	            }
	
	            Set<Section> sections = quarterPlan.getSections();
	            if( sections == null )
	            {
	                sections = new HashSet<Section>();
	                quarterPlan.setSections( sections );
	            }
	
	            List<Course> courseList = new ArrayList<Course>();
	            courseList.addAll( courses );
	            Collections.sort( courseList, new Comparator<Object>() {
	
	                public int compare( Object a, Object b )
	                {
	                    String codeA = ((Course) a).getCode();
	                    String codeB = ((Course) b).getCode();
	                    return codeA.compareTo( codeB );
	                }
	            } );
	
	            quarterPlan.getCourses().clear();
	            quarterPlan.getCourses().addAll( courseList );
	
	            List<Section> sectionList = new ArrayList<Section>();
	            sectionList.addAll( sections );
	            Collections.sort( sectionList, new Comparator<Object>() {
	
	                public int compare( Object a, Object b )
	                {
	                    String codeA = ((Section) a).getCourse().getCode();
	                    String codeB = ((Section) b).getCourse().getCode();
	                    return codeA.compareTo( codeB );
	                }
	            } );
	            quarterPlan.getSections().clear();
	            quarterPlan.getSections().addAll( sectionList );
	
	            for( Course course : quarterPlan.getCourses() )
	            {
	                totalUnits = totalUnits + course.getUnits();
	            }
	
	            for( Section section : quarterPlan.getSections() )
	            {
	                totalUnits = totalUnits + section.getUnits();
	            }
	
	            quarterPlan.setUnits( totalUnits );
	
	            coursePlan.getQuarterPlans().add( quarterPlan );
	        }
	
	        session.setAttribute( "coursePlan", coursePlan );
	
	    }
	
	    // if user click finish button
	    if( request.getParameter( "_finish" ) != null )
	    {
	        Set<QuarterPlan> quarterPlanList = coursePlan.getQuarterPlans();
	        Set<QuarterPlan> removedQuarterPlans = new HashSet<QuarterPlan>();
	        for( QuarterPlan quarterPlanTmp : quarterPlanList )
	        {
	            if( quarterPlanTmp.getQuarter().getCode() > quarterCode )
	                removedQuarterPlans.add( quarterPlanTmp );
	        }
	
	        quarterPlanList.removeAll( removedQuarterPlans );
	        coursePlan.setQuarterPlans( quarterPlanList );
	        double unitsForCoursePlan = 0;
	        for( QuarterPlan quarterPlantmp : coursePlan.getQuarterPlans() )
	        {
	            unitsForCoursePlan = unitsForCoursePlan
	                + quarterPlantmp.getUnits();
	        }
	
	        coursePlan.setUnits( unitsForCoursePlan );
	
	        model.addAttribute( "currentPage", targetPage + 1 );
	
	        Quarter nextQuarter = new Quarter( quarterCode ).next();
	        model.addAttribute( "quarterCode", nextQuarter.getCode() );
	
	        return "coursePlan/coursePlanDisplay";
	    }
	
	    /***************************************
	     * prepare data for coursePlanForm.jsp**
	     ***************************************/
	    else
	    {
	
	        if( request.getParameter( "_previous" ) != null )
	        {
	            Quarter prevQuarter = new Quarter( quarterCode ).previous()
	                .previous();
	            quarterCode = prevQuarter.getCode();
	        }
	
	        Set<Course> allCourses = new HashSet<Course>();
	        Set<Course> GECourses = null;
	        List<Course> coursesNeedToTake = new ArrayList<Course>();
	        List<CourseTaken> coursesTaken = null;
	        List<CourseWaived> coursesWaived = null;
	        List<CourseTransferred> coursesTransferred = null;
	        List<Course> takenCourses = new ArrayList<Course>();
	
	        if( !coursePlan.getIsTemplate() )
	        {
	            int majorId1 = currentUser.getMajor().getId();
	            Major currentMajor = majorDao.getMajorById( majorId1 );
	
	            allCourses.addAll( currentMajor.getCourses() );
	
	            GECourses = majorDao.getMajorBySymbol( "GENERAL" ).getCourses();
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
	
	            Major selectedMajor = majorDao.getMajorById( majorTemplate
	                .getId() );
	            allCourses.addAll( selectedMajor.getCourses() );
	            GECourses = majorDao.getMajorBySymbol( "GENERAL" ).getCourses();
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
	        for( Course course : allCourses )
	        {
	            allCoursesStr.add( course.getCode() );
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
	        if( quarterPlanList != null && quarterCode != null
	            && firstQuarter == null )
	        {
	            for( QuarterPlan quarterPlanTmp : quarterPlanList )
	            {
	                if( quarterPlanTmp.getQuarter().getCode() <= quarterCode )
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
	                            preCoursesStr.add( selectedSection.getCourse()
	                                .getCode() );
	                        if( courseNeedToTakeStr.contains( selectedSection
	                            .getCourse().getCode() )
	                            && selectedSection.getCourse().isRepeatable() == false )
	                        {
	                            courseNeedToTakeStr.remove( selectedSection
	                                .getCourse().getCode() );
	                        }
	                    }
	
	                    for( Course selectedCourse : selectedCourseList )
	                    {
	                        preCoursesStr.add( selectedCourse.getCode() );
	                        if( courseNeedToTakeStr.contains( selectedCourse
	                            .getCode() ) )
	                        {
	                            courseNeedToTakeStr.remove( selectedCourse
	                                .getCode() );
	                        }
	                    }
	                }
	            }
	        }
	
	        // get coursesNeedToTaken
	        for( String courseCode : courseNeedToTakeStr )
	        {
	            coursesNeedToTake.add( courseDao.getCourseByCode( courseCode ) );
	        }
	
	        // compare the preCourses to coursesNeedToTaken, get result
	        List<String> meetPreCoursesStr = new ArrayList<String>();
	        HashMap<String, String> notMeetPreCoursesStr = new HashMap<String, String>();
	
	        for( Course course : coursesNeedToTake )
	        {
	            StringBuffer courseToTake = new StringBuffer();
	
	            // if preCourses has section course, then continue
	            if( preCoursesStr.contains( course.getCode() ) )
	                continue;
	            else
	            {
	
	                // get prerequisites course from course
	                Set<Prerequisite> prerequisites = course.getPrerequisites();
	                Boolean isSatisfied = true;
	
	                for( Prerequisite prerequisite : prerequisites )
	                {
	
	                    Set<Course> prereCourses = prerequisite.getCourses();
	
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
	                                courseToTake.append( prereCourse2 + " " );
	                                isSatisfied = false;
	                            }
	                        }
	
	                    }
	
	                }
	                if( isSatisfied == true )
	                {
	                    meetPreCoursesStr.add( course.getCode() );
	                }
	                else
	                {
	                    notMeetPreCoursesStr.put( course.getCode(),
	                        courseToTake.toString() );
	                }
	
	            }
	        }
	
	        Boolean isEmptySections = true;
	
	        Quarter quarter = new Quarter( quarterCode );
	
	        Quarter nextQuarter = null;
	
	        if( firstQuarter != null )
	        {
	            if( firstQuarter )
	
	            {
	                nextQuarter = quarter;
	            }
	        }
	        else
	        {
	            nextQuarter = quarter.next();
	        }
	
	        if( request.getParameter( "_previous" ) != null )
	        {
	            targetPage = targetPage - 1;
	        }
	
	        int counter = targetPage;
	        List<QuarterPlan> quarterPlans = new ArrayList<QuarterPlan>();
	        quarterPlans.addAll( quarterPlanList );
	
	        QuarterPlan newQuarterPlan = null;
	
	        if( quarterPlans.size() > 0 )
	        {
	            Quarter lastQuarterSaved = quarterPlans.get(
	                quarterPlans.size() - 1 ).getQuarter();
	
	            if( nextQuarter.getCode() > lastQuarterSaved.getCode() )
	            {
	                newQuarterPlan = new QuarterPlan();
	                newQuarterPlan.setQuarter( nextQuarter );
	            }
	            else
	            {
	                for( QuarterPlan quarterPlanTemp : quarterPlans )
	                {
	                    if( quarterPlanTemp.getQuarter().getCode() == nextQuarter
	                        .getCode() )
	                    {
	                        newQuarterPlan = quarterPlanTemp;
	                        break;
	                    }
	                }
	            }
	        }
	        else
	        {
	            newQuarterPlan = new QuarterPlan();
	            newQuarterPlan.setQuarter( nextQuarter );
	        }
	
	        // get next schedule
	
	        Set<Section> availSections = new HashSet<Section>();
	
	        List<Major> majors = majorDao.getMajors();
	
	        for( Major major : majors )
	        {
	            Schedule schedule = scheduleDao.getScheduleByMajorAndQuarter(
	                nextQuarter, major );
	            if( schedule != null )
	                availSections.addAll( schedule.getSections() );
	        }
	
	        if( availSections.size() > 0 )
	        {
	            isEmptySections = false;
	        }
	
	        if( isEmptySections == true )
	        {
	            String warning = "WARNING: The courses to be offered in "
	                + nextQuarter
	                + " are not available, You are suggested"
	                + " to take following courses based on the course prerequisites and the courses you have or would have taken by this quarter. ";
	            model.addAttribute( "warning", warning );
	
	            // green courses
	            for( String meetCourseStr : meetPreCoursesStr )
	            {
	                Course course = courseDao.getCourseByCode( meetCourseStr );
	                blueCourses.add( course );
	            }
	
	            // grey courses
	            Set<String> courseKeys = notMeetPreCoursesStr.keySet();
	
	            for( String courseCode : courseKeys )
	            {
	                String notMeetPres = notMeetPreCoursesStr.get( courseCode );
	                Course course = courseDao.getCourseByCode( courseCode );
	                course.setExtraInfo( notMeetPres );
	                greyCourses.add( course );
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
	
	            for( Section section : availSections )
	            {
	                // green or blue
	                if( meetPreCoursesStr.contains( section.getCourse()
	                    .getCode() ) )
	                {
	
	                    greenSections.add( section );
	
	                    // yellow(Str)
	                    unavailCoursesStr
	                        .remove( section.getCourse().getCode() );
	                }
	
	                // reclassify notMeetPreCourse into Grey and yellow
	                // in schedule: red and grey(Str)
	                String notMeetPreCourseStr = notMeetPreCoursesStr
	                    .get( section.getCourse().getCode() );
	                if( notMeetPreCourseStr != null )
	                {
	                    // red sections
	                    section.setExtraInfo( notMeetPreCourseStr );
	                    // newSection.setExtraInfo( notMeetPreCourseStr );
	                    yellowSections.add( section );
	
	                    // grey(Str)
	                    notMeetNUnavailPreCourseStr.remove( section.getCourse()
	                        .getCode() );
	                }
	            }
	
	            // red courses
	            for( String courseCode : unavailCoursesStr )
	            {
	                redCourses.add( courseDao.getCourseByCode( courseCode ) );
	            }
	
	            // grey courses
	            Set<String> courseKeys = notMeetNUnavailPreCourseStr.keySet();
	
	            for( String courseCode : courseKeys )
	            {
	                String notMeetPres = notMeetNUnavailPreCourseStr
	                    .get( courseCode );
	                Course course = new Course();
	                course.setCode( courseDao.getCourseByCode( courseCode )
	                    .getCode() );
	                course.setName( courseDao.getCourseByCode( courseCode )
	                    .getName() );
	                course.setExtraInfo( notMeetPres );
	                greyCourses.add( course );
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
	            model.addAttribute( "currentPage", counter );
	        }
	
	        targetPage = targetPage + 1;
	        model.addAttribute( "targetPage", targetPage );
	        model.addAttribute( "availSections", availSections );
	        model.addAttribute( "quarterPlan", newQuarterPlan );
	        model.addAttribute( "previousPage", counter - 1 );
	
	        session.setAttribute( "quarterPlan", newQuarterPlan );
	
	        if( !(SecurityUtils.isUserInRole( "ROLE_ADVISOR" ) || SecurityUtils
	            .isUserInRole( "ROLE_STAFF" )) )
	        {
	            model.addAttribute( "fromStudent", true );
	        }
	        return (String) pageForms.get( 1 );
	
	    }
	}

    public ArrayList<Integer> timeConflict( List<Section> yellowSections,
        List<Section> greenSections )
    {
        List<Section> selectedSections = new ArrayList<Section>();

        ArrayList<Integer> conflictInt = new ArrayList<Integer>();

        if( greenSections.size() > 2 )
        {
            Section selectedSection = greenSections.get( 0 );
            selectedSections.add( selectedSection );

            Section selectedSection1 = greenSections.get( 1 );
            selectedSections.add( selectedSection1 );

            greenSections.remove( selectedSection );
            greenSections.remove( selectedSection1 );
        }

        ArrayList<StartAndEndTime> stList = new ArrayList<StartAndEndTime>();

        for( Section section : selectedSections )
        {

            HashSet<WeekDay> weekDays = (HashSet<WeekDay>) section
                .getWeekDays();

            String startTimeStr = section.getStartTime();
            String endTimeStr = section.getEndTime();

            for( WeekDay weekDay : weekDays )
            {
                Date startTimeDate = StringTimeConvert( startTimeStr,
                    weekDay.getCode() );
                Date endTimeDate = StringTimeConvert( endTimeStr,
                    weekDay.getCode() );
                StartAndEndTime saet = new StartAndEndTime( startTimeDate,
                    endTimeDate );
                stList.add( saet );
            }

        }
        HashMap<Integer, ArrayList<StartAndEndTime>> sectionMap = new HashMap<Integer, ArrayList<StartAndEndTime>>();

        ArrayList<Section> availSections = new ArrayList<Section>();
        availSections.addAll( greenSections );
        availSections.addAll( yellowSections );

        for( int i = 0; i < availSections.size(); i++ )
        {
            Section section = availSections.get( i );
            HashSet<WeekDay> weekDays = (HashSet<WeekDay>) section
                .getWeekDays();
            ArrayList<StartAndEndTime> sneList = new ArrayList<StartAndEndTime>();
            sneList.clear();
            String startTimeStr = section.getStartTime();
            String endTimeStr = section.getEndTime();

            for( WeekDay weekDay : weekDays )
            {
                Date startTimeDate = StringTimeConvert( startTimeStr,
                    weekDay.getCode() );
                Date endTimeDate = StringTimeConvert( endTimeStr,
                    weekDay.getCode() );
                StartAndEndTime saet = new StartAndEndTime( startTimeDate,
                    endTimeDate );
                stList.add( saet );
            }

            sectionMap.put( i, stList );

        }

        // check for the conflict
        HashSet<Integer> keys = (HashSet<Integer>) sectionMap.keySet();

        for( Integer key : keys )
        {
            ArrayList<StartAndEndTime> tmpSectionTimes = sectionMap.get( key );

            Boolean isConflict = false;
            outter: for( StartAndEndTime saet : tmpSectionTimes )
            {
                for( StartAndEndTime selectedSAET : stList )
                {
                    if( saet.getStartTime().after( selectedSAET.getStartTime() )
                        && saet.getStartTime().before(
                            selectedSAET.getEndTime() ) )
                    {
                        isConflict = true;
                        break outter;
                    }
                    else if( saet.getEndTime().after(
                        selectedSAET.getStartTime() )
                        && saet.getEndTime().before( selectedSAET.getEndTime() ) )
                    {
                        isConflict = true;
                        break outter;
                    }
                    else if( saet.getStartTime().before(
                        selectedSAET.getStartTime() )
                        && saet.getEndTime().after( selectedSAET.getEndTime() ) )
                    {
                        isConflict = true;
                        break outter;
                    }
                }
            }

            if( isConflict == true )
            {
                conflictInt.add( key );
            }

        }
        return conflictInt;
    }

    public Date StringTimeConvert( String time, Integer weekDay )
    {
        Calendar currentCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat( "hh:mm a" );
        Date time1 = null;
        try
        {
            time1 = df.parse( time );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        currentCalendar.setTime( time1 );
        currentCalendar.set( Calendar.DAY_OF_WEEK, weekDay );
        Date parsedDate = currentCalendar.getTime();

        return parsedDate;
    }

    class StartAndEndTime {

        private Date startTime;
        private Date endTime;

        public StartAndEndTime( Date startTime, Date endTime )
        {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public Date getStartTime()
        {
            return startTime;
        }

        public void setStartTime( Date startTime )
        {
            this.startTime = startTime;
        }

        public Date getEndTime()
        {
            return endTime;
        }

        public void setEndTime( Date endTime )
        {
            this.endTime = endTime;
        }

    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Course.class, coursePropertyEditor );
        binder.registerCustomEditor( Section.class, sectionPropertyEditor );
        binder.registerCustomEditor( Quarter.class, quarterPropertyEditor );
    }
}
