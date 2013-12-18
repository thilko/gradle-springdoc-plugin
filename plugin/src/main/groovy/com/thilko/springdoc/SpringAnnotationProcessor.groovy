package com.thilko.springdoc

import org.springframework.stereotype.Controller

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

class SpringAnnotationProcessor extends AbstractProcessor {

    @Override
    boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return true
        }

        def classes = annotations.collect { roundEnv.getElementsAnnotatedWith(it) }.flatten()
        def doc = SpringDoc.withClasses(classes)
        doc.generate(outfile())

        return true
    }

    String outfile() {
        return processingEnv.options["outfile"]
    }

    @Override
    Set<String> getSupportedAnnotationTypes() {
        return [Controller.class.canonicalName]
    }

    @Override
    SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    Set<String> getSupportedOptions() {
        return ["outfile"] as Set<String>
    }
}


