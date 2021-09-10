/*
 * Copyright (C) 2021 Orange S.A.
 * 
 * This software is distributed under the terms and conditions of the '3-Clause BSD License'
 * license which can be found in the file 'LICENSE.txt' in this package distribution. 
 */

var decodeAndSplit = function(dataMessage) {
    try {
    	
        var parsedDataMessage = JSON.parse(dataMessage);  
        var payload = parsedDataMessage.value.payload;
        var classicResult = decodePayload(payload);
        var decodedResult =  [];
        if(typeof classicResult.messageType !== 'undefined') {
        	var mtype = classicResult.messageType;
        	if (mtype === 'Datalog Message') {
                decodedResult = buildDecodedResult(classicResult, parsedDataMessage);       		
        	}
        	else {                                  /* Only one stream */
        		decodedResult[0] =  {
        				timestamp: parsedDataMessage.timestamp,
        				value: classicResult
                };      		
        	}
        }
        else {
        		var errorObj= { "error" : "decoding failed"};
        		decodedResult[0] =  {
        				timestamp: parsedDataMessage.timestamp,
        				value: errorObj
                };
        }        
        return JsSplitUtils.stringify(decodedResult);
    } catch(e) {
        return JsSplitUtils.toErrorString(e);
    }
};

function buildDecodedResult(classicResult, parsedDataMessage) {

	var measures = [];
	measures[0] = classicResult['datalog_n-0'];
	measures[1] = classicResult['datalog_n-1'];
	measures[2] = classicResult['datalog_n-2'];
	measures[3] = classicResult['datalog_n-3'];
	measures[4] = classicResult['datalog_n-4'];
	for(var m=0; m < measures.length; m++) {		
		measures[m].productType = classicResult['productType'];
		measures[m].messageType = classicResult['messageType'];
		measures[m].frameIndex = classicResult['frameIndex'];
	}
	var interval = classicResult['timeBetweenMeasurements']['value'];
	var mn2substract = 0 - interval;
    return measures.map(function (measure, index) {
        return {
        	timestamp: dateAdd(parsedDataMessage.timestamp, 'minute', mn2substract * index),
        	value: measure
        }
    });
}

function dateAdd(date, interval, units) {
	  var ret = new Date(date); 
	  var checkRollover = function() { 
		  if(ret.getDate() != date.getDate()) ret.setDate(0);
	  };
	  
	  switch(interval.toLowerCase()) {
	    case 'year'   :	ret.setFullYear(ret.getFullYear() + units);
	    				checkRollover();
	    				break;
	    case 'quarter':	ret.setMonth(ret.getMonth() + 3 * units);
	    				checkRollover();
	    				break;
	    case 'month'  :	ret.setMonth(ret.getMonth() + units);
	    				checkRollover();
	    				break;
	    case 'week'   :	ret.setDate(ret.getDate() + 7 * units);
	    				break;
	    case 'day'    :	ret.setDate(ret.getDate() + units);
	    				break;
	    case 'hour'   :	ret.setTime(ret.getTime() + units * 3600000);
	    				break;
	    case 'minute' :	ret.setTime(ret.getTime() + units * 60000);
	    				break;
	    case 'second' :	ret.setTime(ret.getTime() + units * 1000);
	    				break;
	    default       :	ret = undefined;
	    				break;
	  }
	  return ret.toISOString();
}

