package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.ExtraCurriculumActivity;
import cesar.model.User;
import cesar.model.dao.ExtraCurriculumActivityDao;

@Repository("extraCurriculumActivityDao")
public class ExtraCurriculumActivityDaoImpl implements
    ExtraCurriculumActivityDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public ExtraCurriculumActivity getExtraCurriculumActivityById( Integer id )
    {
        return (ExtraCurriculumActivity) hibernateTemplate.get(
            ExtraCurriculumActivity.class, id );
    }

    @SuppressWarnings("unchecked")
    public List<ExtraCurriculumActivity> getExtraCurriculumActivities( User user )
    {
        return (List<ExtraCurriculumActivity>) hibernateTemplate.find(
            "from ExtraCurriculumActivity where student = ?", user );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveExtraCurriculumActivity(
        ExtraCurriculumActivity extraCurriculumActivity )
    {
        hibernateTemplate.saveOrUpdate( extraCurriculumActivity );

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteExtraCurriculumActivity(
        ExtraCurriculumActivity extraCurriculumActivity )
    {
        hibernateTemplate.delete( extraCurriculumActivity );

    }

}
