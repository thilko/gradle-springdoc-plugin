package com.thilko.springdoc

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class SpringAnnotationProcessorTest extends Specification {
    def compiler
    def indexHtmlFile

    def setup() {
        compiler = TestCompiler.javaCompiler()
        compiler.withTestSources()
        indexHtmlFile = Paths.get("index.html")
    }

    def cleanup() {
        if (Files.exists(indexHtmlFile)){
            Files.delete(indexHtmlFile)
        }
    }

    def "parser can be initialized with classes"() {
        when:
        compiler.call()

        then:
        !compiler.hasErrors()
        Files.exists(indexHtmlFile)
    }

    def "html doc contains all classes"() {
        when:
        compiler.call()

        then:
        !compiler.hasErrors()
        indexHtml().contains("CustomerController")
        indexHtml().contains("StatisticsController")
    }

    def "html doc contains the operations"() {
        when:
        compiler.call()

        then:
        !compiler.hasErrors()
        indexHtml().contains("updateCustomer")
        indexHtml().contains("getCompletedInvoices")
    }

    def "html doc does not contain the constructor method"() {
        when:
        compiler.call()

        then:
        !indexHtml().contains("&ltinit&gt")
    }

    def "html doc contains the HTTP method of operations"() {
        when:
        compiler.call()

        then:
        indexHtml().contains("GET")
    }

    def "html doc contains the HTTP parameters of operations"() {
        when:
        startCompilation()

        then:
        indexHtml().contains("customerid")
    }

    def "html doc contains an example url"() {
        when:
        startCompilation()

        then:
        indexHtml().contains("http://example.com/customers/invoices/completed?amount=&amp;customerid=")
    }

    private static def indexHtml() {
        new File("index.html").getText()
    }

    private void startCompilation() {
        compiler.call()
    }
}