var decodePayload = function(encoded) {
	
	var payloadLength = 0;
	var productIndex = 0;
	var messageIndex = 0;
	var structuredTable = [];
	var binaryBufferV2;
	var goodPayloadTest;

	try {
			payloadLength = encoded.length;			
			if (payloadLength < 2)
			{
				return { "error" : "decoding failed"};
			}

			binaryBufferV2 = hexToBin(encoded);
			productIndex = getProductTypeIndex(binaryBufferV2);
			messageIndex = getMessageTypeIndex(binaryBufferV2);
			/* Error management : product type, message type, payload length */
			goodPayloadTest = validPayloadFormat(productIndex,messageIndex,payloadLength/2);
			if (goodPayloadTest == false)
			{
				return { "error" : "decoding failed"};
			}
						
			/* Array associated to the hexadecimal payload */
			structuredTable = getStructuredFloatingValuesTable(messageIndex,binaryBufferV2);
			switch(messageIndex)
			{
				case 0: /*  Product Status */ 
						return  {"productType" : getProductType(productIndex),
									"messageType": getMessageType(messageIndex),
									"batteryLevel": getBatteryLevel(structuredTable[0]),
									"productHWStatus": getHardwareStatus(structuredTable[1]),
									"frameIndex": structuredTable[2],
									"pendingJoin": structuredTable[3]
									};
									
				case 1: /* Real Time Data */
						return {"productType" : getProductType(productIndex),
									"messageType": getMessageType(messageIndex),
									"temperature":{"unit": "°C","currentValue":parseFloat(getTemperature(structuredTable[0]))},
									"relativeHumidity": {"unit": "%","currentValue": getRelativeHumidity(structuredTable[1])},
									"indoorAirQuality": {"global":getIAQGlobal(structuredTable[2]),"source":getIAQSrc(structuredTable[3]),"co2":getIAQGlobal(structuredTable[4]),"dry":getIAQGlobal(structuredTable[5]),"mould":getIAQGlobal(structuredTable[6]),"dustmites":getIAQGlobal(structuredTable[7])},
									"hygrothermalComfortIndex" : getHCI(structuredTable[7]),
									"frameIndex": structuredTable[8]
									};
						
				case 2: /* Datalog Message */ 				
						return {"productType" : getProductType(productIndex),
									   "messageType": getMessageType(messageIndex),
									   "datalog_n-4":{"temperature":{"unit":"°C","value":parseFloat(getTemperature(structuredTable[0]))},"humidity":{"unit":"%","value":getRelativeHumidity(structuredTable[1])}},
									   "datalog_n-3":{"temperature":{"unit":"°C","value":parseFloat(getTemperature(structuredTable[2]))},"humidity":{"unit":"%","value":getRelativeHumidity(structuredTable[3])}},
									   "datalog_n-2":{"temperature":{"unit":"°C","value":parseFloat(getTemperature(structuredTable[4]))},"humidity":{"unit":"%","value":getRelativeHumidity(structuredTable[5])}},
									   "datalog_n-1":{"temperature":{"unit":"°C","value":parseFloat(getTemperature(structuredTable[6]))},"humidity":{"unit":"%","value":getRelativeHumidity(structuredTable[7])}},
									   "datalog_n-0":{"temperature":  {"unit":"°C","value":parseFloat(getTemperature(structuredTable[8]))},"humidity":{"unit":"%","value":getRelativeHumidity(structuredTable[9])}},
									   "timeBetweenMeasurements":{"unit":"minute","value":structuredTable[10]*10},
									   "frameIndex":structuredTable[11]
									};
											
				case 3: /* Configuration of product functions */
						return {"productType" : getProductType(productIndex),
									"messageType": getMessageType(messageIndex),
									"activeindoorAirQualityLED": getSmartPeriodActive(structuredTable[0]),
									"activeButtonNotification" : getSmartPeriodActive(structuredTable[1]),	
									"activeRealTimeData":getSmartPeriodActive(structuredTable[2]),
									"activeDatalog":getSmartPeriodActive(structuredTable[3]),
									"activeTemperatureAlerts":getSmartPeriodActive(structuredTable[4]),
									"activeKeepalive" :  getSmartPeriodActive(structuredTable[5]),
									"periodBetweenTwoMeasurements" : {"unit": "minutes","value":structuredTable[7]},
									"datalogDecimation" : structuredTable[8],
									"thresholdsTemperatureAlert": {"unit":"°C","currentsThresholdsValues":{"threshold1":parseFloat(getTemperature(structuredTable[9])),"threshold2":parseFloat(getTemperature(structuredTable[10]))}},
									"deltaTemperature" : {"unit": "°C","currentValue":structuredTable[11]*0.1},
									"deltaHumidity" : {"unit": "%","currentValue":getRelativeHumidity(structuredTable[12])},
									"keepAlivePeriod" : {"unit": "hours","value":structuredTable[13]},
									"swVersion" : structuredTable[14],
									"nfcStatus" : getNFCStatus(structuredTable[15])};		
									
				case 4: /*Button Press*/
						return {"productType" : getProductType(productIndex),
									"messageType": getMessageType(messageIndex),
									"buttonPress": getPressedButton(structuredTable[0]),
									"frameIndex" : structuredTable[1]};	
					
				case 5: /* Temperature Alerts */
						return {"productType" : getProductType(productIndex),
									"messageType": getMessageType(messageIndex),
									"temperature":{"unit": "°C","currentValue":parseFloat(getTemperature(structuredTable[0]))},
									"temperatureThresholdStatus":{"threshold1":getThreshold(structuredTable[1]),"threshold2":getThreshold(structuredTable[2])},
									"frameIndex" : structuredTable[3]};
							
				case 6 : /* KeepAlive */
					return {"productType" : getProductType(productIndex),"messageType": getMessageType(messageIndex)};
					
			}		 
		}		
		catch (e) {
			return {"error": "decoding failed"};
	}
};

