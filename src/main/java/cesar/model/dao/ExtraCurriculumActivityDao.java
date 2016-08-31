package cesar.model.dao;

import java.util.List;

import cesar.model.ExtraCurriculumActivity;
import cesar.model.User;

public interface ExtraCurriculumActivityDao {

    public ExtraCurriculumActivity getExtraCurriculumActivityById( Integer id );

    public List<ExtraCurriculumActivity> getExtraCurriculumActivities( User user );

    public void saveExtraCurriculumActivity(
        ExtraCurriculumActivity extraCurriculumActivity );

    public void deleteExtraCurriculumActivity(
        ExtraCurriculumActivity extraCurriculumActivity );

}
