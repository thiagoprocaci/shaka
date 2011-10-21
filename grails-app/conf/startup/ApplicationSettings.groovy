package startup

import com.shaka.Forum
import com.shaka.Usuario

/**
 *  Configuracoes necessarias para iniciar
 *  uma aplicacao
 *
 */
class ApplicationSettings {

	def springSecurityService

    def init = {
         def forumNoticias = new Forum(nome:"Noticias",descricao:"Noticias gerais sobre o assunto tal. ")
         def forumCultura = new Forum(nome:"Cultura",descricao:"Espaco para falar sobre cultura")
         def forumReclamacoes = new Forum(nome:"Reclame!",descricao:"Espaco para reclamar")

         forumNoticias.save()
         forumCultura.save()
         forumReclamacoes.save()

		 def u = new Usuario(username : "thomas", email : "edison@teste.com", password: springSecurityService.encodePassword("senha"), nome:"Thomas Alva Edison")
		 u.save()

    }
}
