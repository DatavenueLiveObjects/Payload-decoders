/*
 * Copyright (C) 2021 Orange S.A.
 * 
 * This software is distributed under the terms and conditions of the '3-Clause BSD License'
 * license which can be found in the file 'LICENSE.txt' in this package distribution. 
 */

package com.orange.lo.decoder.js.example4;

import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotButtonPressItem;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotButtonPressResult;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotDatalogMessageItem;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotDatalogMessageResult;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotKeepAliveItem;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotKeepAliveResult;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotProductGeneralConfigurationItem;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotProductGeneralConfigurationResult;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotProductStatusItem;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotProductStatusResult;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotRealTimeDataItem;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotRealTimeDataResult;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotTemperatureAlertItem;
import com.orange.lo.decoder.js.example4.pojo.SplitPilotTemperatureAlertResult;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.orange.iaes.commons.json.JsonEncodingException;

@DeviceDescription(name = "Pilot", manufacturer = "MANUFACTURER_EXAMPLE", docLink = "", encoding = "example_pilot_split_lora_v1.0.v1.0", hidden = false)
public class SplitTest extends TestBase {
    private final String SCRIPT_PATH = "example4/splitDecoder";

    @Test
    public void should_check_script() throws JsDecodingException {
        checkScript(SCRIPT_PATH);
    }

	@Test
    @PayloadDescription(name = "Product Status", description = "Product Status frame relayed by decoder in 1 message")
    public void should_decode_split_60_payload() throws JsDecodingException, JsonEncodingException {	
    
    	String input = "6068";
        String datamessage = "{\n" +
                "    \"streamId\": \"pilotTemp1234\",\n" +
                "    \"model\": \"example_pilot_split_lora_v1.0\",\n" +
                "    \"timestamp\": \"2021-03-10T20:00:00.000Z\",\n" +
                "    \"value\": {\n" +
                "    \"payload\": \"" + input + "\"\n" +
                "    }\n" +
                "}";
        
        SplitPilotProductStatusResult decodedOutput = formatAndDecodeSplit(SCRIPT_PATH, datamessage, SplitPilotProductStatusResult.class);
        
        SplitPilotProductStatusItem item = decodedOutput.get(0);
        assertThat(item.getTimestamp()).isEqualTo("2021-03-10T20:00:00.000Z");
        assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
        assertThat(item.getValue().getMessageType()).isEqualTo("Product Status");
        assertThat(item.getValue().getBatteryLevel()).isEqualTo("Medium");
        assertThat(item.getValue().getProductHWStatus()).isEqualTo("Hardware Fault");
        assertThat(item.getValue().getFrameIndex()).isEqualTo(2);
        assertThat(item.getValue().getPendingJoin()).isEqualTo(0);
     }
	
    @Test
    @PayloadDescription(name = "Real Time Data", description = "Real Time Data frame relayed by decoder in 1 message") 
    public void should_decode_split_61_payload() throws JsDecodingException, JsonEncodingException {
    	String input = "61703262c090";
    	String datamessage = "{\n" +
                 "    \"streamId\": \"pilotTemp1234\",\n" +
                 "    \"model\": \"example_pilot_split_lora_v1.0\",\n" +
                 "    \"timestamp\": \"2021-03-10T20:00:00.000Z\",\n" +
                 "    \"value\": {\n" +
                 "    \"payload\": \"" + input + "\"\n" +
                 "    }\n" +
                 "}";
    	SplitPilotRealTimeDataResult decodedOutput = formatAndDecodeSplit(SCRIPT_PATH, datamessage, SplitPilotRealTimeDataResult.class);
        
        SplitPilotRealTimeDataItem item = decodedOutput.get(0);

        assertThat(item.getTimestamp()).isEqualTo("2021-03-10T20:00:00.000Z");
        assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
        assertThat(item.getValue().getMessageType()).isEqualTo("Real Time Data");
        assertThat(item.getValue().getFrameIndex()).isEqualTo(2);
        assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
        assertThat(item.getValue().getTemperature().getCurrentValue()).isEqualTo(22.4f);
        assertThat(item.getValue().getRelativeHumidity().getUnit()).isEqualTo("%");
        assertThat(item.getValue().getRelativeHumidity().getCurrentValue()).isEqualTo(25f);
        assertThat(item.getValue().getIndoorAirQuality().getGlobal()).isEqualTo("Poor");
        assertThat(item.getValue().getIndoorAirQuality().getSource()).isEqualTo("Dryness Indicator");
        assertThat(item.getValue().getIndoorAirQuality().getCo2()).isEqualTo("Poor");
        assertThat(item.getValue().getIndoorAirQuality().getDry()).isEqualTo("Excellent");
        assertThat(item.getValue().getIndoorAirQuality().getMould()).isEqualTo("Excellent");
        assertThat(item.getValue().getIndoorAirQuality().getDustmites()).isEqualTo("Fair");
        assertThat(item.getValue().getHygrothermalComfortIndex()).isEqualTo("Poor");
    }

