<g:formRemote name="fileUploadForm" url="[controller:'fileUpload',action:'upload']" update="divUpload" >
    <input type="file" name="file" id="file"/>
    <input type="submit" value="${message(code: 'upload')}" />
</g:formRemote >
<div id="divUpload"></div>
