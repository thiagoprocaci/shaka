package com.shaka.service

import grails.test.*

import com.shaka.Mensagem
import com.shaka.Topico
import com.shaka.business.DebateService
import com.shaka.validation.TextValidationService



class DebateServiceTests extends GrailsUnitTestCase {
    def textValidationService
    def debateService

    protected void setUp() {
        super.setUp()
        debateService = new DebateService()
        mockDomain(Topico)
        mockDomain(Mensagem)
        textValidationService = mockFor(TextValidationService)
        debateService.textValidationService = textValidationService.createMock()
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
        def topico = new Topico()
        def mensagem = new Mensagem()
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

    void testSaveCodigoMalicioso(){
        def topico = new Topico()
        def mensagem = new Mensagem()
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

    void testSaveSucesso(){
        def topico = new Topico()
        def mensagem = new Mensagem()
        topico.titulo = "a"
        mensagem.texto = "a"
        textValidationService.demand.hasTextInHtml() { texto ->
            return true
        }
        textValidationService.demand.hasJSCodeinHtml() { texto ->
            return false
        }
        assertTrue(debateService.save(topico,mensagem))
        assertTrue mensagem.errors.isEmpty()
        assertTrue topico.errors.isEmpty()
        assertNotNull topico.id
        assertNotNull mensagem.id
        assertEquals mensagem.topico, topico
    }
}
