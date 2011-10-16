package com.shaka.support

import grails.converters.JSON



class FileUploadController {

    def usuarioService

    def upload = {
         if(params.file) {
            def nomeOriginal = params.file.originalFilename
            def file = request.getFile("file")
            def success = usuarioService.saveUserImage(nomeOriginal, file)
            if(success){
                render([success: true, pathImage: usuarioService.getCurrentUser()?.pathImagem] as JSON)
            } else {
                render([success: false] as JSON)
            }
        }
       render([success: false] as JSON)
    }
}