/*
Translate Hexadecimal Message Into a Binary String Array
*/
function hexToBin(input)
{
	var output;
	var binBuffer = 0;
	var hexMsg = 0;
	var binMsg = 0;
	var replacePadStart = 0;
	
	for(i = 0;i < input.length;i++)
	{
		hexMsg = input.charAt(i);	
		binMsg = parseInt(hexMsg, 16).toString(2);
		replacePadStart = ("0000" + binMsg).slice(-4);
		binBuffer = binBuffer + replacePadStart;
	}
	output = binBuffer;
	return output;
}

/*
Get the Index of Product Type From a Binary String
*/
function getProductTypeIndex(input)
{
	var output = 0;
	var get_product_bin = 0;
	var get_product_dec = 0;
	get_product_bin = input.substring(1, 5);
	get_product_dec = parseInt(get_product_bin, 2);
	
	output = get_product_dec;
	return output;
}

/*
Get the Index of Message Type From a Binary String
*/
function getMessageTypeIndex(input)
{
	var output = 0;
	var get_message_bin = 0;
	var get_message_dec = 0;
	get_message_bin = input.substring(6, 9);
	get_message_dec = parseInt(get_message_bin, 2);
	
	output = get_message_dec;
	return output;	
}

/*
Get a Dynamic Structured Table From a Type of Message
input : Integer : type of message
input2 : String : binary string
output : integer : structured table filled by data of message  
*/
function getStructuredFloatingValuesTable(input,input2)
{
	var output = 0;
	var offset_msg_array = 0;
	var buffer = [];
	var offset2 = 9;
	var data_extract_from_bytes_msg = [];
	
	offset_msg_array = getOffsetMessageChoice(input);		
	for(i = 0; i < offset_msg_array.length;i++)
	{
		data_extract_from_bytes_msg[i] = 0;
	}
	
	for(k = 0;k < offset_msg_array.length;k++)
	{
		buffer[k] = input2.substring(offset2, (offset2+offset_msg_array[k]));
		offset2 = offset2 + offset_msg_array[k];
		data_extract_from_bytes_msg[k] = parseInt(buffer[k], 2);
	}
	output = data_extract_from_bytes_msg;
	return output;
}

/*
Get a Product Type Text From an Index Product Type
input : Integer : index of the product type
output: String : product type text 
*/
function getProductType(input)
{
	var typeOfProduct = ["Insafe Pilot Sigfox", "Insafe Carbon Sigfox", "Insafe PM Sigfox", "Insafe Pilot LoRa", "Insafe Carbon LoRa", "Insafe Pilot Mov LoRa", "Insafe Carbon Mov LoRa"];
	var output = "";	
	switch(input)
	{
		case 3:
			output = typeOfProduct[0];
			break;
		case 4:
			output = typeOfProduct[1];
			break;
		case 5:
			output = typeOfProduct[2];
			break;
		case 6:
			output = typeOfProduct[3];
			break;
		case 7:
			output = typeOfProduct[4];
			break;
		case 8:
			output = typeOfProduct[5];
			break;
		case 9:
			output = typeOfProduct[6];
			break;
		default:
			output = "Unknown";
			break;
	}
	return output;
}

