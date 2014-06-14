package com.thilko.springdoc.model

import javax.lang.model.element.ExecutableElement
import javax.lang.model.util.ElementScanner7

class ResourceGroupVisitor extends ElementScanner7<ResourceGroup, ResourceGroup> {
    @Override
    ResourceGroup visitExecutable(ExecutableElement e, ResourceGroup p) {
        p.applyExecutable(e)

        return p
    }
}
