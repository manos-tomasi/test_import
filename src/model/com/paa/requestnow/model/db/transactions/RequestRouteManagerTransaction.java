package com.paa.requestnow.model.db.transactions;

import com.paa.requestnow.model.data.RequestRoute;
import com.paa.requestnow.model.db.Database;
import com.paa.requestnow.model.db.Schema;
import com.paa.requestnow.model.filter.DefaultFilter;
import com.paa.requestnow.model.filter.RequestFilter;
import com.paa.requestnow.model.filter.RequestRouteFilter;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author lucas
 */
public class RequestRouteManagerTransaction 
        extends 
            ManagerTransaction<RequestRoute>
{    
    @Override
    public void add(Database db, RequestRoute obj) throws Exception 
    {    
        Schema.RequestsRoutes S = Schema.RequestsRoutes.table;
        
        String sql = " insert into "           + S.name + 
                     " values "                + "("    + 
                     " DEFAULT"                + ", "   +
                      obj.getRequest()         + ", "   +
                      obj.getTypeRoute()       + ", "   +
                      obj.getIn()              + ", "   +
                      obj.getOut()             + ", "   +
                      obj.getState()           + ", "   +
                      obj.getUser()            + ", "   +
                      obj.getInfo()            + ")";
        
        db.executeCommand(sql);
    }

    @Override
    public void update(Database db, RequestRoute obj) throws Exception 
    {
        if( obj == null )
        {
            throw new Exception( "Request Route cannot be null" );
        }
        
        Schema.RequestsRoutes S = Schema.RequestsRoutes.table;
        
        String sql = " update " + S.name       + " set " +
                        S.columns.USER         + " = " + obj.getUser()      + ", " +
                        S.columns.INFO         + " = " + obj.getInfo()      + ", " +
                        S.columns.OUT          + " = " + obj.getOut()       + ", " +
                        S.columns.STATE        + " = " + obj.getState()     + ", " +
                        S.columns.TYPE_ROUTE   + " = " + obj.getTypeRoute() + ", " +
                        S.columns.REQUEST      + " = " + obj.getRequest()   + ", " +
                        S.columns.IN           + " = " + obj.getIn()        + 
                     " where " + 
                        S.columns.ID           + " = " + obj.getId();
        
        db.executeCommand( sql );
    }

    @Override
    public RequestRoute get(Database db, int id) throws Exception 
    {
        Schema.RequestsRoutes S = Schema.RequestsRoutes.table;
        
        String sql = S.select + " where " + S.columns.ID + " = " + id;
        
        return  db.fetchOne( sql , S.fetcher );
    }
    
    @Override
    public List get(Database db) throws Exception 
    {
        Schema.RequestsRoutes S = Schema.RequestsRoutes.table;
        
        String sql = S.select;
        
        return db.fetchAll(sql, S.fetcher );
    }

    @Override
    public List get(Database db, DefaultFilter filter) throws Exception 
    {
        Schema.RequestsRoutes S = Schema.RequestsRoutes.table;
        
        StringBuilder sql = new StringBuilder();
        
        sql.append( S.select );
        sql.append( " where true " );
        
        filter.getConditions().forEach( ( key , values ) ->
        {
            String conditions = " and ( "; 
            
            for (int i = 0; i < values.size(); i++) 
            {
                switch( key )
                {                    
                    case RequestRouteFilter.STATE :
                    {
                        conditions += S.columns.STATE + " = " + values.get(i);
                    }
                    break;
                    
                    case RequestRouteFilter.REQUEST :
                    {
                        conditions += S.columns.REQUEST + " = " + values.get(i);
                    }
                    break;
                    
                    case RequestFilter.USER :
                    {
                        conditions += S.columns.USER + " = " + values.get(i);
                    }
                    break;
                    
                    case RequestRouteFilter.IN :
                    {
                        if( values.get( i ) instanceof Date[] )
                        {
                            Date[] dates = (Date[]) values.get( i );
                            
                            conditions += S.columns.IN + 
                                         " between " + 
                                         db.quote( dates[0] ) +
                                         " and " +
                                         db.quote( dates[1] );
                        }
                    }
                    break;
                    
                    case RequestRouteFilter.OUT :
                    {
                        if( values.get( i ) instanceof Date[] )
                        {
                            Date[] dates = (Date[]) values.get( i );
                            
                            conditions += S.columns.OUT + 
                                         " between " + 
                                         db.quote( dates[0] ) +
                                         " and " +
                                         db.quote( dates[1] );
                        }
                    }
                    break;
                }
                conditions += i == values.size() - 1 ? " ) " : " or ";    
            }
            
            sql.append(conditions);
        });
        
        return db.fetchAll(sql.toString() , S.fetcher );
    }
}