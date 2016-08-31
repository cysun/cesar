package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.VisitReasonType;
import cesar.model.dao.VisitReasonTypeDao;

@Repository("visitReasonTypeDao")
public class VisitReasonTypeDaoImpl implements VisitReasonTypeDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public VisitReasonType getVisitReasonTypeById( Integer id )
    {
        return (VisitReasonType) hibernateTemplate.get( VisitReasonType.class,
            id );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VisitReasonType> getVisitReasonTypes()
    {
        return (List<VisitReasonType>) hibernateTemplate
            .find( "from VisitReasonType where deleted = false " );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addVisitReasonType( VisitReasonType visitReasonType )
    {
        hibernateTemplate.saveOrUpdate( visitReasonType );

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteVisitReasonType( VisitReasonType visitReasonType )
    {
        hibernateTemplate.delete( visitReasonType );

    }

}
