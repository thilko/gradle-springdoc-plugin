package com.thilko.springdoc

import com.thilko.springdoc.model.Controller
import spock.lang.Specification


class ResponseTest extends Specification {

    def compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.compile();
    }

    def "responseClass returns 'String' if method returns a string"() {
        when:
        def resourceGroups = Controller.resourceGroupsFor(compiler.customerController())

        then:
        resourceGroups[0].resources[4].response().className() == "java.lang.String"
    }

    def "responseClass returns 'com.thilko.springdoc.User' if method returns a User"() {
        when:
        def resourceGroups = Controller.resourceGroupsFor(compiler.customerController())

        then:
        resourceGroups[0].resources[2].response().className() == "com.thilko.springdoc.User"
    }


    def "responseClass returns 'void' if method returns nothing"() {
        when:
        def resourceGroups = Controller.resourceGroupsFor(compiler.customerController())

        then:
        resourceGroups[0].resources[3].response().className() == "void"
    }

    def "responseAsJson for ResponseEntity<InformationResponse> returns json for InformationResponse"(){
        when:
        def resourceGroups = Controller.resourceGroupsFor(compiler.customerController())

        then:
        resourceGroups[2].resources[0].response().asJson() ==
                """{
    "valid": true
}"""
    }

    def "responseAsJson returns json"() {
        when:
        def resourceGroups = Controller.resourceGroupsFor(compiler.customerController())

        then:
        resourceGroups[0].resources[2].response().asJson() ==
                """{
    "firstName": null,
    "id": null,
    "lastName": null,
    "dateOfBirth": null
}"""
    }

    def "responseAsJson returns '' when response is void"() {
        when:
        def resourceGroups = Controller.resourceGroupsFor(compiler.customerController())

        then:
        resourceGroups[0].resources[3].response().asJson() == ""
    }
}
