package com.thilko.springdoc

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile


class SpringDocPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.plugins.apply("java")

        project.dependencies {
            compile "com.thilko.spring:gradle-springdoc-plugin:0.1.SNAPSHOT"
            compile localGroovy()
        }

        project.repositories {
            maven {
                url ('https://oss.sonatype.org/content/groups/public')
            }
        }

        project.task(type: JavaCompile, "generateSpringDoc") {
            project.afterEvaluate {
                source = it.sourceSets.main.java
                classpath = it.sourceSets.main.output + project.configurations.compile

                options.compilerArgs = [
                        "-s", "${project.buildDir}",
                        "-proc:only",
                        "-processor", "com.thilko.springdoc.SpringAnnotationProcessor"
                ]

                destinationDir = it.sourceSets.main.java.srcDirs.iterator().next()
            }
        }
    }
}
