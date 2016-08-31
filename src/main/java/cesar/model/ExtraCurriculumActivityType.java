package cesar.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "extra_curriculum_activity_types")
public class ExtraCurriculumActivityType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    private String            name;

    public ExtraCurriculumActivityType()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

}
