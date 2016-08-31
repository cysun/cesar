package cesar.spring.controller.storedQuery;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cesar.model.StoredQuery;
import cesar.model.User;
import cesar.model.dao.StoredQueryDao;
import cesar.model.dao.UserDao;
import cesar.spring.controller.HomeController;
import cesar.spring.security.SecurityUtils;
import cesar.util.StoredQueryResult;

@Controller
public class StoredQueryController {

    @Autowired
    private StoredQueryDao storedQueryDao;

    @Autowired
    private UserDao        userDao;

    @Autowired
    private JdbcTemplate   jdbcTemplate;

    private Logger         logger = LoggerFactory
                                      .getLogger( StoredQueryController.class );

    /****************
     * Create Query *
     ****************/
    @RequestMapping(value = ("/storedQuery/createStoredQuery"),
        method = RequestMethod.GET)
    protected String createQueryGet( ModelMap model, Integer queryId )
    {
        StoredQuery storedQuery = null;
        if( queryId == null )
            storedQuery = new StoredQuery();
        else
            storedQuery = storedQueryDao.getStoredQueryById( queryId ).clone();

        model.addAttribute( "query", storedQuery );

        return "storedQuery/createStoredQuery";
    }

    @RequestMapping(value = ("/storedQuery/createStoredQuery"),
        method = RequestMethod.POST)
    protected String createQueryPost( ModelMap model, StoredQuery query )
    {
        User user = userDao.getUserByUsername( SecurityUtils.getUsername() );

        query.setAuthor( user );
        query.setDate( new Date() );
        query.setEnabled( true );

        storedQueryDao.saveStoredQuery( query );

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " added a query " );

        return "redirect:viewStoredQuery.html?queryId=" + query.getId();

    }

    /******************
     ** View Queries **
     *****************/

    @RequestMapping(value = ("/storedQuery/viewStoredQueries"),
        method = RequestMethod.GET)
    protected String viewStoredQueries( ModelMap model )
    {
        model.addAttribute( "queries", storedQueryDao.getStoredQueries() );
        return "/storedQuery/viewStoredQueries";
    }

    /****************
     *** View Query **
     ****************/

    @RequestMapping(value = ("/storedQuery/viewStoredQuery"),
        method = RequestMethod.GET)
    protected String viewStoredQuery( ModelMap model, Integer queryId )
    {
        StoredQuery query = storedQueryDao.getStoredQueryById( queryId );

        if( query == null )
        {
            model.addAttribute( "msgTitle", "Query Not Found" );
            model.addAttribute( "msg", "The stored query with id=" + queryId
                + " is not found." );
            return "status";
        }

        User user = userDao.getUserByUsername( SecurityUtils.getUsername() );
        model.addAttribute( "query", query );
        model.addAttribute( "isAuthor", user == query.getAuthor() );
        model.addAttribute( "isAdmin", SecurityUtils.isAdministrator() );

        return "/storedQuery/viewStoredQuery";

    }

    /***************
     ** edit Query**
     ***************/
    @RequestMapping(value = ("/storedQuery/editStoredQuery"),
        method = RequestMethod.GET)
    protected String editStoredQueryGET( ModelMap model, Integer queryId )
    {
        model.addAttribute( "query",
            storedQueryDao.getStoredQueryById( queryId ) );
        return "/storedQuery/editStoredQuery";
    }

    @RequestMapping(value = ("/storedQuery/editStoredQuery"),
        method = RequestMethod.POST)
    protected String editStoredQueryPOST( ModelMap model, StoredQuery query )
    {
        User author = userDao.getUserByUsername( SecurityUtils.getUsername() );
        query.setAuthor( author );
        query.setEnabled( true );
        storedQueryDao.saveStoredQuery( query );

        logger.info( "User: "
            + userDao.getUserByUsername( SecurityUtils.getUsername() )
                .getName() + " edited a query " );

        return "redirect:viewStoredQuery.html?queryId=" + query.getId();
    }

    /**************
     * run query***
     **************/
    @RequestMapping(value = ("/storedQuery/runStoredQuery"),
        method = RequestMethod.GET)
    protected String runStoredQuery( ModelMap model, Integer queryId )
    {
        StoredQuery storedQuery = storedQueryDao.getStoredQueryById( queryId );

        if( !storedQuery.isEnabled() )
        {
            model.addAttribute( "msgTitle", "Cannot Run Query" );
            model.addAttribute( "msg", "This query has not been reviewed." );
            model
                .addAttribute( "backUrl", "storedQuery/viewStoredQueries.html" );
            return "status";
        }

        @SuppressWarnings("unchecked")
        StoredQueryResult result = (StoredQueryResult) jdbcTemplate.query(
            storedQuery.getQuery(), new StoredQueryResult() );
        if( storedQuery.isTransposeResults() ) result.transpose();

        model.addAttribute( "query", storedQuery );
        model.addAttribute( "result", result );

        return "storedQuery/runStoredQuery";
    }

    /****************
     ** Plot query
     * 
     * @throws IOException
     *             ***
     ***************/

    @RequestMapping(value = ("storedQuery/plotStoredQuery"),
        method = RequestMethod.GET)
    protected String plotStoredQuery( ModelMap model, Integer queryId,
        HttpServletResponse response ) throws IOException
    {
        StoredQuery storedQuery = storedQueryDao.getStoredQueryById( queryId );

        if( !storedQuery.isEnabled() )
        {

            model.addAttribute( "msgTitle", "Cannot Run Query" );
            model.addAttribute( "msg", "This query has not been reviewed." );
            model
                .addAttribute( "backUrl", "storedQuery/viewStoredQueries.html" );
            return "status";
        }

        @SuppressWarnings("unchecked")
        StoredQueryResult result = (StoredQueryResult) jdbcTemplate.query(
            storedQuery.getQuery(), new StoredQueryResult() );
        if( storedQuery.isTransposeResults() ) result.transpose();

        JFreeChart chart = ChartFactory.createBarChart(
            storedQuery.getChartTitle(), storedQuery.getChartXAxisLabel(),
            storedQuery.getChartYAxisLabel(), result.getCategoryDataset(),
            PlotOrientation.VERTICAL, true, true, true );

        response.setContentType( "image/png" );
        ChartUtilities.writeChartAsPNG( response.getOutputStream(), chart, 800,
            600 );

        return null;
    }
}
