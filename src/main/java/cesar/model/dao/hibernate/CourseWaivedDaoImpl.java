package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Course;
import cesar.model.CourseTransferred;
import cesar.model.CourseWaived;
import cesar.model.User;
import cesar.model.dao.CourseWaivedDao;

@Repository("courseWaivedDao")
public class CourseWaivedDaoImpl implements CourseWaivedDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public CourseWaived getCourseWaivedById( Integer id )
    {
        return (CourseWaived) hibernateTemplate.get( CourseWaived.class, id );
    }

    @Override
    public CourseWaived getCourseWaived( User student, Course course )
    {
        @SuppressWarnings("rawtypes")
        List results = hibernateTemplate.find(
            "from CourseWaived where student = ? and course = ?", student,
            course );
        return results.size() == 0 ? null : (CourseWaived) results.get( 0 );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CourseWaived> getCourseWaiveds( User student )
    {
        return (List<CourseWaived>) hibernateTemplate.find(
            "from CourseWaived where student = ?", student );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveCourseWaived( CourseWaived courseWaived )
    {
        hibernateTemplate.saveOrUpdate( courseWaived );

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteCourseWaiver( CourseWaived courseWaived )
    {
        hibernateTemplate.delete( courseWaived );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CourseWaived> getNonrepeatableCourseWaivedsByStudent(
        User student )
    {
        return (List<CourseWaived>) hibernateTemplate
            .find(
                "from CourseWaived cw where cw.student = ? and cw.course.repeatable = false ",
                student );
    }

}
