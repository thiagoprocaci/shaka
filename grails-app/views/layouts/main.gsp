<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />

        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
        <g:javascript library="prototype" />
        <g:javascript library="outside-events.min" />
        <g:javascript src='prototype/scriptaculous.js?load=effects' />

    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
        <div id="grailsLogo">
            <a href="http://grails.org"><img src="${resource(dir:'images',file:'grails_logo.png')}" alt="Grails" border="0" /></a>
        </div>

        <g:layoutBody />

<!--
        <g:render template="/templates/fileUpload"></g:render>
         -->
    </body>
</html>