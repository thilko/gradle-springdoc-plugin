package com.thilko.springdoc

import com.thilko.springdoc.model.Resource
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
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[0].httpMethod() == "GET"
    }

    def "httpMethod returns POST if annotated with HttpMethod.POST"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[1].httpMethod() == "POST"
    }

    def "httpMethodCssClass returns 'label label-primary' for GET"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[0].httpMethodCssClass() == "label label-primary"
    }

    def "httpMethodCssClass returns 'label label-success' for POST"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[1].httpMethodCssClass() == "label label-success"
    }

    def "httpMethodCssClass returns 'label label-info' for PUT"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[2].httpMethodCssClass() == "label label-info"
    }

    def "httpMethodCssClass returns 'label label-danger' for DELETE"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[3].httpMethodCssClass() == "label label-danger"
    }


    def "name returns correct name of the annotated method"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[0].name().contentEquals("getCompletedInvoices")
    }

    def "path returns / if no value RequestMapping is set"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[5].path() == "/"
    }

    def "path returns correct value if value of RequestMapping is set"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[1].path() == "/customers"
    }

    def "path takes contains prefix from controller´s class mapping"(){
        when:
        def resource = Resource.create(compiler.metricsController())

        then:
        resource.methods[0].path() == "/metrics/spo2"
    }

    def "parameter returns all params that are annotated on the mpi method"(){
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[0].queryParameter().size() == 2
    }

    def "parameter returns only parameter annotated with @RequestParam"(){
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[6].queryParameter().size() == 1
        resource.methods[6].queryParameter()[0].name() == "test"
    }

    def "response returns a response object representing the returning data"(){
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[0].response() != null
    }

    def "hasResponse returns true if method returns a response"(){
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[2].hasResponse()
    }

    def "hasResponse returns false if method´s return type is void"(){
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        !resource.methods[0].hasResponse()
    }

    def "url returns an example url"(){
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[0].url() == "http://example.com/customers/invoices/completed?amount=&customerid="
    }


    def "hasRequestBody returns false if method has no @RequestBody annotation"(){
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        !resource.methods[0].hasRequestBody()
    }

    def "hasRequestBody returns true if method has no @RequestBody annotation"(){
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[7].hasRequestBody()
    }

    def "hasQueryParameter returns false if method has no query parameter"(){
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        !resource.methods[1].hasQueryParameter()
    }

    def "requestBody returns '' if method has no @RequestBody annotation"(){
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[0].requestBody() == """"""
    }

    def "requestBody returns the @RequestBody as json string"(){
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[7].requestBody() ==
    """{
    "firstName": null,
    "id": null,
    "lastName": null,
    "dateOfBirth": null
}"""
    }
}
