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
@Table(name = "appointment_sections")
public class AppointmentSection implements Serializable {

    private static final long    serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer              id;

    private String               title;

    @Column(name = "start_time", nullable = false)
    private Date                 startTime;

    @Column(name = "end_time", nullable = false)
    private Date                 endTime;

    @Column(name = "start_time_int")
    private Integer              startTimeInt;
    @ManyToOne
    @JoinColumn(name = "advisor_id", nullable = false)
    private User                 advisor;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User                 student;

    @Column(name = "is_available")
    private Boolean              isAvailable;

    @Column(name = "is_show_up")
    private Boolean              isShowUp;

    @Column(name = "cancelled_by_advisor")
    private Boolean              cancelledByAdvisor;

    @Column(name = "cancelled_by_student")
    private Boolean              cancelledByStudent;

    @Column(name = "is_walk_in_appointment")
    private Boolean              isWalkInAppointment;

    @ManyToOne
    @JoinColumn(name = "reason_for_appointment_id", nullable = false)
    private ReasonForAppointment reasonForAppointment;

    @ManyToOne
    @JoinColumn(name = "appointment_type_id", nullable = false)
    private AppointmentType      appointmentType;
    
    @Column(name = "notes")
    private String               notes;

    public AppointmentSection()
    {
        isAvailable = true;
        isShowUp = true;
        cancelledByAdvisor = false;
        cancelledByStudent = false;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public User getAdvisor()
    {
        return advisor;
    }

    public void setAdvisor( User advisor )
    {
        this.advisor = advisor;
    }

    public User getStudent()
    {
        return student;
    }

    public void setStudent( User student )
    {
        this.student = student;
    }

    public Boolean getIsAvailable()
    {
        return isAvailable;
    }

    public void setIsAvailable( Boolean isAvailable )
    {
        this.isAvailable = isAvailable;
    }

    public Boolean getIsWalkInAppointment()
    {
        return isWalkInAppointment;
    }

    public void setIsWalkInAppointment( Boolean isWalkInAppointment )
    {
        this.isWalkInAppointment = isWalkInAppointment;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime( Date startTime )
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime( Date endTime )
    {
        this.endTime = endTime;
    }

    public Integer getStartTimeInt()
    {
        return startTimeInt;
    }

    public void setStartTimeInt( Integer startTimeInt )
    {
        this.startTimeInt = startTimeInt;
    }

    public Boolean getIsShowUp()
    {
        return isShowUp;
    }

    public void setIsShowUp( Boolean isShowUp )
    {
        this.isShowUp = isShowUp;
    }

    public Boolean getCancelledByAdvisor()
    {
        return cancelledByAdvisor;
    }

    public void setCancelledByAdvisor( Boolean cancelledByAdvisor )
    {
        this.cancelledByAdvisor = cancelledByAdvisor;
    }

    public Boolean getCancelledByStudent()
    {
        return cancelledByStudent;
    }

    public void setCancelledByStudent( Boolean cancelledByStudent )
    {
        this.cancelledByStudent = cancelledByStudent;
    }

    public ReasonForAppointment getReasonForAppointment()
    {
        return reasonForAppointment;
    }

    public void setReasonForAppointment(
        ReasonForAppointment reasonForAppointment )
    {
        this.reasonForAppointment = reasonForAppointment;
    }

    public AppointmentType getAppointmentType()
    {
        return appointmentType;
    }

    public void setAppointmentType( AppointmentType appointmentType )
    {
        this.appointmentType = appointmentType;
    }

    public String getNotes() 
	{
		return notes;
	}

	public void setNotes(String notes) 
	{
		this.notes = notes;
	}

}
