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

	requestService(com.shaka.support.RequestService) { bean ->
		bean.scope = 'session'
	}

	applicationFilters(com.shaka.filter.ApplicationFilters) { bean ->
		bean.scope = 'request'
		requestService = ref("requestService")
	}

}
