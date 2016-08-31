package cesar.model.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Quarter;
import cesar.model.User;
import cesar.model.dao.UserDao;

@Repository("userDao")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserDaoImpl implements UserDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public User getUserById( Integer id )
    {
        return (User) hibernateTemplate.get( User.class, id );
    }

    public User getUserByCin( String cin )
    {
        List results = hibernateTemplate.find( "from User where cin = ?", cin );
        return results.size() == 0 ? null : (User) results.get( 0 );
    }

    public User getUserByEmail( String email )
    {
        List results = hibernateTemplate.find( "from User where email = ?",
            email );
        return results.size() == 0 ? null : (User) results.get( 0 );
    }

    public List<User> getUsersByAdvisor( User advisor )
    {
        String query = "select user from User user where currentAdvisor = ? ";
        return (List<User>) hibernateTemplate.find( query, advisor );
    }

    public User getUserByUsername( String username )
    {
        List results = hibernateTemplate.find( "from User where username = ?",
            username );
        return results.size() == 0 ? null : (User) results.get( 0 );
    }

    public User getUserByName( String name )
    {
    	String[] n = name.split(" ");
    	List results = null;
    	if (n.length == 2) {
    		String f = n[0].substring(0, 1).toUpperCase() + n[0].substring(1).toLowerCase();
    		String l = n[1].substring(0, 1).toUpperCase() + n[1].substring(1).toLowerCase();
    		results = hibernateTemplate.find( "from User where first_name = ? and last_name = ?",
    		            f, l );
    	} else if (n.length == 3) {
    		String f = n[0].substring(0, 1).toUpperCase() + n[0].substring(1).toLowerCase();
    		String m = n[1].substring(0, 1).toUpperCase() + n[1].substring(1).toLowerCase();
    		String l = n[2].substring(0, 1).toUpperCase() + n[2].substring(1).toLowerCase();
    		results = hibernateTemplate.find( "from User where first_name = ? and middle_name = ? and last_name = ?",
		            f, m, l );
    	}
    	
        return results.size() == 0 ? null : (User) results.get( 0 );
    }

    public List<User> getUsersById( final Integer[] ids )
    {
        return (List<User>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( User.class );
                criteria.add( Restrictions.in( "id", ids ) );
                return criteria.list();
            }

        } );
    }

    public List<User> getUsersByRoleName( String roleName )
    {
        String query = "select user from User user join user.roles role "
            + "where role.name = ? order by user.lastName asc";
        return (List<User>) hibernateTemplate.find( query, roleName );
    }

    public List<User> searchStudents( String term )
    {
        String query1 = "select user from User user join user.roles role where role.name ='ROLE_STUDENT' and ( user.cin = ? or lower(user.username) = ? "
            + "or lower(user.firstName) = ? or lower(user.lastName) = ? "
            + "or lower(user.firstName || ' ' || user.lastName) = ?)";
        Object params1[] = { term, term, term, term, term };
        return (List<User>) hibernateTemplate.find( query1, params1 );
    }

    public List<User> searchStudentsByPrefix( String term )
    {
        String query = "select user from User user join user.roles role where role.name ='ROLE_STUDENT' and (user.cin like ? || '%' "
            + "or lower(user.username) like ? || '%' "
            + "or lower(user.firstName) like ? || '%' "
            + "or lower(user.lastName) like ? || '%' "
            + "or lower(user.firstName || ' ' || user.lastName) like ? || '%' )";
        Object params[] = { term, term, term, term, term };

        return (List<User>) hibernateTemplate.find( query, params );
    }

    public List<User> searchAdvisors( String term )
    {
        String query1 = "select user from User user join user.roles role where role.name ='ROLE_ADVISOR' and ( user.cin = ? or lower(user.username) = ? "
            + "or lower(user.firstName) = ? or lower(user.lastName) = ? "
            + "or lower(user.firstName || ' ' || user.lastName) = ?)";
        Object params1[] = { term, term, term, term, term };
        return (List<User>) hibernateTemplate.find( query1, params1 );
    }

    public List<User> searchAdvisorsByPrefix( String term )
    {
        String query = "select user from User user join user.roles role where role.name ='ROLE_ADVISOR' and (user.cin like ? || '%' "
            + "or lower(user.username) like ? || '%' "
            + "or lower(user.firstName) like ? || '%' "
            + "or lower(user.lastName) like ? || '%' "
            + "or lower(user.firstName || ' ' || user.lastName) like ? || '%' )";
        Object params[] = { term, term, term, term, term };

        return (List<User>) hibernateTemplate.find( query, params );
    }

    public List<User> searchUsers( String term )
    {
        String query1 = "select user from User user where user.cin = ? or lower(user.username) = ? "
            + "or lower(user.firstName) = ? or lower(user.lastName) = ? "
            + "or lower(user.firstName || ' ' || user.lastName) = ?";
        Object params1[] = { term, term, term, term, term };
        return (List<User>) hibernateTemplate.find( query1, params1 );
    }

    public List<User> searchUsersByPrefix( String term )
    {
        String query = "select user from User user  where user.cin like ? || '%' "
            + "or lower(user.username) like ? || '%' "
            + "or lower(user.firstName) like ? || '%' "
            + "or lower(user.lastName) like ? || '%' "
            + "or lower(user.firstName || ' ' || user.lastName) like ? || '%' ";
        Object params[] = { term, term, term, term, term };

        return (List<User>) hibernateTemplate.find( query, params );
    }

    public List<User> searchStaffs( String term )
    {
        String query1 = "select user from User user join user.roles role where role.name ='ROLE_STAFF' and ( user.cin = ? or lower(user.username) = ? "
            + "or lower(user.firstName) = ? or lower(user.lastName) = ? "
            + "or lower(user.firstName || ' ' || user.lastName) = ?)";
        Object params1[] = { term, term, term, term, term };
        return (List<User>) hibernateTemplate.find( query1, params1 );
    }

    public List<User> searchStaffsByPrefix( String term )
    {
        String query = "select user from User user join user.roles role where role.name ='ROLE_STAFF' and (user.cin like ? || '%' "
            + "or lower(user.username) like ? || '%' "
            + "or lower(user.firstName) like ? || '%' "
            + "or lower(user.lastName) like ? || '%' "
            + "or lower(user.firstName || ' ' || user.lastName) like ? || '%' )";
        Object params[] = { term, term, term, term, term };

        return (List<User>) hibernateTemplate.find( query, params );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveUser( User user )
    {
        hibernateTemplate.merge( user );
    }

    @Override
    public User getUserByConfirmId( String confirmId )
    {
        List results = hibernateTemplate.find( "from User where confirmId = ?",
            confirmId );
        return results.size() == 0 ? null : (User) results.get( 0 );
    }

    public List<User> getStudentsByQuarter(final Quarter quarter)
    {
        return (List<User>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( User.class, "user" )
                	.setFetchMode("user.roles", FetchMode.JOIN)
                	.createAlias("user.roles", "role")
                	.add(Restrictions.eq("role.name", "ROLE_STUDENT"))
                	.add(Restrictions.le("quarterAdmitted.code", quarter.getCode()))
                    .add(Restrictions.sqlRestriction("quarter(expected_graduation_date) >= " + quarter.getCode()))                   	
                    .setProjection(Projections.projectionList()
                    	.add(Projections.rowCount())
                    );
                    
                List list = criteria.list();
                    
                return list;
            }

        } );
    }
    
    public List<User> getStudentsByYearOfStudent(final Quarter quarter, final int year)
    {
        return (List<User>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( User.class, "user" )
                	.setFetchMode("user.roles", FetchMode.JOIN)
                	.createAlias("user.roles", "role")
                	.add(Restrictions.eq("role.name", "ROLE_STUDENT"))
                	.add(Restrictions.le("quarterAdmitted.code", quarter.getCode()))
                    .add(Restrictions.sqlRestriction("quarter(expected_graduation_date) >= " + quarter.getCode()))
                    .add(Restrictions.sqlRestriction("(" + quarter.getCode() + "- quarter_admitted) < " + year))
                	.setProjection(Projections.projectionList()
                    	.add(Projections.rowCount())
                    );
                    
                List list = criteria.list();
                    
                return list;
            }

        } );
    }
}
