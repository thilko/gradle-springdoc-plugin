package com.thilko.springdoc

import spock.lang.Specification

class SpringAnnotationProcessorTest extends Specification {
    def compiler

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();
    }


    def "parser can be initialized with classes"() {
        when:
        compiler.call();

        then:
        !compiler.hasErrors()
        new File("index.html").exists()
    }

    def "html doc contains all classes"() {
        when:
        compiler.call();

        then:
        !compiler.hasErrors()
        indexHtml().contains("CustomerController")
        indexHtml().contains("StatisticsController")
    }

    def "html doc contains the operations"() {
        when:
        compiler.call();

        then:
        !compiler.hasErrors()
        indexHtml().contains("getCompletedInvoices")
        indexHtml().contains("createNewCustomer")
    }

    def "html doc does not contain the constructor method"() {
        when:
        compiler.call();

        then:
        !indexHtml().contains("&lt;init&gt;")
    }

    def "html doc contains the HTTP method of operations"() {
        when:
        compiler.call();

        then:
        indexHtml().contains("GET")
    }

    def "html doc contains the HTTP parameters of operations"() {
        when:
        compiler.call();

        then:
        indexHtml().contains("amount")
    }

    def "html doc contains an example url"() {
        when:
        compiler.call();

        then:
        indexHtml().contains("http://example.com/customers/invoices/completed?amount=&amp;customerid=")
    }

    private static def indexHtml() {
        new File("index.html").getText()
    }

}
