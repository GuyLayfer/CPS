package server;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.*;
import java.sql.Date;
import java.util.TimerTask;
import server.db.dbAPI.ServerUtils;
import java.util.Timer;


public class AutoStatsGenerator {

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


	
	
	
