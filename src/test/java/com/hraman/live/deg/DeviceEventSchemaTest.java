package com.hraman.live.deg;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeviceEventSchemaTest {
    private static Schema schema;

    @Test
    void jsonToJava() throws IOException {
        String deviceEventJson = "{\n" +
                                 "  \"device_id\": \"52a9351a-7030-434d-932c-dd0fe4d3b052\",\n" +
                                 "  \"charging_source\": \"SOLAR\",\n" +
                                 "  \"charging\": -378,\n" +
                                 "  \"processor4_temp\": 166,\n" +
                                 "  \"processor2_temp\": 5,\n" +
                                 "  \"processor1_temp\": -3,\n" +
                                 "  \"current_capacity\": 4959,\n" +
                                 "  \"inverter_state\": 3,\n" +
                                 "  \"moduleL_temp\": 31,\n" +
                                 "  \"moduleR_temp\": 81,\n" +
                                 "  \"processor3_temp\": 54,\n" +
                                 "  \"SoC_regulator\": 28.51637\n" +
                                 "}";
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(deviceEventJson.getBytes(StandardCharsets.UTF_8)));
        Decoder decoder = DecoderFactory.get().jsonDecoder(schema, input);
        DatumReader<DeviceEvent> reader = new SpecificDatumReader<>(schema);
        final DeviceEvent deviceEventJava = reader.read(null, decoder);
        assertNotNull(deviceEventJava);
        assertEquals("52a9351a-7030-434d-932c-dd0fe4d3b052", deviceEventJava.getDeviceId().toString());
        assertEquals(ChargingSource.SOLAR, deviceEventJava.getChargingSource());
        assertEquals(-378, deviceEventJava.getCharging());
        assertEquals(28.51637f, deviceEventJava.getSoCRegulator());
    }

    @BeforeAll
    static void beforeAll() throws IOException {
            schema = new Schema.Parser()
                    .parse(DeviceEventSchemaTest.class.getResourceAsStream("/avro/device-event.avsc"));
    }
}
