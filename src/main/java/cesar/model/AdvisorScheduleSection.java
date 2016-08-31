package cesar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "advisor_schedule_sections")
public class AdvisorScheduleSection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @Column(name = "start_time")
    Integer                   startTime;

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

}
