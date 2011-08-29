package com.shaka.validation

import java.util.regex.Matcher;
import java.util.regex.Pattern

import org.springframework.util.StringUtils

/**
 * Entidade respondavel pela validacao de textos
 */
class TextValidationService {

    static transactional = false

    /**
     * Verifica se existe texto em um html
     * @param htmlText texto html a ser validado
     * @return Retorna true caso o parametro htmlText tenha texto. Caso contrario false.
     */
    def hasTextInHtml(String htmlText) {
        if(htmlText == null){
            return  false
        }
        // substituicao dos caracteres especiais para abrir e fechar tags
        def txt = htmlText.replace('&lt;', '<').replace('&gt;', '>')
        // remove comentario e tags html
        txt = txt.replaceAll(/<!--.*?-->/, '').replaceAll(/<.*?>/, '')
        // remove espacos em branco
        txt = txt.replace('&nbsp;', '')
        return StringUtils.hasText(txt)
    }

    /**
    * Verifica se existe java script em um html
    * @param htmlText texto html a ser validado
    * @return Retorna true caso o parametro htmlText tenha java script. Caso contrario false.
    */
    def hasJSCodeinHtml(String htmlText) {
        if(htmlText == null){
            return  false
        }
        // substituicao dos caracteres especiais para abrir e fechar tags
        def txt = htmlText.replace('&lt;', '<').replace('&gt;', '>')
        return txt.toLowerCase().contains('<script')

    }
}
