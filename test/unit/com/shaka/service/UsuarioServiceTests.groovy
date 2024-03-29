package com.shaka.service

import grails.plugins.springsecurity.SpringSecurityService
import grails.test.*

import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile

import com.shaka.Usuario
import com.shaka.business.UsuarioService
import com.shaka.image.ImageService

class UsuarioServiceTests extends GrailsUnitTestCase {
    def springSecurityService
    def imageService
    UsuarioService usuarioService

    protected void setUp() {
        super.setUp()
        buildMocks()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGetCurrentUserSucesso(){
        Usuario u = getUsuario()
        u.save()
        springSecurityService.demand.getPrincipal() { -> return u }
        def user = usuarioService.getCurrentUser()
        assertNotNull user
        assertEquals u, user
    }

    void testSaveUsuarioInvalido(){
        springSecurityService.demand.encodePassword("senha") { -> return "senha" }
        assertFalse usuarioService.save(null, null, null,null,null)
        Usuario u = getUsuario()
        u.nome = null
        assertFalse usuarioService.save(u, null, null,null,null)
        assertFalse u.errors.isEmpty()
        assertNull u.errors['pathImagem']
        assertNotNull u.errors['nome']
    }

    void testSaveUsuarioExtensaoInvalida() {
        springSecurityService.demand.encodePassword("senha") { -> return "senha" }
		springSecurityService.demand.encodePassword("senha") { -> return "senha" }
        imageService.demand.extensaoValida() { nomeImagem -> return false }
        Usuario u = getUsuario()
        // u.id = 1 -- isDirty nao eh adicionado dinamicamente no mock GRAILS-7506
        byte[] content = "teste".getBytes()
        MultipartFile multipartFile = new MockMultipartFile("nome", content)
        assertFalse usuarioService.save(u, "nomeImagem", multipartFile,"senha","senha")
        assertNotNull u.errors['pathImagem']
    }

    void testSaveUsuarioTamanhoImagemInvalida() {
        springSecurityService.demand.encodePassword("senha") { -> return "senha" }
		springSecurityService.demand.encodePassword("senha") { -> return "senha" }
        imageService.demand.extensaoValida() { nomeImagem -> return true }
        imageService.demand.tamanhoImagemValido() { imagem, tamanhoMax -> return false }
        Usuario u = getUsuario()
        // u.id = 1 -- isDirty nao eh adicionado dinamicamente no mock GRAILS-7506
        byte[] content = "teste".getBytes()
        MultipartFile multipartFile = new MockMultipartFile("nome", content)
        assertFalse usuarioService.save(u, "nomeImagem", multipartFile,"senha","senha")
        assertNotNull u.errors['pathImagem']
    }

    void testSaveUsuarioExtensaoSucesso() {
        springSecurityService.demand.encodePassword("senha") { -> return "senha" }
		springSecurityService.demand.encodePassword("senha") { -> return "senha" }
        springSecurityService.demand.reauthenticate() {usuario,senha ->  }
        imageService.demand.extensaoValida() { nomeImagem -> return true }
        imageService.demand.tamanhoImagemValido() { imagem, tamanhoMax -> return true }
        imageService.demand.deleteImage() { diretorio, nomeImagem -> }
		imageService.demand.deleteImage() { diretorio, nomeImagem -> }
        imageService.demand.saveImage() { diretorio, nomeImagem,imagem -> }
		imageService.demand.saveImage() { diretorio, nomeImagem,imagem -> }
        Usuario u = getUsuario()
        // u.id = 1 -- isDirty nao eh adicionado dinamicamente no mock GRAILS-7506
        u.pathImagem = "teste"
        byte[] content = "teste".getBytes()
        MultipartFile multipartFile = new MockMultipartFile("nome", content)
        assertTrue usuarioService.save(u, "nomeImagem", multipartFile,"senha","senha")
        assertTrue u.errors.isEmpty()
    }

    private void buildMocks(){
        usuarioService = new UsuarioService()
        mockDomain(Usuario)
        springSecurityService = mockFor(SpringSecurityService)
        imageService = mockFor(ImageService)
        usuarioService.springSecurityService = springSecurityService.createMock()
        usuarioService.imageService = imageService.createMock()
    }

    private Usuario getUsuario(){
        Usuario u = new Usuario(username : "username", email : "teste@teste.com", password: "senha", nome:"nome")
        u.springSecurityService = springSecurityService.createMock()
        return u
    }
}
