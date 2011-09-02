package com.shaka

/**
 *
 * tagLig de suporte. Contem tags gerais para uso no sistema
 *
 */
class SupportTagLib {

    def menu  = { attrs ->
        out << render(template:"/templates/menuBarTemplate")
    }

    def redirectMainPage = {
        response.sendRedirect("${request.contextPath}/forum/list.gsp")
    }
}
