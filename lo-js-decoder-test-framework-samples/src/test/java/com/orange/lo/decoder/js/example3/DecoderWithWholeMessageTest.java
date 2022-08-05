/*
 * Copyright (C) 2019 Orange S.A.
 * 
 * This software is distributed under the terms and conditions of the '3-Clause BSD License'
 * license which can be found in the file 'LICENSE.txt' in this package distribution. 
 */

package com.orange.lo.decoder.js.example3;

import static org.assertj.core.api.Assertions.assertThat;

import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.example1.pojo.FirstDecoderOutput;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import org.junit.Test;
import lombok.extern.slf4j.Slf4j;

@DeviceDescription(name = "My device name", manufacturer = "Manufacturer name", docLink = "http://www.mydevice.com/doc/doc.pdf", encoding = "")
@Slf4j
public class DecoderWithWholeMessageTest extends TestBase {
    private final String SCRIPT_PATH = "example3/decoderWithWholeMessage";

    @Test
    public void should_check_script() throws JsDecodingException {
        checkScript(SCRIPT_PATH);
    }
    
    @Test
    public void should_profile() throws JsDecodingException {
    	String input = "<your payload example (copied from the device documentation for instance)>";

        String inputDataMessage = "		{" + "   \"metadata\": {" + "      \"source\": \"urn:lora:70B3D59BA0000000\"," + "      \"connector\": \"lora\"," + "      \"encoding\": \"myEncoding\","
                + "      \"network\": {" + "         \"lora\": {" + "            \"port\": 2," + "            \"devEUI\": \"70B3D59BA0000000\"" + "         }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D59BA0000000!uplink\"," + "   \"created\": \"2017-11-26T14:55:09.048Z\"," + "   \"model\": \"lora_v0\"," + "   \"value\": {"
                + "      \"payload\": \"0001fe\"" + "   }," + "   \"timestamp\": \"2017-11-26T14:55:08.767Z\"" + "}";
        log.info("result: {}", profile(SCRIPT_PATH, input, inputDataMessage));
    }

    @Test
    @PayloadDescription(name = "the payload identifier", description = "the payload frame description.")
    public void should_decode_using_my_js_decoder_with_whole_data_message() throws JsDecodingException {
        String input = "<your payload example (copied from the device documentation for instance)>";

        String inputDataMessage = "		{" + "   \"metadata\": {" + "      \"source\": \"urn:lora:70B3D59BA0000000\"," + "      \"connector\": \"lora\"," + "      \"encoding\": \"myEncoding\","
                + "      \"network\": {" + "         \"lora\": {" + "            \"port\": 2," + "            \"devEUI\": \"70B3D59BA0000000\"" + "         }" + "      }" + "   },"
                + "   \"streamId\": \"urn:lora:70B3D59BA0000000!uplink\"," + "   \"created\": \"2017-11-26T14:55:09.048Z\"," + "   \"model\": \"lora_v0\"," + "   \"value\": {"
                + "      \"payload\": \"0001fe\"" + "   }," + "   \"timestamp\": \"2017-11-26T14:55:08.767Z\"" + "}";

        // Decode the test payload
        FirstDecoderOutput decodedOutput = formatAndDecode(SCRIPT_PATH, input, inputDataMessage, FirstDecoderOutput.class);

        // Assertions to check that the decoded payload is compliant with the expected result
        assertThat(decodedOutput.getField1()).isEqualTo("decodedPort2");

    }
}
