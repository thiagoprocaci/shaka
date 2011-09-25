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
	def processRequest = {	request ->
		// somente http request...
		if(request instanceof HttpServletRequest){
			// nao nos interessa ajax request
			if(isAjax(request) == false){
				if("POST".equals(request.getMethod())) {
					lastPostUrl =  request.forwardURI
				} else if ("GET".equals(request.getMethod())){
					lastGetUrl =  request.forwardURI
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

	private boolean isAjax(def request) {
		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
			return true
		}
		// assume-se que as requisicoes ajax contem a string "ajax" na url...
		// a primeira condicao nao eh suficiente para diferentes ambientes
		String url = request.forwardURI
		if(url.contains("ajax")){
			return true
		}
		return  false
	}

}
