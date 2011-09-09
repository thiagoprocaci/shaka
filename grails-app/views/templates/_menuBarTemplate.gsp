<%@ page import="com.shaka.Usuario" %>
<div class="nav">
     <span class="menuButton">
         <a class="home" href="${createLink(uri: '/')}">
             <g:message code="home"/>
         </a>
     </span>
     <sec:ifNotLoggedIn>
         <span class="menuButton">
            <g:link class="create" action="create" controller="usuario">
                <g:message code="cadastrarSe"  />
            </g:link>
        </span>
     </sec:ifNotLoggedIn>
     <sec:ifLoggedIn>
          <g:set var="userObject" value="${Usuario.findByUsername(sec.loggedInUserInfo(field:'username'))}"/>
         <span class="spanUser menuButton">
            <a href="#" name="userLink">
                 ${userObject?.nome}
           </a>
        </span>
     </sec:ifLoggedIn>
     <sec:ifNotLoggedIn>
         <span class="spanUser menuButton">
            <g:link class="list" action="auth" controller="login">
                <g:message code="fazerLogin" />
            </g:link>
         </span>
     </sec:ifNotLoggedIn>
 </div>
 <sec:ifLoggedIn>
        <div id="menuUsuario" style="display:none">
            <g:imagemUsuario usuarioInstance="${userObject}"/>
            <div id="menuDadosUsuario">
                <div class="menuInfo boldText">${userObject?.nome}</div>
                <div class="menuInfo">${userObject?.email}</div>
                <div class="menuInfo"><br>
                   <g:link controller="usuario" action="edit"><g:message code="meuPerfil" /></g:link>
                </div>
            </div>
            <br class="clear">
            <hr />
            <div>
                 <g:link controller="logout"><g:message code="sair" /></g:link>
            </div>
        </div>
    <script type="text/javascript">
         var $j = jQuery.noConflict();
         $j(document).ready(function() {
             $j('a[name="userLink"]').click(function(event) {
                if($j('#menuUsuario').is(':visible')) {
                    $j('#menuUsuario').hide();
                } else {
                    $j('#menuUsuario').show();
                }
             });
             $j('#menuUsuario').bind( 'clickoutside', function(event) {
                 if($j(event.target).attr('name') != "userLink") {
                     $j('#menuUsuario').hide();
                  }
               });
         });
    </script>
 </sec:ifLoggedIn>
 <sec:ifNotLoggedIn>
 <script type='text/javascript'>

// center the form
Event.observe(window, 'load', function() { var ajaxLogin = $('ajaxLogin'); $('ajaxLogin').style.left = ((document.body.getDimensions().width - ajaxLogin.getDimensions().width) / 2) + 'px'; $('ajaxLogin').style.top = ((document.body.getDimensions().height - ajaxLogin.getDimensions().height) / 2) + 'px'; });

function showLogin() { $('ajaxLogin').style.display = 'block'; }

function cancelLogin() { Form.enable(document.ajaxLoginForm); Element.hide('ajaxLogin'); }

function authAjax() { Form.enable(document.ajaxLoginForm); Element.update('loginMessage', 'Sending request ...'); Element.show('loginMessage');

var form = document.ajaxLoginForm; var params = Form.serialize(form); Form.disable(form); new Ajax.Request(form.action, { method: 'POST', postBody: params, onSuccess: function(response) { Form.enable(document.ajaxLoginForm); var responseText = response.responseText || '[]'; var json = responseText.evalJSON(); if (json.success) { Element.hide('ajaxLogin'); $('loginLink').update('Logged in as ' + json.username + ' (<%=link(controller: 'logout') { 'Logout' }%>)'); } else if (json.error) { Element.update('loginMessage', "<span class='errorMessage'>" + json.error + '</error>'); } else { Element.update('loginMessage', responseText); } } }); }

</script>
 </sec:ifNotLoggedIn>

