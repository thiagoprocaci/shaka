package com.shaka.controller

import grails.test.*

import com.shaka.DebateController
import com.shaka.Forum
import com.shaka.Mensagem
import com.shaka.Topico
import com.shaka.business.DebateService
import com.shaka.business.UsuarioService

class DebateControllerTests extends ControllerUnitTestCase {

    def debateService
    def usuarioService

    DebateControllerTests(){
        super(DebateController.class)
    }

    protected void setUp() {
        super.setUp()
        controller.metaClass.message = {args -> "${args.code}"}
        debateService = mockFor(DebateService)
        usuarioService = mockFor(UsuarioService)
        controller.debateService = debateService.createMock()
        controller.usuarioService = usuarioService.createMock()
        mockDomain(Forum)
        mockDomain(Topico)
        mockDomain(Mensagem)
        controller.usuarioService.diretorioImagemRelativo = "dir"
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
        def paramsMock = [id : 1]
        controller.params.putAll(paramsMock)
        controller.index()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.action
        assertNotNull controller.redirectArgs.params

        assertEquals 2, controller.redirectArgs.size()
        assertEquals paramsMock.id, controller.redirectArgs.params.id
        assertEquals "createTopico", controller.redirectArgs.action
    }

    void testCreateTopico() {
        def paramsMock = [id : 1]
        controller.params.putAll(paramsMock)
        def map = controller.createTopico()
        assertNotNull map
        assertNotNull map.topicoInstance
        assertNotNull map.mensagemInstance
        assertNotNull map.forumId

        assertEquals 3, map.size()
        assertEquals paramsMock.id, map.forumId
    }

    void testSaveTopicoForumNull() {
        controller.saveTopico()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.uri
        assertEquals 1,controller.redirectArgs.size()
        assertEquals "/",controller.redirectArgs.uri
    }

    void testSaveTopicoErroSalvar() {
        def forum = new Forum(nome:"nome", descricao:"descricao")
        forum.save()
        def paramsMock = [forumId : forum.id, texto:'texto', titulo:'titulo']
        controller.params.putAll(paramsMock)

        debateService.demand.save() { topicoInstance, mensagemInstance -> return false }

        controller.saveTopico()
        assertNotNull controller.renderArgs
        assertNotNull controller.renderArgs.model
        assertNotNull controller.renderArgs.model.topicoInstance
        assertNotNull controller.renderArgs.model.mensagemInstance
        assertNotNull controller.renderArgs.model.forumId
        assertNotNull controller.renderArgs.view

        assertEquals 2, controller.renderArgs.size()
        assertEquals 3, controller.renderArgs.model.size()
        assertEquals "createTopico", controller.renderArgs.view
        assertEquals paramsMock.titulo, controller.renderArgs.model.topicoInstance.titulo
        assertEquals paramsMock.texto, controller.renderArgs.model.mensagemInstance.texto
        assertEquals paramsMock.forumId, controller.renderArgs.model.forumId
    }

    void testSaveTopicoSucesso() {
        def forum = new Forum(nome:"nome", descricao:"descricao")
        forum.save()
        def paramsMock = [forumId : forum.id, id:1]
        controller.params.putAll(paramsMock)
        debateService.demand.save() { topicoInstance, mensagemInstance -> return true }
        controller.saveTopico()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.action
        assertNotNull controller.redirectArgs.id

        assertEquals 2, controller.redirectArgs.size()
        assertEquals "showTopico", controller.redirectArgs.action
        assertEquals paramsMock.id, controller.redirectArgs.id
        assertEquals "salvoSucesso", controller.flash.message
    }

    void testShowTopico() {
        def paramsMock = [id : 1]
        controller.params.putAll(paramsMock)
        debateService.demand.visitarTopico() { id ->
            return new Topico()
        }
        controller.showTopico()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.action
        assertNotNull controller.redirectArgs.params
        assertEquals 2, controller.redirectArgs.size()
        assertEquals paramsMock.id, controller.redirectArgs.params.id
        assertEquals 'listTopico', controller.redirectArgs.action
    }

    void testListTopicoErro() {
        controller.listTopico()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.uri
        assertEquals 1, controller.redirectArgs.size()
        assertEquals "/", controller.redirectArgs.uri
    }

