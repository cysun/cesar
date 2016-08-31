package cesar.model.dao;

import java.util.List;

import cesar.model.Block;
import cesar.model.User;

public interface BlockDao {

    public Block getBlockById( Integer id );

    public List<Block> getBlock();

    public List<Block> getBlocksByAdvisor( User advisor );

    public void saveBlock( Block block );

    public void deleteBlock( Block block );

}
