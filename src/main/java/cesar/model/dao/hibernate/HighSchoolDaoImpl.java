package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.HighSchool;
import cesar.model.dao.HighSchoolDao;

@Repository("highSchoolDao")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class HighSchoolDaoImpl implements HighSchoolDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public HighSchool getHighSchoolById( Integer id )
    {
        return (HighSchool) hibernateTemplate.get( HighSchool.class, id );
    }

    @Override
    public List<HighSchool> getHighSchools()
    {
        String query = "from HighSchool";
        return (List<HighSchool>) hibernateTemplate.find( query );
    }

    public HighSchool getHighSchoolByName( String name )
    {
        List results = hibernateTemplate.find(
            "from HighSchool where name = ?", name );
        return results.size() == 0 ? null : (HighSchool) results.get( 0 );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveHighSchool( HighSchool highSchool )
    {
        hibernateTemplate.saveOrUpdate( highSchool );
    }

}
