package cesar.model.dao;

import java.util.List;

import cesar.model.Course;
import cesar.model.CourseTransferred;
import cesar.model.CourseWaived;
import cesar.model.User;

public interface CourseWaivedDao {

    public CourseWaived getCourseWaivedById( Integer id );

    public CourseWaived getCourseWaived( User student, Course course );

    public List<CourseWaived> getCourseWaiveds( User student );

    public List<CourseWaived> getNonrepeatableCourseWaivedsByStudent(
        User student );

    public void saveCourseWaived( CourseWaived courseWaived );

    public void deleteCourseWaiver( CourseWaived courseWaived );

}
