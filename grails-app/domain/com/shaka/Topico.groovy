package com.shaka

class Topico {
    String titulo
    Date dateCreated

    static hasMany = [mensagemList:Mensagem]
    static belongsTo = [forum:Forum]

    static constraints = { titulo(blank:false) }

    static mapping = {
        mensagemList (sort:"dateCreated", order:"asc")
    }

    def beforeInsert = { dateCreated = new Date() }
}