    @Test
    @PayloadDescription(name = "Datalog Message", description = "Datalog Message frame relayed by decoder in 5 messages")
    public void should_decode_split_62_payload() throws JsDecodingException, JsonEncodingException {	
    
    	String input = "6263486349644a644a654b18";
        String datamessage = "{\n" +
                "    \"streamId\": \"pilotTemp1234\",\n" +
                "    \"model\": \"example_pilot_split_lora_v1.0\",\n" +
                "    \"timestamp\": \"2021-03-10T20:00:00.000Z\",\n" +
                "    \"value\": {\n" +
                "    \"payload\": \"" + input + "\"\n" +
                "    }\n" +
                "}";

       SplitPilotDatalogMessageResult decodedOutput = formatAndDecodeSplit(SCRIPT_PATH, datamessage, SplitPilotDatalogMessageResult.class);
        
       SplitPilotDatalogMessageItem item = decodedOutput.get(0);
       assertThat(item.getTimestamp()).isEqualTo("2021-03-10T20:00:00.000Z"); 
       assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
       assertThat(item.getValue().getMessageType()).isEqualTo("Datalog Message");
       assertThat(item.getValue().getFrameIndex()).isEqualTo(4);
       assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
       assertThat(item.getValue().getTemperature().getValue()).isEqualTo(20.2f);
       assertThat(item.getValue().getHumidity().getUnit()).isEqualTo("%");
       assertThat(item.getValue().getHumidity().getValue()).isEqualTo(37.5f);

       item = decodedOutput.get(1);
       assertThat(item.getTimestamp()).isEqualTo("2021-03-10T19:50:00.000Z"); 
       assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
       assertThat(item.getValue().getMessageType()).isEqualTo("Datalog Message");
       assertThat(item.getValue().getFrameIndex()).isEqualTo(4);
       assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
       assertThat(item.getValue().getTemperature().getValue()).isEqualTo(20f);
       assertThat(item.getValue().getHumidity().getUnit()).isEqualTo("%");
       assertThat(item.getValue().getHumidity().getValue()).isEqualTo(37f);

       item = decodedOutput.get(2);
       assertThat(item.getTimestamp()).isEqualTo("2021-03-10T19:40:00.000Z"); 
       assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
       assertThat(item.getValue().getMessageType()).isEqualTo("Datalog Message");
       assertThat(item.getValue().getFrameIndex()).isEqualTo(4);
       assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
       assertThat(item.getValue().getTemperature().getValue()).isEqualTo(20f);
       assertThat(item.getValue().getHumidity().getUnit()).isEqualTo("%");
       assertThat(item.getValue().getHumidity().getValue()).isEqualTo(37f);
        
       item = decodedOutput.get(3);
       assertThat(item.getTimestamp()).isEqualTo("2021-03-10T19:30:00.000Z");
       assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
       assertThat(item.getValue().getMessageType()).isEqualTo("Datalog Message");
       assertThat(item.getValue().getFrameIndex()).isEqualTo(4);
       assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
       assertThat(item.getValue().getTemperature().getValue()).isEqualTo(19.8f);
       assertThat(item.getValue().getHumidity().getUnit()).isEqualTo("%");
       assertThat(item.getValue().getHumidity().getValue()).isEqualTo(36.5f);     
        
       item = decodedOutput.get(4);
       assertThat(item.getTimestamp()).isEqualTo("2021-03-10T19:20:00.000Z"); 
       assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
       assertThat(item.getValue().getMessageType()).isEqualTo("Datalog Message");
       assertThat(item.getValue().getFrameIndex()).isEqualTo(4);
       assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
       assertThat(item.getValue().getTemperature().getValue()).isEqualTo(19.8f);
       assertThat(item.getValue().getHumidity().getUnit()).isEqualTo("%");
       assertThat(item.getValue().getHumidity().getValue()).isEqualTo(36f);
    }

