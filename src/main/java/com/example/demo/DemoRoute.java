package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.*;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class DemoRoute extends RouteBuilder {

    @Autowired
    private CamelContext context;

    @Override
    public void configure() {
        from("timer:test?period=5s").id("demoRoute")
                .process(e -> {
                    Invoice invoice = new Invoice(Math.abs(new Random().nextLong()), "num123");
                    e.getIn().setHeader("CoupaInvoiceID", invoice.getId());
                    //log.info("1. invoice generated {}", keyValue("coupaInvoiceId", invoice.getId()));
                    //log.info("2. invoice generated {} and {}", keyValue("coupaInvoiceId", invoice.getId()), keyValue("coupaInvoiceNumber", invoice.getNumber()));
                    //log.info("3. invoice generated", kv("coupaInvoiceId", invoice.getId()), kv("coupaInvoiceNumber", invoice.getNumber()));
                    //log.info("4. invoice generated", kv("coupaInvoice",invoice));
                    e.getIn().setBody(invoice);
                })
                .log(LoggingLevel.INFO, "Hello ${body}");
    }
}
