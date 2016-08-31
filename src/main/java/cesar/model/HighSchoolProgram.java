package cesar.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "high_school_programs")
public class HighSchoolProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @Column(nullable = false)
    private String            name;

    public HighSchoolProgram()
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
