package com.bignerdranch.android.GPSParSMS02;

import java.util.Arrays;
import java.util.List;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;


public class PollService extends IntentService {
    private static final String TAG = "PollService";
    private TextView mInfoProcess;
    public static boolean isOk2SendSMS; 
    OutilsMRJ outil = new OutilsMRJ();
    
    public static boolean isOk2SendSMS()
	{
		return isOk2SendSMS;
	}

	public static void setOk2SendSMS(boolean isOk2SendSMS)
	{
		PollService.isOk2SendSMS = isOk2SendSMS;
	}
	private static final int POLL_INTERVAL = 1000 * 30 * 1; // 0.5 minutes

    public PollService()
    {
        super(TAG);

    }

    @SuppressWarnings("deprecation")
	@Override
    public void onHandleIntent(Intent intent) 
    {
    	
    	String tag = "PollService.OnHandleIntent()";
    	
    	String ladate = outil.GetDate();
        String strMsg = ladate;
        strMsg +="\nEntre dans la methode onHandleIntent()\n ";

    	
    	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        boolean isNetworkAvailable = cm.getBackgroundDataSetting() &&  cm.getActiveNetworkInfo() != null;        

        
        
        if (!isNetworkAvailable)
        {
        	strMsg += " reseau pas disponible ... on sort";
        	 Log.i(tag, strMsg);
        }
        else
        {
		        // envoi le sms
		        if (isOk2SendSMS)
		        {
		        	strMsg += " ok pour envoi sms";
		        	sendSMS();
		        }
		        else
		        {
		        	strMsg += " pas ok pour envoi sms";
		        }
        }
        Log.i(tag, strMsg);
        
    }
    
    public static void setServiceAlarm(Context context, boolean isOn) 
    {
    	 Log.i(TAG, "demarre service");
    	
        Intent i = new Intent(context, PollService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) 
        {
        	Log.i(TAG, "ON peut determiner le service");
            alarmManager.setRepeating(AlarmManager.RTC,System.currentTimeMillis(), POLL_INTERVAL, pi);
        }
        else
        {
            alarmManager.cancel(pi);
            pi.cancel();
            Log.i(TAG, "ON ne peut pas demarrer le  service parce qu'il");
        }
    }
    
    public static boolean isServiceAlarmOn(Context context) {
        Intent i = new Intent(context, PollService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
    	OutilsMRJ outils = new OutilsMRJ();
        String msg = outils.GetDate() + " Service alarm: ";;
        if (pi == null)
        {
        	msg += "OFF";
        }
        else
        {
        	msg += "ON";
        }
        Log.d(TAG, msg );
        return pi != null;
    }
    
    public void sendSMSByManager(String msg, String strPhoneNumber)
    {
    	String tag = "SMSManager";
   
    	try {
        	Log.i(tag, outil.GetDate() + "\nsendSMSByManager() Envoi message: \n" + msg);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(strPhoneNumber,
                 null, 
                 msg,
                 null,
                 null);

    	} 
    	catch (Exception ex) 
        {
        	String errMsg = "\nErreur: " + ex.getMessage();
            Log.d(tag, errMsg + "\nStackTrace: \n" + ex.getStackTrace() + "\n\n");
            ex.printStackTrace();
        }
        Log.d(tag, "Sort de  sendSmsByManager()");
    }
    
	public void sendDataSMS(byte[] donnees,  String strPhoneNumber)
	{

		short SMS_PORT = 15005; 
		SmsManager smsManager = SmsManager.getDefault(); 
		smsManager.sendDataMessage(strPhoneNumber, null, SMS_PORT, donnees, null, null); 
	}
    
	
	public byte[] getDataMessage2send()
	{
		 byte[] donnees = new byte[1];
		MsgPosition pos = new MsgPosition();
		//RunFragment frm = this.instantiate(getActivity(), "RunFragment");
    	OutilsMRJ outil = new OutilsMRJ();
    	
    	String strCodePage = "US-ASCII";
    	
    	try
    	{
    	String msg = pos.getMessage();
        List<String> listDonnes = Arrays.asList(msg.split(","));
        
        int maxLargeur = 0;
        
        for (String d : listDonnes)
        {
        	d += ",";
  
      		maxLargeur += d.length();
  
        	
        }
        
        int k = 0;
        Log.i(TAG, "la largeur maximal est " + maxLargeur);
        donnees = new byte[maxLargeur];
        
        for (String d : listDonnes)
        {
        	byte[] b = new byte[d.length()];
        	b = d.getBytes(strCodePage);
        	for (int i = 0; i < d.length() ; i++)
        	{
        	   donnees[k++] = b[i];
        	}
        	
        }
		
    	}
    	catch (Exception e)
    	{
    		Log.e(TAG, e.toString());
    		
    		
    	}
    	//donnees[0] = msg.getBytes(strCodePage);
    	
		return donnees;
		
		
	}
	

	
    
    
    public void sendSMS()
    {
        String phoneNumber = "4384024543";
        MsgPosition msgpos  = new MsgPosition();
        msgpos.setIdBracelet("00000008");
        	
        Log.d(TAG, "SendSMS() ...");
    	boolean sendData = false;
    	
    	if  (sendData)
    	{
    		
    		Log.d(TAG, "\nlatitude: " + msgpos.getLatitude() + "\nlongitude: " + msgpos.getLongitude());
    		
    		
    		sendDataSMS(getDataMessage2send(), phoneNumber);
    	}
    	else
    	{
            // envoi message sous forme de texte
    		
    	      Log.d(TAG, " fait l'appel a  sendSMSByManager()");  		
            sendSMSByManager(msgpos.getMessage(), phoneNumber);
    		
    		
    	}
    	
    }
    
    
    
    
    private void AfficheInfoLog(String str)
    {
    	
    	//mInfoProcess.setText(str);
    	
    }
}
