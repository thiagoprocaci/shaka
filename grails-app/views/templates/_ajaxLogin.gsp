<div id='ajaxLogin'>
   <div class='inner'>
   <div class='fheader'><g:message code="entreComDados" /></div>
   <form action='${request.contextPath}/j_spring_security_check' method='POST'
       id='ajaxLoginForm' name='ajaxLoginForm' class='cssform'>
      <p>
         <label for='username'><g:message code="login" /></label>
         <input type='text' class='text_' name='j_username' id='username' />
      </p>
      <p>
         <label for='password'><g:message code="senha" /></label>
         <input type='password' class='text_' name='j_password' id='password' />
      </p>
      <p>
         <label for='remember_me'><g:message code="lembrarMe" /></label>
         <input type='checkbox' class='chk' id='remember_me'  name='_spring_security_remember_me'/>
      </p>
      <p>
      	 <input type="submit" onclick="authAjax(); return false;" value="${message(code:'entrar')}">
      </p>
   </form>
    <div style='display: none; text-align: left;' id='loginMessage'></div>
   </div>
</div>

<script type='text/javascript'>

function authAjax() {
   Form.enable(document.ajaxLoginForm);
   Element.update('loginMessage', '${message(code:"enviando")}');
   Element.show('loginMessage');

   var form = document.ajaxLoginForm;
   var params = Form.serialize(form);
   Form.disable(form);
   new Ajax.Request(form.action, {
      method: 'POST',
      postBody: params,
      onSuccess: function(response) {
         Form.enable(document.ajaxLoginForm);
         var responseText = response.responseText || '[]';
         var json = responseText.evalJSON();
         if (json.success) {
        	 window.location.reload();
         }
         else if (json.error) {
            Element.update('loginMessage', "<span class='errorMessage'>" +
                                           json.error + '</error>');
         }
         else {
            Element.update('loginMessage', responseText);
         }
      }
   });

}
</script>