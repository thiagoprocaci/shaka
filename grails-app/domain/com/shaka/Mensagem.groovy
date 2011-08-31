package com.shaka

import java.util.Date;

class Mensagem {
    String texto
    Date dateCreated

    static belongsTo = [topico:Topico]

    static constraints = { texto(blank:false) }

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
        def mensagem = criteria.list {

            max:1
            offset:0
            sort:"dateCreated"
            order:"desc"
        }
        return mensagem
    }
}
