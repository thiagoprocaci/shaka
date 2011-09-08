package com.shaka.domain

import grails.test.*

import com.shaka.Forum

class ForumTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        mockDomain(Forum)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNomeBranco() {
        def forum = new Forum(descricao:'desc', nome:'')
        forum.validate()
        assertNotNull(forum.errors)
        assertFalse(forum.errors.isEmpty())
        assertEquals("blank", forum.errors['nome'])
    }

    void testNomeNulo() {
        def forum = new Forum(descricao:'desc', nome:null)
        forum.validate()
        assertNotNull(forum.errors)
        assertFalse(forum.errors.isEmpty())
        assertEquals("nullable", forum.errors['nome'])
    }

    void testDescricaoBranco() {
        def forum = new Forum(descricao:'', nome:'nome')
        forum.validate()
        assertNotNull(forum.errors)
        assertFalse(forum.errors.isEmpty())
        assertEquals("blank", forum.errors['descricao'])
    }

    void testDescricaoNula() {
        def forum = new Forum(descricao:null, nome:'nome')
        forum.validate()
        assertNotNull(forum.errors)
        assertFalse(forum.errors.isEmpty())
        assertEquals("nullable", forum.errors['descricao'])
    }
}
