package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Course;
import cesar.model.Major;
import cesar.model.dao.CourseDao;

@Repository("courseDao")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public Course getCourseById( Integer id )
    {
        return (Course) hibernateTemplate.get( Course.class, id );
    }

    @Override
    public Course getCourseByCode( String code )
    {
        List results = hibernateTemplate.find(
            "from Course where deleted = false and code = ?", code );
        return results.size() == 0 ? null : (Course) results.get( 0 );
    }

    @Override
    public List<Course> getUndergraduateCourses()
    {
        return (List<Course>) hibernateTemplate
            .find( "from Course where isGraduateCourse = false and deleted  = false " );
    }

    @Override
    public List<Course> getGraduateCourses()
    {
        return (List<Course>) hibernateTemplate
            .find( "from Course where isGraduateCourse = true and deleted  = false" );
    }

    @Override
    public List<Course> getAllCourses()
    {
        return (List<Course>) hibernateTemplate
            .find( "from Course where deleted  = false" );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveCourse( Course course )
    {
        hibernateTemplate.saveOrUpdate( course );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteCourse( Course course )
    {
        hibernateTemplate.delete( course );

    }
}
