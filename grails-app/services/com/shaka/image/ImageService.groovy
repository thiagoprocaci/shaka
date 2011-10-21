package com.shaka.image

import org.springframework.web.multipart.MultipartFile



/**
 *
 * Servicos para manipulacao de imagens
 *
 */
class ImageService {
    def extensaoPermitidasList = ["gif", "jpg", "jpeg", "png"]
    static transactional = false

    /**
     * Realiza a validacao da extensao do arquivo (imagem)
     * @param nomeImagem nome da imagem
     * @return Retorna true caso a extensao do arquivo seja valida. Caso contrario false.
     */
    public boolean extensaoValida(String nomeImagem){
        String extensao = getExtensao(nomeImagem)
        return extensaoPermitidasList.contains(extensao)
    }

    /**
     *
     * @param nomeImagem nome da imagem
     * @return Retorna extensao da imagem.
     */
    public String getExtensao(String nomeImagem){
        if(nomeImagem == null || !nomeImagem.contains(".")){
            return ""
        }
        return nomeImagem.substring(nomeImagem.lastIndexOf("."), nomeImagem.length()).toLowerCase().replace(".","")
    }

    /**
     * Grava uma imagem
     * @param diretorio diretorio onde a imagem sera salva
     * @param nomeImagem nome do arquivo
     * @param imagem que sera salva
     * @throws IOException caso o diretorio nao exista
     */
    public void saveImage(String diretorio, String nomeImagem, MultipartFile imagem){
        if(imagem != null && !imagem.empty){
             File file = new File(diretorio + nomeImagem)
             FileOutputStream fop = new FileOutputStream(file);
             fop.write(imagem.getBytes());
             fop.flush();
             fop.close();
        }
    }


    /**
     * Exclui uma imagem
     * @param diretorio diretorio onde a imagem sera salva
     * @param nomeImagem nome do arquivo
     * @throws IOException caso o diretorio nao exista
     */
    public void deleteImage(String diretorio, String nomeImagem){
        def file = new File(diretorio + nomeImagem)
        file.delete()
    }

	/**
	* Verifica se uma imagem existe
	* @param diretorio diretorio onde a imagem se localiza
	* @param nomeImagem nome do arquivo
	*
	*/
	public boolean existImage(String diretorio, String nomeImagem){
		def file = new File(diretorio + nomeImagem)
		return file.exists()
	}

	/**
	 * Copia uma imagem de um diretorio para outro
	 * @param pathSrc caminho da imagem original
	 * @param pathDst caminho da copia da imagem
	 * @throws IOException
	 */
	public void copyImage(String pathSrc, String pathDst) throws IOException{
		File src = new File(pathSrc)
		File dst = new File(pathDst)
		InputStream in_ = new FileInputStream(src)
		OutputStream out = new FileOutputStream(dst)
		byte[] buf = new byte[1024]
		int len
		while ((len = in_.read(buf)) > 0) {
			out.write(buf, 0, len)
		}
		in_.close()
		out.close()
	}

    /**
     * Verifica se o tamanho da imagem eh valida
     * @param imagem imagem a ser validada
     * @param tamanhoMax tamanho maximo da imagem em bytes
     * @return retorna true caso a imagem tenha um tamanho valido. Caso contrario false.
     */
    public boolean tamanhoImagemValido(MultipartFile imagem, long tamanhoMax) {
        if (imagem == null || imagem.getSize() > tamanhoMax || imagem.getSize() == 0) {
            return false;
        }
        return true;
    }

}


