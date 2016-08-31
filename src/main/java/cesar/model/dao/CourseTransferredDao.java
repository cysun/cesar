package cesar.model.dao;

import java.util.List;

import cesar.model.Course;
import cesar.model.CourseTaken;
import cesar.model.CourseTransferred;
import cesar.model.User;

public interface CourseTransferredDao {

    public CourseTransferred getCourseTransferById( Integer id );

    public CourseTransferred getCourseTransfer( User student, Course course );

    public List<CourseTransferred> getCourseTransferreds( User student );

    public List<CourseTransferred> getNonrepeatableCourseTransferredsByStudent(
        User student );

    public void saveCourseTransfer( CourseTransferred courseTransferred );

    public void deleteCourseTransfer( CourseTransferred courseTransferred );

}
