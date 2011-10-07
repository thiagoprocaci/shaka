package com.shaka.support

import grails.converters.JSON



class FileUploadController {

    def usuarioService

    def upload = {
         if(params.file) {
            def nomeOriginal = params.file.originalFilename
            def file = request.getFile("file")
            def image = usuarioService.saveTempUserImage(nomeOriginal, file)
            if(image){
                render([success: true, pathImage: image.getAbsolutePath()] as JSON)
            } else {
                render([error: true] as JSON)
            }

        }
        render ""
    }
}
