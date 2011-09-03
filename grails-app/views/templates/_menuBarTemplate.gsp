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
         <span class="menuButton">
            <g:link class="list" action="edit" controller="usuario">
                <g:message code="meusDados"  />
            </g:link>
        </span>
     </sec:ifLoggedIn>
     <sec:ifNotLoggedIn>
         <span class="menuButton">
            <g:link class="list" action="auth" controller="login">
                <g:message code="fazerLogin" />
            </g:link>
         </span>
     </sec:ifNotLoggedIn>
 </div>