package com.thilko.springdoc

import com.thilko.springdoc.model.Controller
import spock.lang.Specification


class ControllerTest extends Specification {

    TestCompiler compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.compile();
    }

    def "a controller contains resource groups"() {
        when:
        def resourceGroup = Controller.withController([compiler.customerController()] as List)

        then:
        resourceGroup[0].resources.size() == 6
        resourceGroup[1].resources.size() == 2
        resourceGroup[2].resources.size() == 1
    }

    def "private methods are not detected as an api method"() {
        when:
        def resourceGroup = Controller.withController(compiler.controller())

        then:
        !resourceGroup.collect{it.resources}.flatten().find{it.name() == "notAnApiMethod"}
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
        resourceGroups.size() == 5
    }

    def "each resource group has a name"(){
        when:
        def resourceGroups = Controller.withController(compiler.controller())

        then:
        resourceGroups.collect {it.name}.containsAll("/metrics", "/", "/user", "/customers")
    }
}
