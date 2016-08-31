package cesar.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "schedule_table_sections")
public class ScheduleTableSection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @Column(name = "start_time")
    private Integer           startTime;

    @ManyToMany
    @Cascade(CascadeType.ALL)
    @JoinTable(name = "participate_advisors", joinColumns = @JoinColumn(
        name = "schedule_table_section_id"), inverseJoinColumns = @JoinColumn(
        name = "advisor_id"))
    private Set<User>         advisors;

    public ScheduleTableSection()
    {

    }

    public ScheduleTableSection( Integer startTime )
    {
        this.startTime = startTime;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getStartTime()
    {
        return startTime;
    }

    public void setStartTime( Integer startTime )
    {
        this.startTime = startTime;
    }

    public Set<User> getAdvisors()
    {
        return advisors;
    }

    public void setAdvisors( Set<User> advisors )
    {
        this.advisors = advisors;
    }

}
