package cesar.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "majors")
public class Major implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    @Column(nullable = false, unique = true)
    private String            symbol;

    @Column(nullable = false, unique = true)
    private String            name;

    @ManyToMany
    @JoinTable(name = "major_courses", joinColumns = @JoinColumn(
        name = "major_id", nullable = false), inverseJoinColumns = @JoinColumn(
        name = "course_id", nullable = false))
    @OrderBy("code asc")
    Set<Course>               courses;

    @Column(name = "is_regular", nullable = false)
    private boolean           isRegular;

    public Major()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol( String symbol )
    {
        this.symbol = symbol;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public boolean isRegular()
    {
        return isRegular;
    }

    public void setRegular( boolean isRegular )
    {
        this.isRegular = isRegular;
    }

    public Set<Course> getCourses()
    {
        Set<Course> newCourses = new HashSet<Course>();
        for( Course course : courses )
        {
            if( course.isDeleted() == false ) newCourses.add( course );
        }
        courses.clear();
        courses.addAll( newCourses );
        return courses;
    }

    public void setCourses( Set<Course> courses )
    {
        this.courses = courses;
    }

}
