package cesar.model;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "sections")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int   unitValues[]     = { 0, 1, 2, 3, 4, 5, 6 };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "course_id")
    private Course            course;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "section_week_days",
        joinColumns = @JoinColumn(name = "section_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "week_day_id", nullable = false))
    @OrderBy("code asc")
    private Set<WeekDay>      weekDays;

    @Column(name = "start_time")
    private String            startTime;

    @Column(name = "end_time")
    private String            endTime;

    @Column(nullable = false)
    private double            units;

    @Column(name = "option_info")
    private String            option;

    private String            info;

    @Column(name = "section_number")
    private String            sectionNumber;

    @Column(name = "call_number")
    private String            callNumber;

    private String            location;

    private String            capacity;

    @Transient
    private String            extraInfo;

    @Column(name = "enrollment_total")
    private int            enrollmentTotal;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "section_students_enrolled", joinColumns = @JoinColumn(
        name = "section_id"), inverseJoinColumns = @JoinColumn(
        name = "user_id"))
    protected Set<User>     students;
    
    public Section()
    {
        weekDays = new HashSet<WeekDay>();
        this.enrollmentTotal = 0;
    }

    public Section( Course course, String info )
    {
        this.course = course;
        this.info = info;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Course getCourse()
    {
        return course;
    }

    public void setCourse( Course course )
    {
        this.course = course;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo( String info )
    {
        this.info = info;
    }

    public Set<WeekDay> getWeekDays()
    {
        return weekDays;
    }

    public void setWeekDays( Set<WeekDay> weekDays )
    {
        this.weekDays = weekDays;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime( String startTime )
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime( String endTime )
    {
        this.endTime = endTime;
    }

    public String getExtraInfo()
    {
        return extraInfo;
    }

    public void setExtraInfo( String extraInfo )
    {
        this.extraInfo = extraInfo;
    }

    public String getSectionNumber()
    {
        return sectionNumber;
    }

    public void setSectionNumber( String sectionNumber )
    {
        this.sectionNumber = sectionNumber;
    }

    public String getCallNumber()
    {
        return callNumber;
    }

    public void setCallNumber( String callNumber )
    {
        this.callNumber = callNumber;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation( String location )
    {
        this.location = location;
    }

    public String getCapacity()
    {
        return capacity;
    }

    public void setCapacity( String capacity )
    {
        this.capacity = capacity;
    }

    public int[] getUnitValues()
    {
        return unitValues;
    }

    public double getUnits()
    {
        return units;
    }

    public void setUnits( double units )
    {
        this.units = units;
    }

    public String getOption()
    {
        return option;
    }

    public void setOption( String option )
    {
        this.option = option;
    }

	public int getEnrollmentTotal() {
		return enrollmentTotal;
	}

	public void setEnrollmentTotal(int enrollmentTotal) {
		this.enrollmentTotal = enrollmentTotal;
	}

	public Set<User> getStudents() {
		return students;
	}

	public void setStudents(Set<User> students) {
		this.students = students;
	}
	
}
