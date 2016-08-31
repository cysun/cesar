package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.NoSeenReasonType;
import cesar.model.dao.NoSeenReasonTypeDao;

@Repository("noSeenReasonTypeDao")
public class NoSeenReasonTypeDaoImpl implements NoSeenReasonTypeDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public NoSeenReasonType getNoSeenReasonTypeById( Integer id )
    {
        return (NoSeenReasonType) hibernateTemplate.get(
            NoSeenReasonType.class, id );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NoSeenReasonType> getNoSeenReasonTypes()
    {
        return (List<NoSeenReasonType>) hibernateTemplate
            .find( "from NoSeenReasonType where deleted = false " );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addNoSeenReasonType( NoSeenReasonType noSeenReasonType )
    {
        hibernateTemplate.saveOrUpdate( noSeenReasonType );

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteNoSeenReasonType( NoSeenReasonType noSeenReasonType )
    {
        // TODO Auto-generated method stub
        hibernateTemplate.delete( noSeenReasonType );

    }

}
