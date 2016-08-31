package cesar.model.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.NoSeenReason;
import cesar.model.Quarter;
import cesar.model.dao.NoSeenReasonDao;

@Repository("noSeenReasonDao")
public class NoSeenReasonDaoImpl implements NoSeenReasonDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public NoSeenReason getNoSeenReasonById( Integer id )
    {
        return hibernateTemplate.get( NoSeenReason.class, id );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveNoSeenReason( NoSeenReason noSeenReason )
    {
        hibernateTemplate.saveOrUpdate( noSeenReason );

    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getNoSeenReasonsByQuarter(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<NoSeenReason>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( NoSeenReason.class )
                
                	.setFetchMode("noSeenReasonType", FetchMode.JOIN)
                	.add(Restrictions.sqlRestriction("quarter(create_date) >= " + fromQuarter.getCode() + " and quarter(create_date) <= " + toQuarter.getCode()))
                	.setProjection(Projections.projectionList()
                		.add(Projections.rowCount())
                    	.add(Projections.sqlGroupProjection("quarter(create_date) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER }))
                    	.add( Projections.groupProperty("noSeenReasonType"))
                    );
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }

}
