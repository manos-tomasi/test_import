package com.paa.requestnow.model.db.service;

import com.paa.requestnow.model.data.Sector;
import com.paa.requestnow.model.db.Database;
import com.paa.requestnow.model.db.transactions.SectorManagerTransactions;

/**
 *
 * @author lucas
 */
public class SectorManagerService 
        extends 
            Manager<Sector, SectorManagerTransactions>
{
    private static SectorManagerService service;
   
    public static SectorManagerService getInstance()
    {
        if( service == null )
        {
            service = new SectorManagerService();
        }
        
        return  service;
    }
    
    private SectorManagerService()
    {
        transactions = new SectorManagerTransactions();
    }
    
    public boolean hasDependences( Sector sector ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
           return transactions.hasDependences( db, sector );
        }
        
        finally
        {
            db.release();
        }
    }
}
