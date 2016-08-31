package cesar.model.dao;

import java.util.List;

import cesar.model.HighSchool;

public interface HighSchoolDao {

    public HighSchool getHighSchoolById( Integer id );

    public List<HighSchool> getHighSchools();

    public HighSchool getHighSchoolByName( String name );

    public void saveHighSchool( HighSchool highSchool );

}
