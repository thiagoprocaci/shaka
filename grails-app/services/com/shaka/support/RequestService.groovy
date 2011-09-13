package com.shaka.support

/**
 *
 * Classe de sessao de servicos http.
 * Responsavel por armazenar a ultima url get e post do
 *
 */
class RequestService {

    static transactional = false

	String lastGetUrl
	String lastPostUrl

}
