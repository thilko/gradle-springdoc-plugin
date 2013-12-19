package com.thilko.springdoc

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile


class SpringDocPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task(type: JavaCompile, "generateSpringDoc") {
            project.afterEvaluate {

            source = it.sourceSets.main.java
            classpath = it.sourceSets.main.output + project.configurations.compile

            options.compilerArgs = [
                    "-proc:only",
                    "-processor", "com.thilko.springdoc.SpringAnnotationProcessor"
            ]
            // specify output of generated code
            destinationDir = it.sourceSets.generated.java.srcDirs.iterator().next()
            }
        }
    }
}
