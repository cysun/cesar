package cesar.spring.controller.course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import cesar.model.Course;
import cesar.model.Major;
import cesar.model.Prerequisite;
import cesar.model.Role;
import cesar.model.User;
import cesar.model.dao.CourseDao;
import cesar.model.dao.MajorDao;
import cesar.model.dao.UserDao;

import cesar.spring.editor.MajorPropertyEditor;
import cesar.spring.security.SecurityUtils;

@Controller
public class CourseController {

    @Autowired
    private CourseDao           courseDao;

    @Autowired
    private MajorDao            majorDao;

    @Autowired
    private UserDao             userDao;

    @Autowired
    private MajorPropertyEditor majorPropertyEditor;

    private Logger              logger = LoggerFactory
                                           .getLogger( CourseController.class );

    private String              tabs[] = { "CS", "CE", "EE", "ME", "TECH",
        "MATH", "PHSY", "GENERAL"     };

    int getTabIndex( String major )
    {
        for( int i = 0; i < tabs.length; ++i )
            if( tabs[i].equals( major ) ) return i + 1;

        return 0;
    }

    @RequestMapping(value = ("course/displayCourses.html"),
        method = RequestMethod.GET)
    protected String displayCourses( String symbol, ModelMap model )
    {
        if(SecurityUtils.isAdministrator())
        {
            model.addAttribute("staffOnly", true);
        }else
        {
            model.addAttribute("staffOnly", false);
        }
        if( symbol == null ) return "course/displayCourses";
        Major major = majorDao.getMajorBySymbol( symbol );
        if( major == null ) return "course/displayCourses";
        
        model.addAttribute( "symbol", symbol );
        model.addAttribute( "courses", major.getCourses() );
        return "course/displayCoursesTab";
    }

    @RequestMapping(value = { "course/{path}.html" },
        method = RequestMethod.GET)
    protected String editCourse( Integer courseId,
        @PathVariable("path") String path, ModelMap model )
    {
        Course course = courseId == null ? new Course() : courseDao
            .getCourseById( courseId );

        course.setPrereqString( course.getPrereqAsString() );

        model.addAttribute( "majors", majorDao.getMajors() );
        model.addAttribute( "course", course );

        if( path.equals( "addCourse" ) )
            return "course/addCourse";
        else if( path.equals( "editCourse" ) ) return "course/editCourse";

        return "redirect:course/displayCourses.html";
    }

    @RequestMapping(
        value = { "course/addCourse.html", "course/editCourse.html" },
        method = RequestMethod.POST)
    protected String editCourse( Course course, String[] symbols, ModelMap model )
    {
        Set<Prerequisite> prerequisites = new HashSet<Prerequisite>();
        String errorMsg = null;
        String errorForCode = null;
        String errorForMajors = null;
        boolean hasPreError = false;
        boolean hasError = false;

        String prereqString = course.getPrereqString();
        if( StringUtils.hasText( prereqString.trim() ) )
        {
            errorMsg =  "The following courses do not exsit: ";
            String tokens[] = prereqString.split( "\\s+" );
            for( String token : tokens )
            {
                Prerequisite prerequisite = new Prerequisite();
                String codes[] = token.split( "/" );
                for( String code : codes )
                {
                    Course prereqCourse = courseDao.getCourseByCode( code );
                    if( prereqCourse == null )
                    {
                        errorMsg += code + " ";
                        hasPreError = true;
                        hasError = true;
                        
                    }
                    else
                        prerequisite.addCourse( prereqCourse );
                }
                prerequisites.add( prerequisite );
            }
        }
        
        if(symbols == null)
        {
            errorForMajors = "Please select at least one major";
            
            hasError = true;
            
        }else
        {

            if(symbols.length==0)
            {
                errorForMajors = "Please select at least one major";
                
                hasError = true;
            }
        }
        
        if(course.getCode()==null)
        {
            errorForCode = "The code could not be empty";
            
            hasError = true;
        }else
        {
            if(course.getCode().length()==0)
            {
                errorForCode = "The code could not be empty";
                
                hasError = true;
            }
        }

        if( !hasError )
        {
            course.setPrerequisites( prerequisites );
            courseDao.saveCourse( course );

            List<Major> majors = majorDao.getMajors();
            List<Major> selectedMajors = new ArrayList<Major>();
            List<Major> storedMajors = new ArrayList<Major>();

            for( int i = 0; i < symbols.length; i++ )
            {
                Major selectedMajor = majorDao.getMajorBySymbol( symbols[i] );
                selectedMajors.add( selectedMajor );
            }

            for( Major major : majors )
            {
                Set<Course> storedCourses = major.getCourses();
                for( Course storedCourse : storedCourses )
                {
                    if( storedCourse.getId().intValue() == course.getId()
                        .intValue() )
                    {
                        storedMajors.add( major );
                        break;
                    }
                }

            }

            for( Major storedMajor : storedMajors )
            {
                Boolean isDelete = true;
                for( Major selectedMajor : selectedMajors )
                {
                    if( selectedMajor.getId().intValue() == storedMajor.getId()
                        .intValue() )
                    {
                        isDelete = false;
                        break;
                    }
                }

                if( isDelete )
                {
                    Set<Course> storedCourses = storedMajor.getCourses();
                    Set<Course> removedCourses = new HashSet<Course>();
                    for( Course storedCourse : storedCourses )
                    {
                        if( storedCourse.getId().intValue() == course.getId()
                            .intValue() )
                        {
                            removedCourses.add( storedCourse );
                            break;
                        }
                    }
                    storedCourses.removeAll( removedCourses );
                }
                majorDao.saveMajor( storedMajor );
            }

            for( Major selectedMajor : selectedMajors )
            {
                Boolean isAdd = true;
                for( Major storedMajor : storedMajors )
                {
                    if( selectedMajor.getId().intValue() == storedMajor.getId()
                        .intValue() )
                    {
                        isAdd = false;
                        break;
                    }
                }

                if( isAdd == true )
                {
                    selectedMajor.getCourses().add( course );
                    majorDao.saveMajor( selectedMajor );
                    logger.info( "User: "
                        + userDao.getUserByUsername(
                            SecurityUtils.getUsername() ).getName()
                        + " added a course: " + course.getCode() );
                }

                if( isAdd == false )
                {
                    logger.info( "User: "
                        + userDao.getUserByUsername(
                            SecurityUtils.getUsername() ).getName()
                        + " modified a course: " + course.getCode() );
                }
            }

            return "redirect:displayCourses.html#ui-tabs-"
                + getTabIndex( selectedMajors.get( 0 ).getSymbol() );
        }
        else
        {
            if(hasPreError)
            {
                model.addAttribute( "prerequisiteError", errorMsg );
            }
            model.addAttribute( "majorError", errorForMajors);
            model.addAttribute( "codeError", errorForCode );
            model.addAttribute( "course", course );
            model.addAttribute( "majors", majorDao.getMajors() );
            return course.getId() == null ? "course/addCourse"
                : "course/editCourse";
        }

    }

