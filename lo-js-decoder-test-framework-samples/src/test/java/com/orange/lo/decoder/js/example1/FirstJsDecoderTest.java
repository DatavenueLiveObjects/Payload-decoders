/*
 * Copyright (C) 2019 Orange S.A.
 * 
 * This software is distributed under the terms and conditions of the '3-Clause BSD License'
 * license which can be found in the file 'LICENSE.txt' in this package distribution. 
 */

package com.orange.lo.decoder.js.example1;

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
public class FirstJsDecoderTest extends TestBase {

    private final String SCRIPT_PATH = "example1/firstDecoder";

    @Test
    public void should_check_script() throws JsDecodingException {
        checkScript(SCRIPT_PATH);
    }
    
    @Test
    public void should_profile() throws JsDecodingException {
        String input = "<your payload example (copied from the device documentation for instance)>";
        log.info("result: {}", profile(SCRIPT_PATH, input, null));
    }	

    @Test
    @PayloadDescription(name = "the payload identifier", description = "the payload frame description.")
    public void should_decode_using_my_js_decoder() throws JsDecodingException {
        String input = "<your payload example (copied from the device documentation for instance)>";

        // Decode the test payload
        FirstDecoderOutput decodedOutput = formatAndDecode(SCRIPT_PATH, input, FirstDecoderOutput.class);

        // Assertions to check that the decoded payload is compliant with the expected result
        assertThat(decodedOutput.getField1()).isEqualTo("1");

    }
}
