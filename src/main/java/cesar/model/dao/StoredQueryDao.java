package cesar.model.dao;

import java.util.List;

import cesar.model.StoredQuery;

public interface StoredQueryDao {

    public StoredQuery getStoredQueryById( Integer id );

    public StoredQuery getStoredQueryByName( String name );

    public List<StoredQuery> getStoredQueries();

    public void saveStoredQuery( StoredQuery storedQuery );
}
