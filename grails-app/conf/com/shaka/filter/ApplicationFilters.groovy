package com.shaka.filter

/**
 *
 * Filtro da aplicacao
 *
 */
class ApplicationFilters {

    def flowManagerServiceProxy

    def filters = {
        all(controller:'*', action:'*') {
            before = {
                // deixamos o flow manager saber o que esta ocorrendo...
               flowManagerServiceProxy.processRequest(request)
            }
            after = {

            }
            afterView = {

            }
        }
    }

}
