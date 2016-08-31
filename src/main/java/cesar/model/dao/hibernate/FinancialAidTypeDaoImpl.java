package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cesar.model.FinancialAidType;
import cesar.model.dao.FinancialAidTypeDao;

@Repository("financialAidTypeDao")
public class FinancialAidTypeDaoImpl implements FinancialAidTypeDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public FinancialAidType getFinancialAidTypeById( Integer id )
    {
        return (FinancialAidType) hibernateTemplate.get(
            FinancialAidType.class, id );
    }

    @SuppressWarnings("unchecked")
    public List<FinancialAidType> getFinancialAidTypes()
    {
        String query = "from FinancialAidType order by name asc";
        return (List<FinancialAidType>) hibernateTemplate.find( query );
    }

}
