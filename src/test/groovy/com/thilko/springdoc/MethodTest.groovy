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
        endpoint.methods[0].httpMethod() == "GET"
    }

    def "httpMethod returns POST if annotated with HttpMethod.POST"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[1].httpMethod() == "POST"
    }

    def "httpMethodCssClass returns 'label label-primary' for GET"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[0].httpMethodCssClass() == "label label-primary"
    }

    def "httpMethodCssClass returns 'label label-success' for POST"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[1].httpMethodCssClass() == "label label-success"
    }

    def "httpMethodCssClass returns 'label label-info' for PUT"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[2].httpMethodCssClass() == "label label-info"
    }

    def "httpMethodCssClass returns 'label label-danger' for DELETE"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[3].httpMethodCssClass() == "label label-danger"
    }


    def "name returns correct name of the annotated method"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[0].name().contentEquals("askMe")
    }

    def "path returns / if no value RequestMapping is set"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[0].path() == "/"
    }

    def "path returns correct value if value of RequestMapping is set"() {
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[1].path() == "/data"
    }

    def "parameter returns all params that are annotated on the mpi method"(){
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[0].parameter().size() == 2
    }

    def "responseClass returns 'String' if method returns a string"(){
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[4].responseClass() == "java.lang.String"
    }

    def "responseClass returns 'com.thilko.springdoc.User' if method returns a User"(){
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[2].responseClass() == "com.thilko.springdoc.User"
    }


    def "responseClass returns 'void' if method returns nothing"(){
        when:
        def endpoint = Endpoint.create(compiler.customerController())

        then:
        endpoint.methods[3].responseClass() == "void"
    }
}
