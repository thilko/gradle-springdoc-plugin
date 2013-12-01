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
}
