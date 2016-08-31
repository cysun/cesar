package cesar.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "quarter_plans")
public class QuarterPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "code", column = @Column(
        name = "quarter")) })
    private Quarter           quarter;

    @ManyToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "plan_sections", joinColumns = @JoinColumn(
        name = "quarter_plan_id"), inverseJoinColumns = @JoinColumn(
        name = "section_id"))
    private Set<Section>      sections;

    @ManyToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "plan_courses", joinColumns = @JoinColumn(
        name = "quarter_plan_id"), inverseJoinColumns = @JoinColumn(
        name = "course_id"))
    private Set<Course>       courses;

    private String            notes;

    private Double            units;

    public QuarterPlan()
    {
        sections = new HashSet<Section>();
        courses = new HashSet<Course>();
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

    public Set<Course> getCourses()
    {
        return courses;
    }

    public void setCourses( Set<Course> courses )
    {
        this.courses = courses;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes( String notes )
    {
        this.notes = notes;
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
