package com.thilko.springdoc.model


class Response {

    def returnType

    static def fromReturnType(returnType) {
        new Response(returnType)
    }

    private Response(returnType){
        this.returnType = returnType
    }


}
