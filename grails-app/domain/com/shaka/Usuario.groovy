package com.shaka

import java.util.Date

import com.shaka.authentication.SecUser

class Usuario extends SecUser{
    //TODO mapear paginas de acesso no security

    String nome
    String email
    String assinatura
    Date dateCreated
    String pathImagem

    static hasMany = [mensagemList:Mensagem, topicoList:Topico]

    static constraints = {
        email(unique:true, blank: false, email:true)
        nome(blank: false, nullable:false)
        assinatura(blank: true, nullable:true)
        pathImagem(blank: true, nullable:true)
    }

    static mapping = { assinatura type: 'text' }

    def beforeInsert = { dateCreated = new Date() }
}
