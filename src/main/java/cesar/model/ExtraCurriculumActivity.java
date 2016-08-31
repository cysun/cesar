package cesar.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "extra_curriculum_activities")
public class ExtraCurriculumActivity implements Serializable {

    private static final long           serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer                     id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User                        student;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ExtraCurriculumActivityType type;

    private String                      description;

    public ExtraCurriculumActivity()
    {
    }

    public ExtraCurriculumActivity( User student,
        ExtraCurriculumActivityType type, String description )
    {
        this.student = student;
        this.type = type;
        this.description = description;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public User getStudent()
    {
        return student;
    }

    public void setStudent( User student )
    {
        this.student = student;
    }

    public ExtraCurriculumActivityType getType()
    {
        return type;
    }

    public void setType( ExtraCurriculumActivityType type )
    {
        this.type = type;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

}
