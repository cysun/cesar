package cesar.model.dao;

import java.util.List;

import cesar.model.Quarter;
import cesar.model.User;

public interface UserDao {

    public User getUserById( Integer id );

    public User getUserByCin( String cin );

    public User getUserByEmail( String email );

    public User getUserByUsername( String username );
    
    public User getUserByName( String name );

    public User getUserByConfirmId( String confirmId );

    public List<User> getUsersById( Integer ids[] );

    public List<User> getUsersByRoleName( String roleName );

    public List<User> searchStudents( String term );

    public List<User> searchStudentsByPrefix( String term );

    public List<User> searchAdvisors( String term );

    public List<User> searchAdvisorsByPrefix( String term );

    public List<User> searchUsers( String term );

    public List<User> searchUsersByPrefix( String term );

    public List<User> searchStaffs( String term );

    public List<User> searchStaffsByPrefix( String term );

    public List<User> getUsersByAdvisor( User advisor );

    public void saveUser( User user );

    public List<User> getStudentsByQuarter(Quarter quarter);
    
    public List<User> getStudentsByYearOfStudent(Quarter quarter, int year);
}
