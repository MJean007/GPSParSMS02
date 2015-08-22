package com.bignerdranch.android.GPSParSMS02;

public final class BatteryInfo 
{

	private static  int status = 0;
	private static  int level = 0;
	private static  int voltage = 0;
	
	
	
	public static int getStatus() {
		return status;
	}
	public static void setStatus(int status) {
		BatteryInfo.status = status;
	}
	public static int getLevel() {
		return level;
	}
	public static void setLevel(int level) {
		BatteryInfo.level = level;
	}
	public static int getVoltage() {
		
		return voltage;
	}
	public static void setVoltage(int voltage) {
		BatteryInfo.voltage = voltage;
	}
	
	
	
	
}
