package cesar.model.dao;

import cesar.model.Prerequisite;

public interface PrerequisiteDao {

    public Prerequisite getPrerequisiteById( Integer id );

    public void savePrerequisite( Prerequisite prerequisite );

}
