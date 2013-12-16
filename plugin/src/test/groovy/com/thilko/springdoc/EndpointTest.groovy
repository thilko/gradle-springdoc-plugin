package com.thilko.springdoc

import com.thilko.springdoc.model.Endpoint
import spock.lang.Specification


class EndpointTest extends Specification {

    def "an endpoint as methods"() {
        given:
        def compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();
        compiler.call();

        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methodCount == 1
    }
}
