package cesar.spring.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cesar.model.Role;
import cesar.model.ScheduleTable;
import cesar.model.ScheduleTableSection;
import cesar.model.User;
import cesar.model.dao.RoleDao;
import cesar.model.dao.ScheduleTableDao;
import cesar.model.dao.UserDao;
import cesar.spring.security.SecurityUtils;

@Controller
public class RemoveUserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ScheduleTableDao scheduleTableDao;

    private Logger logger = LoggerFactory.getLogger( RemoveUserController.class );

    private void removeRole( Integer id, String roleName )
    {
        Role role = roleDao.getRoleByName( roleName );
        User user = userDao.getUserById( id );
        user.getRoles().remove( role );
        user.getAdvisorScheduleSections().clear();
        userDao.saveUser( user );

        ScheduleTable scheduleTable = scheduleTableDao.getLatestScheduleTable();
        for( ScheduleTableSection section : scheduleTable.getScheduleTableSections() )
            section.getAdvisors().remove( user );
        scheduleTableDao.save( scheduleTable );

        logger.info( SecurityUtils.getUsername() + " removed role " + roleName
            + " from " + user.getUsername() );
    }

    @RequestMapping("/user/removeAdvisor")
    public String removeAdvisor( Integer id )
    {
        removeRole( id, "ROLE_ADVISOR" );
        return "redirect:/user/displayAdvisorsTable";
    }

    @RequestMapping("/user/removeStaff")
    public String removeStaff( Integer id )
    {
        removeRole( id, "ROLE_STAFF" );
        return "redirect:/user/displayStaffsTable";
    }

}
