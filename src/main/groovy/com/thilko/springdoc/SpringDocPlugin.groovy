package com.thilko.springdoc

import org.gradle.api.Plugin
import org.gradle.api.Project


class SpringDocPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("generateSpringDoc") {
            source = sourceSets.main.java
            classpath = sourceSets.main.output + configurations.compile

            options.compilerArgs = [
                    "-proc:only",
                    "-processor", "com.thilko.springdoc.SpringAnnotationProcessor"
            ]
            // specify output of generated code
            destinationDir = sourceSets.generated.java.srcDirs.iterator().next()
        }
    }
}
