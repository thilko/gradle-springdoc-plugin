package com.thilko.springdoc

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
            }
            body {
                h1 "Api Documentation"
                endpoints().each { endpoint ->
                    h2 endpoint.className()
                    table {
                        tr {
                            th "Operation"
                            th "Method"
                        }

                        endpoint.operations().each { op ->
                            tr {
                                th op.simpleName
                                th "BLA"
                            }
                        }
                    }
                }

                script(src: "http://netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js")
            }
        }
    }

    private endpoints() {
        def bla = classes.collect { Endpoint.create(it) }

        return bla
    }

    private builder() {
        return new MarkupBuilder(new PrintWriter(new File("index.html")))
    }

}
