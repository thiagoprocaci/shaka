import org.springframework.aop.scope.ScopedProxyFactoryBean

// Place your Spring DSL code here
beans = {

    applicationSettings(startup.ApplicationSettings) {

    }

    usuarioService(com.shaka.business.UsuarioService) {
        springSecurityService = ref("springSecurityService")
        imageService = ref("imageService")
        diretorioImagem = "web-app/images/uploads/"
        diretorioImagemRelativo = "images/uploads/"
        }

    flowManagerServiceProxy(ScopedProxyFactoryBean) {
        targetBeanName = 'flowManagerService'
        proxyTargetClass = true
    }

}
