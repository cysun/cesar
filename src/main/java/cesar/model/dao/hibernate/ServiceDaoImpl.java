package cesar.model.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Quarter;
import cesar.model.Service;
import cesar.model.dao.ServiceDao;

@Repository("serviceDao")
public class ServiceDaoImpl implements ServiceDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public Service getServiceById( Integer id )
    {
        return (Service) hibernateTemplate.get( Service.class, id );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveService( Service service )
    {
        hibernateTemplate.saveOrUpdate( service );

    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getServicesByQuarter(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<Service>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( Service.class )
                
                	.setFetchMode("serviceType", FetchMode.JOIN)
                	.add(Restrictions.sqlRestriction("quarter(create_date) >= " + fromQuarter.getCode() + " and quarter(create_date) <= " + toQuarter.getCode()))
                	.setProjection(Projections.projectionList()
                		.add(Projections.rowCount())
                    	.add(Projections.sqlGroupProjection("quarter(create_date) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER }))
                    	.add( Projections.groupProperty("serviceType"))
                    );
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }

}
