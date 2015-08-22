package com.bignerdranch.android.GPSParSMS02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
   

public class RunFragment extends Fragment {
    private static final String TAG = "RunFragment";
    
    private BroadcastReceiver mLocationReceiver = new LocationReceiver() 
    {

        @Override
        protected void onLocationReceived(Context context, Location loc) {
            mLastLocation = loc;
            if (isVisible()) 
                updateUI();
        }
        
        @Override
        protected void onProviderEnabledChanged(boolean enabled) 
        {
            int toastText = enabled ? R.string.gps_enabled : R.string.gps_disabled;
            Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG).show();
        }
        
	  
        
        
    };
    
//    private void registerBatteryLevelReceiver() {
//        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
// 
//        registerReceiver(batteryInfoReceiver, filter);
//    }
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver()
	{
	    @Override
	    public void onReceive(Context context, Intent intent) 
		{
	     
	    	BatteryInfo.setLevel(intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0));
	    	BatteryInfo.setStatus(intent.getIntExtra(BatteryManager.EXTRA_STATUS,0));
	    	BatteryInfo.setVoltage(intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0));
	    }
    };
    
    private RunManager mRunManager;
    
    private Run mRun;
    private Location mLastLocation;

    
    
    
    private Button mStartButton, mStopButton;
    private TextView mStartedTextView, mLatitudeTextView, mLongitudeTextView, mAltitudeTextView, mDurationTextView, mInfoProcess;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mRunManager = RunManager.get(getActivity());
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run, container, false);
        
        mStartedTextView = (TextView)view.findViewById(R.id.run_startedTextView);
        mLatitudeTextView = (TextView)view.findViewById(R.id.run_latitudeTextView);
        mLongitudeTextView = (TextView)view.findViewById(R.id.run_longitudeTextView);
        mAltitudeTextView = (TextView)view.findViewById(R.id.run_altitudeTextView);
        mDurationTextView = (TextView)view.findViewById(R.id.run_durationTextView);
        mInfoProcess = (TextView)view.findViewById(R.id.InfoProcess);
        mStartButton = (Button)view.findViewById(R.id.run_startButton);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	AfficheInfoLog("cliquee sur le boutton start");
                mRunManager.startLocationUpdates();
                mRun = new Run();
                updateUI();
            }
        });
        
        mStopButton = (Button)view.findViewById(R.id.run_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	AfficheInfoLog("cliquee sur le boutton stop");
                mRunManager.stopLocationUpdates();
                updateUI();
            }
        });
        
        updateUI();
        
        return view;
    }
    
    @Override
    public void onStart() {
    	String strInfo = "";
        super.onStart();
        Log.i(TAG, "cliquee sur start ....");
        
     
        getActivity().registerReceiver(mLocationReceiver, new IntentFilter(RunManager.ACTION_LOCATION));
        
        getActivity().registerReceiver(this.batteryInfoReceiver,	new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        
        // active le service d'envoi de sms
        boolean shouldStartAlarm = !PollService.isServiceAlarmOn(getActivity());
        
        if (shouldStartAlarm)
        {
        	strInfo += " should start est ON ";
        	Log.i(TAG, "should start est ON");
        }
        else
        {
        	strInfo += " should start est OFF ";
        	Log.i(TAG, "should start est OFF");
        	
        }
        strInfo += " sort de onStart ....";
        PollService.isOk2SendSMS = true;
        PollService.setServiceAlarm(getActivity(), shouldStartAlarm);
        AfficheInfoLog(strInfo);
        Log.i(TAG, " sort de onStart ....");
    }
    
    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mLocationReceiver);
        super.onStop();
        Log.i(TAG, "Stopping the process ....");
        PollService.isOk2SendSMS = false;
        
    }
    
    private void updateUI() {
        boolean started = mRunManager.isTrackingRun();
        
        if (mRun != null)
            mStartedTextView.setText(mRun.getStartDate().toString());
        
        int durationSeconds = 0;
        if (mLastLocation != null) {
            durationSeconds = mRun.getDurationSeconds(mLastLocation.getTime());
            mLatitudeTextView.setText(Double.toString(mLastLocation.getLatitude()));
            mLongitudeTextView.setText(Double.toString(mLastLocation.getLongitude()));
            mAltitudeTextView.setText(Double.toString(mLastLocation.getAltitude()));
        }
        mDurationTextView.setText(Run.formatDuration(durationSeconds));
        
        mStartButton.setEnabled(!started);
        mStopButton.setEnabled(started);
    }
    
    public String getLatitude()
    {
    	
    	return Double.toString(mLastLocation.getLatitude());
    	
    }
    
    
    public String getLongitude()
    {
    	
    	return Double.toString(mLastLocation.getLongitude());
    }
    
    private void AfficheInfoLog(String str)
    {
    	
    	mInfoProcess.setText(str);
    	
    }
}
