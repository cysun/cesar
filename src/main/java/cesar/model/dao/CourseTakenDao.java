package cesar.model.dao;

import java.util.List;

import cesar.model.CourseTaken;
import cesar.model.User;

public interface CourseTakenDao {

    public CourseTaken getCourseTakenById( Integer id );

    public List<CourseTaken> getCourseTakenByStudent( User student );

    public List<CourseTaken> getNonrepeatableCourseTakenByStudent( User student );

    public void saveCourseTaken( CourseTaken courseTaken );

    public void deleteCourseTaken( CourseTaken courseTaken );

}
