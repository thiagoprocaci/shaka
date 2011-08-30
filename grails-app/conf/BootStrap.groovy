class BootStrap {

    def applicationSettings

    def init = { servletContext ->
        applicationSettings.init()
    }
    def destroy = {
    }
}
