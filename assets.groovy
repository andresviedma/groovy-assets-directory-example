import dynapiclient.rest.*
import dynapiclient.jsonrpc.*
import dynapiclient.auth.*

// *** Keys intended to be defined in local ~/.groovy/assets_secret.groovy
if (auth.marvel.isEmpty()) {
    auth {
        marvel {
            publicKey = '<undefined>'
            privateKey = '<undefined>'
        }
    }
}

rest {
    jsonplaceholder = new RestDynClient(
        base: 'http://jsonplaceholder.typicode.com/')

    marvel = new RestDynClient(
        base: 'http://gateway.marvel.com/',
        path: '/v1/public',
        paramsHandler: this.&marvelAuthenticate,
        metaLoader: { new OpenApiDoc(it) },
        exceptionOnError: false
        )
}

jsonrpc {
    kanboard = new JsonRpcClient(
        base: 'http://demo.kanboard.net/jsonrpc.php',
        clientHandler: { it.auth.basic 'demo', 'demo123' }
        )

    random = new JsonRpcClient(
        base: 'https://api.random.org/json-rpc/1/invoke',
        paramsHandler: { (it instanceof Map? it : [:]) + [apiKey: auth.random.apiKey] }
        )
}

void marvelAuthenticate(Map callParams, String method) {
    def ts = System.currentTimeMillis().toString()
    def hash = EncryptionUtils.md5(ts + auth.marvel.privateKey + auth.marvel.publicKey)
    callParams.query += [apikey: auth.marvel.publicKey, hash: hash, ts: ts]
}
