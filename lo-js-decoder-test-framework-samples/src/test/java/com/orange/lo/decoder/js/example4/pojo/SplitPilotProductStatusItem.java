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
public class SplitPilotProductStatusItem extends SplitPilotHeaderItem {
	 
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
        										+ "<li>KeepAlive</li> "
        										+ "</ul>")
        private String  messageType;
        @FieldDescription(description = "Battery level state. Value can be High, Medium, Low or Critical.")
        private String  batteryLevel;
        @FieldDescription(description = "Product hardware status.Values can be : <ul> "
        										+ "<li>Hardware OK</li> "
        										+ "<li>Hardware Fault</li> "
        										+ "<li>HW Working Correctly</li> "
        										+ "<li>HW Fault Detected ?</li> "
        										+ "</ul>")
        private String  productHWStatus;
        @FieldDescription(description = "Frame index, loop counter to check if a frame has been lost.")
        private Integer  frameIndex;
        
        @FieldDescription(description = "Pending Join. Join request scheduled. Values can be No join request scheduled or Join request scheduled.")
        private Integer  pendingJoin;
        
    }
}
