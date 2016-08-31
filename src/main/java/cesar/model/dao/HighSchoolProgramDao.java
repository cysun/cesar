package cesar.model.dao;

import java.util.List;

import cesar.model.HighSchoolProgram;

public interface HighSchoolProgramDao {

    public HighSchoolProgram getHighSchoolProgramById( Integer id );

    public List<HighSchoolProgram> getHighSchoolPrograms();

}
