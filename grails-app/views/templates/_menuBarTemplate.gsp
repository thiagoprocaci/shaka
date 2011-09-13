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
            <a href="#" name="userLink">
                 <g:message code="fazerLogin" />
           </a>
         </span>
     </sec:ifNotLoggedIn>
 </div>
 <sec:ifLoggedIn>
        <div id="menuUsuario" style="display:none">
            <g:imagemUsuario usuarioInstance="${userObject}"/>
            <div id="menuDadosUsuario">
                <div class="menuInfo boldText">${userObject?.nome}</div>
                <div class="menuInfo">${userObject?.email}</div>
                <div class="menuInfo"><br />
                   <div>
                      <g:link controller="usuario" action="edit"><g:message code="meuPerfil" /></g:link>
                   </div>
                   <div>
                       <g:link controller="usuario" action="changePassword"><g:message code="trocaSenha" /></g:link>
                   </div>
                </div>
            </div>
            <br class="clear">
            <hr />
            <div>
                 <g:link controller="logout"><g:message code="sair" /></g:link>
            </div>
        </div>
 </sec:ifLoggedIn>
 <sec:ifNotLoggedIn>
     <div id="menuUsuario" style="display:none">
        <g:render template='/templates/ajaxLogin'/>
    </div>
 </sec:ifNotLoggedIn>

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

