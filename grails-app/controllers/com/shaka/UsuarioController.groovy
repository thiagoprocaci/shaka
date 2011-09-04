package com.shaka

class UsuarioController {
    static allowedMethods = [save: "POST", update: "POST"]
    def usuarioService

    def index = {
        redirect(action: "create", params: params)
    }

    def create = {
        def usuarioInstance = new Usuario()
        usuarioInstance.properties = params
        return [usuarioInstance: usuarioInstance]
    }

    def save = {
        def usuarioInstance = new Usuario(params)
        def nomeOriginal = null
        def file = null
        if(params.file) {
            nomeOriginal = params.file.originalFilename
            file = request.getFile("file")
        }
        if (usuarioService.save(usuarioInstance, nomeOriginal, file, params.senha, params.confirmacaoSenha)) {
            flash.message = "${message(code: 'salvoSucesso')}"
            redirect(uri:"/")
        } else {
            render(view: "create", model: [usuarioInstance: usuarioInstance])
        }
    }

    def edit = {
        def usuarioInstance = usuarioService.getCurrentUser()
        if (usuarioInstance) {
            return [usuarioInstance: usuarioInstance, diretorioImagem : usuarioService.diretorioImagemRelativo]
        } else {
          redirect(uri:"/")
        }
    }

    def update = {
        def usuarioInstance = usuarioService.getCurrentUser()
        if (usuarioInstance) {
            usuarioInstance.properties = params
            def nomeOriginal = null
            def file = null
            if(params.file) {
                nomeOriginal = params.file.originalFilename
                file = request.getFile("file")
            }
            if (usuarioService.save(usuarioInstance, nomeOriginal, file, params.senha, params.confirmacaoSenha)) {
                flash.message = "${message(code: 'salvoSucesso')}"
                redirect(uri:"/")
            } else {
                render(view: "edit", model: [usuarioInstance: usuarioInstance])
            }
        } else {
		  redirect(uri:"/")
		}
    }
}
