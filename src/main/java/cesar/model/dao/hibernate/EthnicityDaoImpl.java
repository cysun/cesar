package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cesar.model.Ethnicity;
import cesar.model.dao.EthnicityDao;

@Repository("ethnicityDao")
public class EthnicityDaoImpl implements EthnicityDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public Ethnicity getEthnicityById( Integer id )
    {
        return (Ethnicity) hibernateTemplate.get( Ethnicity.class, id );
    }

    @SuppressWarnings("unchecked")
    public List<Ethnicity> getEthnicities()
    {
        String query = "from Ethnicity";
        return (List<Ethnicity>) hibernateTemplate.find( query );
    }

}
