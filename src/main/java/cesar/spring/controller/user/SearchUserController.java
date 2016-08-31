package cesar.spring.controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cesar.model.User;
import cesar.model.dao.UserDao;

@Controller
public class SearchUserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/user/search/{path}/search.html")
    public String search( String term, ModelMap model,
        @PathVariable("path") String path, HttpServletRequest request )
    {
        List<User> users = null;
        Integer directory = null;
        HttpSession session = request.getSession();

        if( StringUtils.hasText( term ) )
        {
            if( path.equals( "student" ) )
            {
                users = userDao.searchStudents( term.toLowerCase() );

            }
            if( path.equals( "advisor" ) )
            {
                users = userDao.searchAdvisors( term.toLowerCase() );
            }
            if( path.equals( "staff" ) )
            {
                users = userDao.searchStaffs( term.toLowerCase() );
            }
            if( path.equals( "user" ) )
            {
                users = userDao.searchUsers( term.toLowerCase() );
            }
        }

        if( path.equals( "student" ) )
        {
            directory = 0;
        }
        else if( path.equals( "advisor" ) )
        {
            directory = 1;
        }
        else if( path.equals( "staff" ) )
        {
            directory = 2;
        }
        else if( path.equals( "user" ) )
        {
            directory = 3;
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

        model.addAttribute( "term", term );
        model.addAttribute( "users", users );

        return "user/search";
    }

    @RequestMapping("/user/search/{path}/ajaxSearch.html")
    public void ajaxSearch( String term, HttpServletResponse response,
        @PathVariable("path") String path ) throws JSONException, IOException
    {
        List<User> users = new ArrayList<User>();
        if( StringUtils.hasText( term ) )
        {
            if( path.equals( "student" ) )
                users = userDao.searchStudentsByPrefix( term.toLowerCase() );
            if( path.equals( "advisor" ) )
                users = userDao.searchAdvisorsByPrefix( term.toLowerCase() );
            if( path.equals( "staff" ) )
                users = userDao.searchStaffsByPrefix( term.toLowerCase() );
            if( path.equals( "user" ) )
                users = userDao.searchUsersByPrefix( term.toLowerCase() );
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
