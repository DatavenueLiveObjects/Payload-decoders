/*
 * Copyright (C) 2019 Orange S.A.
 * 
 * This software is distributed under the terms and conditions of the '3-Clause BSD License'
 * license which can be found in the file 'LICENSE.txt' in this package distribution. 
 */

package com.orange.lo.decoder.js.example2.pojo;

import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class BinaryDecoderOutput {
    public Integer altitudeGps;
    public Pressure pressure;
    public Temperature temperature;
    public Location location;
    public String led;
    public Battery battery;

    @Data
    public class Location {
        @FieldDescription(description = "latitude in decimal degrees")
        public Float lat;
        @FieldDescription(description = "longitude in decimal degrees")
        public Float lon;
        @FieldDescription(description = "altitude in meters")
        public Float alt;
    }

    @Data
    public class Pressure {
        @FieldDescription(description = "pressure value")
        public Float value;
        @FieldDescription(description = "hPa")
        public String unit;
    }

    @Data
    public class Temperature {
        public CurrentTemperature currentTemperature;

        @Data
        public class CurrentTemperature {
            @FieldDescription(description = "temperature value")
            public Float value;
            @FieldDescription(description = "°C")
            public String unit;
        }
    }

    @Data
    public class Battery {
        public Level level;

        @Data
        public class Level {
            @FieldDescription(description = "remaining battery")
            public Integer value;
            @FieldDescription(description = "%")
            public String unit;
        }
    }
}
