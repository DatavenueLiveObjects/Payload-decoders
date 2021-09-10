/*
 * Copyright (C) 2021 Orange S.A.
 * 
 * This software is distributed under the terms and conditions of the '3-Clause BSD License'
 * license which can be found in the file 'LICENSE.txt' in this package distribution. 
 */

package com.orange.lo.decoder.js.example4.pojo;
import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;

import lombok.Data;

@Data
@OutputPojo
public class SplitPilotProductGeneralConfigurationItem extends SplitPilotHeaderItem {
	 
	@FieldDescription(description = "Decoded payload")
	public Value value;

    @Data
    @OutputPojo
    public class Value {
    	
        @FieldDescription(description = "Product type: Insafe Pilot LoRa")
        public String  productType;
        @FieldDescription(description = "Message type. Values can be : <ul> "
        										+ "<li>Product Status</li> "
        										+ "<li>Real Time Data</li> "
        										+ "<li>Datalog Message</li> "
        										+ "<li>Product General Configuration</li> "
        										+ "<li>Button Press</li> "
        										+ "<li>Temperature Alert</li> "
        										+ "<li>KeepAlive</li> "
        										+ "</ul>")
        public String  messageType;
        
        @FieldDescription(description = "True if active indoorAirQuality LED.")
        public Boolean activeindoorAirQualityLED;
        @FieldDescription(description = "True if active Button Notification.")
        public Boolean activeButtonNotification;
        @FieldDescription(description = "True if active RealTimeData.")
        public Boolean activeRealTimeData;
        @FieldDescription(description = "True if active Datalog.")
        public Boolean activeDatalog;
        @FieldDescription(description = "True if active TemperatureAlerts.")
        public Boolean activeTemperatureAlerts;
        @FieldDescription(description = "True if active KeepAlive.")
        public Boolean activeKeepalive;
        
        @FieldDescription(description = "Period between 2 measurements  (in minutes).")
        public Period periodBetweenTwoMeasurements;
		
        @FieldDescription(description = "Datalog decimation. Keep only 1 measurement among x.")
        public Integer datalogDecimation;
        
        @FieldDescription(description = "thresholdsTemperatureAlert.")
        public ThresholdsTemperatureAlert thresholdsTemperatureAlert;
		
        @FieldDescription(description = "Delta temperature. Temperature change leading to a real-time message transmission.")
        public Temperature deltaTemperature;
        
        @FieldDescription(description = "Delta humidity. Relative humidity change leading to a real-time message transmission.")
        public Humidity deltaHumidity;
		
        @FieldDescription(description = "KeepAlive period (in hours).")
        public Period keepAlivePeriod;
	
        @FieldDescription(description = "Software version.")
        public Integer swVersion;
        
        @FieldDescription(description = "Nfc status. Values can be : Discoverable, Not Discoverable or RFU.")
        public String  nfcStatus;

    @Data
    @OutputPojo
    public class ThresholdsTemperatureAlert  {
    	 @FieldDescription(description = "°C")
    	 public String unit;
    	 @FieldDescription(description = "Temperature value")
    	 public ThresholdValues currentsThresholdsValues ;
    	 
    	 @Data
    	    @OutputPojo
    	    public class ThresholdValues  {
    	    	 @FieldDescription(description = "Threshold1 value in °C")
    	    	 public Integer threshold1;
    	    	 @FieldDescription(description = "Threshold2 value in °C")
    	    	 public Integer threshold2;
    	    }
    }
  
    @Data
    @OutputPojo
    public class Temperature  {
    	 @FieldDescription(description = "°C")
    	 public String unit;
    	 @FieldDescription(description = "Temperature value")
    	 public Float currentValue ;
    }

    @Data
    @OutputPojo
    public class Humidity  {
    	 @FieldDescription(description = "%")
    	 public String unit;
    	 @FieldDescription(description = "Relative humidity value")
    	 public Float currentValue ;
    }
    
    @Data
    @OutputPojo
    public class Period  {
    	 @FieldDescription(description = "Period unit")
    	 public String unit;
    	 @FieldDescription(description = "Period value")
    	 public Integer value ;
    }   
 }
}
