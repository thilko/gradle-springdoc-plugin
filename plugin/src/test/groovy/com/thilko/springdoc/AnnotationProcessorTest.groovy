package com.thilko.springdoc

import spock.lang.Specification

class AnnotationProcessorTest extends Specification {

    def "parser can be initialized with classes"() {
        given:
        def compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();

        when:
        compiler.call();

        then:
        !compiler.hasDiagnostics()
        new File("index.html").exists()
    }

    def "html doc contains all classes"() {
        given:
        def compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();

        when:
        compiler.call();

        then:
        !compiler.hasDiagnostics()
        indexHtml().contains("CustomerController")
        indexHtml().contains("StatisticsController")
    }

    def "html doc contains the operations"() {
        given:
        def compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();

        when:
        compiler.call();

        then:
        !compiler.hasDiagnostics()
        indexHtml().contains("userPerMonth")
        indexHtml().contains("askMe")
    }

    def "html doc does not contain the constructor method"() {
        given:
        def compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();

        when:
        compiler.call();

        then:
        !indexHtml().contains("&lt;init&gt;")
    }

    def "html doc contains the HTTP method of operations"() {
        given:
        def compiler = TestCompiler.javaCompiler()
        compiler.withTestSources();

        when:
        compiler.call();

        then:
        indexHtml().contains("GET")
    }

    private static def indexHtml() {
        new File("index.html").getText()
    }

}
