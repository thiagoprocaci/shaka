package com.shaka

class AvaliacaoMensagem {
    boolean positivo
    static belongsTo = [mensagem:Mensagem,usuario:Usuario]

    static constraints = {
    }

	/**
	 *  Retorna valor boolean que diz se um usuario avaliou
	 *  uma mensagem.
	 */
	def static avaliouMensagem = { mensagem,  username ->
		def criteria = AvaliacaoMensagem.createCriteria()
		def total = criteria.get {
		    eq('mensagem',mensagem)
			usuario{
				eq('username',username)
			}
			projections {
				countDistinct('id')
			}
		}
		return total > 0
	}
}
