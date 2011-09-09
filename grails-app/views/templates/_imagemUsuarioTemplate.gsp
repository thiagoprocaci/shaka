<div class="divFoto">
   <g:if test="${usuarioInstance?.pathImagem != null}">
       <img src="${resource(dir: usuarioInstance?.diretorioImagemRelativo , file: usuarioInstance?.pathImagem)}"  />
   </g:if>
   <g:if test="${usuarioInstance?.pathImagem == null}">
       <img src="${resource(dir: 'images/icones', file: 'anonimo.jpg')}"  />
   </g:if>
</div>