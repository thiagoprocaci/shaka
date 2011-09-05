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
        mockDomain(Usuario)
        usuarioService = mockFor(UsuarioService)
        controller.usuarioService = usuarioService.createMock()
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
}
