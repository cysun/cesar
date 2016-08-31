package cesar.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "courses_taken")
public class CourseTaken implements Serializable {

    private static final long  serialVersionUID = 1L;

    public static final String gradeValues[]    = { "A", "A-", "B+", "B", "B-",
        "C+", "C", "C-", "D+", "D", "D-", "F", "NC", "CR" };

    public static final String quarterValues[]  = { "Fall", "Winter", "Spring",
        "Summer"                               };
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer            id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User               student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course             course;

    private String             grade;

    private String             quarter;

    private String             year;

    public String[] getGradeValues()
    {
        return gradeValues;
    }

    public String[] getQuarterValues()
    {
        return quarterValues;
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

    public Course getCourse()
    {
        return course;
    }

    public void setCourse( Course course )
    {
        this.course = course;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade( String grade )
    {
        this.grade = grade;
    }

    public String getQuarter()
    {
        return quarter;
    }

    public void setQuarter( String quarter )
    {
        this.quarter = quarter;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear( String year )
    {
        this.year = year;
    }

}
