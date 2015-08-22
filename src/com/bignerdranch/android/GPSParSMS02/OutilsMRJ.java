package com.bignerdranch.android.GPSParSMS02;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import android.text.format.Time;
import android.util.Log;


public class OutilsMRJ 
{
	 private static final String TAG = "OutilsMRJ";
	OutilsMRJ()
	{
		
		
	}
	
	public String ComputeChecksum(String str)
	{
		int sum = 0;
		try
		{
			byte[] liste = str.getBytes("US-ASCII");
			sum = liste[0];
			for (int i = 1; i < liste.length ; i++)
			{
				sum ^= liste[i];
			}
			
		}
		catch (Exception e)
		{
			Log.d(TAG, e.toString());
		}
    	return Integer.toHexString(sum).toUpperCase();
       
	}
	public String GetDate()
	{
		
		String ladate = "";
		
    	Time now = new Time();
        now.setToNow();
		ladate =  now.format("%d.%m.%Y %H.%M.%S");
		
		return ladate;
		
	}

	public String getUTCDate()
	{
		String str = "";
//		long dateasNumber = 0;
		final Date currentTime = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//		 str = sdf.toString();
//		 dateasNumber  = currentTime.getTime();
		// date, mois , an 
		//str = now.format("%d%m%Y");
		// str = Long.toString(dateasNumber);
		str = sdf.format(currentTime);
		return str;
		
	}

	
	
	public String getUTCTime()
	{
		
		// pour 20h40, 11 sec il faut retourner 204011 
		String str ="";
		
    	Time now = new Time();
        now.setToNow();
		str = now.format("%H%M%S");
		return str;
	}
	
	
	
	
}
