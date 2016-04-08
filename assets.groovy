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
        exceptionOnError: false,
        logCalls: true
        )
}

jsonrpc {
    kanboard = new JsonRpcClient(
        base: 'http://demo.kanboard.net/jsonrpc.php',
        clientHandler: { it.auth.basic 'demo', 'demo123' }
        )
}

void marvelAuthenticate(Map callParams, String method) {
    def ts = System.currentTimeMillis().toString()
    def hash = EncryptionUtils.md5(ts + auth.marvel.privateKey + auth.marvel.publicKey)
    callParams.query += [apikey: auth.marvel.publicKey, hash: hash, ts: ts]
}
