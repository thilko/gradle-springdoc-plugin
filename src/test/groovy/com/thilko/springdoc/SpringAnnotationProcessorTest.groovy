package com.thilko.springdoc

import groovy.io.FileType
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

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
        indexHtml().contains("userPerMonth")
        indexHtml().contains("askMe")
    }

    def "html doc does not contain the constructor method"() {
        when:
        compiler.call();

        then:
        !indexHtml().contains("&lt;init&gt;")
        true
    }

    def "html doc contains the HTTP method of operations"() {
        when:
        compiler.call();

        then:
        indexHtml().contains("GET")
        true
    }

    def "html doc contains the HTTP parameters of operations"() {
        when:
        compiler.call();

        then:
        indexHtml().contains("question")
        true
    }

    def "html doc contains an example url"() {
        when:
        compiler.call();

        then:
        indexHtml().contains("http://example.com/ask?question=&amp;priority=")
        true
    }

    private static def indexHtml() {
        if(!Files.exists(Paths.get("index.html"))){
            throw new IllegalStateException("index.html does not exists!")
        }

        new File("index.html").getText()
    }

}
