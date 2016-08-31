package cesar.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class StoredQueryResult implements ResultSetExtractor {

    private List<String>   colLabels;
    private List<String>   rowLabels;
    private List<double[]> data;

    public StoredQueryResult()
    {
        colLabels = new ArrayList<String>();
        rowLabels = new ArrayList<String>();
        data = new ArrayList<double[]>();
    }

    public int getSize()
    {
        return data.size();
    }

    public Object extractData( ResultSet resultSet ) throws SQLException,
        DataAccessException
    {
        colLabels.clear();
        rowLabels.clear();
        data.clear();

        ResultSetMetaData metaData = resultSet.getMetaData();

        int colCount = metaData.getColumnCount();
        for( int i = 2; i <= colCount; ++i )
            colLabels.add( metaData.getColumnLabel( i ) );

        while( resultSet.next() )
        {
            double row[] = new double[colLabels.size()];
            rowLabels.add( resultSet.getString( 1 ) );
            for( int i = 0; i < row.length; ++i )
                row[i] = resultSet.getDouble( i + 2 );
            data.add( row );
        }

        return this;
    }

    public void transpose()
    {
        List<String> tempLabels = colLabels;
        colLabels = rowLabels;
        rowLabels = tempLabels;

        int numOfColumns = data.size();
        if( numOfColumns == 0 ) return;
        int numOfRows = data.get( 0 ).length;

        List<double[]> tempData = data;
        data = new ArrayList<double[]>();
        for( int i = 0; i < numOfRows; ++i )
        {
            double row[] = new double[numOfColumns];
            for( int j = 0; j < numOfColumns; ++j )
                row[j] = tempData.get( j )[i];
            data.add( row );
        }
    }

    public CategoryDataset getCategoryDataset()
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for( int i = 0; i < data.size(); ++i )
            for( int j = 0; j < data.get( i ).length; ++j )
                dataset.addValue( data.get( i )[j], rowLabels.get( i ),
                    colLabels.get( j ) );

        return dataset;

    }

    public List<String> getColLabels()
    {
        return colLabels;
    }

    public void setColLabels( List<String> colLabels )
    {
        this.colLabels = colLabels;
    }

    public List<String> getRowLabels()
    {
        return rowLabels;
    }

    public void setRowLabels( List<String> rowLabels )
    {
        this.rowLabels = rowLabels;
    }

    public List<double[]> getData()
    {
        return data;
    }

    public void setData( List<double[]> data )
    {
        this.data = data;
    }

}
