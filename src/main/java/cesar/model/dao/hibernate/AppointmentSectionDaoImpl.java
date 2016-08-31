package cesar.model.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
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

import cesar.model.AppointmentSection;
import cesar.model.Course;
import cesar.model.Quarter;
import cesar.model.Schedule;
import cesar.model.User;
import cesar.model.dao.AppointmentSectionDao;

@Repository("appointmentSectionDao")
public class AppointmentSectionDaoImpl implements AppointmentSectionDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public AppointmentSection getAppointmentById( Integer id )
    {
        return (AppointmentSection) hibernateTemplate.get(
            AppointmentSection.class, id );
    }

    @SuppressWarnings({ "unchecked" })
    public List<AppointmentSection> getAvailableAppointmentSectionsByStudent(
        User student )
    {
        return (List<AppointmentSection>) hibernateTemplate.find(
            "from AppointmentSection where isAvailable = true and student = ? order by startTime asc",
            student );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AppointmentSection> getAvailableAppointmentSectionsByAdvisor(
        User advisor )
    {
        return (List<AppointmentSection>) hibernateTemplate.find(
            "from AppointmentSection where isAvailable = true and advisor = ? order by startTime asc",
            advisor );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveAppointmentSection( AppointmentSection appointmentSection )
    {
        hibernateTemplate.saveOrUpdate( appointmentSection );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteAppointmentSection( AppointmentSection appointmentSection )
    {
        hibernateTemplate.delete( appointmentSection );

    }

    @Override
    public AppointmentSection getAppointmentSectionByAdvisorAndStartTimeAndNotWorkingApp(
        User advisor, Date start )
    {
        List results = hibernateTemplate
            .find(
                "from AppointmentSection where isWalkInAppointment = false and isAvailable = true and startTime = ? and advisor = ?",
                start, advisor );
        return results.size() == 0 ? null : (AppointmentSection) results
            .get( 0 );

    }

    @SuppressWarnings("unchecked")
    public List<AppointmentSection> getActiveAppointmentSectionByAdvisorWithSpeDay(
        User advisor, Date currentDate )
    {
        return (List<AppointmentSection>) hibernateTemplate
            .find(
                "from AppointmentSection where isAvailable = true and advisor = ? and endTime > ? order by startTime asc",
                advisor, currentDate );

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AppointmentSection> getAppointmentSectionsByStudentWithSpeDay(
        User student, Date currentTime )
    {
        return (List<AppointmentSection>) hibernateTemplate
            .find(
                "from AppointmentSection where isAvailable = true and student  = ? and endTime > ? order by startTime asc",
                student, currentTime );
    }

    @Override
    public AppointmentSection getAppointmentSectionByAdvisorAndStartTimeAndWorkingApp(
        User advisor, Date start )
    {
        @SuppressWarnings("rawtypes")
        List results = hibernateTemplate
            .find(
                "from AppointmentSection where isAvailable = true and startTime = ? and advisor = ?",
                start, advisor );
        return results.size() == 0 ? null : (AppointmentSection) results
            .get( 0 );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AppointmentSection> getAppointmentSectionByAdvisorAndBetweenSpeDates(
        User advisor, Date start, Date end )
    {
        return (List<AppointmentSection>) hibernateTemplate
            .find(
                "from AppointmentSection where isAvailable = true and advisor = ? and startTime >= ? and startTime < ? or isAvailable = true and advisor = ? and endTime > ? and endTime <= ? order by startTime asc",
                advisor, start, end, advisor, start, end );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List getCompletedAppointmentSectionsByQuarter(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<AppointmentSection>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( AppointmentSection.class )
                		.add(Restrictions.eq("title", "Appointment"))
                		.add(Restrictions.and(Restrictions.eq("cancelledByStudent", false), Restrictions.and(Restrictions.eq("cancelledByAdvisor", false), Restrictions.and(Restrictions.eq("isAvailable", true), Restrictions.eq("isShowUp", true)))))
                		.add(Restrictions.sqlRestriction("quarter(start_time) >= " + fromQuarter.getCode() + " and quarter(start_time) <= " + toQuarter.getCode()))
                		
                		.setProjection(Projections.projectionList()
                				.add(Projections.rowCount())
                				.add(Projections.sqlGroupProjection("quarter(start_time) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER })));
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getCancelledAppointmentSectionsByQuarter(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<AppointmentSection>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            { 
                Criteria criteria = session.createCriteria( AppointmentSection.class )
                		.add(Restrictions.eq("title", "Appointment"))
                		.add(Restrictions.or(Restrictions.eq("cancelledByAdvisor", true), Restrictions.eq("cancelledByStudent", true)))
                		.add(Restrictions.sqlRestriction("quarter(start_time) >= " + fromQuarter.getCode() + " and quarter(start_time) <= " + toQuarter.getCode()))
                		.setProjection(Projections.projectionList()
                				.add(Projections.rowCount())
                				.add(Projections.sqlGroupProjection("quarter(start_time) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER })));
                
                List list = criteria.list();
            	
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getMissedAppointmentSectionsByQuarter(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<AppointmentSection>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( AppointmentSection.class )
                		.add(Restrictions.eq("title", "Appointment"))
                		.add(Restrictions.eq("isShowUp", false))
                		.add(Restrictions.sqlRestriction("quarter(start_time) >= " + fromQuarter.getCode() + " and quarter(start_time) <= " + toQuarter.getCode()))
                		.setProjection(Projections.projectionList()
                				.add(Projections.rowCount())
                				.add(Projections.sqlGroupProjection("quarter(start_time) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER })));
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getSeenWalkInAppointmentSectionsByQuarter(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<AppointmentSection>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {
                Criteria criteria = session.createCriteria( AppointmentSection.class )
                		.add(Restrictions.eq("title", " Walk In Appointment "))
                		.add(Restrictions.and(Restrictions.eq("cancelledByStudent", false), Restrictions.and(Restrictions.eq("cancelledByAdvisor", false), Restrictions.and(Restrictions.eq("isAvailable", true), Restrictions.eq("isShowUp", true)))))
                		.add(Restrictions.sqlRestriction("quarter(start_time) >= " + fromQuarter.getCode() + " and quarter(start_time) <= " + toQuarter.getCode()))
                		.setProjection(Projections.projectionList()
                				.add(Projections.rowCount())
                				.add(Projections.sqlGroupProjection("quarter(start_time) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER })));
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List getNotSeenWalkInAppointmentSectionsByQuarter(final Quarter fromQuarter, final Quarter toQuarter) {
    	return (List<AppointmentSection>) hibernateTemplate.execute( new HibernateCallback() {

            public Object doInHibernate( Session session )
            {  
                Criteria criteria = session.createCriteria( AppointmentSection.class )
                		.add(Restrictions.eq("title", " Walk In Appointment "))
                		.add(Restrictions.or(Restrictions.eq("cancelledByStudent", true), Restrictions.or(Restrictions.eq("cancelledByAdvisor", true), Restrictions.or(Restrictions.eq("isAvailable", false), Restrictions.eq("isShowUp", false)))))
                		.add(Restrictions.sqlRestriction("quarter(start_time) >= " + fromQuarter.getCode() + " and quarter(start_time) <= " + toQuarter.getCode()))
                		.setProjection(Projections.projectionList()
                				.add(Projections.rowCount())
                				.add(Projections.sqlGroupProjection("quarter(start_time) as quarter", "quarter", new String[] { "quarter" }, new Type[] { StandardBasicTypes.INTEGER })));
                
                List list = criteria.list();
                
                return list;
            }

        } );
    }
  
}
