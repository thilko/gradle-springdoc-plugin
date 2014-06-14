package com.thilko.springdoc

import org.springframework.stereotype.Controller

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.StandardLocation

class SpringAnnotationProcessor extends AbstractProcessor {

    @Override
    boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return true
        }
        def fileObject = processingEnv.filer.createResource(StandardLocation.SOURCE_OUTPUT, "", "springdoc/index.html", null)
        def classes = annotations.collect { roundEnv.getElementsAnnotatedWith(it) }.flatten()
        def doc = SpringDoc.withClasses(classes)
        doc.generate(fileObject.name)

        return true
    }

    @Override
    Set<String> getSupportedAnnotationTypes() {
        return [Controller.class.canonicalName]
    }

    @Override
    SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}


