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
public class SplitPilotRealTimeDataItem extends SplitPilotHeaderItem {
	 
	@FieldDescription(description = "Decoded payload")
	public Value value;

    @Data
    @OutputPojo
    public class Value {
    	
        @FieldDescription(description = "Product type: Insafe Pilot LoRa")
        private String  productType;
        @FieldDescription(description = "Message type. Values can be : <ul> "
        										+ "<li>Product Status</li> "
        										+ "<li>Real Time Data</li> "
        										+ "<li>Datalog Message</li> "
        										+ "<li>Product General Configuration</li> "
        										+ "<li>Button Press</li> "
        										+ "<li>Temperature Alert</li> "
        										+ "<li>KeepAlive</li>"
        										+ "</ul>")
        private String  messageType;     
        
        @FieldDescription(description = "Frame index, loop counter to check if a frame has been lost.")
        private Integer  frameIndex;
        
        @FieldDescription(description = "Temperature")
        public Temperature temperature;
        
        @FieldDescription(description = "Relative humidity")
        public Humidity relativeHumidity;
        
        @FieldDescription(description = "Indoor air quality")
        public IndoorAirQuality indoorAirQuality;
    
        @FieldDescription(description = "Hygrothermal comfort index. Values can be :<ul> "
        										+ "<li>Good </li> "
        										+ "<li>Fair</li> "
        										+ "<li>Poor</li> "
        										+ "<li>Error</li> "
        										+ "</ul>")
        public  String hygrothermalComfortIndex;

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
    public class IndoorAirQuality  {
    	@FieldDescription(description = "Global indoor air quality. Values can be: <ul> "
																	+ "<li>Excellent</li> "
																	+ "<li>Good</li> "
																	+ "<li>Fair</li> "
																	+ "<li>Poor</li> "
																	+ "<li>Bad</li> "
																	+ "<li>Reserved</li> "
																	+ "<li>Error</li> "
																	+ "</ul>")
   	 	public String global;
   	 	@FieldDescription(description = "Indoor air quality source.  Values can be :<ul> "
																	+ "<li>All</li>"
																	+ "<li>Drought Index</li> "
																	+ "<li>Mold Index</li> "
																	+ "<li>Mite Index</li> "
																	+ "<li>CO</li> "
																	+ "<li>CO2</li> "
																	+ "<li>Error</li> "
																	+ "</ul>")
	 	public String source;
   	 	@FieldDescription(description = "Co2. Values can be: <ul> "
																	+ "<li>Excellent</li> "
																	+ "<li>Good</li> "
																	+ "<li>Fair</li> "
																	+ "<li>Poor</li> "
																	+ "<li>Bad</li> "
																	+ "<li>Reserved</li> "
																	+ "<li>Error</li> "
																	+ "</ul>")
	 	public String co2;
   	 	@FieldDescription(description = "Dry. Values can be: <ul> "
																	+ "<li>Excellent</li> "
																	+ "<li>Good</li> "
																	+ "<li>Fair</li> "
																	+ "<li>Poor</li> "
																	+ "<li>Bad</li> "
																	+ "<li>Reserved</li> "
																	+ "<li>Error</li> "
																	+ "</ul>")
	 	public String dry;
  	 	@FieldDescription(description = "Mould. Values can be: <ul> "
																	+ "<li>Excellent</li> "
																	+ "<li>Good</li> "
																	+ "<li>Fair</li> "
																	+ "<li>Poor</li> "
																	+ "<li>Bad</li> "
																	+ "<li>Reserved</li> "
																	+ "<li>Error</li> "
																	+ "</ul>")
	 	public String mould;
  	 	@FieldDescription(description = "Dustmites. Values can be:<ul> "
																	+ "<li>Excellent</li> "
																	+ "<li>Good</li> "
																	+ "<li>Fair</li> "
																	+ "<li>Poor</li> "
																	+ "<li>Bad</li> "
																	+ "<li>Reserved</li> "
																	+ "<li>Error</li> "
																	+ "</ul>")
	 	public String dustmites;
    }        
    }
}
