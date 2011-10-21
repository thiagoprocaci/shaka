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
	String diretorioImagemFileSystem
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
        if(currentUser != null && !("anonymousUser".equals(currentUser)) && currentUser.id != null){
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
    public boolean save(Usuario usuario, String nomeImagem, MultipartFile imagem, String senha, String confirmacaoSenha) {
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
	 * Sincroniza imagem do usuario gravada no file system
	 * com imagem na pasta da aplicacao.
	 * Nao eh possivel carregar diretamente em uma aplicacao web
	 * uma imagem diretamente do file system por questoes de seguranca.
	 * Neste caso deve-se move-la para a pasta da aplicacao
	 */
	public void synchronizeImageUsuario() {
		def usuario = getCurrentUser()
		if(usuario && usuario.pathImagem) {
			boolean exist = imageService.existImage(diretorioImagem, usuario.pathImagem)
			if(Boolean.FALSE.equals(exist)) {
				// nao existe a imagem no diretorio da aplicacao..
				// razao principal: novo deploy..
				String pathSrc = diretorioImagemFileSystem + usuario.pathImagem
				String pathDst = diretorioImagem + usuario.pathImagem
				imageService.copyImage(pathSrc, pathDst)
			}
		}
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
            if(usuario.pathImagem) {
                // apaga a imagem antiga caso exista
				imageService.deleteImage(diretorioImagemFileSystem, usuario.pathImagem)
                imageService.deleteImage(diretorioImagem, usuario.pathImagem)
            }
            def nome = UUID.randomUUID().toString() + "_imagem_" + usuario.id + "_" + nomeImagem
			// salva imagem no file system
			imageService.saveImage(diretorioImagemFileSystem,nome,imagem)
			// salva uma copia da imagem no servidor de aplicacao para uso da aplicacao
			imageService.saveImage(diretorioImagem,nome,imagem)
            usuario.pathImagem = nome
        }
        return true
    }
}
