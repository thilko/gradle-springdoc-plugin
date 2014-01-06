package com.thilko.springdoc.model

import javax.lang.model.element.ExecutableElement
import javax.lang.model.util.ElementScanner7

class ResourceVisitor extends ElementScanner7<Resource, Resource> {
    @Override
    Resource visitExecutable(ExecutableElement e, Resource p) {
        p.applyExecutable(e)

        return p
    }
}
