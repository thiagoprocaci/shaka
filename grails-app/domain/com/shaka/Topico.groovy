package com.shaka

class Topico {
    String titulo
    Date dateCreated
    Long numeroVisitas

    static hasMany = [mensagemList:Mensagem]
    static belongsTo = [forum:Forum]

    static constraints = {
        titulo(blank:false)
        numeroVisitas (nullable:true)
    }

    static mapping = {
        mensagemList (sort:"dateCreated", order:"asc")
    }

    def beforeInsert = {
        dateCreated = new Date()
        numeroVisitas = 1L
    }
}
