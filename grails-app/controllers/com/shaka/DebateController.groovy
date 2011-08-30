package com.shaka


/**
 * Controller para manipular uma debate (topico + mensagens)
 */
class DebateController {

    //TODO paginacao na exibiacao das mensagens

    static allowedMethods = [saveTopico: "POST", saveResposta: "POST"]

    def debateService

    def index = {
        redirect(action: "createTopico", params: params)
    }

    /**
     *  Inicializa a criacao de um novo topico para debate
     */
    def createTopico = {
        def topico = new Topico()
        def mensagem = new Mensagem()
        return [topicoInstance:topico, mensagemInstance:mensagem, forumId:params.id]
    }

    /**
     * Salva um novo topico
     */
    def saveTopico = {
        def forum = Forum.get(params.forumId)
        def topicoInstance = new Topico(params)
        def mensagemInstance = new Mensagem(params)
        topicoInstance.forum = forum
        if (debateService.save(topicoInstance, mensagemInstance)) {
            flash.message = "${message(code: 'salvoSucesso')}"
            redirect(action: "showTopico", id: topicoInstance.id)
        } else {
            render(view: "createTopico", model: [topicoInstance: topicoInstance, mensagemInstance:mensagemInstance, forumId:params.forumId])
        }
    }

    /**
     * Mostra o topico com suas respostas
     */
    def showTopico = {
        def topicoInstance = Topico.get(params.id)
        return [topicoInstance: topicoInstance]
    }

    /**
     * Inicializa a criacao de uma nova resposta para o topico
     */
    def responderTopico = {
        def topicoInstance = Topico.get(params.id)
        def mensagemInstance = new Mensagem()
        return [topicoInstance:topicoInstance, mensagemInstance:mensagemInstance]
    }

    /**
     * Salva uma nova resposta
     */
    def saveResposta = {
        def topicoInstance = Topico.get(params.id)
        def mensagemInstance = new Mensagem()
        mensagemInstance.texto = params.texto
        if (debateService.save(topicoInstance, mensagemInstance)) {
            flash.message = "${message(code: 'salvoSucesso')}"
            redirect(action: "showTopico", id: topicoInstance.id)
        } else {
            render(view: "responderTopico", model: [topicoInstance: topicoInstance, mensagemInstance:mensagemInstance])
        }
    }

    /**
     * Visualizacao de uma resposta ainda nao salva
     */
    def visualizar = {
        def mensagemInstance = new Mensagem()
        mensagemInstance.texto = params.texto
        def topicoInstance = null
        if(params.id) {
            topicoInstance = Topico.get(params.id)
            render(view: "responderTopico", model: [topicoInstance: topicoInstance, mensagemInstance:mensagemInstance,visualizar:true])
        } else {
            topicoInstance = new Topico(params)
            render(view: "createTopico", model: [topicoInstance: topicoInstance, mensagemInstance:mensagemInstance,visualizar:true, forumId:params.forumId])
        }
    }

    /**
     * Mostra o forum
     */
    def showForum = {
        redirect(controller:"forum" , action: "detail", params: ['id':params.forumId])
    }
}
