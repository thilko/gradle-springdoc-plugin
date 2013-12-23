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

    def "name returns 'question' if annotation 'value' is 'question'"(){
        when:
        def parameter = Endpoint.create(customerController).methods[0].parameter("question")

        then:
        parameter.name() == "question"
    }
}
