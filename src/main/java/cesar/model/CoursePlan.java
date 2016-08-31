package cesar.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "course_plans")
public class CoursePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    private String            name;

    private String            note;

    private Double            units;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major             major;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User              student;

    String                    advisor;

    @Column(name = "time_stamp")
    Date                      timeStamp;

    @Column(name = "approved_date")
    Date                      approvedDate;

    @ManyToMany
    @Cascade(CascadeType.ALL)
    @JoinTable(name = "course_quarter_plans", joinColumns = @JoinColumn(
        name = "course_plan_id"), inverseJoinColumns = @JoinColumn(
        name = "quarter_plan_id"))
    @org.hibernate.annotations.OrderBy(clause = "quarter_plan_id asc")
    Set<QuarterPlan>          quarterPlans;

    Boolean                   approved;

    @Column(name = "is_template")
    Boolean                   isTemplate;

    Boolean                   deleted;

    public CoursePlan()
    {
        quarterPlans = new LinkedHashSet<QuarterPlan>();
        timeStamp = new Date();
        isTemplate = false;
        deleted = false;
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

    public String getAdvisor()
    {
        return advisor;
    }

    public void setAdvisor( String advisor )
    {
        this.advisor = advisor;
    }

    public Set<QuarterPlan> getQuarterPlans()
    {
        return quarterPlans;
    }

    public void setQuarterPlans( Set<QuarterPlan> quarterPlans )
    {
        this.quarterPlans = quarterPlans;
    }

    public Date getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp( Date timeStamp )
    {
        this.timeStamp = timeStamp;
    }

    public Date getApprovedDate()
    {
        return approvedDate;
    }

    public void setApprovedDate( Date approvedDate )
    {
        this.approvedDate = approvedDate;
    }

    public Boolean getApproved()
    {
        return approved;
    }

    public void setApproved( Boolean approved )
    {
        this.approved = approved;
    }

    public Boolean getIsTemplate()
    {
        return isTemplate;
    }

    public void setIsTemplate( Boolean isTemplate )
    {
        this.isTemplate = isTemplate;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote( String note )
    {
        this.note = note;
    }

    public Major getMajor()
    {
        return major;
    }

    public void setMajor( Major major )
    {
        this.major = major;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted( Boolean deleted )
    {
        this.deleted = deleted;
    }

    public Double getUnits()
    {
        return units;
    }

    public void setUnits( Double units )
    {
        this.units = units;
    }

}
