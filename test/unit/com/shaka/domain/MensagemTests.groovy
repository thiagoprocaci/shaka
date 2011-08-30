package com.shaka.domain

import com.shaka.Mensagem;
import com.shaka.Topico;

import grails.test.*

class MensagemTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
		mockDomain(Mensagem)
		mockDomain(Topico)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testTextoNulo() {
		def mensagem = new Mensagem()
		mensagem.validate()
		assertNotNull(mensagem.errors)
		assertFalse(mensagem.errors.isEmpty())
		assertEquals("nullable", mensagem.errors['texto'])
    }

	void testTextoBranco() {
		def mensagem = new Mensagem()
		mensagem.texto = ''
		mensagem.validate()
		assertNotNull(mensagem.errors)
		assertFalse(mensagem.errors.isEmpty())
		assertEquals("blank", mensagem.errors['texto'])
	}

	void testSaveSucesso() {
		def topico  = new Topico()
		def mensagem = new Mensagem()
		mensagem.texto = 'texto'
		mensagem.topico = topico
		mensagem.save()
		assertNotNull(mensagem.errors)
		assertTrue(mensagem.errors.isEmpty())
		assertNotNull(mensagem.dateCreated)
	}
}
