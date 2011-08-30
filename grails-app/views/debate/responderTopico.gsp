<%@ page import="com.shaka.Topico"%>
<%@ page import="com.shaka.Mensagem"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title><g:message code="shakaProject"  /></title>
</head>
<body>
    <g:formularioSaveTopico saveAction="saveResposta"
        visualizarAction="visualizar"
        voltarAction="showTopico"
        mensagemInstance="${mensagemInstance}"
        topicoInstance="${topicoInstance}"
        message="${flash.message}" />



</body>
</html>
