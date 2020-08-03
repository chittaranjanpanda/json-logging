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

    @Override
    public UnitOfWork createUnitOfWork(Exchange exchange) {
        UnitOfWork answer;

        if (exchange.getContext().isUseMDCLogging()) {

            // allow customization of the logged headers
            if (headerNames != null) {
                answer = new DemoMDCUnitOfWork(exchange, headerNames);
            } else {
                // fallback to default headers
                answer = new DemoMDCUnitOfWork(exchange);
            }
        } else {
            answer = new DefaultUnitOfWork(exchange);
        }

        return answer;
    }
}
