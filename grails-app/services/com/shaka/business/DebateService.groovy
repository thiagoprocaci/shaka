package com.shaka.business

import com.shaka.AvaliacaoMensagem;
import com.shaka.Mensagem
import com.shaka.Topico

/**
 * Servicos de negocios dos debates criados no sistema
 */
class DebateService {
    def textValidationService
    def usuarioService

    static transactional = true

    /**
     * Salva um topico e sua nova mensagem
     * @param topico topico a ser salvo
     * @param mensagem mensagem a ser salva
     * @return Retorna true caso o topico e a sua mensagem seja salvo com sucesso. Caso contrario, false.
     */
    def save(Topico topico, Mensagem mensagem) {
        if(topico != null && mensagem != null){
            mensagem.topico = topico
            if(!textValidationService.hasTextInHtml(mensagem.texto)){
                mensagem.texto = ''
            }
            if(topico.validate() & mensagem.validate()) {
                if(textValidationService.hasJSCodeinHtml(mensagem.texto) || textValidationService.hasCCSCodeinHtml(mensagem.texto)){
                    mensagem.errors.rejectValue "texto", "codigoMalicioso"
                    return false
                }
                mensagem.usuario = usuarioService.getCurrentUser()
                if(topico.id == null){
                    topico.usuario = usuarioService.getCurrentUser()
                }
                topico.save()
                mensagem.save()
                return true
            }
        }
        return false
    }

    /**
     * Incrementa contador de visitas do topico
     * @param id id do topico
     * @return topico atualizado
     */
    def visitarTopico(def id) {
        Topico topico = Topico.get(id)
        if(topico) {
            topico.numeroVisitas++
            return topico.save()
        }
        return null
    }

    /**
     * Avalia se um usuario gostou ou nao de uma mensagem
     * @param mensagem mensagem a ser avaliada
     * @param avaliacao valor booleano com a avaliacao da mensagem
     */
    def avaliarMensagem(Mensagem mensagem, boolean avaliacao) {
        if(mensagem && mensagem.id != null){
            def usuario = usuarioService.getCurrentUser();
            if(usuario) {
                def avaliacaoMensagem = new AvaliacaoMensagem()
                avaliacaoMensagem.usuario = usuario
                avaliacaoMensagem.mensagem = mensagem
                avaliacaoMensagem.positivo = avaliacao
                avaliacaoMensagem.save()
            }
        }
    }
}
