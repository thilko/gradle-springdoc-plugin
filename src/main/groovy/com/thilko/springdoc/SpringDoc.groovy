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
                link(href: "http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css", rel: "stylesheet")
                link(href: "springdoc.css", rel: "stylesheet")
            }
            body {
                div(class: "panel-group", id: "accordion") {
                    endpoints().eachWithIndex { endpoint, idx ->
                        div(class: "panel panel-default") {
                            div(class: "panel-heading") {
                                h2(class: "panel-title") {
                                    a("data-toggle": "collapse", "data-parent": "#accordion", href: "#collapse$idx", endpoint.className())
                                }
                            }
                            div(id: "collapse$idx", class: "panel-collapse collapse") {
                                div(class: "panel-group", id: "accordion2") {
                                    endpoint.methods().eachWithIndex { apiMethod, methodIdx ->
                                        def apiMethodIdx = endpoint.className() + apiMethod.name()

                                        div(class: "panel panel-default") {
                                            div(class: "panel-heading") {
                                                h3(class: "panel-title") {
                                                    span(class: apiMethod.httpMethodCssClass(), apiMethod.httpMethod())
                                                    span {
                                                        a("data-toggle": "collapse", "data-parent": "#accordion2", href: "#collapse$apiMethodIdx", apiMethod.name())
                                                    }
                                                    span(apiMethod.path())
                                                }
                                            }
                                            div(id: "collapse$apiMethodIdx", class: "panel-collapse collapse") {
                                                ul(class: "nav nav-tabs") {
                                                    li(class: "active") {
                                                        a("href": "#documentation", "data-toggle": "tab", "Documentation")
                                                    }
                                                    li {
                                                        a("href": "#parameter", "data-toggle": "tab", "Parameter")
                                                    }
                                                    li {
                                                        a("href": "#request", "data-toggle": "tab", "Request")
                                                    }
                                                    li {
                                                        a("href": "#response", "data-toggle": "tab", "Response")
                                                    }
                                                }

                                                div(class: "tab-content") {
                                                    div(class: "tab-pane active", id: "documentation") {
                                                        p "Lorum ipsum"
                                                    }
                                                    div(class: "tab-pane", id: "parameter") {
                                                        table {
                                                            tbody {
                                                                apiMethod.parameter().each { param ->
                                                                    tr {
                                                                        td(param.name())
                                                                    }
                                                                }
                                                            }
                                                        }

                                                    }
                                                    div(class: "tab-pane", id: "request") {
                                                        p "Lorum ipsum"
                                                    }
                                                    div(class: "tab-pane", id: "response") {
                                                        p "Lorum ipsum"
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
