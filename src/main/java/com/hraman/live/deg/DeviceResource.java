package com.hraman.live.deg;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Properties;

@Path("/device-event/")
public class DeviceResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @POST
    @Path("{uuid}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    @Produces(MediaType.APPLICATION_JSON)
    public void deviceEvent(@PathParam("uuid") String uuid, DeviceEvent event) {
        log.info("Got {} {}", event.getSchema(), event.getDeviceId());
        send(event);
    }

    private Object send(DeviceEvent deviceEvent) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:29092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        properties.put("schema.registry.url", "http://localhost:8090");

        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        try {
            kafkaProducer.send(new ProducerRecord("deg-device-events",deviceEvent.getDeviceId().toString(), deviceEvent));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            kafkaProducer.close();
        }
        return true;
    }
}
