package cesar.model.dao;

import java.util.List;

import cesar.model.CoursePlan;
import cesar.model.Major;
import cesar.model.Quarter;
import cesar.model.User;

public interface CoursePlanDao {

    public CoursePlan getCoursePlanById( Integer id );

    public List<CoursePlan> getCoursePlanByStudent( User student );

    public void saveCoursePlan( CoursePlan coursePlan );

    public void deleteCoursePlan( CoursePlan coursePlan );

    public List<CoursePlan> getCoursePlanTemplates();

    public List<CoursePlan> getCoursePlanTemplatesByMajor( Major major );
    
    public List<CoursePlan> getCoursePlans();
    
    public List<CoursePlan> getCoursePlansByQuarter(Quarter fromQuarter, Quarter toQuarter);
    
    public List<CoursePlan> getCoursePlansByGender(Quarter fromQuarter, Quarter toQuarter);

    public List<CoursePlan> getCoursePlansByDepartment(Quarter fromQuarter, Quarter toQuarter);
    
    public List<CoursePlan> getCoursePlansByStudentClassification(Quarter fromQuarter, Quarter toQuarter);
    
    public List<CoursePlan> getCoursePlansByQuarterOfStudent(Quarter fromQuarter, Quarter toQuarter, int fromY, int toY);
    
    public List<CoursePlan> getCoursePlansByGenderOfStudent(Quarter fromQuarter, Quarter toQuarter, int fromY, int toY);

    public List<CoursePlan> getCoursePlansByDepartmentOfStudent(Quarter fromQuarter, Quarter toQuarter, int fromY, int toY);
}
