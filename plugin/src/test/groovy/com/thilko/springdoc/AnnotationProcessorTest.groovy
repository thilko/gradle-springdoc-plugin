package com.thilko.springdoc

import spock.lang.Specification

import javax.tools.*
import java.nio.charset.Charset

class AnnotationProcessorTest extends Specification {
    private JavaCompiler compiler
    DiagnosticCollector<JavaFileObject> collector
    private StandardJavaFileManager fileManager

    def setup() {
        collector = new DiagnosticCollector<JavaFileObject>();
        compiler = ToolProvider.systemJavaCompiler

        fileManager = compiler.getStandardFileManager(collector, Locale.GERMAN, Charset.defaultCharset())

    }

    def "parser can be initialized with classes"() {
        given:
        def f = new File("plugin/src/main/java/com/thilko/springdoc/CustomerController.java")
        def options = ["-proc:only", "-processor", "com.thilko.springdoc.SpringAnnotationProcessor"]
        def t = compiler.getTask(null, fileManager, collector, options , null, fileManager.getJavaFileObjects(f))

        when:
        t.call()

        then:
        collector.getDiagnostics().isEmpty()
        new File("index.html").exists()
    }
}
