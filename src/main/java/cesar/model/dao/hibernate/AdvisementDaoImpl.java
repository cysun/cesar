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

import cesar.model.dao.AdvisementDao;

import cesar.model.Advisement;
import cesar.model.Quarter;
import cesar.model.User;

@Repository("advisementDao")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AdvisementDaoImpl implements AdvisementDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public Advisement getAdvisementById( Integer id )
    {
        return (Advisement) hibernateTemplate.get( Advisement.class, id );
    }

    @Override
    public List<Advisement> getAdvisementsByStudent( User student )
    {
        List results = hibernateTemplate.find(
            "from Advisement where student = ? and deleted = false", student );
        return results;
    }

    @Override
    public List<Advisement> getAdvisementsByAdvisor( User advisor )
    {
        List results = hibernateTemplate.find(
            "from Advisement where advisor= ? and deleted = false ", advisor );
        return results;
    }

    @Override
    public List<Advisement> getAdvisementsByStudentForStudentOnly( User student )
    {
        List results = hibernateTemplate
            .find(
                "from Advisement where forAdvisorOnly = false and deleted = false and student = ?",
                student );
        return results;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveAdvisement( Advisement advisement )
    {
        hibernateTemplate.saveOrUpdate( advisement );

    }

    @SuppressWarnings("unchecked")
    @Override
    public List getAdvisementsByQuarter(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<Advisement>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( Advisement.class )
                
                	.add(Restrictions.sqlRestriction("quarter(create_date) >= " + fromQuarter.getCode() + " and quarter(create_date) <= " + toQuarter.getCode()))
                	.setProjection(Projections.projectionList()
                		.add(Projections.countDistinct("student"))
                    	.add(Projections.sqlGroupProjection("quarter(create_date) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER }))
                    );
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getAdvisementsByGender(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<Advisement>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( Advisement.class, "advisement" )
                	.setFetchMode("advisement.student", FetchMode.JOIN)
                    .createAlias("advisement.student", "student")
                	.add(Restrictions.sqlRestriction("quarter(create_date) >= " + fromQuarter.getCode() + " and quarter(create_date) <= " + toQuarter.getCode()))
                	.setProjection(Projections.projectionList()
                		.add(Projections.countDistinct("student"))
                    	.add(Projections.sqlGroupProjection("quarter(create_date) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER }))
                    	.add( Projections.groupProperty("student.gender"))
                	);
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getAdvisementsByDepartment(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<Advisement>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
            	Criteria criteria = session.createCriteria( Advisement.class, "advisement" )
                  	.setFetchMode("advisement.student", FetchMode.JOIN)
                    .createAlias("advisement.student", "student")
                   	.add(Restrictions.sqlRestriction("quarter(create_date) >= " + fromQuarter.getCode() + " and quarter(create_date) <= " + toQuarter.getCode()))
                   	.setProjection(Projections.projectionList()
                   		.add(Projections.countDistinct("student"))
                       	.add(Projections.sqlGroupProjection("quarter(create_date) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER }))
                       	.add( Projections.groupProperty("student.major"))
                   	);
                    
                List list = criteria.list();
                    
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getAdvisementsByStudentClassification(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<Advisement>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
            	Criteria criteria = session.createCriteria( Advisement.class, "advisement" )
                  	.setFetchMode("advisement.student", FetchMode.JOIN)
                    .createAlias("advisement.student", "student")
                   	.add(Restrictions.sqlRestriction("quarter(create_date) >= " + fromQuarter.getCode() + " and quarter(create_date) <= " + toQuarter.getCode()))
                   	.setProjection(Projections.projectionList()
                   		.add(Projections.countDistinct("student"))
                       	.add(Projections.sqlGroupProjection("quarter(create_date) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER }))
                       	.add(Projections.sqlGroupProjection("quarter(create_date) - (quarter_admitted) as studentYear", "studentYear", new String[] { "studentYear" }, new Type[] { StandardBasicTypes.INTEGER }))
                   	);
                    
                List list = criteria.list();
                    
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getAdvisementsByQuarterOfStudent(final Quarter fromQuarter, final Quarter toQuarter, final int fromY, final int toY) {
    	return (List<Advisement>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( Advisement.class, "advisement" )
                	.setFetchMode("advisement.student", FetchMode.JOIN)
                    .createAlias("advisement.student", "student")
                	.add(Restrictions.sqlRestriction("quarter(create_date) - (quarter_admitted) >= " + fromY + " and quarter(create_date) - (quarter_admitted) < " + toY))
                	.add(Restrictions.sqlRestriction("quarter(create_date) >= " + fromQuarter.getCode() + " and quarter(create_date) <= " + toQuarter.getCode()))
                	.setProjection(Projections.projectionList()
                		.add(Projections.countDistinct("student"))
                    	.add(Projections.sqlGroupProjection("quarter(create_date) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER }))
                    );
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getAdvisementsByGenderOfStudent(final Quarter fromQuarter, final Quarter toQuarter, final int fromY, final int toY) {
    	return (List<Advisement>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( Advisement.class, "advisement" )
                	.setFetchMode("advisement.student", FetchMode.JOIN)
                    .createAlias("advisement.student", "student")
                    .add(Restrictions.sqlRestriction("quarter(create_date) - (quarter_admitted) >= " + fromY + " and quarter(create_date) - (quarter_admitted) < " + toY))
                	.add(Restrictions.sqlRestriction("quarter(create_date) >= " + fromQuarter.getCode() + " and quarter(create_date) <= " + toQuarter.getCode()))
                	.setProjection(Projections.projectionList()
                		.add(Projections.countDistinct("student"))
                    	.add(Projections.sqlGroupProjection("quarter(create_date) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER }))
                    	.add( Projections.groupProperty("student.gender"))
                	);
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getAdvisementsByDepartmentOfStudent(final Quarter fromQuarter, final Quarter toQuarter, final int fromY, final int toY) {
    	return (List<Advisement>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
            	Criteria criteria = session.createCriteria( Advisement.class, "advisement" )
                  	.setFetchMode("advisement.student", FetchMode.JOIN)
                    .createAlias("advisement.student", "student")
                    .add(Restrictions.sqlRestriction("quarter(create_date) - (quarter_admitted) >= " + fromY + " and quarter(create_date) - (quarter_admitted) < " + toY))
                   	.add(Restrictions.sqlRestriction("quarter(create_date) >= " + fromQuarter.getCode() + " and quarter(create_date) <= " + toQuarter.getCode()))
                   	.setProjection(Projections.projectionList()
                   		.add(Projections.countDistinct("student"))
                       	.add(Projections.sqlGroupProjection("quarter(create_date) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER }))
                       	.add( Projections.groupProperty("student.major"))
                   	);
                    
                List list = criteria.list();
                    
                return list;
            }

        } );
    }
}
