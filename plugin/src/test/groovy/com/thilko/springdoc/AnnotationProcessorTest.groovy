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

    private def indexHtml() {
        new File("index.html").getText()
    }

}
