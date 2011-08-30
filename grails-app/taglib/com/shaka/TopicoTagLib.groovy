package com.shaka

/**
 *
 * Taglib para manipular um topico e suas respostas
 *
 */
class TopicoTagLib {

    /**
     * tag que compoe um novo topico ou uma resposta
     */
    def formularioSaveTopico = { attrs ->
        out << render(template:"/templates/saveTopicoTemplate",
            model:[topicoInstance:attrs.topicoInstance, mensagemInstance:attrs.mensagemInstance,
                 saveAction:attrs.saveAction, visualizarAction:attrs.visualizarAction,
                 voltarAction:attrs.voltarAction,
                , message: attrs.message])
    }

    /**
     * tag que mostra o historico das mensagens de um topico
     */
    def historicoMensagens = { attrs ->
        out << render(template:"/templates/historicoMensagemTemplate",
            model:[mensagemList:attrs.mensagemList])
    }
}
