package com.shaka.domain

import grails.test.*

import com.shaka.Forum
import com.shaka.Topico

class TopicoTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        mockDomain(Topico)
        mockDomain(Forum)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testTituloNulo() {
        def topico = new Topico()
        topico.validate()
        assertNotNull(topico.errors)
        assertFalse(topico.errors.isEmpty())
        assertEquals("nullable", topico.errors['titulo'])
    }

    void testTituloBranco() {
        def topico = new Topico()
        topico.titulo = ' '
        topico.validate()
        assertNotNull(topico.errors)
        assertFalse(topico.errors.isEmpty())
        assertEquals("blank", topico.errors['titulo'])
    }

    void testSaveSucesso(){
        def forum = new Forum()
        def topico = new Topico()
        topico.forum = forum
        topico.titulo = 'titulo'
        topico.save()
        assertNotNull(topico.errors)
        assertTrue(topico.errors.isEmpty())
        assertNotNull(topico.dateCreated)
        assertEquals 0,topico.numeroVisitas

    }
}
