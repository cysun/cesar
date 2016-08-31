package cesar.model.dao;

import java.util.List;

import cesar.model.Ethnicity;

public interface EthnicityDao {

    public Ethnicity getEthnicityById( Integer id );

    public List<Ethnicity> getEthnicities();

}
