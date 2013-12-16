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

    public generate() {
        builder().html {
            head {
                title "Api documentation"
                link(href: "http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css", rel: "stylesheet")
                link(href: "mvcdoc.css", rel: "stylesheet")
            }
            body {
                h1 "Api Documentation"
                div(class: "panel-group", id: "accordion") {
                    endpoints().eachWithIndex { endpoint, idx ->
                        div(class: "panel panel-default") {
                            div(class: "panel-heading") {
                                h4(class: "panel-title") {
                                    a("data-toggle": "collapse", "data-parent": "#accordion", href: "#collapse$idx", endpoint.className())
                                }
                            }
                            div(id: "collapse$idx", class: "panel-collapse collapse") {
                                div(class: "panel-body") {
                                    table(class: "endpoint table table-striped") {
                                        thead {
                                            tr {
                                                th "Operation"
                                                th "Method"
                                            }
                                        }
                                        tbody {
                                            endpoint.operations().each { op ->
                                                tr {
                                                    td op.name()
                                                    td {
                                                        span(class: op.httpMethodCssClass(), op.httpMethod())
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
                script(src: "http://code.jquery.com/jquery-1.10.1.min.js", async: "true", "")
                script(src: "http://code.jquery.com/jquery-migrate-1.2.1.min.js", async: "true", "")
                script(src: "http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js", async: "true", "")
            }
        }
    }

    private endpoints() {
        return classes.collect { Endpoint.create(it) }
    }

    private builder() {
        return new MarkupBuilder(new PrintWriter(new File("index.html")))
    }

}
