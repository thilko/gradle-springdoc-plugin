package com.thilko.springdoc

import com.thilko.springdoc.model.Resource
import spock.lang.Specification


class ParameterTest extends Specification {

    def customerController

    def setup() {
        def compiler = TestCompiler.javaCompiler()
        compiler.compile();

        customerController = compiler.customerController()
    }

    def "name() returns 'question' if annotation 'value' is 'amount'"(){
        when:
        def parameter = Resource.create(customerController).methods[0].queryParameter("amount")

        then:
        parameter.name() == "amount"
    }

    def "defaultValue() returns 'How much is the fish'"(){
        when:
        def parameter = Resource.create(customerController).methods[0].queryParameter("amount")

        then:
        parameter.defaultValue() == "200"
    }

    def "defaultValue() returns '' if not set"(){
        when:
        def parameter = Resource.create(customerController).methods[0].queryParameter("customerid")

        then:
        parameter.defaultValue() == ""
    }

    def "required() returns 'true' if required not set"(){
        when:
        def parameter = Resource.create(customerController).methods[0].queryParameter("amount")

        then:
        parameter.required()
    }

    def "required() returns 'false' if required set to false"(){
        when:
        def parameter = Resource.create(customerController).methods[2].queryParameter("name")

        then:
        !parameter.required()
    }

}
