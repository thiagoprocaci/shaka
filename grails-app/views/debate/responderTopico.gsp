<%@ page import="com.shaka.Topico"%>
<%@ page import="com.shaka.Mensagem"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'topico.label', default: 'Topico')}" />
<title><g:message code="default.create.label"
		args="[entityName]" />
</title>
</head>
<body>
	<g:formularioSaveTopico action="saveResposta"
		mensagemInstance="${mensagemInstance}"
		topicoInstance="${topicoInstance}"
		message="${flash.message}" />

</body>
</html>
