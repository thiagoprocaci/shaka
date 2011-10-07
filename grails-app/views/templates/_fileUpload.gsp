<form action='${request.contextPath}/fileUpload/upload' method='POST' id='ajaxUploadImageForm' name='ajaxUploadImageForm' enctype="multipart/form-data"">
    <input type="file" name="file" id="file"/>
    <input type="submit" onclick="uploadImageAjax(); return false;" value="${message(code: 'upload')}" />
</form>
<div id="divUpload" style="display:none"></div>


<script type='text/javascript'>

function uploadImageAjax() {
   Form.enable(document.ajaxUploadImageForm);
   Element.update('divUpload', '${message(code:"enviando")}');
   Element.show('divUpload');

   var form = document.ajaxUploadImageForm;
   var params = buildMessage(document.getElementById('file'), 'AaB03x');
   Form.disable(form);
   new Ajax.Request(form.action, {
      contentType: 'multipart/form-data; charset=UTF-8; boundary=AaB03x',
      postBody: params,
      onSuccess: function(response) {
         Form.enable(document.ajaxUploadImageForm);
         var responseText = response.responseText || '[]';
         var json = responseText.evalJSON();
         if (json.success) {
             Element.update('divUpload', "<img src='" + json.pathImage + "' />");
         }
         else {
            Element.update('divUpload', responseText);
         }
      }
   });
}

function buildMessage(element, boundary) {
    var CRLF = "\r\n";
    var parts = [];
    var part = "";

    var fieldName = element.name;
    var fileName = element.files[0].fileName;

    part += 'Content-Disposition: form-data; ';
    part += 'name="' + fieldName + '"; ';
    part += 'filename="'+ fileName + '"' + CRLF;

    part += "Content-Type: application/octet-stream";
    part += CRLF + CRLF;

    part += element.files[0].getAsBinary() + CRLF;

    parts.push(part);

    var request = "--" + boundary + CRLF;
    request+= parts.join("--" + boundary + CRLF);
    request+= "--" + boundary + "--" + CRLF;
    return request;

}
</script>
