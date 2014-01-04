package com.thilko.springdoc

import com.thilko.springdoc.model.Endpoint
import spock.lang.Specification


class EndpointTest extends Specification {

    def compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();
        compiler.call();
    }

    def "an endpoint has methods"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methodCount == 6
    }

    def "an endpoint has not annotated methods"() {
        when:
        def endpoint = Endpoint.create(compiler.statisticsController())

        then:
        endpoint.methods[1].name().contentEquals("visitorPerMinute") == true
    }

}
