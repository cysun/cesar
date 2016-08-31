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

import cesar.model.CoursePlan;
import cesar.model.Major;
import cesar.model.Quarter;
import cesar.model.User;
import cesar.model.dao.CoursePlanDao;

@Repository("coursePlanDao")
public class CoursePlanDaoImpl implements CoursePlanDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public CoursePlan getCoursePlanById( Integer id )
    {
        return (CoursePlan) hibernateTemplate.get( CoursePlan.class, id );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoursePlan> getCoursePlanByStudent( User student )
    {
        return (List<CoursePlan>) hibernateTemplate.find(
            "from CoursePlan where student = ? and deleted = false", student );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveCoursePlan( CoursePlan coursePlan )
    {
        hibernateTemplate.merge( coursePlan );

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteCoursePlan( CoursePlan coursePlan )
    {
        hibernateTemplate.delete( coursePlan );

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoursePlan> getCoursePlanTemplates()
    {
        return (List<CoursePlan>) hibernateTemplate
            .find( "from CoursePlan where isTemplate = true  and deleted = false" );

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoursePlan> getCoursePlanTemplatesByMajor( Major major )
    {
        return (List<CoursePlan>) hibernateTemplate
            .find(
                "from CoursePlan where isTemplate = true  and deleted = false and major = ?",
                major );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoursePlan> getCoursePlans()
    {
        return (List<CoursePlan>) hibernateTemplate
            .find( "from CoursePlan where isTemplate = false  and deleted = false" );

    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getCoursePlansByQuarter(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<CoursePlan>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( CoursePlan.class )
                
                	.add(Restrictions.eq("approved", true))
                	.add(Restrictions.sqlRestriction("quarter(time_stamp) >= " + fromQuarter.getCode() + " and quarter(time_stamp) <= " + toQuarter.getCode()))
                	.setProjection(Projections.projectionList()
                		.add(Projections.countDistinct("student"))
                    	.add(Projections.sqlGroupProjection("quarter(time_stamp) as q", "q", new String[] { "q" }, new Type[] { StandardBasicTypes.INTEGER }))
                    );
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getCoursePlansByGender(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<CoursePlan>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( CoursePlan.class, "coursePlan" )
                	.setFetchMode("coursePlan.student", FetchMode.JOIN)
                    .createAlias("coursePlan.student", "student")
                    .add(Restrictions.eq("approved", true))
                	.add(Restrictions.sqlRestriction("quarter(time_stamp) >= " + fromQuarter.getCode() + " and quarter(time_stamp) <= " + toQuarter.getCode()))
                	.setProjection(Projections.projectionList()
                		.add(Projections.countDistinct("student"))
                    	.add(Projections.sqlGroupProjection("quarter(time_stamp) as q", "q", new String[] { "q" }, new Type[] { StandardBasicTypes.INTEGER }))
                    	.add( Projections.groupProperty("student.gender"))
                	);
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getCoursePlansByDepartment(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<CoursePlan>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
            	Criteria criteria = session.createCriteria( CoursePlan.class, "coursePlan" )
                  	.setFetchMode("coursePlan.student", FetchMode.JOIN)
                    .createAlias("coursePlan.student", "student")
                    .add(Restrictions.eq("approved", true))
                   	.add(Restrictions.sqlRestriction("quarter(time_stamp) >= " + fromQuarter.getCode() + " and quarter(time_stamp) <= " + toQuarter.getCode()))
                   	.setProjection(Projections.projectionList()
                   		.add(Projections.countDistinct("student"))
                       	.add(Projections.sqlGroupProjection("quarter(time_stamp) as q", "q", new String[] { "q" }, new Type[] { StandardBasicTypes.INTEGER }))
                       	.add( Projections.groupProperty("student.major"))
                   	);
                    
                List list = criteria.list();
                    
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getCoursePlansByStudentClassification(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<CoursePlan>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
            	Criteria criteria = session.createCriteria( CoursePlan.class, "coursePlan" )
                  	.setFetchMode("coursePlan.student", FetchMode.JOIN)
                    .createAlias("coursePlan.student", "student")
                    .add(Restrictions.eq("approved", true))
                   	.add(Restrictions.sqlRestriction("quarter(time_stamp) >= " + fromQuarter.getCode() + " and quarter(time_stamp) <= " + toQuarter.getCode()))
                   	.setProjection(Projections.projectionList()
                   		.add(Projections.countDistinct("student"))
                       	.add(Projections.sqlGroupProjection("quarter(time_stamp) as q", "q", new String[] { "q" }, new Type[] { StandardBasicTypes.INTEGER }))
                       	.add(Projections.sqlGroupProjection("quarter(time_stamp) - (quarter_admitted) as studentYear", "studentYear", new String[] { "studentYear" }, new Type[] { StandardBasicTypes.INTEGER }))
                   	);
                    
                List list = criteria.list();
                    
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getCoursePlansByQuarterOfStudent(final Quarter fromQuarter, final Quarter toQuarter, final int fromY, final int toY) {
    	return (List<CoursePlan>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( CoursePlan.class, "coursePlan" )
                      	.setFetchMode("coursePlan.student", FetchMode.JOIN)
                        .createAlias("coursePlan.student", "student")
                        .add(Restrictions.eq("approved", true))
                        .add(Restrictions.sqlRestriction("quarter(time_stamp) - (quarter_admitted) >= " + fromY + " and quarter(time_stamp) - (quarter_admitted) < " + toY))
                       	.add(Restrictions.sqlRestriction("quarter(time_stamp) >= " + fromQuarter.getCode() + " and quarter(time_stamp) <= " + toQuarter.getCode()))
                       	.setProjection(Projections.projectionList()
                       		.add(Projections.countDistinct("student"))
                           	.add(Projections.sqlGroupProjection("quarter(time_stamp) as q", "q", new String[] { "q" }, new Type[] { StandardBasicTypes.INTEGER }))
                        );
                        
                List list = criteria.list();
                
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getCoursePlansByGenderOfStudent(final Quarter fromQuarter, final Quarter toQuarter, final int fromY, final int toY) {
    	return (List<CoursePlan>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( CoursePlan.class, "coursePlan" )
                      	.setFetchMode("coursePlan.student", FetchMode.JOIN)
                        .createAlias("coursePlan.student", "student")
                        .add(Restrictions.eq("approved", true))
                        .add(Restrictions.sqlRestriction("quarter(time_stamp) - (quarter_admitted) >= " + fromY + " and quarter(time_stamp) - (quarter_admitted) < " + toY))
                       	.add(Restrictions.sqlRestriction("quarter(time_stamp) >= " + fromQuarter.getCode() + " and quarter(time_stamp) <= " + toQuarter.getCode()))
                       	.setProjection(Projections.projectionList()
                       		.add(Projections.countDistinct("student"))
                           	.add(Projections.sqlGroupProjection("quarter(time_stamp) as q", "q", new String[] { "q" }, new Type[] { StandardBasicTypes.INTEGER }))
                           	.add( Projections.groupProperty("student.gender"))
                        );
                        
                List list = criteria.list();
                
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getCoursePlansByDepartmentOfStudent(final Quarter fromQuarter, final Quarter toQuarter, final int fromY, final int toY) {
    	return (List<CoursePlan>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
            	Criteria criteria = session.createCriteria( CoursePlan.class, "coursePlan" )
                      	.setFetchMode("coursePlan.student", FetchMode.JOIN)
                        .createAlias("coursePlan.student", "student")
                        .add(Restrictions.eq("approved", true))
                        .add(Restrictions.sqlRestriction("quarter(time_stamp) - (quarter_admitted) >= " + fromY + " and quarter(time_stamp) - (quarter_admitted) < " + toY))
                       	.add(Restrictions.sqlRestriction("quarter(time_stamp) >= " + fromQuarter.getCode() + " and quarter(time_stamp) <= " + toQuarter.getCode()))
                       	.setProjection(Projections.projectionList()
                       		.add(Projections.countDistinct("student"))
                           	.add(Projections.sqlGroupProjection("quarter(time_stamp) as q", "q", new String[] { "q" }, new Type[] { StandardBasicTypes.INTEGER }))
                           	.add( Projections.groupProperty("student.major"))
                        );
                        
                List list = criteria.list();
                
                return list;
            }

        } );
    }
}
