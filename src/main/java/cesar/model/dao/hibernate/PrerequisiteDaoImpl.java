package cesar.model.dao.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Prerequisite;
import cesar.model.dao.PrerequisiteDao;

@Repository("prerequisiteDao")
public class PrerequisiteDaoImpl implements PrerequisiteDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public Prerequisite getPrerequisiteById( Integer id )
    {
        return (Prerequisite) hibernateTemplate.get( Prerequisite.class, id );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void savePrerequisite( Prerequisite prerequisite )
    {
        hibernateTemplate.saveOrUpdate( prerequisite );

    }

}
