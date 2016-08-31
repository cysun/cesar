package cesar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "service_types")
public class ServiceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer                   id;

    @Column(nullable = false)
    String                    name;

    Boolean                   deleted;

    public ServiceType()
    {
        deleted = false;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted( Boolean deleted )
    {
        this.deleted = deleted;
    }

}
