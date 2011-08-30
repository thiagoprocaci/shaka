package com.shaka

/**
 *
 * Taglib para manipular um topico e suas respostas
 *
 */
class TopicoTagLib {

	def formularioSaveTopico = { attrs ->
		out << render(template:"/templates/saveTopicoTemplate",
			model:[topicoInstance:attrs.topicoInstance, mensagemInstance:attrs.mensagemInstance,
				 action:attrs.action,
				, message: attrs.message])
	}
}
