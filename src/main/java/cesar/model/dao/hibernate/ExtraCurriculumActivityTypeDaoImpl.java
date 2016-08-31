package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cesar.model.ExtraCurriculumActivityType;
import cesar.model.dao.ExtraCurriculumActivityTypeDao;

@Repository("extraCurriculumActivityTypeDao")
public class ExtraCurriculumActivityTypeDaoImpl implements
    ExtraCurriculumActivityTypeDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public ExtraCurriculumActivityType getExtraCurriculumActivityTypeById(
        Integer id )
    {
        return (ExtraCurriculumActivityType) hibernateTemplate.get(
            ExtraCurriculumActivityType.class, id );
    }

    @SuppressWarnings("unchecked")
    public List<ExtraCurriculumActivityType> getExtraCurriculumActivityTypes()
    {
        String query = "from ExtraCurriculumActivityType order by name asc";
        return (List<ExtraCurriculumActivityType>) hibernateTemplate
            .find( query );
    }

}
