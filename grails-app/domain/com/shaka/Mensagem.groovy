package com.shaka

import java.util.Date

import org.springframework.util.StringUtils

class Mensagem {
    String texto
    Date dateCreated

    static belongsTo = [topico:Topico]
    static hasOne = [usuario:Usuario]
    static hasMany = [avaliacaoMensagemList:AvaliacaoMensagem]

    static constraints = {
        texto blank:false
        usuario nullable:true
    }

    static mapping = { texto type: 'text' }

    def beforeInsert = { dateCreated = new Date() }

    /**
     * Conta mensagens de um forum
     */
    def static countByForum =  { forum ->
        def criteria = Mensagem.createCriteria()
        def total = criteria.get {
            topico {
                eq('forum',forum)
            }
            projections {
                countDistinct('id')
            }
        }
        return total
    }

    /**
     * Retorna ultima mensagem do forum
     */
    def static getLast = { forum ->
        def criteria = Mensagem.createCriteria()
        def mensagemList = criteria.list {
            topico {
                eq('forum',forum)
            }
            order("dateCreated","desc")
            maxResults(1)
        }
        if(mensagemList != null && !mensagemList.isEmpty()){
            return mensagemList.get(0)
        }
        return null
    }
}
