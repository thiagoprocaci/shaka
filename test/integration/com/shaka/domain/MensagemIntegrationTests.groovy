package com.shaka.domain

import grails.test.*

import com.shaka.Forum
import com.shaka.Mensagem
import com.shaka.Topico

class MensagemIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCountByForum() {
        def forum = new Forum(nome:"nome", descricao:"descricao")
        forum.save()
        def topico = new Topico(titulo: 'titulo', forum: forum)
        topico.save()
        def mensagem = new Mensagem(texto:'texto', topico:topico)
        mensagem.save()
        def mensagem_ = new Mensagem(texto:'texto_', topico:topico)
        mensagem_.save()
        assertEquals 2, Mensagem.countByForum(forum)
    }

    void testGetLast() {
        def forum = new Forum(nome:"nome", descricao:"descricao")
        forum.save()
        def topico = new Topico(titulo: 'titulo', forum: forum)
        topico.save()
        def mensagem_ = new Mensagem(texto:'texto_', topico:topico)
        mensagem_.save()
        assertEquals mensagem_, Mensagem.getLast(forum)
    }
}