    @Test
    @PayloadDescription(name = "Datalog Message", description = "Datalog Message frame relayed by decoder in 5 messages")
    public void should_decode_split_62b_payload() throws JsDecodingException, JsonEncodingException {	
    
    	String input = "627450745173517350754F3C";
        String datamessage = "{\n" +
                "    \"streamId\": \"pilotTemp1234\",\n" +
                "    \"model\": \"example_pilot_split_lora_v1.0\",\n" +
                "    \"timestamp\": \"2021-03-10T20:00:00.000Z\",\n" +
                "    \"value\": {\n" +
                "    \"payload\": \"" + input + "\"\n" +
                "    }\n" +
                "}";

       SplitPilotDatalogMessageResult decodedOutput = formatAndDecodeSplit(SCRIPT_PATH, datamessage, SplitPilotDatalogMessageResult.class);
        
       SplitPilotDatalogMessageItem item = decodedOutput.get(0);
       assertThat(item.getTimestamp()).isEqualTo("2021-03-10T20:00:00.000Z");
       assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
       assertThat(item.getValue().getMessageType()).isEqualTo("Datalog Message");
       assertThat(item.getValue().getFrameIndex()).isEqualTo(6);
       assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
       assertThat(item.getValue().getTemperature().getValue()).isEqualTo(23.40f);
       assertThat(item.getValue().getHumidity().getUnit()).isEqualTo("%");
       assertThat(item.getValue().getHumidity().getValue()).isEqualTo(39.5f);
 
       item = decodedOutput.get(1);
       assertThat(item.getTimestamp()).isEqualTo("2021-03-10T19:30:00.000Z"); 
       assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
       assertThat(item.getValue().getMessageType()).isEqualTo("Datalog Message");
       assertThat(item.getValue().getFrameIndex()).isEqualTo(6);
       assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
       assertThat(item.getValue().getTemperature().getValue()).isEqualTo(23f);
       assertThat(item.getValue().getHumidity().getUnit()).isEqualTo("%");
       assertThat(item.getValue().getHumidity().getValue()).isEqualTo(40f);

       item = decodedOutput.get(2);
       assertThat(item.getTimestamp()).isEqualTo("2021-03-10T19:00:00.000Z"); 
       assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
       assertThat(item.getValue().getMessageType()).isEqualTo("Datalog Message");
       assertThat(item.getValue().getFrameIndex()).isEqualTo(6);
       assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
       assertThat(item.getValue().getTemperature().getValue()).isEqualTo(23f);
       assertThat(item.getValue().getHumidity().getUnit()).isEqualTo("%");
       assertThat(item.getValue().getHumidity().getValue()).isEqualTo(40.5f);
 
       item = decodedOutput.get(3);
       assertThat(item.getTimestamp()).isEqualTo("2021-03-10T18:30:00.000Z");
       assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
       assertThat(item.getValue().getMessageType()).isEqualTo("Datalog Message");
       assertThat(item.getValue().getFrameIndex()).isEqualTo(6);
       assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
       assertThat(item.getValue().getTemperature().getValue()).isEqualTo(23.2f);
       assertThat(item.getValue().getHumidity().getUnit()).isEqualTo("%");
       assertThat(item.getValue().getHumidity().getValue()).isEqualTo(40.5f);     
        
       item = decodedOutput.get(4);
       assertThat(item.getTimestamp()).isEqualTo("2021-03-10T18:00:00.000Z"); 
       assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
       assertThat(item.getValue().getMessageType()).isEqualTo("Datalog Message");
       assertThat(item.getValue().getFrameIndex()).isEqualTo(6);
       assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
       assertThat(item.getValue().getTemperature().getValue()).isEqualTo(23.2f);
       assertThat(item.getValue().getHumidity().getUnit()).isEqualTo("%");
       assertThat(item.getValue().getHumidity().getValue()).isEqualTo(40f);     
    }
      
