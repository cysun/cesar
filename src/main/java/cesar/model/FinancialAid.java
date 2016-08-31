package cesar.model;

import javax.persistence.*;

import java.io.*;

@Entity
@Table(name = "financial_aids")
public class FinancialAid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User              student;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private FinancialAidType  type;

    private String            details;

    public FinancialAid()
    {

    }

    public FinancialAid( User student, FinancialAidType type, String details )
    {
        this.student = student;
        this.type = type;
        this.details = details;
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

    public FinancialAidType getType()
    {
        return type;
    }

    public void setType( FinancialAidType type )
    {
        this.type = type;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails( String details )
    {
        this.details = details;
    }

}
