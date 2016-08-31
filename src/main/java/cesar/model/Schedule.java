package cesar.model;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "schedules")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "code", column = @Column(
        name = "quarter")) })
    private Quarter           quarter;

    @ManyToMany
    @Cascade(CascadeType.ALL)
    @JoinTable(name = "schedule_sections", joinColumns = @JoinColumn(
        name = "schedule_id"), inverseJoinColumns = @JoinColumn(
        name = "section_id"))
    private Set<Section>      sections;

    @ManyToOne
    @JoinColumn(name = "major_id", nullable = false)
    private Major             major;

    public Schedule()
    {
        sections = new HashSet<Section>();
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

    public Set<Section> getSections()
    {
        
        return sections;
    }

    public void setSections( Set<Section> sections )
    {
        this.sections = sections;
    }

    public Major getMajor()
    {
        return major;
    }

    public void setMajor( Major major )
    {
        this.major = major;
    }

}