    @Test
    @PayloadDescription(name = "Product General Configuration", description = "Product General Configuration frame relayed by decoder in 1 message") 
    public void should_decode_split_63_payload() throws JsDecodingException, JsonEncodingException {
    	String input = "636c0a065a73050a181500";
    	String datamessage = "{\n" +
                 "    \"streamId\": \"pilotTemp1234\",\n" +
                 "    \"model\": \"example_pilot_split_lora_v1.0\",\n" +
                 "    \"timestamp\": \"2021-03-10T20:00:00.000Z\",\n" +
                 "    \"value\": {\n" +
                 "    \"payload\": \"" + input + "\"\n" +
                 "    }\n" +
                 "}";
    	SplitPilotProductGeneralConfigurationResult decodedOutput = formatAndDecodeSplit(SCRIPT_PATH, datamessage, SplitPilotProductGeneralConfigurationResult.class);
    	SplitPilotProductGeneralConfigurationItem item = decodedOutput.get(0);
    	assertThat(item.getTimestamp()).isEqualTo("2021-03-10T20:00:00.000Z");
        assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
        assertThat(item.getValue().getMessageType()).isEqualTo("Product General Configuration");
        assertThat(item.getValue().getActiveindoorAirQualityLED()).isEqualTo(false);
        assertThat(item.getValue().getActiveButtonNotification()).isEqualTo(true);
        assertThat(item.getValue().getActiveRealTimeData()).isEqualTo(true);
        assertThat(item.getValue().getActiveDatalog()).isEqualTo(false);
        assertThat(item.getValue().getActiveTemperatureAlerts()).isEqualTo(true);
        assertThat(item.getValue().getActiveKeepalive()).isEqualTo(true);
        assertThat(item.getValue().getPeriodBetweenTwoMeasurements().getUnit()).isEqualTo("minutes");
        assertThat(item.getValue().getPeriodBetweenTwoMeasurements().getValue()).isEqualTo(10);
        assertThat(item.getValue().getDatalogDecimation()).isEqualTo(6);
        assertThat(item.getValue().getThresholdsTemperatureAlert().getUnit()).isEqualTo("°C");
        assertThat(item.getValue().getThresholdsTemperatureAlert().getCurrentsThresholdsValues().getThreshold1()).isEqualTo(18);
        assertThat(item.getValue().getThresholdsTemperatureAlert().getCurrentsThresholdsValues().getThreshold2()).isEqualTo(23);
        assertThat(item.getValue().getDeltaTemperature().getUnit()).isEqualTo("°C");
        assertThat(item.getValue().getDeltaTemperature().getCurrentValue()).isEqualTo(0.5f);
        assertThat(item.getValue().getDeltaHumidity().getUnit()).isEqualTo("%");
        assertThat(item.getValue().getDeltaHumidity().getCurrentValue()).isEqualTo(5f);
        assertThat(item.getValue().getKeepAlivePeriod().getUnit()).isEqualTo("hours");
        assertThat(item.getValue().getKeepAlivePeriod().getValue()).isEqualTo(24);
        assertThat(item.getValue().getSwVersion()).isEqualTo(21);
        assertThat(item.getValue().getNfcStatus()).isEqualTo("Discoverable");      
    }
        
