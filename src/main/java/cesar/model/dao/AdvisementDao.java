package cesar.model.dao;

import java.util.List;

import cesar.model.Advisement;
import cesar.model.Quarter;
import cesar.model.User;

public interface AdvisementDao {

    public Advisement getAdvisementById( Integer id );

    public List<Advisement> getAdvisementsByStudent( User student );

    public List<Advisement> getAdvisementsByAdvisor( User advisor );

    public List<Advisement> getAdvisementsByStudentForStudentOnly( User student );

    public void saveAdvisement( Advisement advisement );
    
    public List<Advisement> getAdvisementsByQuarter(Quarter fromQuarter, Quarter toQuarter);
    
    public List<Advisement> getAdvisementsByGender(Quarter fromQuarter, Quarter toQuarter);

    public List<Advisement> getAdvisementsByDepartment(Quarter fromQuarter, Quarter toQuarter);
    
    public List<Advisement> getAdvisementsByStudentClassification(Quarter fromQuarter, Quarter toQuarter);
    
    public List<Advisement> getAdvisementsByQuarterOfStudent(Quarter fromQuarter, Quarter toQuarter, int fromY, int toY);
    
    public List<Advisement> getAdvisementsByGenderOfStudent(Quarter fromQuarter, Quarter toQuarter, int fromY, int toY);
    
    public List<Advisement> getAdvisementsByDepartmentOfStudent(Quarter fromQuarter, Quarter toQuarter, int fromY, int toY);
}
