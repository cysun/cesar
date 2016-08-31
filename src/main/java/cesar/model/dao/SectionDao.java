package cesar.model.dao;

import cesar.model.Section;

public interface SectionDao {

    public Section getSectionById( Integer id );

    public void save( Section section );

    public void deleteSection( Section section );

}
