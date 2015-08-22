package com.bignerdranch.android.GPSParSMS02;


public final class clsGPSCurrentInfo 
{
    private static String mLongitude = "";
    private static String mLatitude = "";
    //  in meter/sec
    private static String mSpeed = "";
    // in milliseconds since January 1 1970
	private static String mUtcTime  =  "";
	private static boolean HasSpeed = false;
	private static float Accuracy = 0;

	
	
    public static float getAccuracy() 
    {
		return Accuracy;
	}
	public static void setAccuracy(float accuracy) 
	{
		Accuracy = accuracy;
	}
	
	
	public static boolean HasSpeed() {
		return HasSpeed;
	}
	public static void setHasSpeed(boolean hasSpeed) {
		HasSpeed = hasSpeed;
	}
	public static String getmSpeed() {
		return mSpeed;
	}
	public static void setmSpeed(String mSpeed) {
		clsGPSCurrentInfo.mSpeed = mSpeed;
	}
	public static String getmUtcTime() {
		return mUtcTime;
	}
	public static void setmUtcTime(String mUtcTime) {
		clsGPSCurrentInfo.mUtcTime = mUtcTime;
	}
	
	
    
    
    public static String getLongitude()
	{
	    return mLongitude;	
	}
    public static void setLongitude(String _longitude)
    {
    	mLongitude = _longitude;
    }
    

	public static String getLatitude()
	{
	    return mLatitude;	
	}
    public static void setLatitude(String _Latitude)
    {
    	mLatitude = _Latitude;
    }
}
