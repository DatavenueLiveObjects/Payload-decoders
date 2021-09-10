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
public abstract class SplitPilotHeaderItem {
    @FieldDescription(description = "Model")
    private String model;
    @FieldDescription(description = "StreamId")
    private String streamId;
    @FieldDescription(description = "Timestamp")
    private String timestamp;
}
