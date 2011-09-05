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
                , message: attrs.message])
    }

    /**
     * tag que mostra o historico das mensagens de um topico
     */
    def historicoMensagens = { attrs ->
        out << render(template:"/templates/historicoMensagemTemplate",
            model:[mensagemList:attrs.mensagemList, diretorioImagem:attrs.diretorioImagem,
                   mensagemTotal:attrs.mensagemTotal, topicoInstance:attrs.topicoInstance] )
    }
}
