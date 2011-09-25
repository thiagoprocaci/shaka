package com.shaka.support.web.handler

/**
 *
 * Handler utilizado para nao permitir que um usuario
 * logado veja a pagina de cadastro de usuario.
 *
 */
class AuthHandlerService {

    static transactional = false
	def springSecurityService

    def processRequest = {	request, response ->
		String url = request.forwardURI
		if(url.endsWith("usuario/create") && springSecurityService.isLoggedIn()){
			response.sendRedirect(request.contextPath)
		}
    }
}
