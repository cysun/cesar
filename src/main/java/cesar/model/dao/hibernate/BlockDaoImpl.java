package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cesar.model.Block;
import cesar.model.User;
import cesar.model.dao.BlockDao;

@Repository("blockDao")
public class BlockDaoImpl implements BlockDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public Block getBlockById( Integer id )
    {
        return (Block) hibernateTemplate.get( Block.class, id );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Block> getBlock()
    {
        return (List<Block>) hibernateTemplate.find( "from Block" );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Block> getBlocksByAdvisor( User advisor )
    {
        return (List<Block>) hibernateTemplate.find(
            "from Block where advisor = ?", advisor );
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveBlock( Block block )
    {
        hibernateTemplate.saveOrUpdate( block );

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteBlock( Block block )
    {
        hibernateTemplate.delete( block );
    }

}
