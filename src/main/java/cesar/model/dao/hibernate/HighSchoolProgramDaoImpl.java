package cesar.model.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cesar.model.HighSchoolProgram;
import cesar.model.dao.HighSchoolProgramDao;

@Repository("highSchoolProgramDao")
public class HighSchoolProgramDaoImpl implements HighSchoolProgramDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public HighSchoolProgram getHighSchoolProgramById( Integer id )
    {
        return (HighSchoolProgram) hibernateTemplate.get(
            HighSchoolProgram.class, id );
    }

    @SuppressWarnings("unchecked")
    public List<HighSchoolProgram> getHighSchoolPrograms()
    {
        String query = "from HighSchoolProgram order by name asc";
        return (List<HighSchoolProgram>) hibernateTemplate.find( query );
    }

}
