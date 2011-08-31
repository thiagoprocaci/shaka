<div class="historicoMensagemList">
     <table>
        <tbody>
             <g:each in="${mensagemList}" var="m" status="i">
                 <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                     <td valign="top" class="value">
                             ${m?.texto?.decodeHTML()}
                     </td>
                 </tr>
             </g:each>
         </tbody>
     </table>
 </div>