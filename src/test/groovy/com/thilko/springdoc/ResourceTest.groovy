package com.thilko.springdoc

import com.thilko.springdoc.model.Controller
import com.thilko.springdoc.model.Resource
import spock.lang.Specification


class ResourceTest extends Specification {

    TestCompiler compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.compile();
    }

    def "httpMethod returns GET if annotated with HttpMethod.GET"() {
        when:
        def resources = customerResources()

        then:
        resources[0].httpMethod() == "GET"
    }

    private List<Resource> customerResources() {
        Controller.resourceGroupsFor(compiler.customerController())[0].resources
    }

    def "httpMethod returns POST if annotated with HttpMethod.POST"() {
        when:
        def resources = customerResources()

        then:
        resources[1].httpMethod() == "POST"
    }

    def "httpMethodCssClass returns 'label label-primary' for GET"() {
        when:
        def resources = customerResources()

        then:
        resources[0].httpMethodCssClass() == "label label-primary"
    }

    def "httpMethodCssClass returns 'label label-success' for POST"() {
        when:
        def resources = customerResources()

        then:
        resources[1].httpMethodCssClass() == "label label-success"
    }

    def "httpMethodCssClass returns 'label label-info' for PUT"() {
        when:
        def resources = customerResources()

        then:
        resources[2].httpMethodCssClass() == "label label-info"
    }

    def "httpMethodCssClass returns 'label label-danger' for DELETE"() {
        when:
        def resources = customerResources()

        then:
        resources[3].httpMethodCssClass() == "label label-danger"
    }


    def "name returns correct name of the annotated method"() {
        when:
        def resources = customerResources()

        then:
        resources[0].implementationName().contentEquals("getCompletedInvoices")
    }

    def "path returns / if no value RequestMapping is set"() {
        when:
        def resources = Controller.resourceGroupsFor(compiler.customerController())[1].resources

        then:
        resources[0].path() == "/"
    }

    def "path returns correct value if value of RequestMapping is set"() {
        when:
        def resources = customerResources()

        then:
        resources[1].path() == "/customers"
    }

    def "path takes contains prefix from controller´s class mapping"() {
        when:
        def resources = Controller.resourceGroupsFor(compiler.metricsController())[0].resources

        then:
        resources[0].path() == "/metrics/spo2"
    }

    def "parameter returns all params that are annotated on the mpi method"() {
        when:
        def resources = customerResources()

        then:
        resources[0].queryParameter().size() == 2
    }

    def "parameter returns only parameter annotated with @RequestParam"() {
        when:
        def resources = Controller.resourceGroupsFor(compiler.customerController())[0].resources

        then:
        resources[5].queryParameter().size() == 1
        resources[5].queryParameter()[0].name() == "test"
    }

    def "response returns a response object representing the returning data"() {
        when:
        def resources = customerResources()

        then:
        resources[0].response() != null
    }

    def "hasResponse returns true if method returns a response"() {
        when:
        def resources = customerResources()

        then:
        resources[2].hasResponse()
    }

    def "hasResponse returns false if method´s return type is void"() {
        when:
        def resources = customerResources()

        then:
        !resources[0].hasResponse()
    }

    def "url returns an example url"() {
        when:
        def resources = customerResources()

        then:
        resources[0].url() == "http://example.com/customers/invoices/completed?amount=&customerid="
    }


    def "hasRequestBody returns false if method has no @RequestBody annotation"() {
        when:
        def resources = customerResources()

        then:
        !resources[0].hasRequestBody()
    }

    def "hasRequestBody returns true if method has a @RequestBody annotation"() {
        when:
        def resources = Controller.resourceGroupsFor(compiler.customerController())[1].resources

        then:
        resources[1].hasRequestBody()
    }

    def "hasQueryParameter returns false if method has no query parameter"() {
        when:
        def resources = customerResources()

        then:
        !resources[1].hasQueryParameter()
    }

    def "requestBody returns '' if method has no @RequestBody annotation"() {
        when:
        def resources = customerResources()

        then:
        resources[0].requestBody() == """"""
    }

    def "requestBody returns the @RequestBody as json string"() {
        when:
        def resources = Controller.resourceGroupsFor(compiler.customerController())[1].resources

        then:
        resources[1].requestBody() ==
                """{
    "firstName": null,
    "id": null,
    "lastName": null,
    "dateOfBirth": null
}"""
    }

    def "path returns /foo if class request mapping is '/' and method mapping is '/foo"(){
        when:
        def resourceGroups = Controller.resourceGroupsFor([compiler.deviceController()] as List<DeviceController>)

        then:
        resourceGroups.first().resources[0].path() == "/foo"
    }

}
