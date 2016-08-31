package cesar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "users")
public class User implements Serializable {

    protected static final long           serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer                     id;

    // Account Information

    @Column(nullable = false, unique = true)
    protected String                      username;
    @Column(nullable = false)
    protected String                      password;
    @Transient
    protected String                      password1;
    @Transient
    protected String                      password2;
    @Column(nullable = false)
    protected boolean                     enabled;

    @Column(nullable = false, unique = true)
    protected String                      cin;
    @Column(name = "first_name", nullable = false)
    protected String                      firstName;
    @Column(name = "middle_name")
    protected String                      middleName;
    @Column(name = "last_name", nullable = false)
    protected String                      lastName;
    @Column(nullable = false, unique = true)
    protected String                      email;

    @ManyToMany
    @JoinTable(name = "authorities",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected Set<Role>                   roles;

    @ManyToMany
    @Cascade(CascadeType.ALL)
    @OrderBy("startTime asc")
    @JoinTable(name = "appointment_schedules", joinColumns = @JoinColumn(
        name = "user_id"), inverseJoinColumns = @JoinColumn(
        name = "appointment_section_id"))
    protected List<AppointmentSection>     appointmentSections;

    @ManyToOne
    @JoinColumn(name = "current_advisor_id")
    protected User                        currentAdvisor;

    @ManyToMany(cascade = javax.persistence.CascadeType.ALL)
    @Cascade(CascadeType.DELETE_ORPHAN)
    @JoinTable(name = "office_hours_schedule_sections",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "advisor_schedule_section_id"))
    protected Set<AdvisorScheduleSection> advisorScheduleSections;

    // Contact Information

    protected String                      address1;
    protected String                      address2;
    protected String                      city;
    protected String                      state;
    protected String                      zip;
    protected String                      country;

    @Column(name = "home_phone")
    protected String                      homePhone;
    @Column(name = "office_phone")
    protected String                      officePhone;
    @Column(name = "cell_phone")
    protected String                      cellPhone;

    // Demographic Information

    protected String                      gender;
    protected Date                        birthday;

    @ManyToOne
    @JoinColumn(name = "ethnicity_id")
    protected Ethnicity                   ethnicity;

    @Column(name = "work_hours_per_week")
    protected String                      workHoursPerWeek;

    @Column(name = "commute_time_in_minutes")
    protected String                      commuteTimeInMinutes;

