package com.thilko.springdoc

import com.thilko.springdoc.model.Resource
import spock.lang.Specification


class ResourceTest extends Specification {

    TestCompiler compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.compile();
    }

    def "a resource has methods"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methodCount == 9
    }

    def "methods returns only public api methods"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        !resource.methods.find { it.name().toString() == "notAnApiMethod" }
    }

    def "a resource uses the path from @RequestMapping annotation if exist"(){
        when:
        def resource = Resource.create(compiler.metricsController())

        then:
        resource.name() == "/metrics"
    }
}
