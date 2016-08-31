package cesar.model.dao.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.GeneratedCin;
import cesar.model.dao.GeneratedCinDao;

@Repository("generatedCinDao")
public class GeneratedCinDaoImpl implements GeneratedCinDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveGeneratedCin( GeneratedCin generatedCin )
    {
        hibernateTemplate.saveOrUpdate( generatedCin );
    }

}
