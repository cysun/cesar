package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Course;
import cesar.model.CourseTaken;
import cesar.model.CourseTransferred;
import cesar.model.CourseWaived;
import cesar.model.User;
import cesar.model.dao.CourseTransferredDao;

@Repository("courseTransferredDao")
public class CourseTransferredDaoImpl implements CourseTransferredDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public CourseTransferred getCourseTransferById( Integer id )
    {
        return (CourseTransferred) hibernateTemplate.get(
            CourseTransferred.class, id );
    }

    @Override
    public CourseTransferred getCourseTransfer( User student, Course course )
    {
        @SuppressWarnings("rawtypes")
        List results = hibernateTemplate.find(
            "from CourseTransferred where student = ? and course = ?", student,
            course );
        return results.size() == 0 ? null : (CourseTransferred) results.get( 0 );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CourseTransferred> getCourseTransferreds( User student )
    {
        return (List<CourseTransferred>) hibernateTemplate.find(
            "from CourseTransferred where student = ?", student );
    }

    @SuppressWarnings("unchecked")
    public List<CourseTransferred> getNonrepeatableCourseTransferredsByStudent(
        User student )
    {
        return (List<CourseTransferred>) hibernateTemplate
            .find(
                "from CourseTransferred ct where ct.student = ? and ct.course.repeatable = false ",
                student );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveCourseTransfer( CourseTransferred courseTransferred )
    {
        hibernateTemplate.saveOrUpdate( courseTransferred );

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteCourseTransfer( CourseTransferred courseTransferred )
    {
        hibernateTemplate.delete( courseTransferred );

    }

}
