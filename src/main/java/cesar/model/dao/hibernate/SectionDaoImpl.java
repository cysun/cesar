package cesar.model.dao.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Section;
import cesar.model.dao.SectionDao;

@Repository("sectionDao")
public class SectionDaoImpl implements SectionDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public Section getSectionById( Integer id )
    {

        return (Section) hibernateTemplate.get( Section.class, id );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void save( Section section )
    {
        hibernateTemplate.saveOrUpdate( section );

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSection( Section section )
    {
        hibernateTemplate.delete( section );

    }

}
