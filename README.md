gradle-springdoc-plugin
=======================

Generate html documentation from spring annotations. Project is in an early development stage.

The plugin contains an annotation processor wich is called with an extra task. You java sources are
compiled and html document with twitter bootstrap is generated.

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



