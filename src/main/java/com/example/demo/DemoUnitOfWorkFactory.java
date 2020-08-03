package com.example.demo;

import org.apache.camel.Exchange;
import org.apache.camel.impl.engine.DefaultUnitOfWork;
import org.apache.camel.spi.UnitOfWork;
import org.apache.camel.spi.UnitOfWorkFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DemoUnitOfWorkFactory implements UnitOfWorkFactory {

    private final List<String> headerNames;

    public DemoUnitOfWorkFactory() {
        this.headerNames = null;
    }

    public DemoUnitOfWorkFactory(List<String> headerNames) {
        this.headerNames = headerNames;
    }

    public Optional<List<String>> headerNames() {
        return Optional.ofNullable(headerNames);
    }

    @Override
    public UnitOfWork createUnitOfWork(Exchange exchange) {
        UnitOfWork unit;

        if (exchange.getContext().isUseMDCLogging()) {

            // allow customization of the logged headers
            if (headerNames().isPresent()) {
                unit = new DemoMDCUnitOfWork(exchange, headerNames);
            } else {
                // fallback to default headers
                unit = new DemoMDCUnitOfWork(exchange);
            }
        } else {
            unit = new DefaultUnitOfWork(exchange);
        }

        return unit;
    }
}
