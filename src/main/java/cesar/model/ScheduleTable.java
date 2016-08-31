package cesar.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
@Table(name = "schedule_tables")
public class ScheduleTable implements Serializable {

    private static final long         serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer                   id;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "code", column = @Column(
        name = "quarter")) })
    private Quarter                   quarter;

    @Column(name = "is_register_period")
    Boolean                           isRegisterPeriod;

    @Column(name = "created_date")
    Date                              createdDate;

    @Column(name = "updated_date")
    Date                              updatedDate;

    @ManyToMany
    @Cascade(CascadeType.ALL)
    @JoinTable(name = "table_sections_mapping", joinColumns = @JoinColumn(
        name = "schedule_table_id"), inverseJoinColumns = @JoinColumn(
        name = "schedule_table_section_id"))
    private Set<ScheduleTableSection> scheduleTableSections;

    public ScheduleTable()
    {
        quarter = new Quarter( new Date() );
        isRegisterPeriod = true;
        createdDate = new Date();
        scheduleTableSections = new HashSet<ScheduleTableSection>();
        for( int i = 1000; i < 6000; i = i + 1000 )
        {
            for( int j = 90; j < 210; j = j + 10 )
            {
                int startTime = i + j;
                ScheduleTableSection section = new ScheduleTableSection(
                    startTime );
                scheduleTableSections.add( section );

                ScheduleTableSection section1 = new ScheduleTableSection(
                    startTime + 1 );
                scheduleTableSections.add( section1 );
            }
        }

    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Quarter getQuarter()
    {
        return quarter;
    }

    public void setQuarter( Quarter quarter )
    {
        this.quarter = quarter;
    }

    public Boolean getIsRegisterPeriod()
    {
        return isRegisterPeriod;
    }

    public void setIsRegisterPeriod( Boolean isRegisterPeriod )
    {
        this.isRegisterPeriod = isRegisterPeriod;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate( Date createdDate )
    {
        this.createdDate = createdDate;
    }

    public Set<ScheduleTableSection> getScheduleTableSections()
    {
        return scheduleTableSections;
    }

    public void setScheduleTableSections(
        Set<ScheduleTableSection> scheduleTableSections )
    {
        this.scheduleTableSections = scheduleTableSections;
    }

    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate( Date updatedDate )
    {
        this.updatedDate = updatedDate;
    }

}
