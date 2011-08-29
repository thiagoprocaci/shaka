package com.shaka.business

import com.shaka.Mensagem
import com.shaka.Topico

/**
 * Servicos de negocios dos debates criados no sistema
 */
class DebateService {
    def textValidationService

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
            if(topico.validate() & mensagem.validate()){
                if(textValidationService.hasJSCodeinHtml(mensagem.texto)){
                    mensagem.errors.rejectValue "texto", "codigoMalicioso"
                    return false
                }
                topico.save()
                mensagem.save()
                return true
            }
        }
        return false
    }
}