    void testListTopicoSucesso() {
        def forum = new Forum(nome:"nome", descricao:"descricao")
        forum.save()
        def topico = new Topico(titulo: 'titulo', forum: forum)
        topico.save()
        def mensagem = new Mensagem(texto:'texto', topico:topico)
        mensagem.save()
        def mensagem_ = new Mensagem(texto:'texto_', topico:topico)
        mensagem_.save()

        def paramsMock = [id : topico.id, max:1, offset:1]
        controller.params.putAll(paramsMock)

        def mensagemList = Mensagem.findAllByTopico(topico,[max: paramsMock.max, offset: paramsMock.offset , sort:"dateCreated", order:"asc"])

        controller.listTopico()
        assertNotNull controller.renderArgs
        assertNotNull controller.renderArgs.view
        assertNotNull controller.renderArgs.model
        assertNotNull controller.renderArgs.model.topicoInstance
        assertNotNull controller.renderArgs.model.mensagemList
        assertNotNull controller.renderArgs.model.diretorioImagem
        assertNotNull controller.renderArgs.model.mensagemTotal

        assertEquals 2, controller.renderArgs.size()
        assertEquals 4, renderArgs.model.size()
        assertEquals "showTopico" , controller.renderArgs.view
        assertEquals topico, controller.renderArgs.model.topicoInstance
        assertEquals mensagemList, controller.renderArgs.model.mensagemList
        assertEquals  controller.usuarioService.diretorioImagemRelativo, controller.renderArgs.model.diretorioImagem
        assertEquals 2, controller.renderArgs.model.mensagemTotal
        assertTrue mensagem_ in controller.renderArgs.model.mensagemList
        assertFalse mensagem in controller.renderArgs.model.mensagemList

        compareList(mensagemList, controller.renderArgs.model.mensagemList)
    }

    void testListTopicoSucessoSemMaxOffset() {
        def forum = new Forum(nome:"nome", descricao:"descricao")
        forum.save()
        def topico = new Topico(titulo: 'titulo', forum: forum)
        topico.save()
        def mensagem = new Mensagem(texto:'texto', topico:topico)
        mensagem.save()
        def mensagem_ = new Mensagem(texto:'texto_', topico:topico)
        mensagem_.save()

        def paramsMock = [id : topico.id]
        controller.params.putAll(paramsMock)

        def mensagemList = Mensagem.findAllByTopico(topico,[max: 10, offset: 0 , sort:"dateCreated", order:"asc"])
        controller.listTopico()
        assertNotNull controller.renderArgs
        assertNotNull controller.renderArgs.view
        assertNotNull controller.renderArgs.model
        assertNotNull controller.renderArgs.model.topicoInstance
        assertNotNull controller.renderArgs.model.mensagemList
        assertNotNull controller.renderArgs.model.diretorioImagem
        assertNotNull controller.renderArgs.model.mensagemTotal

        assertEquals 2, controller.renderArgs.size()
        assertEquals 4, renderArgs.model.size()
        assertEquals "showTopico" , controller.renderArgs.view
        assertEquals topico, controller.renderArgs.model.topicoInstance
        assertEquals mensagemList, controller.renderArgs.model.mensagemList
        assertEquals  controller.usuarioService.diretorioImagemRelativo, controller.renderArgs.model.diretorioImagem
        assertEquals 2, controller.renderArgs.model.mensagemTotal
        assertTrue mensagem_ in controller.renderArgs.model.mensagemList
        assertTrue mensagem in controller.renderArgs.model.mensagemList

        compareList(mensagemList, controller.renderArgs.model.mensagemList)
    }

    void testResponderTopicoErro() {
        controller.responderTopico()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.uri
        assertEquals 1, controller.redirectArgs.size()
        assertEquals "/", controller.redirectArgs.uri
    }

    void testResponderTopicoSucesso() {
        def forum = new Forum(nome:"nome", descricao:"descricao")
        forum.save()
        def topico = new Topico(titulo: 'titulo', forum: forum)
        topico.save()
        def mensagem = new Mensagem(texto:'texto', topico:topico)
        mensagem.save()
        def mensagem_ = new Mensagem(texto:'texto_', topico:topico)
        mensagem_.save()

        def mensagemList = Mensagem.findAllByTopico(topico,[max: 10 , sort:"dateCreated", order:"desc"])

        def paramsMock = [id : topico.id]
        controller.params.putAll(paramsMock)

        def map = controller.responderTopico()
        assertNotNull map
        assertNotNull map.topicoInstance
        assertNotNull map.mensagemInstance
        assertNotNull map.diretorioImagem
        assertNotNull map.mensagemList
        assertNull map.mensagemInstance.id
        assertEquals 4, map.size()
        assertEquals topico, map.topicoInstance
        assertEquals controller.usuarioService.diretorioImagemRelativo, map.diretorioImagem
        compareList(mensagemList, map.mensagemList)
    }

    void testSaveRespostaErroTopicoNull() {
        controller.saveResposta()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.uri
        assertEquals 1, controller.redirectArgs.size()
        assertEquals "/", controller.redirectArgs.uri
    }

