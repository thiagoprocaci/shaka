package com.shaka.domain

import java.util.Date;

import grails.plugins.springsecurity.SpringSecurityService;
import grails.test.*

import com.shaka.Usuario

class UsuarioTests extends GrailsUnitTestCase {
    def springSecurityService

    protected void setUp() {
        super.setUp()
        mockDomain(Usuario)
        springSecurityService = mockFor(SpringSecurityService)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNomeBranco() {
        def usuario = getUsuario()
        usuario.nome = ''
        usuario.validate()
        assertNotNull(usuario.errors)
        assertFalse(usuario.errors.isEmpty())
        assertEquals("blank", usuario.errors['nome'])
    }

    void testNomeNulo() {
        def usuario = getUsuario()
        usuario.nome = null
        usuario.validate()
        assertNotNull(usuario.errors)
        assertFalse(usuario.errors.isEmpty())
        assertEquals("nullable", usuario.errors['nome'])
    }

    void testEmailBranco() {
        def usuario = getUsuario()
        usuario.email = ''
        usuario.validate()
        assertNotNull(usuario.errors)
        assertFalse(usuario.errors.isEmpty())
        assertEquals("blank", usuario.errors['email'])
    }

    void testEmailNulo() {
        def usuario = getUsuario()
        usuario.email = null
        usuario.validate()
        assertNotNull(usuario.errors)
        assertFalse(usuario.errors.isEmpty())
        assertEquals("nullable", usuario.errors['email'])
    }

    void testEmailUnique() {
        def usuario = getUsuario()
        def usuario2 = getUsuario()
        usuario2.springSecurityService = springSecurityService.createMock()
        springSecurityService.demand.encodePassword() { senha -> return "senha" }
        usuario2.username = 'eee'
        usuario2.save()
        usuario.save()
        assertNotNull(usuario.errors)
        assertFalse(usuario.errors.isEmpty())
        assertEquals("unique", usuario.errors['email'])
    }

    void testEmailInvalido() {
        def usuario = getUsuario()
        usuario.email = "vc vc vc quer?"
        usuario.validate()
        assertNotNull(usuario.errors)
        assertFalse(usuario.errors.isEmpty())
        assertEquals("email", usuario.errors['email'])
    }

    private getUsuario(){
        def usuario = new Usuario()
        usuario.nome = 'nome'
        usuario.email = 'email@email.com'
        usuario.password = '123'
        usuario.username = 'username'
        return usuario
    }
}
