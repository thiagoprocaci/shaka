package com.shaka.business

import org.springframework.web.multipart.MultipartFile

import com.shaka.Usuario

class UsuarioService {
    static transactional = true
    String diretorioImagem
    String diretorioImagemRelativo
    long tamanhoMaximoImagem = 512000 // (512000 bytes = 500 KB)
    def springSecurityService
    def imageService

    /**
     *
     * @return Retorna o usuario corrente da aplicacao
     */
    public Usuario getCurrentUser(){
        def currentUser = springSecurityService.getPrincipal()
        if(currentUser != null && currentUser.id != null){
            return Usuario.get(currentUser.id)
        }
        return null
    }

    /**
     *  Salva  usuario
     * @param usuario usuario a ser atualizado
     * @param nomeImagem nome da imagem
     * @param imagem foto do usuario
     * @return Retorna true caso seja bem sucedida a atualizacao
     */
    public boolean save(Usuario usuario, String nomeImagem, MultipartFile imagem){
        if(usuario == null){
            return false
        }
        def saved =  !usuario.hasErrors() && usuario.save() && uploadImagemUsuario(usuario, nomeImagem, imagem)
        return saved
    }
    /**
     * Cria novo usuario e autentica o mesmo no sistema.
     * Metodo usado durante o cadastro de usuario
     * @param usuario usuario a ser salvo
     * @return Retorna true caso o usuario seja salvo com sucesso. Caso contrario false.
     */
    public boolean create(Usuario usuario) {
        // TODO criar um servico de email para notificar o usuario
        if (usuario != null && usuario.validate()) {
            String password = usuario.password
            // usuario.password = springSecurityService.encodePassword(usuario.password)
            usuario.save()
            springSecurityService.reauthenticate(usuario.username, password)
            return true
        }
        return false
    }

    /**
     * Realiza upload da imagem do usuario
     * @param usuario usuario com a imagem
     * @param nomeImagem nome da imagem
     * @param imagem foto do usuario
     * @return Retorna true caso a imagem seja tranferida com sucesso. Caso contrario false.
     */
    private boolean uploadImagemUsuario(Usuario usuario, String nomeImagem, MultipartFile imagem){
        if(imagem != null && !imagem.empty && nomeImagem != null){
            if(!imageService.extensaoValida(nomeImagem)){
                usuario.errors.rejectValue "pathImagem", "extensaoArquivoInvalida"
                return false
            } else if(!imageService.tamanhoImagemValido(imagem,tamanhoMaximoImagem)){
                usuario.errors.rejectValue "pathImagem", "tamanhoArquivoInvalido"
                return false
            }
            if(usuario.pathImagem){
                // apaga a imagem antiga caso exista
                imageService.deleteImage(diretorioImagem, usuario.pathImagem)
            }
            def nome = "imagem_" + usuario.id
            imageService.saveImage(diretorioImagem,nome,imagem)
            usuario.pathImagem = nome
        }
        return true
    }
}
