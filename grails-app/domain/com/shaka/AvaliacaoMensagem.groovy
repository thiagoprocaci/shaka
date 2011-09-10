package com.shaka

class AvaliacaoMensagem {
    boolean positivo
    static belongsTo = [mensagem:Mensagem,usuario:Usuario]

    static constraints = {
    }
}
