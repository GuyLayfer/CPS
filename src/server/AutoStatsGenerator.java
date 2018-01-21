package server;
import java.sql.Date;
import java.util.TimerTask;
import server.db.dbAPI.ServerUtils;
import java.util.Timer;


// TODO: Auto-generated Javadoc
/**
 * The Class AutoStatsGenerator.
 */
public class AutoStatsGenerator {

	/**
	 * Generate stats last week periodly.
	 */
	public static void generateStatsLastWeekPeriodly() {
		
        final TimerTask test = new GenerateStats();

//        (TimerTask task, Date firstTime, long period)
		 Timer timer = new Timer();
        
        Date today = ServerUtils.getToday();
        
        Date lastWeek = ServerUtils.getLastWeek();
        
        
        long l = System.currentTimeMillis() - lastWeek.getTime();
        timer.schedule(test, l, l);
        
	    }


    }


	
	
	
