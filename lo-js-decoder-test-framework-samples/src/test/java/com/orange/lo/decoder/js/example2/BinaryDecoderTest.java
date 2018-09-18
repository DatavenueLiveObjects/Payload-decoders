package com.orange.lo.decoder.js.example2;

import static org.assertj.core.api.Assertions.assertThat;

import com.orange.lo.decoder.js.TestBase;
import com.orange.lo.decoder.js.doc.annotation.DeviceDescription;
import com.orange.lo.decoder.js.doc.annotation.PayloadDescription;
import com.orange.lo.decoder.js.example2.pojo.BinaryDecoderOutput;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import org.junit.Test;

@DeviceDescription(name = "Simple device", manufacturer = "manufacturer name", docLink = "", encoding = "")
public class BinaryDecoderTest extends TestBase {
    private final String SCRIPT_PATH = "example2/binaryDecoder";

    @Test
    public void should_check_script() throws JsDecodingException {
        checkScript(SCRIPT_PATH);
    }

    @Test
    @PayloadDescription(name = "simple Binary Decoder payload", description = "temperature, location, battery level")
    public void should_decode_binary_payload() throws JsDecodingException {

        String input = "0127520a5a0213244567bdded154ffff07";
        BinaryDecoderOutput simpleBinaryDecoderOutput = formatAndDecode(SCRIPT_PATH, input, BinaryDecoderOutput.class);

        // Assertions to check that the decoded payload is compliant with the expected result
        assertThat(simpleBinaryDecoderOutput.getBattery().getLevel().getValue()).isEqualTo(14);
        assertThat(simpleBinaryDecoderOutput.getBattery().getLevel().getUnit()).isEqualTo("%");
        assertThat(simpleBinaryDecoderOutput.getLocation().getLat()).isEqualTo(48.80056f);
        assertThat(simpleBinaryDecoderOutput.getLocation().getLon()).isEqualTo(-46.66261f);
        assertThat(simpleBinaryDecoderOutput.getLocation().getAlt()).isEqualTo(53.1f);
        assertThat(simpleBinaryDecoderOutput.getLed()).isEqualTo("ON");
        assertThat(simpleBinaryDecoderOutput.getTemperature().getCurrentTemperature().getValue()).isEqualTo(26.5f);
        assertThat(simpleBinaryDecoderOutput.getTemperature().getCurrentTemperature().getUnit()).isEqualTo("°C");

    }
}
