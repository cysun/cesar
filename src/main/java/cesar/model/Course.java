package cesar.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "courses")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int   unitValues[]     = { 0, 1, 2, 3, 4, 5, 6 };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @Column(nullable = false, unique = true)
    private String            code;
    private String            name;
    @Column(nullable = false)
    private double            units;

    @ManyToMany
    @Cascade(CascadeType.ALL)
    @JoinTable(name = "course_prerequisites", joinColumns = @JoinColumn(
        name = "course_id"), inverseJoinColumns = @JoinColumn(
        name = "prerequisite_id"))
    private Set<Prerequisite> prerequisites;

    @Column(name = "is_graduate_course", nullable = false)
    private boolean           isGraduateCourse;

    @Transient
    private String            prereqString;

    @Transient
    private String            extraInfo;

    private boolean           deleted;

    private boolean           repeatable;

    public Course()
    {
        units = 4;
        isGraduateCourse = false;
        deleted = false;
        prerequisites = new HashSet<Prerequisite>();
    }

    public boolean isPrequisitesMet( Collection<Course> coursesTaken )
    {
        for( Prerequisite prerequisite : prerequisites )
            if( !prerequisite.isPrerequisiteMet( coursesTaken ) ) return false;

        return true;
    }

    public int[] getUnitValues()
    {
        return unitValues;
    }

    public String getPrereqAsString()
    {
        String s = "";
        for( Prerequisite prerequisite : prerequisites )
            s += prerequisite.toString() + " ";

        return s;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public double getUnits()
    {
        return units;
    }

    public void setUnits( double units )
    {
        this.units = units;
    }

    public Set<Prerequisite> getPrerequisites()
    {
        return prerequisites;
    }

    public void setPrerequisites( Set<Prerequisite> prerequisites )
    {
        this.prerequisites = prerequisites;
    }

    public boolean isGraduateCourse()
    {
        return isGraduateCourse;
    }

    public void setGraduateCourse( boolean isGraduateCourse )
    {
        this.isGraduateCourse = isGraduateCourse;
    }

    public String getPrereqString()
    {
        return prereqString;
    }

    public void setPrereqString( String prereqString )
    {
        this.prereqString = prereqString;
    }

    public String getExtraInfo()
    {
        return extraInfo;
    }

    public void setExtraInfo( String extraInfo )
    {
        this.extraInfo = extraInfo;
    }

    public String toString()
    {
        return this.code + "";
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted( boolean deleted )
    {
        this.deleted = deleted;
    }

    public boolean isRepeatable()
    {
        return repeatable;
    }

    public void setRepeatable( boolean repeatable )
    {
        this.repeatable = repeatable;
    }

}
