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
@Table(name = "visit_reasons")
public class VisitReason implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    private String            cin;

    @ManyToOne
    @JoinColumn(name = "visit_reason_type_id")
    private VisitReasonType   visitReasonType;

    @Column(name = "create_date")
    private Date              createDate;

    public VisitReason()
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

    public VisitReasonType getVisitReasonType()
    {
        return visitReasonType;
    }

    public void setVisitReasonType( VisitReasonType visitReasonType )
    {
        this.visitReasonType = visitReasonType;
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