    @Test
    @PayloadDescription(name = "Button Press", description = "Button Press frame relayed by decoder in 1 message") 
    public void should_decode_split_64_payload() throws JsDecodingException, JsonEncodingException {
    	String input = "6400";
    	String datamessage = "{\n" +
                 "    \"streamId\": \"pilotTemp1234\",\n" +
                 "    \"model\": \"example_pilot_split_lora_v1.0\",\n" +
                 "    \"timestamp\": \"2021-03-10T20:00:00.000Z\",\n" +
                 "    \"value\": {\n" +
                 "    \"payload\": \"" + input + "\"\n" +
                 "    }\n" +
                 "}";
    	SplitPilotButtonPressResult decodedOutput = formatAndDecodeSplit(SCRIPT_PATH, datamessage, SplitPilotButtonPressResult.class);
    	SplitPilotButtonPressItem item = decodedOutput.get(0);
        assertThat(item.getTimestamp()).isEqualTo("2021-03-10T20:00:00.000Z");
        assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
        assertThat(item.getValue().getMessageType()).isEqualTo("Button Press");
        assertThat(item.getValue().getFrameIndex()).isEqualTo(0);
        assertThat(item.getValue().getButtonPress()).isEqualTo("Short Press");         
    }

    @Test
    @PayloadDescription(name = "Temperature Alert", description = "Temperature Alert frame relayed by decoder in 1 message") 
    public void should_decode_split_65_payload() throws JsDecodingException, JsonEncodingException {
    	String input = "6573c0";
    	String datamessage = "{\n" +
                 "    \"streamId\": \"pilotTemp1234\",\n" +
                 "    \"model\": \"example_pilot_split_lora_v1.0\",\n" +
                 "    \"timestamp\": \"2021-03-10T20:00:00.000Z\",\n" +
                 "    \"value\": {\n" +
                 "    \"payload\": \"" + input + "\"\n" +
                 "    }\n" +
                 "}";
	    SplitPilotTemperatureAlertResult decodedOutput = formatAndDecodeSplit(SCRIPT_PATH, datamessage, SplitPilotTemperatureAlertResult.class);
	    SplitPilotTemperatureAlertItem item = decodedOutput.get(0);
    	assertThat(item.getTimestamp()).isEqualTo("2021-03-10T20:00:00.000Z");
        assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
        assertThat(item.getValue().getMessageType()).isEqualTo("Temperature Alert");
        assertThat(item.getValue().getFrameIndex()).isEqualTo(0);
        assertThat(item.getValue().getTemperature().getUnit()).isEqualTo("°C");
        assertThat(item.getValue().getTemperature().getCurrentValue()).isEqualTo(23f);
        assertThat(item.getValue().getTemperatureThresholdStatus().getThreshold1()).isEqualTo("Threshold Reached");
        assertThat(item.getValue().getTemperatureThresholdStatus().getThreshold2()).isEqualTo("Threshold Reached");
    }

   @Test
    @PayloadDescription(name = "KeepAlive", description = "KeepAlive frame relayed by decoder in 1 message") 
    public void should_decode_split_66_payload() throws JsDecodingException, JsonEncodingException {
	   String input = "66";
    	String datamessage = "{\n" +
                 "    \"streamId\": \"pilotTemp1234\",\n" +
                 "    \"model\": \"example_pilot_split_lora_v1.0\",\n" +
                 "    \"timestamp\": \"2021-03-10T20:00:00.000Z\",\n" +
                 "    \"value\": {\n" +
                 "    \"payload\": \"" + input + "\"\n" +
                 "    }\n" +
                 "}";
    	SplitPilotKeepAliveResult decodedOutput = formatAndDecodeSplit(SCRIPT_PATH, datamessage, SplitPilotKeepAliveResult.class);
    	SplitPilotKeepAliveItem item = decodedOutput.get(0);
    	assertThat(item.getTimestamp()).isEqualTo("2021-03-10T20:00:00.000Z");
        assertThat(item.getValue().getProductType()).isEqualTo("Insafe Pilot LoRa");
        assertThat(item.getValue().getMessageType()).isEqualTo("KeepAlive");
   }
 
}
