package com.thilko.springdoc

import com.thilko.springdoc.model.Controller
import spock.lang.Specification


class ControllerTest extends Specification {

    TestCompiler compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.compile();
    }

    def "a resource has methods"() {
        when:
        def resource = Controller.createController(compiler.customerController())

        then:
        resource.methodCount == 9
    }

    def "methods returns only public api methods"() {
        when:
        def resource = Controller.createController(compiler.customerController())

        then:
        !resource.methods().find { it.name().toString() == "notAnApiMethod" }
    }

    def "a resource uses the path from @RequestMapping annotation if exist"(){
        when:
        def resource = Controller.createController(compiler.metricsController())

        then:
        resource.name() == "/metrics"
    }

    def "methods of the controller clasess are mapped to resource group objects"(){
        when:
        def resourceGroups = Controller.withController(compiler.controller())

        then:
        resourceGroups.size() == 4

    }

    def "each resource group has a name"(){
        when:
        def resourceGroups = Controller.withController(compiler.controller())

        then:
        resourceGroups.collect {it.name}.containsAll("/metrics", "/", "/user", "/customers")
    }
}
