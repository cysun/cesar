package cesar.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

/**
 * A Prerequisite for a course could be several equivalent courses, i.e. not
 * just one course.
 */

@Entity
@Table(name = "prerequisites")
public class Prerequisite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @ManyToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "prerequisite_courses", joinColumns = @JoinColumn(
        name = "prerequisite_id"), inverseJoinColumns = @JoinColumn(
        name = "course_id"))
    private Set<Course>       courses;

    public Prerequisite()
    {
        courses = new HashSet<Course>();
    }

    public void addCourse( Course course )
    {
        courses.add( course );
    }

    public boolean isPrerequisiteMet( Collection<Course> coursesTaken )
    {
        for( Course course : courses )
            if( coursesTaken.contains( course ) ) return true;

        return false;
    }

    @Override
    public String toString()
    {
        Course courseArray[] = courses.toArray( new Course[0] );

        String s = courseArray.length > 0 ? courseArray[0].getCode() : "";
        for( int i = 1; i < courseArray.length; ++i )
            s += "/" + courseArray[i].getCode();

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

    public Set<Course> getCourses()
    {
        return courses;
    }

    public void setCourses( Set<Course> courses )
    {
        this.courses = courses;
    }

}
