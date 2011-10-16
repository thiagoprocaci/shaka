package com.shaka.business

import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile

import com.shaka.Usuario

/**
 *
 * Servicos de usuario
 *
 */
class UsuarioService {
    static transactional = false
    String diretorioImagem
    String diretorioImagemRelativo
    long tamanhoMaximoImagem = 512000 // (512000 bytes = 500 KB)
    def springSecurityService
    def imageService

    /**
     * Salva imagem temporaria do usuario
     * @param image imagem a ser salva
     * @return Retorna imagem
     */
    public boolean saveUserImage(String nomeImagem, MultipartFile image) {
        def usuario = getCurrentUser()
        return save(usuario, nomeImagem, image, null, null)
    }

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
    public boolean save(Usuario usuario, String nomeImagem, MultipartFile imagem, String senha, String confirmacaoSenha){
        // esse metodo deve ser privado
        if(usuario == null) {
            return false
        }
        boolean senhaDiferente = false
        boolean saved = false
        if(StringUtils.hasText(senha)) {;
            if(senha != confirmacaoSenha) {
                senhaDiferente = true
            }
            if(usuario.password != springSecurityService.encodePassword(senha)){
                usuario.password = springSecurityService.encodePassword(senha)
            }
        }
        if(usuario.validate()) {
            if(Boolean.FALSE.equals(senhaDiferente)) {
                saved = usuario.save() && uploadImagemUsuario(usuario, nomeImagem, imagem)
            } else  if(Boolean.TRUE.equals(senhaDiferente)) {
                usuario.errors.rejectValue "password", "senhaDiferenteConfirmacao"
            }
        }
        // somente reautentica se o usuario tenha sido salvo e a sua senha alterada
        if(saved && StringUtils.hasText(senha)) {
            springSecurityService.reauthenticate(usuario.username, senha)
        }
        return saved
    }

    /**
     * Realiza upload da imagem do usuario
     * @param usuario usuario com a imagem
     * @param nomeImagem nome da imagem
     * @param imagem foto do usuario
     * @return Retorna true caso a imagem seja transferida com sucesso. Caso contrario false.
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
            def nome = UUID.randomUUID().toString() + "_imagem_" + usuario.id + "_" + nomeImagem
            imageService.saveImage(diretorioImagem,nome,imagem)
            usuario.pathImagem = nome
        }
        return true
    }
}
