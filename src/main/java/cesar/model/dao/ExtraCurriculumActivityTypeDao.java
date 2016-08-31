package cesar.model.dao;

import java.util.List;

import cesar.model.ExtraCurriculumActivityType;

public interface ExtraCurriculumActivityTypeDao {

    public ExtraCurriculumActivityType getExtraCurriculumActivityTypeById(
        Integer id );

    public List<ExtraCurriculumActivityType> getExtraCurriculumActivityTypes();

}
