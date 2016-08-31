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
@Table(name = "stored_queries")
public class StoredQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer           id;

    private String            name;

    private String            query;

    private Date              date;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User              author;

    @Column(name = "chart_title")
    private String            chartTitle;

    @Column(name = "chart_x_axis_label")
    private String            chartXAxisLabel;

    @Column(name = "chart_y_axis_label")
    private String            chartYAxisLabel;

    @Column(name = "transpose_results")
    private boolean           transposeResults;

    private boolean           enabled;
    private boolean           deleted;

    public StoredQuery()
    {
        date = new Date();
        transposeResults = false;

        enabled = false;
        deleted = false;
    }

    public StoredQuery clone()
    {
        StoredQuery newQuery = new StoredQuery();

        newQuery.name = "Copy of " + name;
        newQuery.query = query;
        newQuery.date = new Date();

        newQuery.chartTitle = chartTitle;
        newQuery.chartXAxisLabel = chartXAxisLabel;
        newQuery.chartYAxisLabel = chartYAxisLabel;

        newQuery.transposeResults = transposeResults;

        newQuery.enabled = false;
        newQuery.deleted = false;

        return newQuery;

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

    public String getQuery()
    {
        return query;
    }

    public void setQuery( String query )
    {
        this.query = query;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor( User author )
    {
        this.author = author;
    }

    public String getChartTitle()
    {
        return chartTitle;
    }

    public void setChartTitle( String chartTitle )
    {
        this.chartTitle = chartTitle;
    }

    public String getChartXAxisLabel()
    {
        return chartXAxisLabel;
    }

    public void setChartXAxisLabel( String chartXAxisLabel )
    {
        this.chartXAxisLabel = chartXAxisLabel;
    }

    public String getChartYAxisLabel()
    {
        return chartYAxisLabel;
    }

    public void setChartYAxisLabel( String chartYAxisLabel )
    {
        this.chartYAxisLabel = chartYAxisLabel;
    }

    public boolean isTransposeResults()
    {
        return transposeResults;
    }

    public void setTransposeResults( boolean transposeResults )
    {
        this.transposeResults = transposeResults;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted( boolean deleted )
    {
        this.deleted = deleted;
    }

}
