package com.thilko.springdoc

import com.sun.source.util.JavacTask

import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.StandardJavaFileManager
import javax.tools.ToolProvider
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths

import static java.lang.String.format

class TestCompiler {

    def compiler
    def collector
    private StandardJavaFileManager fileManager

    def sources
    def task

    public static javaCompiler() {
        return new TestCompiler()
    }

    private TestCompiler() {
        collector = new DiagnosticCollector<JavaFileObject>()
        compiler = ToolProvider.systemJavaCompiler

        fileManager = compiler.getStandardFileManager(collector, Locale.GERMAN, Charset.defaultCharset())
    }

    public call() {
        def options = ["-proc:only", "-processor", "com.thilko.springdoc.SpringAnnotationProcessor"]

        task = (JavacTask) compiler.getTask(null,
                                            fileManager,
                                            collector,
                                            options,
                                            ["com.thilko.springdoc.StatisticsController",
                                                    "com.thilko.springdoc.CustomerController"],
                                            null)
        task.parse()
        task.analyze()
    }

    public withTestSources() {
        sources = []
    }

    public hasErrors() {
        !collector.diagnostics.isEmpty()

    }

    def customerController() {
        task.elements.getTypeElement("com.thilko.springdoc.CustomerController")
    }

    def statisticsController() {
        task.elements.getTypeElement("com.thilko.springdoc.StatisticsController")
    }

    def metricsController() {
        task.elements.getTypeElement("com.thilko.springdoc.MetricsController")
    }
}
