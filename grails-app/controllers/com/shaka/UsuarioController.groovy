package com.shaka

/**
 *
 * Controller para manipular dados de usuario
 *
 */
class UsuarioController {
    static allowedMethods = [save: "POST", update: "POST"]
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
        saveOrUpdate(usuarioInstance, "create")
    }

    /**
     * Get para pagina de edicao do usuario
     */
    def edit = {
        def usuarioInstance = usuarioService.getCurrentUser()
        if (usuarioInstance) {
            return [usuarioInstance: usuarioInstance, diretorioImagem : usuarioService.diretorioImagemRelativo]
        } else {
          redirect(uri:"/")
        }
    }

    /**
     * Post para atualizar o usuario
     */
    def update = {
        def usuarioInstance = usuarioService.getCurrentUser()
        saveOrUpdate(usuarioInstance, "edit")
    }

    /**
     * Salva ou atualiza um usuario
     * @param usuario usuario
     * @param viewError string que identifica a pagina de erro
     * @return Retorna proxima pagina a ser exibida
     */
    private saveOrUpdate(def usuario, def viewError) {
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
                redirect(uri:"/")
            } else {
                render(view: viewError, model: [usuarioInstance: usuario])
            }
        } else {
            // usuario invalido mestre....
          redirect(uri:"/")
        }
    }
}
