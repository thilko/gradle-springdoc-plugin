package com.thilko.springdoc

import com.thilko.springdoc.model.Resource
import spock.lang.Specification


class ResourceTest extends Specification {

    def compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();
        compiler.call();
    }

    def "an ressource has methods"() {
        when:
        def ressource = Resource.create(compiler.customerController())

        then:
        ressource.methodCount == 9
    }

    def "methods returns only public api methods"() {
        when:
        def ressource = Resource.create(compiler.customerController())

        then:
        !ressource.methods.find { it.name().toString() == "notAnApiMethod" }
    }
}
