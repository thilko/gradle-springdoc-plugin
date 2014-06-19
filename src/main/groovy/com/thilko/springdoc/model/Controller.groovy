package com.thilko.springdoc.model

import org.springframework.web.bind.annotation.RequestMapping

import javax.lang.model.element.TypeElement

class Controller {

    private TypeElement resource

    private List<Resource> methods = []

    private Controller(def resource) {
        this.resource = resource
    }

    static def resourceGroupsFor(List<TypeElement> controllerAnnotations) {
        def allMethods = controllerAnnotations.collect { createController(it) }
                .collect { it.methods }
                .flatten()

        allMethods.groupBy { it.resourceName() }.collect {
            new ResourceGroup(resources: it.value, name: it.key.toString())
        }
    }

    static def resourceGroupsFor(TypeElement controllerAnnotation) {
        resourceGroupsFor([controllerAnnotation] as List)
    }

    private static def createController(TypeElement resource) {
        def doc = new Controller(resource)
        resource.accept(new ControllerVisitor(), doc)

        return doc
    }

    def applyExecutable(def executable) {
        if (!isConstructor(executable) && isApiMethod(executable)) {
            this.methods << createApiMethod(executable);
        }
    }

    private static isApiMethod(def executable) {
        executable.getAnnotation(RequestMapping) != null
    }

    private def createApiMethod(executable) {
        def resource = Resource.fromElement(executable)
        if (hasRequestMapping() && !hasRootPath()) {
            resource.applyPathPrefix(controllerPath())
        }

        resource
    }

    boolean hasRootPath() {
        return requestMappingAnnotation().value().first() == "/"
    }

    private def controllerPath() {
        requestMappingAnnotation().value().first()
    }

    def requestMappingAnnotation() {
        resource.getAnnotation(RequestMapping)
    }

    private def hasRequestMapping() {
        requestMappingAnnotation() != null
    }

    private static def isConstructor(executable) {
        executable.simpleName.contentEquals("<init>")
    }
}
