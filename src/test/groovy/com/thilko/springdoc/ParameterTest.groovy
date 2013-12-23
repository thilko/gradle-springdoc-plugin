package com.thilko.springdoc

import com.thilko.springdoc.model.Endpoint
import spock.lang.Specification


class ParameterTest extends Specification {

    def compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();
        compiler.call();
    }

    def "name returns 'question' if annotation 'value' is 'question'"(){
        when:
        def parameter = Endpoint.create(compiler.customerController()).methods[0].parameter()

        then:
        parameter[0].name() == "question"
    }
}
