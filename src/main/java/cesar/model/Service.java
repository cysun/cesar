package cesar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "services")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    private String            cin;

    @ManyToOne
    @JoinColumn(name = "service_type_id")
    private ServiceType       serviceType;

    @Column(name = "create_date")
    private Date              createDate;

    public Service()
    {
        createDate = new Date();
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getCin()
    {
        return cin;
    }

    public void setCin( String cin )
    {
        this.cin = cin;
    }

    public ServiceType getServiceType()
    {
        return serviceType;
    }

    public void setServiceType( ServiceType serviceType )
    {
        this.serviceType = serviceType;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate( Date createDate )
    {
        this.createDate = createDate;
    }

}
