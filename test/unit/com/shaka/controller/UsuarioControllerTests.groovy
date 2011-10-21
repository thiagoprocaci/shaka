package com.shaka.controller

import grails.test.*

import com.shaka.Usuario
import com.shaka.UsuarioController
import com.shaka.business.UsuarioService;

class UsuarioControllerTests extends ControllerUnitTestCase {

    def usuarioService

    public UsuarioControllerTests() {
        super(UsuarioController.class)
    }
    protected void setUp() {
        super.setUp()
        controller.metaClass.message = {args -> "${args.code}"}
        mockDomain(Usuario)
        usuarioService = mockFor(UsuarioService)
        controller.usuarioService = usuarioService.createMock()
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
        assertEquals "create", controller.redirectArgs.action
        assertEquals paramsMock, controller.redirectArgs.params
    }

    void testCreate() {
        def paramsMock = [nome : "nome"]
        controller.params.putAll(paramsMock)
        def map = controller.create()
        assertNotNull map
        assertNotNull map.usuarioInstance
        assertNotNull map.usuarioInstance.nome
        assertEquals paramsMock.nome, map.usuarioInstance.nome
        assertEquals 1, map.size()
    }

    void testSaveErro() {
        def paramsMock = [id : 1]
        controller.params.putAll(paramsMock)

        usuarioService.demand.save()  {usuario, nomeOriginal, file, senha, confirmacaoSenha ->
            return false
        }
        controller.save()
        assertNotNull controller.renderArgs
        assertNotNull controller.renderArgs.view
        assertNotNull controller.renderArgs.model
        assertNotNull controller.renderArgs.model.usuarioInstance
        assertEquals 2, controller.renderArgs.size()
        assertEquals 1, controller.renderArgs.model.size()
        assertEquals "create", controller.renderArgs.view
        assertEquals paramsMock.id, controller.renderArgs.model.usuarioInstance.id
    }

    void testSaveSucesso() {
        usuarioService.demand.save()  {usuario, nomeOriginal, file, senha, confirmacaoSenha ->
            return true
        }
        controller.save()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.uri
        assertNotNull controller.flash.message
        assertEquals 1, controller.redirectArgs.size()
        assertEquals "/", controller.redirectArgs.uri
        assertEquals "salvoSucesso", controller.flash.message
    }

    void testEditErro() {
        usuarioService.demand.getCurrentUser()  { -> return null }
        def map = controller.edit()
        assertNull map
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.uri
        assertEquals 1, controller.redirectArgs.size()
        assertEquals "/", controller.redirectArgs.uri
    }

    void testEditSucesso() {
        def usuario = new Usuario(id:1)
        usuarioService.demand.getCurrentUser()  { -> return usuario }
        def map = controller.edit()
        assertNotNull map
        assertNotNull map.usuarioInstance
        assertEquals 1, map.size()
        assertEquals usuario, map.usuarioInstance
    }

	void testChangeImageErro() {
		usuarioService.demand.getCurrentUser()  { -> return null }
		def map = controller.changeImage()
		assertNull map
		assertNotNull controller.redirectArgs
		assertNotNull controller.redirectArgs.uri
		assertEquals 1, controller.redirectArgs.size()
		assertEquals "/", controller.redirectArgs.uri
	}

	void testChangeImageSucesso() {
		def usuario = new Usuario(id:1)
		usuarioService.demand.getCurrentUser()  { -> return usuario }
		def map = controller.changeImage()
		assertNotNull map
		assertNotNull map.usuarioInstance
		assertNotNull map.diretorioImagem
		assertEquals 2, map.size()
		assertEquals usuario, map.usuarioInstance
		assertEquals controller.usuarioService.diretorioImagemRelativo, map.diretorioImagem
	}

    void testUpdateErroUsuarioNull () {
        usuarioService.demand.getCurrentUser()  { -> return null }
        controller.update()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.uri
        assertEquals 1, controller.redirectArgs.size()
        assertEquals "/", controller.redirectArgs.uri
    }

    void testUpdateErroSalvar() {
        def paramsMock = [id : 1]
        controller.params.putAll(paramsMock)

        def usuario = new Usuario(id:1)
        usuarioService.demand.getCurrentUser()  { -> return usuario }

        usuarioService.demand.save()  {user, nomeOriginal, file, senha, confirmacaoSenha ->
            return false
        }
        controller.update()
        assertNotNull controller.renderArgs
        assertNotNull controller.renderArgs.view
        assertNotNull controller.renderArgs.model
        assertNotNull controller.renderArgs.model.usuarioInstance
        assertEquals 2, controller.renderArgs.size()
        assertEquals 1, controller.renderArgs.model.size()
        assertEquals "edit", controller.renderArgs.view
        assertEquals paramsMock.id, controller.renderArgs.model.usuarioInstance.id
    }

    void testUpdateSucesso() {
        def usuario = new Usuario(id:1)
        usuarioService.demand.getCurrentUser()  { -> return usuario }

        usuarioService.demand.save()  {user, nomeOriginal, file, senha, confirmacaoSenha ->
            return true
        }
        controller.update()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.action
        assertNotNull controller.flash.message
        assertEquals 1, controller.redirectArgs.size()
        assertEquals "edit", controller.redirectArgs.action
        assertEquals "salvoSucesso", controller.flash.message
    }

	void testUpdateImageErro() {
		def usuario = new Usuario(id:1)
		usuarioService.demand.getCurrentUser()  { -> return usuario }
		controller.updateImage()
		assertNotNull(usuario.errors)
		assertFalse(usuario.errors.isEmpty())
		assertEquals("nenhumaImagemSelecionada", usuario.errors['pathImagem'])
		assertNotNull controller.renderArgs
		assertNotNull controller.renderArgs.view
		assertNotNull controller.renderArgs.model
		assertNotNull controller.renderArgs.model.usuarioInstance
		assertNotNull controller.renderArgs.model.diretorioImagem
		assertEquals "changeImage", controller.renderArgs.view
		assertEquals controller.usuarioService.diretorioImagemRelativo, controller.renderArgs.model.diretorioImagem
		assertEquals usuario, controller.renderArgs.model.usuarioInstance
		assertEquals 2, controller.renderArgs.size()
		assertEquals 2, controller.renderArgs.model.size()
	}

	/*void testUpdateImageSucesso() {
		def usuario = new Usuario(id:1)
		usuarioService.demand.getCurrentUser()  { -> return usuario }

	}*/
}
