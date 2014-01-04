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
        endpoint.methodCount == 7
    }

    def "methods returns only public api methods"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods.find { it.name().toString() == "notAnApiMethod" } == null
    }
}
