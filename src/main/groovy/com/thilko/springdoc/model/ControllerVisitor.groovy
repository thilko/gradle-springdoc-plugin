package com.thilko.springdoc.model

import javax.lang.model.element.ExecutableElement
import javax.lang.model.util.ElementScanner7

class ControllerVisitor extends ElementScanner7<Controller, Controller> {
    @Override
    Controller visitExecutable(ExecutableElement e, Controller p) {
        p.applyExecutable(e)

        return p
    }
}
