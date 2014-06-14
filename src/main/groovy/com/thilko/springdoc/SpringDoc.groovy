package com.thilko.springdoc

import com.thilko.springdoc.model.ResourceGroup
import groovy.xml.MarkupBuilder

import javax.lang.model.element.TypeElement

class SpringDoc {

    public static withClasses(classes) {
        return new SpringDoc(classes)
    }

    TypeElement[] classes = []

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
                            div(class: "panel-group", id: "api-resource") {
                                resources().eachWithIndex { resource, idx ->
                                    def resourceIdx = "api-resource$idx"
                                    div(class: "panel panel-default") {
                                        div(class: "panel-heading") {
                                            h2(class: "panel-title") {
                                                a("data-toggle": "collapse", "data-parent": "#api-resource", href: "#$resourceIdx", resource.name())
                                            }
                                        }
                                        div(id: "$resourceIdx", class: "panel-collapse collapse") {
                                            ul(class: "list-unstyled") {
                                                resource.methods().each { method ->
                                                    li {
                                                        a("href": "#${resourceIdx}_${method.name()}", "data-toggle": "tab", "${method.name()}")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        div(class: "col-md-8 content") {
                            div(class: "tab-content") {
                                resources().eachWithIndex { resource, idx ->
                                    resource.methods().each { apiMethod ->
                                        def apiMethodContent = "api-resource${idx}_${apiMethod.name()}"
                                        div(class: "tab-pane", id: "$apiMethodContent") {
                                            div(class: "well") {
                                                h4 {
                                                    span(class: apiMethod.httpMethodCssClass(), apiMethod.httpMethod())
                                                    span apiMethod.path()
                                                }
                                            }
                                            ul(class: "nav nav-tabs") {
                                                li(class: "active") { a("href": "#summary$apiMethodContent", "data-toggle": "tab", "Summary") }
                                                if (apiMethod.hasQueryParameter()) {
                                                    li() { a("href": "#queryparam$apiMethodContent", "data-toggle": "tab", "Query parameter") }
                                                }

                                                li() { a("href": "#impl$apiMethodContent", "data-toggle": "tab", "Implementation") }
                                            }
                                            div(class: "tab-content") {
                                                div(class: "tab-pane active", id: "summary$apiMethodContent") {
                                                    div(class: "well") {
                                                        h4 "Url"
                                                        span apiMethod.url()
                                                        if (apiMethod.hasRequestBody()) {
                                                            h4 "Request"
                                                            pre apiMethod.requestBody()
                                                        }

                                                        if (apiMethod.hasResponse()) {
                                                            h4 "Response"
                                                            pre apiMethod.response().asJson()
                                                        }
                                                    }

                                                }

                                                if (apiMethod.hasQueryParameter()) {
                                                    div(class: "tab-pane", id: "queryparam$apiMethodContent") {
                                                        table(class: "table table-bordered table-striped") {
                                                            caption "Method Parameters"
                                                            thead {
                                                                th "Name"
                                                                th "Required"
                                                                th "Default"
                                                            }
                                                            tbody {
                                                                apiMethod.queryParameter().each { param ->
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

                                                div(class: "tab-pane", id: "impl$apiMethodContent") {
                                                    table(class: "table table-hover") {
                                                        tbody {
                                                            tr {
                                                                td "Method name"
                                                                td apiMethod.name()
                                                            }
                                                            tr {
                                                                td "Response class"
                                                                td apiMethod.response().className()
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
                }


                script(src: "http://code.jquery.com/jquery-1.10.1.min.js", "")
                script(src: "http://code.jquery.com/jquery-migrate-1.2.1.min.js", "")
                script(src: "http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js", "")
            }
        }
    }

    private resources() {
        return classes.collect { ResourceGroup.create(it) }
    }

    private static builder(String outFile) {
        return new MarkupBuilder(new PrintWriter(new File(outFile)))
    }

}
