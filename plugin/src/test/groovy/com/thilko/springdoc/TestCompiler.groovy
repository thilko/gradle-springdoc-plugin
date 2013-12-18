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
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString() + "/plugin/src/test/java";

        def options = ["-proc:only", "-Aoutfile=index.html", "-processor", "com.thilko.springdoc.SpringAnnotationProcessor"]
        def fileObjects = fileManager.getJavaFileObjects(
                format("%s/com/thilko/springdoc/CustomerController.java", path),
                format("%s/com/thilko/springdoc/StatisticsController.java", path));

        task = (JavacTask) compiler.getTask(null, fileManager, collector, options, null, fileObjects)
        task.parse()
        task.analyze()
    }

    public withTestSources() {
        sources = []
    }

    public hasErrors() {
        println(collector.diagnostics)
        !collector.diagnostics.isEmpty()

    }

    def customerController() {
        task.elements.getTypeElement("com.thilko.springdoc.CustomerController")
    }
}
