package com.thilko.springdoc

import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.StandardJavaFileManager
import javax.tools.ToolProvider
import java.nio.charset.Charset


class TestCompiler {

    def compiler
    def collector
    private StandardJavaFileManager fileManager

    def sources;

    public static javaCompiler(){
        return new TestCompiler();
    }

    private TestCompiler(){
        collector = new DiagnosticCollector<JavaFileObject>();
        compiler = ToolProvider.systemJavaCompiler

        fileManager = compiler.getStandardFileManager(collector, Locale.GERMAN, Charset.defaultCharset())
    }

    public call(){
        def options = ["-proc:only", "-processor", "com.thilko.springdoc.SpringAnnotationProcessor"]
        def fileObjects = fileManager.getJavaFileObjects(
                "plugin/src/main/java/com/thilko/springdoc/CustomerController.java",
                "plugin/src/main/java/com/thilko/springdoc/StatisticsController.java")
        compiler.getTask(null, fileManager, collector, options , null, fileObjects).call();
    }

    public withTestSources(){
        sources = []
    }

    public hasDiagnostics(){
        !collector.diagnostics.isEmpty()
    }

}
