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
public class SplitPilotDatalogMessageItem extends SplitPilotHeaderItem {
		 
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
	        
	        @FieldDescription(description = "Frame index, loop counter to check if a frame has been lost.")
	        public Integer  frameIndex;
	        
	        @FieldDescription(description = "Temperature")
	        public Temperature temperature;
	        
	        @FieldDescription(description = "Humidity")
	        public Humidity humidity;
	        

	    

	    @Data
	    @OutputPojo
	    public class Temperature  {
	    	 @FieldDescription(description = "°C")
	    	 public String unit;
	    	 @FieldDescription(description = "Temperature value")
	    	 public Float value ;
	    }
	    

	    @Data
	    @OutputPojo
	    public class Humidity  {
	    	 @FieldDescription(description = "%")
	    	 public String unit;
	    	 @FieldDescription(description = "Humidity value")
	    	 public Float value ;
	    }
	    
	}

}
