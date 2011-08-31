package com.shaka

import java.util.Date

class Forum {
    String nome
    String descricao
    Date dateCreated

    static hasMany = [topicoList:Topico]

    static constraints = {
        nome(blank:false)
        descricao(blank:false)
    }

    static mapping = {
        topicoList (sort:"dateCreated", order:"asc")
    }

    def beforeInsert = { dateCreated = new Date() }
}
