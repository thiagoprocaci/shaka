package com.shaka


class DebateController {

     static allowedMethods = [saveTopico: "POST", saveResposta: "POST"]

     def debateService

    def index = {
        redirect(action: "createTopico", params: params)
    }

    def createTopico = {
        def topico = new Topico()
        def mensagem = new Mensagem()
        return [topicoInstance:topico, mensagemInstance:mensagem]
    }

    def saveTopico = {
        def topicoInstance = new Topico(params)
        def mensagemInstance = new Mensagem(params)
        if (debateService.save(topicoInstance, mensagemInstance)) {
            flash.message = "${message(code: 'salvoSucesso')}"
            redirect(action: "showTopico", id: topicoInstance.id)
        } else {
            render(view: "createTopico", model: [topicoInstance: topicoInstance, mensagemInstance:mensagemInstance])
        }
    }

    def showTopico = {
        def topicoInstance = Topico.get(params.id)
        return [topicoInstance: topicoInstance]
    }

    def responderTopico = {
        def topicoInstance = Topico.get(params.id)
        def mensagemInstance = new Mensagem()
        return [topicoInstance:topicoInstance, mensagemInstance:mensagemInstance]
    }

    def saveResposta = {
        def topicoInstance = Topico.get(params.id)
        def mensagemInstance = new Mensagem(params)
        if (debateService.save(topicoInstance, mensagemInstance)) {
            flash.message = "${message(code: 'salvoSucesso')}"
            redirect(action: "showTopico", id: topicoInstance.id)
        } else {
            render(view: "responderTopico", model: [topicoInstance: topicoInstance, mensagemInstance:mensagemInstance])
        }
    }

}
