gradle-springdoc-plugin
=======================

Dry out your rest api documentation and generate html from your spring mvc annotations.

To be used as gradle plugin. The plugin uses an annotation processor to parse the AST at compile time. I use
twitter bootstrap for the generated html page.

Usage
=======================

Include the plugin in your gradle script like this:

```
buildscript {
    repositories {
        maven {
            url uri('https://oss.sonatype.org/content/groups/public')
        }
        maven {
            url uri('http://repo1.maven.org/maven2')
        }
    }
    dependencies {
        classpath "com.thilko.spring:gradle-springdoc-plugin:0.1.SNAPSHOT"
    }
}
apply plugin: 'springdoc'
```

You got a build task ```generateSpringDoc``` which produces HTML documentation in ```buildDir```.


Not yet implemented / TODOs
=======================
- [ ] Response refactoring
- [ ] rename project to gradle-springdoc-plugin
- [ ] Support all spring mvc annotations
- [ ] Display example requests/responses as JSON
- [ ] Send example request to a remote endpoint
