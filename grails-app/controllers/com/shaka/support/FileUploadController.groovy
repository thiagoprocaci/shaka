package com.shaka.support

class FileUploadController {

    def upload = {
		if(params.file) {
			def nomeOriginal = params.file.originalFilename
			def file = request.getFile("file")
		}
		render "teste"
	}
}