/*
Get a Message Type Text From an Index Message Type
input : Integer : index of the message type
output: String : message type text 
*/
function getMessageType(input)
{
	var typeOfMessage = ["Product Status", "Real Time Data", "Datalog Message", "Product General Configuration", "Button Press",  "Temperature Alert", "KeepAlive"];
	var output = "";	
	switch(input)
	{
		case 0:
			output = typeOfMessage[0];
			break;
		case 1:
			output = typeOfMessage[1];
			break;
		case 2:
  			output = typeOfMessage[2];
  			break;
		case 3:
  			output = typeOfMessage[3];
  			break;
		case 4:
  			output = typeOfMessage[4];
  			break;
		case 5:
  			output = typeOfMessage[5];
  			break;
		case 6:
  			output = typeOfMessage[6];
  			break;
		default:
			output = "Unknown";
			break;
	}
	return output;
}

/*
Get the Offset of Data's Message From an Index Message type
input : Integer : index of the message type
output: Table[Integer] : offset table
*/
function getOffsetMessageChoice(input)
{
	var offset_for_real_time_data = [8, 8, 3, 4, 3, 3, 3, 2, 3, 3];
	var offset_for_product_status = [2, 1, 3, 1, 1];
	var offset_for_button_press = [3, 3, 2];
	var offset_for_datalog_data = [8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 4, 3, 1];
	var offset_for_temperature_alerts = [8, 1, 1, 3, 3];
	var offset_for_product_configuration = [1, 1, 1, 1, 1, 1, 2, 8, 8, 8, 8, 8, 8, 8, 8, 2, 6];
	var output = "";	
	switch(input)
	{
		case 1:
  			output = offset_for_real_time_data;
  			break;
		case 0:
  			output = offset_for_product_status;
  			break;
		case 4:
			output = offset_for_button_press;
			break;
		case 2:
			output = offset_for_datalog_data;
			break;
		case 5:
			output = offset_for_temperature_alerts;
			break;
		case 3:
			output = offset_for_product_configuration;
			break;				
	}
	return output;
}

/*
Get a Temperature From an Integer
*/
function getTemperature(input)
{
	return (output = (input * 0.2).toFixed(2));
}

/*
Get a Relative Humidity From an Integer
*/
function getRelativeHumidity(input)
{
	return (output = input * 0.5);
}

/*
Get an IAQ_Global_Value From an Integer
*/
function getIAQGlobal(input)
{
	var IAQ_level_array = ["Excellent", "Good", "Fair", "Poor", "Bad", "Reserved", "Reserved", "Error"];
	var output = "";
	switch(input)
	{		
		case 0:
			 output = IAQ_level_array[0];
			 break;			 
		case 1:
			 output = IAQ_level_array[1];
			 break;			 
		case 2:
			 output = IAQ_level_array[2];
			 break;			 
		case 3:
			 output = IAQ_level_array[3];
			 break;		
		case 4:
			 output = IAQ_level_array[4];
			 break;			 
		 case 5:
			 output = IAQ_level_array[5];
			 break;			 
		case 6:
			 output = IAQ_level_array[6];
			 break;	 
		case 7:
			 output = IAQ_level_array[7];
			 break;
		default:
			output = "Unknown";
			break;
	}
    return output; 
}


/*
Get an IAQ_Src_Value (IZIAir datasource) From an Integer
*/
function getIAQSrc(input)
{
   var IAQ_source_array = ["All", "Dryness Indicator", "Mould Indicator", "Dust Mites Indicator", "CO", "CO2", "Error"];
   var output = "";
   switch(input)
   {
	    case 0:
	        output = IAQ_source_array[0];
	        break;    
	    case 1:
	        output = IAQ_source_array[1];
	        break;	         
	    case 2:
	        output = IAQ_source_array[2];
	        break;
	    case 3:
	        output = IAQ_source_array[3];
	        break;
	    case 4:
	        output = IAQ_source_array[4];
	        break;	         
	    case 5:
	        output = IAQ_source_array[5];
	        break;	         
	    case 15:
	        output = IAQ_source_array[6];
	        break;
	    default:
			output = "Unknown";
			break;
    }
    return output; 
}