    @RequestMapping(value = { "course/deleteCourse.html" },
        method = RequestMethod.GET)
    public String deleteCourse( Integer courseId, ModelMap model, String symbol )
    {
        Course course = courseDao.getCourseById( courseId );
        course.setDeleted( true );
        courseDao.saveCourse( course );

        List<Major> majors = majorDao.getMajors();

        for( Major major : majors )
        {
            major.getCourses().remove( course );
            majorDao.saveMajor( major );
        }

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " deleted a course : " + course.getCode() );
        return "redirect:displayCourses.html#ui-tabs-" + getTabIndex( symbol );
    }

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Major.class, majorPropertyEditor );
    }

    @RequestMapping(value = ("/course/displayCoHort.html"),
            method = RequestMethod.GET)
    public String displayCoHort( String term, ModelMap model, HttpServletRequest request )
    {
		List<User> users = null;
		Integer directory = null;
 		HttpSession session = request.getSession();
 		User user = userDao.getUserByUsername( SecurityUtils.getUsername() );
 		Set<Role> roles = user.getRoles();
 		String path = "";
    	
 		Iterator<Role> i = roles.iterator();
 		List<Integer> idList = new ArrayList<Integer>(0);
 		while (i.hasNext()){
 			int id = i.next().getId();
 			idList.add(id);
 		}
 		
 		if (idList.contains(22)) {
 			path = "staff";
 		} else if (idList.contains(21)) {
 			path = "advisor";
 		}
 		
 		if( path.equals( "advisor" ) )
        {
        	users = userDao.getUsersByAdvisor(user);
        } 
 		
 		if( path.equals( "staff" ) )
        {
 			if( StringUtils.hasText( term ) )
 			{
                user = userDao.getUserByName(term);
            	users = userDao.getUsersByAdvisor(user);
 			}
        }

        if( path.equals( "advisor" ) )
        {
            directory = 1;
        }
        else if( path.equals( "staff" ) )
        {
            directory = 2;
        }
        else
        {
            model.addAttribute( "error", "Page Not Found" );
            model.addAttribute( "errorCause",
                "The page you requested does not exist." );
            model.addAttribute( "backUrl", "/cesar" );
            return "error";
        }
  		
  		session.setAttribute( "directory", directory );
  		model.addAttribute( "advisor", user );
        model.addAttribute( "term", term );
        model.addAttribute( "users", users );
           
  		return "course/displayCoHort";
    }
    
    @RequestMapping("/course/ajaxAdvisorSearch.html")
    public void ajaxSearch( String term, HttpServletResponse response) throws JSONException, IOException
    {
        List<User> users = new ArrayList<User>();
        if( StringUtils.hasText( term ) )
        {
                users = userDao.searchAdvisorsByPrefix( term.toLowerCase() );
        }
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

}
