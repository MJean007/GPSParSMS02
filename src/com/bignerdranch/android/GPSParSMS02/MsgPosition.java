package com.bignerdranch.android.GPSParSMS02;

public class MsgPosition
{
    private static final String TAG = "MsgPosition";
	private  String idBracelet = "";
	private  String strHeureUTC = "";
	private  String statut = "";
	private  String latitude = "";
	private  String NSindicator= "";
	private  String longitude = "";
	private  String EWindicator= "";
	private  String Speed = "";
	private  String strDateUTC = "";
	private  String course = "";
	private  String EventCode = "";
	private  String BatteryVoltage = "";
	private  String Mileage = "";
	private  String GPSOnOff = "";
	private  String Analog = "";
	private OutilsMRJ outil = new OutilsMRJ();
	
	public MsgPosition()
	{
		 idBracelet = "00000008";                    // 1
		 strHeureUTC = outil.getUTCTime();           // 2
		 statut = "A";                               // 3    
		 latitude = clsGPSCurrentInfo.getLatitude(); // 4
		 NSindicator= "N";                          // 5
		 longitude = clsGPSCurrentInfo.getLongitude(); // 6
		 EWindicator= "W";                             //7  
		 if (clsGPSCurrentInfo.HasSpeed())
		{
			Speed = clsGPSCurrentInfo.getmSpeed();  // 8	
			
		}
		course = "0";                              // 9	
		strDateUTC = outil.getUTCDate();           //10
	
		EventCode = "0";                          // 11 
		BatteryVoltage = "0";                     // 12
		Mileage = "0";                            // 13
		GPSOnOff = "1";                           // 14
		Analog = "0,0";                          // 15
	}


//	public MsgPosition(String _idbracelet,  String _statut,  )
//	{  
	
//		
//		
//		
//	}

	
	public String getIdBracelet() {
		return idBracelet;
	}

	public void setIdBracelet(String idBracelet) {
		this.idBracelet = idBracelet;
	}

	public String getStrHeureUTC() {
		
		strHeureUTC = clsGPSCurrentInfo.getmUtcTime();
		
		return strHeureUTC;
	}

	public void setStrHeureUTC(String strHeureUTC) {
		this.strHeureUTC = strHeureUTC;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getLatitude() {
		
		latitude = clsGPSCurrentInfo.getLatitude();
		return latitude;
	}

	public void setLatitude(String latitude) {
		
		this.latitude = latitude;
	}

	public String getNSindicator() {
		return NSindicator;
	}

	public void setNSindicator(String nSindicator) {
		NSindicator = nSindicator;
	}

	public String getLongitude() 
	{
		
		longitude = clsGPSCurrentInfo.getLongitude();
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getEWindicator() {
		return EWindicator;
	}

	public void setEWindicator(String eWindicator) {
		EWindicator = eWindicator;
	}

	public String getSpeed() {
		if (clsGPSCurrentInfo.HasSpeed())
		{
			Speed = clsGPSCurrentInfo.getmSpeed();	
		}
		
		return Speed;
	}

	public void setSpeed(String speed) {
		Speed = speed;
	}

	public String getStrDateUTC() {
		strDateUTC = clsGPSCurrentInfo.getmUtcTime();
		return strDateUTC;
	}

	public void setStrDateUTC(String strDateUTC) {
		
		
		this.strDateUTC = strDateUTC;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getEventCode() {
		return EventCode;
	}

	public void setEventCode(String eventCode) {
		EventCode = eventCode;
	}

	public String getBatteryVoltage() {
		BatteryVoltage =  Integer.toString(BatteryInfo.getVoltage());
		return BatteryVoltage;
	}

	public void setBatteryVoltage(String batteryVoltage) {
		
		BatteryVoltage = batteryVoltage;
	}

	public String getMileage() {
		return Mileage;
	}

	public void setMileage(String mileage) {
		Mileage = mileage;
	}

	public String getGPSOnOff() {
		return GPSOnOff;
	}

	public void setGPSOnOff(String gPSOnOff) {
		GPSOnOff = gPSOnOff;
	}

	public String getAnalog() {
		return Analog;
	}

	public void setAnalog(String analog) {
		Analog = analog;
	}
	public String getMessage()
	{
       String msg = "";
   	msg = "$AVRMC,";               // 0
   	msg += idBracelet + ",";       // 1
   	msg += strHeureUTC + ",";      // 2
    msg += statut + ",";           // 3    			
   	msg += this.getLatitude() + ",";    // 4
   	msg += NSindicator + ",";      // 5	
   	msg += this.getLongitude() + ",";   // 6    			
   	msg += EWindicator + ",";      // 7	
   	msg += this.getSpeed() + ",";       // 8    			
   	msg += course + ",";           // 9    			
   	msg += strDateUTC + ",";       // 10
   	msg += EventCode + ",";        // 11
   	msg += this.getBatteryVoltage() + ",";   // 12   			
   	msg += Mileage + ",";          // 13
   	msg += GPSOnOff + ",";         // 14
   	msg += Analog;                 // 15 et 16
   	// calcul checksum sans le $
   	msg += "*" + outil.ComputeChecksum(msg.substring(1));   
       
       return msg;
	
	}

}
