package cesar.model.dao;

import java.util.List;

import cesar.model.Major;

public interface MajorDao {

    public Major getMajorById( Integer id );

    public Major getMajorByName( String name );

    public Major getMajorBySymbol( String symbol );

    public List<Major> getRegularMajors();

    public List<Major> getMajors();

    public void saveMajor( Major major );

}
