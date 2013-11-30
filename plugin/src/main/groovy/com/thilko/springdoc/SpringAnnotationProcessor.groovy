package com.thilko.springdoc

import groovy.xml.MarkupBuilder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement


class SpringAnnotationProcessor extends AbstractProcessor{

    @Override
    boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if(roundEnv.processingOver()){
            return true
        }

        def classes = annotations.collect{roundEnv.getElementsAnnotatedWith(it)}.flatten()
        def doc = SpringDoc.withClasses(classes)
        doc.generate()

        return true
    }

    @Override
    Set<String> getSupportedAnnotationTypes() {
        return [Controller.class.canonicalName]
    }

    @Override
    SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }
}


