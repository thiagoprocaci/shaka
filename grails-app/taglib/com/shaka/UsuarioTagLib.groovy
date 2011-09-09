package com.shaka

/**
 *
 * tagLib para manipular dados de usuario
 *
 */
class UsuarioTagLib {

    /**
    * tag para o formulario de cadastro/edicao do usuario
    */
   def formularioUsuario = { attrs ->
       out << render(template:"/templates/usuarioTemplate",
           model:[usuarioInstance:attrs.usuarioInstance, message:attrs.message, action:attrs.action])
   }

   def imagemUsuario = { attrs ->
       out << render(template:"/templates/imagemUsuarioTemplate",
           model:[usuarioInstance:attrs.usuarioInstance])

   }

}
