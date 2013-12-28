package com.thilko.springdoc

import com.thilko.springdoc.model.Endpoint
import groovy.xml.MarkupBuilder

class SpringDoc {

    public static withClasses(classes) {
        return new SpringDoc(classes)
    }

    def classes = []

    private SpringDoc(classes) {
        this.classes = classes
    }

    public generate(outFile) {
        builder(outFile).html {
            head {
                title "Api documentation"
                link(href: "http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css", rel: "stylesheet", "")
                link(href: "springdoc.css", rel: "stylesheet", "")
            }
            body {
                div(class: "springdoc") {
                    div(class: "row") {
                        div(class: "col-md-3 sidebar") {
                            div(class: "panel-group", id: "api-endpoint") {
                                endpoints().eachWithIndex { endpoint, idx ->
                                    def endpointIdx = "api-endpoint$idx"
                                    div(class: "panel panel-default") {
                                        div(class: "panel-heading") {
                                            h2(class: "panel-title") {
                                                a("data-toggle": "collapse", "data-parent": "#api-endpoint", href: "#$endpointIdx", endpoint.name())
                                            }
                                        }
                                        div(id: "$endpointIdx", class: "panel-collapse collapse") {
                                            ul(class: "list-unstyled") {
                                                endpoint.methods().each { method ->
                                                    li {
                                                        a("href": "#${endpointIdx}_${method.name()}", "data-toggle": "tab", "${method.name()}")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        div(class: "col-md-8") {
                            div(class: "tab-content content") {
                                endpoints().eachWithIndex { endpoint, idx ->
                                    endpoint.methods().each { apiMethod ->
                                        def apiMethodContent = "api-endpoint${idx}_${apiMethod.name()}"
                                        div(class: "tab-pane", id: "$apiMethodContent") {
                                            div(class: "well") {
                                                h4 {
                                                    span(class: apiMethod.httpMethodCssClass(), apiMethod.httpMethod())
                                                    span apiMethod.path()
                                                }
                                            }
                                            table(class: "table table-hover"){
                                                tbody{
                                                    tr{
                                                        td "Method name"
                                                        td apiMethod.name()
                                                    }
                                                    tr{
                                                        td "Response class"
                                                        td apiMethod.responseClass()
                                                    }
                                                }
                                            }
                                            table(class: "table table-bordered table-striped") {
                                                caption "Method Parameters"
                                                thead {
                                                    th "Name"
                                                    th "Required"
                                                    th "Default"
                                                }
                                                tbody {
                                                    apiMethod.parameter().each { param ->
                                                        tr {
                                                            td param.name()
                                                            td param.required()
                                                            td param.defaultValue()
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                script(src: "http://code.jquery.com/jquery-1.10.1.min.js", "")
                script(src: "http://code.jquery.com/jquery-migrate-1.2.1.min.js", "")
                script(src: "http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js", "")
            }
        }
    }

    private endpoints() {
        return classes.collect { Endpoint.create(it) }
    }

    private builder(outFile) {
        return new MarkupBuilder(new PrintWriter(new File(outFile)))
    }

}