    void testSaveRespostaErroSalvar() {
        def forum = new Forum(nome:"nome", descricao:"descricao")
        forum.save()
        def topico = new Topico(titulo: 'titulo', forum: forum)
        topico.save()
        def mensagem = new Mensagem(texto:'texto', topico:topico)
        mensagem.save()
        def mensagem_ = new Mensagem(texto:'texto_', topico:topico)
        mensagem_.save()

        def mensagemList = Mensagem.findAllByTopico(topico,[max: 10 , sort:"dateCreated", order:"desc"])

        def paramsMock = [id : topico.id, texto: 'yes, we can!']
        controller.params.putAll(paramsMock)

        debateService.demand.save() { topicoInstance, mensagemInstance ->
            return false
        }
        controller.saveResposta()
        assertNotNull controller.renderArgs
        assertNotNull controller.renderArgs.view
        assertNotNull controller.renderArgs.model
        assertNotNull controller.renderArgs.model.topicoInstance
        assertNotNull controller.renderArgs.model.mensagemInstance
        assertNotNull controller.renderArgs.model.mensagemList
        assertEquals 2, controller.renderArgs.size()
        assertEquals 3, controller.renderArgs.model.size()
        assertEquals "responderTopico", controller.renderArgs.view
        assertEquals topico, controller.renderArgs.model.topicoInstance
        assertEquals paramsMock.texto, controller.renderArgs.model.mensagemInstance.texto
        compareList(mensagemList, controller.renderArgs.model.mensagemList)
    }

    void testSaveRespostaSucesso() {
        def forum = new Forum(nome:"nome", descricao:"descricao")
        forum.save()
        def topico = new Topico(titulo: 'titulo', forum: forum)
        topico.save()

        def paramsMock = [id : topico.id]
        controller.params.putAll(paramsMock)
        debateService.demand.save() { topicoInstance, mensagemInstance ->
            return true
        }
        controller.saveResposta()

        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.action
        assertNotNull controller.redirectArgs.id

        assertEquals 2, controller.redirectArgs.size()
        assertEquals "showTopico", controller.redirectArgs.action
        assertEquals paramsMock.id, controller.redirectArgs.id
        assertEquals "salvoSucesso", controller.flash.message
    }

    void testVisualizarNovoTopico() {
        def paramsMock = [titulo : 'titulo', forumId: 1, texto:'texto']
        controller.params.putAll(paramsMock)
        controller.visualizar()

        assertNotNull controller.renderArgs
        assertNotNull controller.renderArgs.view
        assertNotNull controller.renderArgs.model
        assertEquals 2, controller.renderArgs.size()
        assertEquals 4, controller.renderArgs.model.size()
        assertNotNull controller.renderArgs.model.topicoInstance
        assertNotNull controller.renderArgs.model.mensagemInstance
        assertNotNull controller.renderArgs.model.forumId
        assertNotNull controller.renderArgs.model.visualizar

        assertEquals paramsMock.titulo, controller.renderArgs.model.topicoInstance.titulo
        assertEquals paramsMock.texto, controller.renderArgs.model.mensagemInstance.texto
        assertTrue controller.renderArgs.model.visualizar
        assertEquals paramsMock.forumId, controller.renderArgs.model.forumId
        assertEquals "createTopico", controller.renderArgs.view
    }

    void testVisualizarTopico () {
        def forum = new Forum(nome:"nome", descricao:"descricao")
        forum.save()
        def topico = new Topico(titulo: 'titulo', forum: forum)
        topico.save()
        def mensagem = new Mensagem(texto:'texto', topico:topico)
        mensagem.save()
        def mensagem_ = new Mensagem(texto:'texto_', topico:topico)
        mensagem_.save()

        def mensagemList = Mensagem.findAllByTopico(topico,[max: 10 , sort:"dateCreated", order:"desc"])

        def paramsMock = [titulo : 'titulo', forumId: forum.id, texto:'texto', id:topico.id]
        controller.params.putAll(paramsMock)
        controller.visualizar()

        assertNotNull controller.renderArgs
        assertNotNull controller.renderArgs.view
        assertNotNull controller.renderArgs.model
        assertNotNull controller.renderArgs.model.topicoInstance
        assertNotNull controller.renderArgs.model.mensagemInstance
        assertNotNull controller.renderArgs.model.visualizar
        assertNotNull controller.renderArgs.model.diretorioImagem
        assertNotNull controller.renderArgs.model.mensagemList
        assertEquals 2, controller.renderArgs.size()
        assertEquals 5, controller.renderArgs.model.size()
        assertEquals paramsMock.texto, controller.renderArgs.model.mensagemInstance.texto
        assertEquals topico, controller.renderArgs.model.topicoInstance
        assertEquals "responderTopico", controller.renderArgs.view
        assertTrue controller.renderArgs.model.visualizar
        assertEquals controller.usuarioService.diretorioImagemRelativo, controller.renderArgs.model.diretorioImagem
        compareList(mensagemList, controller.renderArgs.model.mensagemList)
    }

    void testVisualizarTopicoNull() {
        def paramsMock = [id:1]
        controller.params.putAll(paramsMock)
        controller.visualizar()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.uri
        assertEquals 1, controller.redirectArgs.size()
        assertEquals "/", controller.redirectArgs.uri
    }

    private compareList(def list1, def list2) {
        assertNotNull list1
        assertNotNull list2
        assertEquals list1, list2
        assertEquals list1.size() , list2.size()
        for(int i = 0; i < list1.size(); i++) {
            assertNotNull(list1.get(i))
            assertNotNull(list2.get(i))
            assertEquals list1.get(i), list2.get(i)
            assertEquals list1.get(i).id, list2.get(i).id
        }
    }
}
