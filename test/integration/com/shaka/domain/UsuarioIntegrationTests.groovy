package com.shaka.domain

import grails.test.*

import com.shaka.Forum
import com.shaka.Mensagem
import com.shaka.Topico
import com.shaka.Usuario

class UsuarioIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGetByMensagem() {
        def usuario = getUsuario()
        usuario.save()
        def forum = new Forum(nome:"nome", descricao:"descricao")
        forum.save()
        def topico = new Topico(titulo: 'titulo', forum: forum)
        topico.save()
        def mensagem = new Mensagem(texto:'texto', topico:topico, usuario:usuario)
        mensagem.save()
        def mensagem_ = new Mensagem(texto:'texto', topico:topico)
        mensagem_.save()

        assertEquals usuario, Usuario.getByMensagem(mensagem)
        assertNull Usuario.getByMensagem(mensagem_)
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
