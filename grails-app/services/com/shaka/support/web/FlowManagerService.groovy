package com.shaka.support.web

import javax.servlet.http.HttpServletRequest

/**
 *
 * Classe de sessao de servicos http.
 * Responsavel por armazenar a ultima url get e post do
 *
 */
class FlowManagerService {
    static scope = 'session'
    static transactional = false

    String lastGetUrl
    String lastPostUrl

    /**
     * Processa a url vinda do request
     */
    def processRequest = { request ->
        if(request instanceof HttpServletRequest){
			// nao nos interessa ajax request
			if(!'XMLHttpRequest'.equals(request.getHeader('X-Requested-With'))){
				if("POST".equals(request.getMethod())) {
					lastPostUrl = request.getRequestURL()
				} else if ("GET".equals(request.getMethod())){
					lastGetUrl = request.getRequestURL()
				}
			}
        }
    }

    /**
     * Retorna ultima url get
     */
    def getLastGetUrl = {
        return lastGetUrl
    }

}
