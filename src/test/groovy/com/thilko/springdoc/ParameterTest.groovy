package com.thilko.springdoc

import com.thilko.springdoc.model.Endpoint
import spock.lang.Specification


class ParameterTest extends Specification {

    def customerController

    def setup() {
        def compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();
        compiler.call();

        customerController = compiler.customerController()
    }

    def "name() returns 'question' if annotation 'value' is 'question'"(){
        when:
        def parameter = Endpoint.create(customerController).methods[0].queryParameter("question")

        then:
        parameter.name() == "question"
    }

    def "defaultValue() returns 'How much is the fish'"(){
        when:
        def parameter = Endpoint.create(customerController).methods[0].queryParameter("question")

        then:
        parameter.defaultValue() == "How much is the fish?"
    }

    def "defaultValue() returns '' if not set"(){
        when:
        def parameter = Endpoint.create(customerController).methods[2].queryParameter("name")

        then:
        parameter.defaultValue() == ""
    }

    def "required() returns 'true' if required not set"(){
        when:
        def parameter = Endpoint.create(customerController).methods[0].queryParameter("question")

        then:
        parameter.required()
    }

    def "required() returns 'false' if required set to false"(){
        when:
        def parameter = Endpoint.create(customerController).methods[2].queryParameter("name")

        then:
        !parameter.required()
    }

}
