package cesar.model.dao;

import java.util.List;

import cesar.model.Course;
import cesar.model.Major;

public interface CourseDao {

    public Course getCourseById( Integer id );

    public Course getCourseByCode( String code );

    public List<Course> getUndergraduateCourses();

    public List<Course> getGraduateCourses();

    public List<Course> getAllCourses();

    public void saveCourse( Course course );

    public void deleteCourse( Course course );

}
