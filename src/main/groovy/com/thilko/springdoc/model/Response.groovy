package com.thilko.springdoc.model

import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

class Response {

    TypeMirror returnType

    static def fromReturnType(returnType) {
        new Response(returnType)
    }

    def className() {
        if (returnType.toString() =~ /.*ResponseEntity<.*?>$/) {
            return ((DeclaredType) returnType).typeArguments[0].toString()
        }

        returnType.toString()
    }

    def asJson() {
        if (returnType.kind == TypeKind.VOID) {
            return ""
        }

        if(returnType.toString() =~ /List<.*>$/){
            return "returned lists currently not supported in json output"
        }

        def domainClass = this.class.classLoader.loadClass(className())
        return ModelInstance.fromClass(domainClass).toJson()
    }

    private Response(returnType) {
        this.returnType = returnType
    }
}
