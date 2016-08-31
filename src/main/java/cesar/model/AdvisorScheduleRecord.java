package cesar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "advisor_schedule_records")
public class AdvisorScheduleRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @Column(name = "created_date")
    private Date              createdDate;

    private String            log;

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
    }

    public String getLog()
    {
        return log;
    }

    public void setLog( String log )
    {
        this.log = log;
    }

}
