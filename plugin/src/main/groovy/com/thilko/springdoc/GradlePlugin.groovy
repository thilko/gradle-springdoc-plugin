package com.thilko.springdoc

import org.gradle.api.Plugin
import org.gradle.api.Project


class GradlePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("springdoc") {
            println "hello from compile"


        }
    }
}
