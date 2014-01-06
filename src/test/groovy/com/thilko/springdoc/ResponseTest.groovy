package com.thilko.springdoc

import com.thilko.springdoc.model.Resource
import spock.lang.Specification


class ResponseTest extends Specification {

    def compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();
        compiler.call();
    }

    def "responseClass returns 'String' if method returns a string"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[4].response().className() == "java.lang.String"
    }

    def "responseClass returns 'com.thilko.springdoc.User' if method returns a User"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[2].response().className() == "com.thilko.springdoc.User"
    }


    def "responseClass returns 'void' if method returns nothing"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[3].response().className() == "void"
    }

    def "responseAsJson returns json"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[2].response().asJson() ==
                """{
    "firstName": null,
    "id": null,
    "lastName": null,
    "dateOfBirth": null
}"""

    }

    def "responseAsJson returns '' when response is void"() {
        when:
        def resource = Resource.create(compiler.customerController())

        then:
        resource.methods[3].response().asJson() == ""
    }
}
