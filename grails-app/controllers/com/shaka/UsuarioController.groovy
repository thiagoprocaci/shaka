package com.shaka

import org.springframework.util.StringUtils;

/**
 *
 * Controller para manipular dados de usuario
 *
 */
class UsuarioController {
    static allowedMethods = [save: "POST", update: "POST", updatePassword: "POST", updateImage: "POST"]
    def usuarioService

    def index = {
        redirect(action: "create", params: params)
    }

    /**
     * Get da pagina de criacao do usuario
     */
    def create = {
        def usuarioInstance = new Usuario()
        usuarioInstance.properties = params
        return [usuarioInstance: usuarioInstance]
    }

    /**
     * Post para salvar um usuario
     */
    def save = {
        def usuarioInstance = new Usuario()
        saveOrUpdate(usuarioInstance, "create", null)
    }

    /**
     * Get para pagina de edicao do usuario
     */
    def edit = {
        def usuarioInstance = usuarioService.getCurrentUser()
        if (usuarioInstance) {
            return [usuarioInstance: usuarioInstance]
        } else {
          redirect(uri:"/")
        }
    }

	/**
	 * Get para a pagina de trocar imagem do usuario
	 */
	def changeImage = {
		def usuarioInstance = usuarioService.getCurrentUser()
		if (usuarioInstance) {
			return [usuarioInstance: usuarioInstance, diretorioImagem : usuarioService.diretorioImagemRelativo]
		} else {
		  redirect(uri:"/")
		}
	}

	/**
	 * Post para atualizar a imagem do usuario
	 */
	def updateImage = {
		def usuarioInstance = usuarioService.getCurrentUser()
		if(params.file == null || request.getFile("file") == null || request.getFile("file").empty) {
			usuarioInstance.errors.rejectValue "pathImagem", "nenhumaImagemSelecionada"
			render(view: "changeImage", model: [usuarioInstance: usuarioInstance, diretorioImagem : usuarioService.diretorioImagemRelativo])
		} else {
			saveOrUpdate(usuarioInstance, "changeImage", "changeImage")
		}
	}

    /**
     * Post para atualizar o usuario
     */
    def update = {
        def usuarioInstance = usuarioService.getCurrentUser()
        saveOrUpdate(usuarioInstance, "edit", "edit")
    }

    /**
     * Get para a tela de trocar senha do usuario
     */
    def changePassword = {
        def usuarioInstance = usuarioService.getCurrentUser()
        if (usuarioInstance) {
            return [usuarioInstance: usuarioInstance]
        } else {
          redirect(uri:"/")
        }
    }

    /**
     * Post para atualizar a senha do usuario
     */
    def updatePassword = {
        def usuarioInstance = usuarioService.getCurrentUser()
        if(!StringUtils.hasText(params.senha)) {
            usuarioInstance.errors.rejectValue "password", "senhaBranco"
            render(view: "changePassword", model: [usuarioInstance: usuarioInstance])
        } else {
            saveOrUpdate(usuarioInstance, "changePassword","changePassword")
        }
    }

    /**
     * Salva ou atualiza um usuario
     * @param usuario usuario
     * @param viewError string que identifica a pagina de erro
     * @return Retorna proxima pagina a ser exibida
     */
    private saveOrUpdate(def usuario, def viewError, def viewRedirect) {
        if (usuario) {
            usuario.properties = params
            def nomeOriginal = null
            def file = null
            if(params.file) {
                nomeOriginal = params.file.originalFilename
                file = request.getFile("file")
            }
            if (usuarioService.save(usuario, nomeOriginal, file, params.senha, params.confirmacaoSenha)) {
                flash.message = "${message(code: 'salvoSucesso')}"
                if(viewRedirect) {
				  redirect(action:viewRedirect)
				} else {
				  redirect(uri:"/")
				}

            } else {
                render(view: viewError, model: [usuarioInstance: usuario])
            }
        } else {
            // usuario invalido mestre....
          redirect(uri:"/")
        }
    }
}
