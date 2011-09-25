package com.shaka.filter

/**
 *
 * Filtro da aplicacao
 *
 */
class ApplicationFilters {

    def flowManagerServiceProxy
	def authHandlerService

    def filters = {
        all(controller:'*', action:'*') {
            before = {
                // deixamos o flow manager saber o que esta ocorrendo...
               flowManagerServiceProxy.processRequest(request)
			   authHandlerService.processRequest(request, response)
            }
            after = {

            }
            afterView = {

            }
        }
    }

}
