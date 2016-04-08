# groovy-assets-directory-example
Demo repository with an example creating a directory of microservices accesible
through the Groovy shell. The directory makes use of
[Groovy SourceGrape](https://github.com/andresviedma/sourcegrape) and
[Groovy DynApiClient](https://github.com/andresviedma/dynapiclient-groovy).

Usage:

1. Copy this in your local ~/.groovy/groovysh.profile
2. Start the groovysh
3. Call the registered demo services!

```groovy
println "Loading assets directory"

// Load SourceGrape artifact from the Maven repo
@GrabResolver(name='bintray-andresviedma-maven', root='http://dl.bintray.com/andresviedma/maven')
@Grab('com.sourcegrape:sourcegrape')
import com.sourcegrape.*

// Load the directory
@SourceGrab('https://github.com/andresviedma/groovy-assets-directory-example.git')
directoryLoaded = true
bootstrap = new directoryutils.AssetsDirectoryBootstrap()
binding.variables << bootstrap.getAssetsDirectory(this)
```
