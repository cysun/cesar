package cesar.spring.controller.user;


import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cesar.model.User;
import cesar.model.dao.RoleDao;
import cesar.model.dao.UserDao;

@Controller
public class TableDisplayController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
  

	@RequestMapping(value = {"/user/displayStudentsTable"}, method = RequestMethod.GET )
	protected String setUpStudentTable( ModelMap model){
		ArrayList<User> students = (ArrayList<User>) userDao.getUsersByRoleName( "ROLE_STUDENT" );
		model.addAttribute( "students", students );
		return "user/studentsTable";
		
	}

	@RequestMapping(value = {"/user/displayAdvisorsTable"}, method = RequestMethod.GET )
	protected String setUpAdvisorTable( ModelMap model){
		ArrayList<User> advisors = (ArrayList<User>) userDao.getUsersByRoleName( "ROLE_ADVISOR" );
		model.addAttribute( "advisors", advisors );
		return "user/advisorsTable";
	}

    @RequestMapping(value = {"/user/displayStaffsTable"}, method = RequestMethod.GET )
	protected String setUpStffTable( ModelMap model){
	    ArrayList<User> staffs = (ArrayList<User>) userDao.getUsersByRoleName( "ROLE_STAFF" );
	    model.addAttribute( "staffs", staffs );
	    return "user/staffsTable";
    }

}
