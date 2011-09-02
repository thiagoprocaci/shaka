package com.shaka.service

import grails.test.*

import com.shaka.Forum
import com.shaka.Mensagem
import com.shaka.Topico
import com.shaka.Usuario
import com.shaka.business.DebateService
import com.shaka.business.UsuarioService
import com.shaka.validation.TextValidationService



class DebateServiceTests extends GrailsUnitTestCase {
    def textValidationService
    def debateService
    def usuarioService

    protected void setUp() {
        super.setUp()
        debateService = new DebateService()
        mockDomain(Topico)
        mockDomain(Mensagem)
        mockDomain(Forum)
        mockDomain(Usuario)
        textValidationService = mockFor(TextValidationService)
        usuarioService = mockFor(UsuarioService)
        debateService.textValidationService = textValidationService.createMock()
        debateService.usuarioService = usuarioService.createMock()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSaveObjetoNulo() {
        def topico = null
        def mensagem = null
        assertFalse(debateService.save(topico,mensagem))

        topico = new Topico()
        mensagem = null
        assertFalse(debateService.save(topico,mensagem))
        assertTrue topico.errors.isEmpty()

        topico = null
        mensagem = new Mensagem()
        assertFalse(debateService.save(topico,mensagem))
        assertTrue mensagem.errors.isEmpty()
    }

    void testSaveTituloTopicoBranco(){
        def topico = new Topico()
        def mensagem = new Mensagem()
        topico.titulo = ''
        mensagem.texto = "a"
        textValidationService.demand.hasTextInHtml() { texto ->
            return true
        }
        assertFalse(debateService.save(topico,mensagem))
        assertTrue mensagem.errors.isEmpty()
        assertFalse topico.errors.isEmpty()
        assertNotNull topico.errors['titulo']
        assertEquals "blank", topico.errors['titulo']
    }

    void testSaveTituloTopicoNulo(){
        def topico = new Topico()
        def mensagem = new Mensagem()
        mensagem.texto = "a"
        textValidationService.demand.hasTextInHtml() { texto ->
            return true
        }
        assertFalse(debateService.save(topico,mensagem))
        assertTrue mensagem.errors.isEmpty()
        assertFalse topico.errors.isEmpty()
        assertNotNull topico.errors['titulo']
        assertEquals "nullable", topico.errors['titulo']
    }

    void testSaveTextoBranco() {
        def forum = new Forum()
        def topico = new Topico()
        def mensagem = new Mensagem()
        topico.forum = forum
        topico.titulo = "a"
        textValidationService.demand.hasTextInHtml() { texto ->
            return false
        }
        assertFalse(debateService.save(topico,mensagem))
        assertFalse mensagem.errors.isEmpty()
        assertTrue topico.errors.isEmpty()
        assertNotNull mensagem.errors['texto']
        assertEquals "blank", mensagem.errors['texto']
    }

    void testSaveCodigoJSMalicioso(){
        def forum = new Forum()
        def topico = new Topico()
        def mensagem = new Mensagem()
        topico.forum = forum
        topico.titulo = "a"
        mensagem.texto = "a"
        textValidationService.demand.hasTextInHtml() { texto ->
            return true
        }
        textValidationService.demand.hasJSCodeinHtml() { texto ->
            return true
        }
        assertFalse(debateService.save(topico,mensagem))
        assertFalse mensagem.errors.isEmpty()
        assertTrue topico.errors.isEmpty()
        assertNotNull mensagem.errors['texto']
        assertEquals "codigoMalicioso", mensagem.errors['texto']
    }

    void testSaveCodigoCSSMalicioso(){
        def forum = new Forum()
        def topico = new Topico()
        def mensagem = new Mensagem()
        topico.forum = forum
        topico.titulo = "a"
        mensagem.texto = "a"
        textValidationService.demand.hasTextInHtml() { texto ->
            return true
        }
        textValidationService.demand.hasJSCodeinHtml() { text ->
            return false
        }
        textValidationService.demand.hasCCSCodeinHtml() { txt ->
            return true
        }
        assertFalse(debateService.save(topico,mensagem))
        assertFalse mensagem.errors.isEmpty()
        assertTrue topico.errors.isEmpty()
        assertNotNull mensagem.errors['texto']
        assertEquals "codigoMalicioso", mensagem.errors['texto']
    }

    void testSaveSucesso(){
        def forum = new Forum()
        def topico = new Topico()
        def mensagem = new Mensagem()
        topico.forum = forum
        topico.titulo = "a"
        mensagem.texto = "a"
        textValidationService.demand.hasTextInHtml() { texto ->
            return true
        }
        textValidationService.demand.hasJSCodeinHtml() { texto ->
            return false
        }
        textValidationService.demand.hasCCSCodeinHtml() { texto ->
            return false
        }
        Usuario u = new Usuario()
        u.id = 1
        usuarioService.demand.getCurrentUser() { -> return u }
        usuarioService.demand.getCurrentUser() { -> return u }
        assertTrue(debateService.save(topico,mensagem))
        assertTrue mensagem.errors.isEmpty()
        assertTrue topico.errors.isEmpty()
        assertNotNull topico.id
        assertNotNull mensagem.id
        assertEquals mensagem.topico, topico
        assertEquals u, mensagem.usuario
        assertEquals u, topico.usuario
    }

    void testVisitarTopicoErro() {
        assertNull(debateService.visitarTopico(1))
    }

    void testVisitarTopicoSucesso() {
        def forum = new Forum()
        def topico = new Topico()
        topico.titulo = 'titulo'
        topico.forum = forum
        topico.save()

        Topico t = debateService.visitarTopico(topico.id)
        assertEquals(topico, t)
        assertEquals(1,t.numeroVisitas)

        t = debateService.visitarTopico(topico.id)
        assertEquals(topico, t)
        assertEquals(2,t.numeroVisitas)
    }
}
