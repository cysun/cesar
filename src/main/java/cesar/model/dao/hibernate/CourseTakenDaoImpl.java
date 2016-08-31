package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.CourseTaken;
import cesar.model.User;
import cesar.model.dao.CourseTakenDao;

@Repository("courseTakenDao")
@SuppressWarnings({ "unchecked" })
public class CourseTakenDaoImpl implements CourseTakenDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public CourseTaken getCourseTakenById( Integer id )
    {
        return (CourseTaken) hibernateTemplate.get( CourseTaken.class, id );
    }

    @Override
    public List<CourseTaken> getCourseTakenByStudent( User student )
    {
        return (List<CourseTaken>) hibernateTemplate.find(
            "from CourseTaken where student = ? ", student );
    }

    public List<CourseTaken> getNonrepeatableCourseTakenByStudent( User student )
    {
        return (List<CourseTaken>) hibernateTemplate
            .find(
                "from CourseTaken ct where ct.student = ? and ct.course.repeatable = false ",
                student );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveCourseTaken( CourseTaken courseTaken )
    {
        hibernateTemplate.saveOrUpdate( courseTaken );

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteCourseTaken( CourseTaken courseTaken )
    {
        hibernateTemplate.delete( courseTaken );

    }

}
