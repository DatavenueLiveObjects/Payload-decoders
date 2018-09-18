package com.orange.lo.decoder.js.example1.pojo;

import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class FirstDecoderOutput {
    @FieldDescription(description = "field description")
    private String field1;
}
