package cesar.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "advisements")
public class Advisement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User              student;

    @ManyToOne
    @JoinColumn(name = "advisor_id", nullable = false)
    private User              advisor;

    @Column(nullable = false)
    private String            comment;

    @Column(name = "create_date")
    private Date              createDate;

    @Column(nullable = false)
    private boolean           editable;

    @ManyToOne
    @JoinColumn(name = "edited_by")
    private User              editedBy;
    @Column(name = "edit_date")
    private Date              editDate;

    @Column(name = "for_advisor_only", nullable = false)
    private boolean           forAdvisorOnly;
    @Column(name = "emailed_to_student", nullable = false)
    private boolean           emailedToStudent;

    private boolean           deleted;

    public Advisement()
    {
        createDate = new Date();
        editable = true;
        forAdvisorOnly = false;
        emailedToStudent = false;
        deleted = false;
    }

    public Advisement( User student, User advisor, String comment )
    {
        this();

        this.student = student;
        this.advisor = advisor;
        this.comment = comment;
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

    public User getAdvisor()
    {
        return advisor;
    }

    public void setAdvisor( User advisor )
    {
        this.advisor = advisor;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate( Date createDate )
    {
        this.createDate = createDate;
    }

    public boolean isEditable()
    {
        return editable;
    }

    public void setEditable( boolean editable )
    {
        this.editable = editable;
    }

    public User getEditedBy()
    {
        return editedBy;
    }

    public void setEditedBy( User editedBy )
    {
        this.editedBy = editedBy;
    }

    public Date getEditDate()
    {
        return editDate;
    }

    public void setEditDate( Date editDate )
    {
        this.editDate = editDate;
    }

    public boolean isForAdvisorOnly()
    {
        return forAdvisorOnly;
    }

    public void setForAdvisorOnly( boolean forAdvisorOnly )
    {
        this.forAdvisorOnly = forAdvisorOnly;
    }

    public boolean isEmailedToStudent()
    {
        return emailedToStudent;
    }

    public void setEmailedToStudent( boolean emailedToStudent )
    {
        this.emailedToStudent = emailedToStudent;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted( boolean deleted )
    {
        this.deleted = deleted;
    }

}
