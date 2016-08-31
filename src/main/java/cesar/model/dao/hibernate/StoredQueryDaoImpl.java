package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.StoredQuery;
import cesar.model.dao.StoredQueryDao;

@Repository("storedQueryDao")
public class StoredQueryDaoImpl implements StoredQueryDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public StoredQuery getStoredQueryById( Integer id )
    {

        return (StoredQuery) hibernateTemplate.get( StoredQuery.class, id );
    }

    public StoredQuery getStoredQueryByName( String name )
    {
        @SuppressWarnings("rawtypes")
        List results = hibernateTemplate.find(
            "from StoredQuery where name = ? ", name );
        return results.size() == 0 ? null : (StoredQuery) results.get( 0 );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StoredQuery> getStoredQueries()
    {
        String query = "from StoredQuery where deleted = false order by id desc";
        return (List<StoredQuery>) hibernateTemplate.find( query );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveStoredQuery( StoredQuery storedQuery )
    {
        hibernateTemplate.saveOrUpdate( storedQuery );
    }

}
