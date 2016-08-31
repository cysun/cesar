package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Major;
import cesar.model.dao.MajorDao;

@Repository("majorDao")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MajorDaoImpl implements MajorDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public Major getMajorById( Integer id )
    {
        return (Major) hibernateTemplate.get( Major.class, id );
    }

    public Major getMajorByName( String name )
    {
        String query = "from Major where name = ? ";
        List results = hibernateTemplate.find( query, name );
        return results.size() == 0 ? null : (Major) results.get( 0 );
    }

    @Override
    public List<Major> getMajors()
    {
        String query = "from Major order by id asc";
        return (List<Major>) hibernateTemplate.find( query );
    }

    public Major getMajorBySymbol( String symbol )
    {
        String query = "from Major where symbol = ? ";
        List results = hibernateTemplate.find( query, symbol );
        return results.size() == 0 ? null : (Major) results.get( 0 );
    }

    @Override
    public List<Major> getRegularMajors()
    {
        String query = "from Major where isRegular = true";
        return (List<Major>) hibernateTemplate.find( query );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveMajor( Major major )
    {
        hibernateTemplate.saveOrUpdate( major );
    }

}
