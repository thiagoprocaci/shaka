package com.shaka

import org.springframework.util.StringUtils



/**
 * Controller para manipular uma debate (topico + mensagens)
 */
class DebateController {

    static allowedMethods = [saveTopico: "POST", saveResposta: "POST"]

    def debateService
    def usuarioService

    def index = {
        redirect(action: "createTopico", params: params)
    }

    /**
     *  Inicializa a criacao de um novo topico para debate
     */
    def createTopico = {
        def topico = new Topico()
        def mensagem = new Mensagem()
        // TODO verificar parametros
        return [topicoInstance:topico, mensagemInstance:mensagem, forumId:params.id]
    }

    /**
     * Salva um novo topico
     */
    def saveTopico = {
        def forum = Forum.get(params.forumId)
        if(forum){
            def topicoInstance = new Topico(params)
            def mensagemInstance = new Mensagem(params)
            topicoInstance.forum = forum
            if (debateService.save(topicoInstance, mensagemInstance)) {
                flash.message = "${message(code: 'salvoSucesso')}"
                redirect(action: "showTopico", id: topicoInstance.id)
            } else {
                render(view: "createTopico", model: [topicoInstance: topicoInstance, mensagemInstance:mensagemInstance, forumId:params.forumId])
            }
        } else {
            redirect(uri:"/")
        }
    }

    /**
     *  Prepara exibicao do topico
     */
    def showTopico = {
        debateService.visitarTopico(params.id)
        // redirect para evitar que a visita ao topico seja contado mais de uma vez
        redirect(action:"listTopico", params:params)
    }

    /**
     * Lista o topico com suas mensagens
     */
    def listTopico = {
        def topicoInstance = Topico.get(params.id)
        if(topicoInstance){
            params.max = Math.min(params.max ? params.int('max') : 10, 100)
            params.offset = (params.offset ? params.int('offset'): 0);
            def mensagemList = Mensagem.findAllByTopico(topicoInstance,[max: params.max, offset: params.offset , sort:"dateCreated", order:"asc"])
            render(view: "showTopico", model:[topicoInstance: topicoInstance,
                                              mensagemList:mensagemList,
                                              diretorioImagem : usuarioService.diretorioImagemRelativo,
                                              mensagemTotal:Mensagem.countByTopico(topicoInstance)])
        } else {
            redirect(uri:"/")
        }
    }

    /**
     * Inicializa a criacao de uma nova resposta para o topico
     */
    def responderTopico = {
        def topicoInstance = Topico.get(params.id)
        if(topicoInstance){
            def mensagemInstance = new Mensagem()
            def mensagemList = Mensagem.findAllByTopico(topicoInstance,[max: 10 , sort:"dateCreated", order:"desc"])
            return [topicoInstance:topicoInstance, mensagemInstance:mensagemInstance, diretorioImagem : usuarioService.diretorioImagemRelativo, mensagemList:mensagemList]
        } else {
            redirect(uri:"/")
        }
    }

    /**
     * Salva uma nova resposta
     */
    def saveResposta = {
        def topicoInstance = Topico.get(params.id)
        if(topicoInstance) {
            def mensagemInstance = new Mensagem()
            mensagemInstance.texto = params.texto
            if (debateService.save(topicoInstance, mensagemInstance)) {
                flash.message = "${message(code: 'salvoSucesso')}"
                redirect(action: "showTopico", id: topicoInstance.id)
            } else {
                def mensagemList = Mensagem.findAllByTopico(topicoInstance,[max: 10 , sort:"dateCreated", order:"desc"])
                render(view: "responderTopico", model: [topicoInstance: topicoInstance, mensagemInstance:mensagemInstance, mensagemList:mensagemList])
            }
        } else {
            redirect(uri:"/")
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
            if(topicoInstance){
                def mensagemList = Mensagem.findAllByTopico(topicoInstance,[max: 10 , sort:"dateCreated", order:"desc"])
                render(view: "responderTopico", model: [topicoInstance: topicoInstance, mensagemInstance:mensagemInstance,visualizar:true, diretorioImagem : usuarioService.diretorioImagemRelativo, mensagemList:mensagemList])
            } else {
                redirect(uri:"/")
            }
        } else {
            topicoInstance = new Topico(params)
            render(view: "createTopico", model: [topicoInstance: topicoInstance, mensagemInstance:mensagemInstance,visualizar:true, forumId:params.forumId])
        }
    }

    /**
     * Avaliacao de uma mensagem
     */
    def avaliarMensagem = {
        def mensagem = Mensagem.get(params.id)
        debateService.avaliarMensagem(mensagem, params.boolean('avaliacao'))
        render getTextoAvaliacaoMensagem(mensagem)
    }

    def static getTextoAvaliacaoMensagem = { mensagem ->
        def gostei = AvaliacaoMensagem.countByMensagemAndPositivo(mensagem, true)
        def naoGostei = AvaliacaoMensagem.countByMensagemAndPositivo(mensagem, false)
        def textMessage = ''
        if(gostei > 1){
            textMessage = "${message(code: 'pessoasGostam', args: [gostei])} "
        } else if(gostei == 1) {
            textMessage = "${message(code: 'pessoaGosta', args: [gostei])} "
        }
        if(StringUtils.hasText(textMessage) && naoGostei){
            textMessage+= " <br /> "
        }
        if(naoGostei > 1){
            textMessage+= "${message(code: 'pessoasNaoGostam', args: [naoGostei])} "
        } else if(naoGostei == 1) {
            textMessage+= "${message(code: 'pessoaNaoGosta', args: [naoGostei])} "
        }
        return textMessage
    }
}
