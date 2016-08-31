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
@Table(name = "courses_waived")
public class CourseWaived implements Serializable {

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

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course            course;

    private String            comment;

    @Column(name = "updated_date")
    private Date              date;

    public CourseWaived()
    {
        date = new Date();
    }

    public CourseWaived( User student, User advisor, Course course,
        String comment )
    {
        this.student = student;
        this.advisor = advisor;
        this.course = course;
        this.comment = comment;
        date = new Date();
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

    public Course getCourse()
    {
        return course;
    }

    public void setCourse( Course course )
    {
        this.course = course;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

}