    // Program Information

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "major_id")
    protected Major                       major;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "code", column = @Column(
        name = "quarter_admitted")) })
    protected Quarter                     quarterAdmitted;

    @Column(name = "expected_graduation_date")
    protected Date                        expectedGradudationDate;

    // High School Background

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "high_school_id")
    protected HighSchool                  highSchool;

    @Column(name = "high_school_physics")
    protected Boolean                     highSchoolPhysics;
    @Column(name = "high_school_chemistry")
    protected Boolean                     highSchoolChemistry;
    @Column(name = "high_school_calculus")
    protected Boolean                     highSchoolCalculus;
    @Column(name = "high_school_trigonometry")
    protected Boolean                     highSchoolTrigonometry;

    @Column(name = "high_school_gpa")
    protected String                      highSchoolGPA;

    @Column(name = "community_college_courses")
    protected String                      communityCollegeCourses;

    @ManyToMany
    @JoinTable(name = "high_school_programs_attended",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "high_school_program_id"))
    protected Set<HighSchoolProgram>      highSchoolPrograms;

    // Testing Information

    protected String                      elm;
    protected String                      ept;
    @Column(name = "sat_math")
    protected String                      satMath;
    @Column(name = "sat_verbal")
    protected String                      satVerbal;
    @Column(name = "act_math")
    protected String                      actMath;
    @Column(name = "act_verbal")
    protected String                      actVerbal;
    @Column(name = "eap_math")
    protected String                      eapMath;
    @Column(name = "eap_verbal")
    protected String                      eapVerbal;
    @Column(name = "ap_calculus_a")
    protected String                      apCalculusA;
    @Column(name = "ap_calculus_b")
    protected String                      apCalculusB;
    @Column(name = "ap_calculus_c")
    protected String                      apCalculusC;
    @Column(name = "ap_physics")
    protected String                      apPhysics;
    @Column(name = "ap_chemistry")
    protected String                      apChemistry;
    @Column(name = "ap_biology")
    protected String                      apBiology;
    @Column(name = "confirm_id")
    protected String                      confirmId;
    
    @OneToMany(mappedBy="advisor")
    @Cascade(CascadeType.ALL)
    private List<Block> blocks;
    
    @ManyToMany
    @JoinTable(name = "enrolled_sections", joinColumns = @JoinColumn(
        name = "user_id"), inverseJoinColumns = @JoinColumn(
        name = "section_id"))
    protected Set<Section>     enrolledSections;

    public User()
    {
        enabled = true;
        roles = new HashSet<Role>();
    }

    public String getName()
    {
        return firstName + " " + lastName;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getPassword1()
    {
        return password1;
    }

    public void setPassword1( String password1 )
    {
        this.password1 = password1;
    }

    public String getPassword2()
    {
        return password2;
    }

    public void setPassword2( String password2 )
    {
        this.password2 = password2;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }

    public String getCin()
    {
        return cin;
    }

    public void setCin( String cin )
    {
        this.cin = cin;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName( String middleName )
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles( Set<Role> roles )
    {
        this.roles = roles;
    }

    public String getAddress1()
    {
        return address1;
    }

    public void setAddress1( String address1 )
    {
        this.address1 = address1;
    }

    public String getAddress2()
    {
        return address2;
    }

    public void setAddress2( String address2 )
    {
        this.address2 = address2;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState( String state )
    {
        this.state = state;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip( String zip )
    {
        this.zip = zip;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry( String country )
    {
        this.country = country;
    }

    public String getHomePhone()
    {
        return homePhone;
    }

    public void setHomePhone( String homePhone )
    {
        this.homePhone = homePhone;
    }

    public String getOfficePhone()
    {
        return officePhone;
    }

    public void setOfficePhone( String officePhone )
    {
        this.officePhone = officePhone;
    }

    public String getCellPhone()
    {
        return cellPhone;
    }

    public void setCellPhone( String cellPhone )
    {
        this.cellPhone = cellPhone;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender( String gender )
    {
        this.gender = gender;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday( Date birthday )
    {
        this.birthday = birthday;
    }

    public Ethnicity getEthnicity()
    {
        return ethnicity;
    }

    public void setEthnicity( Ethnicity ethnicity )
    {
        this.ethnicity = ethnicity;
    }

    public String getWorkHoursPerWeek()
    {
        return workHoursPerWeek;
    }

    public String getCommuteTimeInMinutes()
    {
        return commuteTimeInMinutes;
    }

    public Major getMajor()
    {
        return major;
    }

    public void setMajor( Major major )
    {
        this.major = major;
    }

    public Quarter getQuarterAdmitted()
    {
        return quarterAdmitted;
    }

    public void setQuarterAdmitted( Quarter quarterAdmitted )
    {
        this.quarterAdmitted = quarterAdmitted;
    }

    public Date getExpectedGradudationDate()
    {
        return expectedGradudationDate;
    }

    public void setExpectedGradudationDate( Date expectedGradudationDate )
    {
        this.expectedGradudationDate = expectedGradudationDate;
    }

    public HighSchool getHighSchool()
    {
        return highSchool;
    }

    public void setHighSchool( HighSchool highSchool )
    {
        this.highSchool = highSchool;
    }

    public Boolean getHighSchoolPhysics()
    {
        return highSchoolPhysics;
    }

    public void setHighSchoolPhysics( Boolean highSchoolPhysics )
    {
        this.highSchoolPhysics = highSchoolPhysics;
    }

    public Boolean getHighSchoolChemistry()
    {
        return highSchoolChemistry;
    }

    public void setHighSchoolChemistry( Boolean highSchoolChemistry )
    {
        this.highSchoolChemistry = highSchoolChemistry;
    }

    public Boolean getHighSchoolCalculus()
    {
        return highSchoolCalculus;
    }

    public void setHighSchoolCalculus( Boolean highSchoolCalculus )
    {
        this.highSchoolCalculus = highSchoolCalculus;
    }

    public Boolean getHighSchoolTrigonometry()
    {
        return highSchoolTrigonometry;
    }

    public void setHighSchoolTrigonometry( Boolean highSchoolTrigonometry )
    {
        this.highSchoolTrigonometry = highSchoolTrigonometry;
    }

    public String getHighSchoolGPA()
    {
        return highSchoolGPA;
    }

    public String getCommunityCollegeCourses()
    {
        return communityCollegeCourses;
    }

    public void setCommunityCollegeCourses( String communityCollegeCourses )
    {
        this.communityCollegeCourses = communityCollegeCourses;
    }

    public Set<HighSchoolProgram> getHighSchoolPrograms()
    {
        return highSchoolPrograms;
    }

    public void setHighSchoolPrograms( Set<HighSchoolProgram> highSchoolPrograms )
    {
        this.highSchoolPrograms = highSchoolPrograms;
    }

    public String getElm()
    {
        return elm;
    }

    public void setElm( String elm )
    {
        this.elm = elm;
    }

    public String getEpt()
    {
        return ept;
    }

    public void setEpt( String ept )
    {
        this.ept = ept;
    }

    public String getSatMath()
    {
        return satMath;
    }

    public void setSatMath( String satMath )
    {
        this.satMath = satMath;
    }

    public String getSatVerbal()
    {
        return satVerbal;
    }

    public void setSatVerbal( String satVerbal )
    {
        this.satVerbal = satVerbal;
    }

    public String getActMath()
    {
        return actMath;
    }

    public void setActMath( String actMath )
    {
        this.actMath = actMath;
    }

    public String getActVerbal()
    {
        return actVerbal;
    }

    public void setActVerbal( String actVerbal )
    {
        this.actVerbal = actVerbal;
    }

    public String getEapMath()
    {
        return eapMath;
    }

    public void setEapMath( String eapMath )
    {
        this.eapMath = eapMath;
    }

    public String getEapVerbal()
    {
        return eapVerbal;
    }

    public void setEapVerbal( String eapVerbal )
    {
        this.eapVerbal = eapVerbal;
    }

    public String getApCalculusA()
    {
        return apCalculusA;
    }

    public void setApCalculusA( String apCalculusA )
    {
        this.apCalculusA = apCalculusA;
    }

    public String getApCalculusB()
    {
        return apCalculusB;
    }

    public void setApCalculusB( String apCalculusB )
    {
        this.apCalculusB = apCalculusB;
    }

    public String getApCalculusC()
    {
        return apCalculusC;
    }

    public void setApCalculusC( String apCalculusC )
    {
        this.apCalculusC = apCalculusC;
    }

    public String getApPhysics()
    {
        return apPhysics;
    }

    public void setApPhysics( String apPhysics )
    {
        this.apPhysics = apPhysics;
    }

    public String getApChemistry()
    {
        return apChemistry;
    }

    public void setApChemistry( String apChemistry )
    {
        this.apChemistry = apChemistry;
    }

    public String getApBiology()
    {
        return apBiology;
    }

    public void setApBiology( String apBiology )
    {
        this.apBiology = apBiology;
    }

    public void setWorkHoursPerWeek( String workHoursPerWeek )
    {
        this.workHoursPerWeek = workHoursPerWeek;
    }

    public void setCommuteTimeInMinutes( String commuteTimeInMinutes )
    {
        this.commuteTimeInMinutes = commuteTimeInMinutes;
    }

    public void setHighSchoolGPA( String highSchoolGPA )
    {
        this.highSchoolGPA = highSchoolGPA;
    }

    public String getConfirmId()
    {
        return confirmId;
    }

    public void setConfirmId( String confirmId )
    {
        this.confirmId = confirmId;
    }

    public List<AppointmentSection> getAppointmentSections()
    {
        return appointmentSections;
    }

    public void setAppointmentSections(
    		List<AppointmentSection> appointmentSections )
    {
        this.appointmentSections = appointmentSections;
    }

    public Set<AdvisorScheduleSection> getAdvisorScheduleSections()
    {
        return advisorScheduleSections;
    }

    public void setAdvisorScheduleSections(
        Set<AdvisorScheduleSection> advisorScheduleSections )
    {
        this.advisorScheduleSections = advisorScheduleSections;
    }

    public User getCurrentAdvisor()
    {
        return currentAdvisor;
    }

    public void setCurrentAdvisor( User currentAdvisor )
    {
        this.currentAdvisor = currentAdvisor;
    }

    public AppointmentSection getLastAppointment() {
    	Date currentTime = new Date();
    	List<AppointmentSection> apps = getAppointmentSections();
    	for (int i = apps.size()-1; i >= 0; i--) {
    		if (apps.get(i).getEndTime().before(currentTime)) {
    			return apps.get(i);
    		}
    	}
    	return null;
    }

    public List<AppointmentSection> getFutureAppointment() {
    	Date currentTime = new Date();
    	List<AppointmentSection> apps = getAppointmentSections();
    	List<AppointmentSection> futureApps = new ArrayList<AppointmentSection>();
    	for (int i = 0; i < apps.size(); i++) {
    		if (apps.get(i).getEndTime().after(currentTime)) {
    			futureApps.add(apps.get(i));
    		}
    	}
    	return futureApps;
    }
    
    public List<AppointmentSection> getPastAppointment() {
    	Date currentTime = new Date();
    	List<AppointmentSection> apps = getAppointmentSections();
    	List<AppointmentSection> pastApps = new ArrayList<AppointmentSection>();
    	for (int i = 0; i < apps.size(); i++) {
    		if (apps.get(i).getEndTime().before(currentTime)) {
    			pastApps.add(apps.get(i));
    		}
    	}
    	return pastApps;
    }
    
    public List<AppointmentSection> getAvailableFutureAppointment(List<AppointmentSection> apps) {
    	Date currentTime = new Date();
    	List<AppointmentSection> futureApps = new ArrayList<AppointmentSection>();
    	for (int i = 0; i < apps.size(); i++) {
    		if (apps.get(i).getEndTime().after(currentTime)) {
    			futureApps.add(apps.get(i));
    		}
    	}
    	return futureApps;
    }
    
    public List<AppointmentSection> getAvailablePastAppointment(List<AppointmentSection> apps)
    {
    	Date currentTime = new Date();
    	List<AppointmentSection> pastAvailableApps = new ArrayList<AppointmentSection>();
    	for (int i = 0; i < apps.size(); i++) {
    		if (apps.get(i).getEndTime().before(currentTime)) {
    			pastAvailableApps.add(apps.get(i));
    		}
    	}
    	return pastAvailableApps;
    }

	public List<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public Set<Section> getEnrolledSections() {
		return enrolledSections;
	}

	public void setEnrolledSections(Set<Section> enrolledSections) {
		this.enrolledSections = enrolledSections;
	}
}
