# Groovy Microservices Directory example

Demo repository with an example creating a directory of microservices accesible
through the Groovy shell. The directory makes use of
[Groovy SourceGrape](https://github.com/andresviedma/sourcegrape) and
[Groovy DynApiClient](https://github.com/andresviedma/dynapiclient-groovy).

For this demo we have used public APIs instead of microservices, concretely
the [rest.marvel API](http://developer.rest.marvel.com/),
[Kanboard](https://kanboard.net/documentation/api-json-rpc) and
[JSONPlaceholder](http://jsonplaceholder.typicode.com/).


The whys and hows of this idea are explained in
['Operating Microservices with Groovy'](http://www.slideshare.net/andresviedma/operating-microservices-with-groovy).

## Usage:

1. Copy this in your local ~/.groovy/groovysh.profile

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

2. Start the groovysh
3. Call the registered demo services!
4. If you want to call also the rest.marvel API, copy this in ~/.groovy/assets_secret.groovy using your API Keys

```groovy
auth {
    rest.marvel {
        publicKey = '<your-rest.marvel-api-public-key>'
        privateKey = '<your-rest.marvel-api-private-key>'
    }
}
```

## Examples of demo services calls

```groovy
pretty jsonrpc.kanboard.getMyProjects()
pretty jsonrpc.kanboard.getAllTasks(project_id: 1, status_id: 1)

pretty rest.jsonplaceholder.posts."37".comments()
pretty rest.jsonplaceholder.posts."37"()
rest.jsonplaceholder.posts."37" = [title: 'Hi!', id: 37, userId: 4, body: 'Good morning.']
println rest.jsonplaceholder.posts << [title: 'Hi!', id: 3747634, userId: 4, body: 'Good morning.']
println rest.jsonplaceholder.posts.add([title: 'Hi2!', id: 3747635, userId: 4, body: 'Good morning again.'])
println rest.jsonplaceholder.posts."37".delete()

rest.marvel.characters
characters = rest.marvel.characters()
pretty characters.data.results.collect { "${it.id}: ${it.name}" }
warlock = characters.data.results.find { it.name == 'Adam Warlock' }
rest.marvel.characters."${warlock.id}".series
series =  rest.marvel.characters."${warlock.id}".series();
pretty series.data.results*.title
```