/*  
Get an HCI_Value From an Integer
*/
function getHCI(input)
{
	var HCILevelArray = ["Good", "Fair", "Poor", "Error"];
    var output = "";    
    switch(input)
    {
		case 0:
			output = HCILevelArray[0];
			break;	         
	    case 1:
	    	output = HCILevelArray[1];
	    	break; 
	    case 2:
	        output = HCILevelArray[2];
	        break;	         
	    case 3:
	        output = HCILevelArray[3];
	        break;
	    default:
			output = "Unknown";
			break;
    }
    return output; 
}

/*
Get a Battery Level From an Integer
*/
function getBatteryLevel(input)
{
	var output;
	var battery_indicator = ["High", "Medium", "Low", "Critical"];
	switch(input)
	{
		case 0:
			output = battery_indicator[0];
			break;		
		case 1:
			output = battery_indicator[1];
			break;
		case 2:
			output = battery_indicator[2];
			break;
		case 3:
			output = battery_indicator[3];
			break;
		default:
			output = "Unknown";
			break;
	}
	return output;
}

/*
Get a Hardware Status From an Integer
*/
function getHardwareStatus(input)
{
	var output;
	var hardware_status = ["Hardware OK", "Hardware Fault", "HW Working Correctly", "HW Fault Detected ?"];
	switch(input)
	{
		case 0:
			output = hardware_status[0];
			break;
		case 1:
			output = hardware_status[1];
			break;	
		case 2:
			output = hardware_status[2];
			break;
		case 3:
			output = hardware_status[3];
			break;
		default:
			output = "Unknown";
			break;
	}
	return output;
}

/*
Get a Pressed Button Status From an Integer
*/
function getPressedButton(input)
{
	var output = "";
	switch(input)
	{
		case 0: 
			output = "Short Press";
			break;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			output = "Reserved For Future Use";
			break;
		default:
			output = "Unknown";
			break;
	}
	return output;
}

/*
Get a Threshold Value From an Integer
*/
function getThreshold(input)
{
	var output = "";	
	switch(input)
	{
		case 0: 
			output = "Threshold Not Reached";
			break;
		case 1:
			output = "Threshold Reached";
			break;
		default:
			output = "Unknown";
			break;
	}
	return output;
}

/*
Get a Smart Period Status From an Integer
*/
function getSmartPeriodActive(input)
{
	var output = "";		
	switch(input)
	{
		case 0:
			output = false;
			break;	
		case 1:
			output = true;
			break;
	}
	return output;
}

/*
Get an NFC Status From an Integer
*/
function getNFCStatus(input)
{
	var output = "";	
	switch(input)
	{
		case 0 :
			output = "Discoverable";
			break;		
		case 1 :
			output = "Not Discoverable";
			break;		
		case 2 :
		case 3 :
			output = "RFU";
			break;
		default:
			output = "Unknown";
			break;
	}	
	return output;
}

/*
Verify the product type (7 allowed values), message type (2-9 range) and the payload length 
product_type : Integer : index of the product type
message_type : Integer : index of the message type
inputLength: Integer : payload length (in bytes)
output : boolean : true if the payload format is valid, false otherwise 
*/
function validPayloadFormat(product_type, message_type, inputLength)
{
	var payloadLength = [2, 6, 12, 11, 2, 3, 1];    
	var output=false;
	if ((product_type == 6) && (message_type >= 0) && (message_type <= 6))
	{
		if (inputLength == payloadLength[message_type])
		{
            output = true;
		}
		else 
		{
			output = false; 
		}
	}
	else
	{ 
        output = false; 
    }			
	return output;
}

