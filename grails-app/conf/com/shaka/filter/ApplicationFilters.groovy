package com.shaka.filter

/**
 *
 * Filtro da aplicacao
 *
 */
class ApplicationFilters {

	def requestService

    def filters = {
        all(controller:'*', action:'*') {
            before = {
              if("POST".equals(request.getMethod())) {
				  requestService.lastPostUrl = request.getRequestURL()
			  } else if ("GET".equals(request.getMethod())){
			  	  requestService.lastGetUrl = request.getRequestURL()
			  }
            }
            after = {

            }
            afterView = {

            }
        }
    }

}
