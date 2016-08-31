package cesar.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cesar.model.Role;

@Repository("roleDao")
public interface RoleDao {

    public Role getRoleById( Integer id );

    public Role getRoleByName( String name );

    public List<Role> getAllRoles();

}
