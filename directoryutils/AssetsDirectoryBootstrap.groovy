package directoryutils

import com.sourcegrape.*

@SourceGrab('https://github.com/andresviedma/dynapiclient-groovy.git')
import dynapiclient.utils.*

class AssetsDirectoryBootstrap {
    static GROOVY_USER_HOME = new File(System.getProperty('user.home'), '.groovy')

    Map getAssetsDirectory(script) {

        //def groovyUserHome = new File(System.getProperty('user.home'), '.groovy')

        // Directory files
        def assetsDirectoryLoader = new AssetsDirectoryLoader()
        def variables = assetsDirectoryLoader.loadDirectoryFiles([
            new File(GROOVY_USER_HOME, 'assets_secret.groovy'),
            new File(GROOVY_USER_HOME,
                'sourcegrapes/git/https__github.com_andresviedma_groovy-assets-directory-example.git/assets.groovy'),
            new File(GROOVY_USER_HOME, 'assets_local.groovy')
            ])

        // Shell utilities
        def shellUtilitiesClass = ShellUtils.class
        variables['pretty'] = shellUtilitiesClass.&pretty
        variables['timed'] = shellUtilitiesClass.&timed

        return variables
    }
}
