package com.thilko.springdoc

import com.thilko.springdoc.model.Endpoint
import spock.lang.Specification


class MethodTest extends Specification {

    def compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();
        compiler.call();
    }

    def "httpMethod returns GET if annotated with HttpMethod.GET"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[0].httpMethod == "GET"
    }

    def "httpMethod returns POST if annotated with HttpMethod.POST"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[1].httpMethod == "POST"
    }

    def "httpMethodCssClass returns 'label-primary' for GET"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[0].httpMethodCssClass == "label-primary"
    }

    def "name returns correct name of the annotated method"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[0].name.contentEquals("askMe")
    }
}
