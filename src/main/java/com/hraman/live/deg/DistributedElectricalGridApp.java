package com.hraman.live.deg;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DistributedElectricalGridApp extends Application<DistributedElectricalConfig> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) throws Exception {
        new DistributedElectricalGridApp().run(args);
    }

    @Override
    public String getName() {
        return "Distributed-Electrical-Grid";
    }


    @Override
    public void run(DistributedElectricalConfig distributedElectricalConfig, Environment environment) {
        log.info("Starting {}", getName());
        final DeviceResource resource = new DeviceResource();
        environment.jersey().register(resource);
    }
}