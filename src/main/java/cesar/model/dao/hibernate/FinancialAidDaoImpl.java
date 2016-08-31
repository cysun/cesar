package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.FinancialAid;
import cesar.model.User;
import cesar.model.dao.FinancialAidDao;

@Repository("financialAidDao")
public class FinancialAidDaoImpl implements FinancialAidDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public FinancialAid getFinancialAidById( Integer id )
    {
        return (FinancialAid) hibernateTemplate.get( FinancialAid.class, id );
    }

    @SuppressWarnings("unchecked")
    public List<FinancialAid> getFinancialAids( User user )
    {
        return (List<FinancialAid>) hibernateTemplate.find(
            "from FinancialAid where student=?", user );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveFinancialAid( FinancialAid financialAid )
    {
        hibernateTemplate.saveOrUpdate( financialAid );

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteFinancialAid( FinancialAid financialAid )
    {
        hibernateTemplate.delete( financialAid );

    }

}
