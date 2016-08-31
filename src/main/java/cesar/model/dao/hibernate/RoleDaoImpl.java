package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cesar.model.Role;
import cesar.model.dao.RoleDao;

@Repository("roleDao")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public Role getRoleById( Integer id )
    {
        return (Role) hibernateTemplate.get( Role.class, id );
    }

    public Role getRoleByName( String name )
    {
        List results = hibernateTemplate.find(
            "from Role role where role.name = ? ", name );

        return results.size() == 0 ? null : (Role) results.get( 0 );
    }

    public List<Role> getAllRoles()
    {
        String query = "from Role order by id asc";
        return (List<Role>) hibernateTemplate.find( query );
    }

}
