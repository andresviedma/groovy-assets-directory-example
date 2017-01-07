package directoryutils

class AssetsDirectoryLoader {

    Map loadDirectory(File file) {
        return loadDirectories([file])
    }

    Map loadDirectoryFiles(List<File> files) {
        def configText = ''
        for (def file : files) {
            if (file.exists()) {
                configText += file.getText()
            }
        }
        return new ConfigSlurper(classLoader: getClass().getClassLoader()).parse(configText)
    }
}
