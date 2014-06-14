package com.thilko.springdoc

import com.thilko.springdoc.model.ResourceGroup
import spock.lang.Specification


class ResourceGroupTest extends Specification {

    TestCompiler compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.compile();
    }

    def "a resource has methods"() {
        when:
        def resource = ResourceGroup.create(compiler.customerController())

        then:
        resource.methodCount == 9
    }

    def "methods returns only public api methods"() {
        when:
        def resource = ResourceGroup.create(compiler.customerController())

        then:
        !resource.methods().find { it.name().toString() == "notAnApiMethod" }
    }

    def "a resource uses the path from @RequestMapping annotation if exist"(){
        when:
        def resource = ResourceGroup.create(compiler.metricsController())

        then:
        resource.name() == "/metrics"
    }

    def "methods of the controller clasess are mapped to resource objects"(){
        when:
        def resources = ResourceGroup.withController(compiler.controller())

        then:
        resources.size() == 11
    }
}
