package com.shaka

import java.util.Date;

class Mensagem {
    String texto
    Date dateCreated

    static belongsTo = [topico:Topico]

    static constraints = { texto(blank:false) }

    static mapping = { texto type: 'text' }

    def beforeInsert = { dateCreated = new Date() }
}
