package com.thilko.springdoc.model

import javax.lang.model.element.ExecutableElement
import javax.lang.model.util.ElementScanner7

class EndpointVisitor extends ElementScanner7<Endpoint, Endpoint> {
    @Override
    Endpoint visitExecutable(ExecutableElement e, Endpoint p) {
        p.applyExecutable(e)

        return p
    }
}
