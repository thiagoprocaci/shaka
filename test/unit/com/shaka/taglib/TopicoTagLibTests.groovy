package com.shaka.taglib

import grails.test.*

import com.shaka.Mensagem
import com.shaka.Topico
import com.shaka.TopicoTagLib



class TopicoTagLibTests extends TagLibUnitTestCase {

    TopicoTagLibTests(){
        super(TopicoTagLib.class)
    }


    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFormularioSaveTopico() {
        def topicoInstance = new Topico(id:1)
        def mensagemInstance = new Mensagem(id:2)
        def saveAction = "save"
        def visualizarAction = "visualizar"
        def message = "message"
        def attrs = [topicoInstance:topicoInstance,mensagemInstance:mensagemInstance,saveAction:saveAction,visualizarAction:visualizarAction,message:message]
        tagLib.formularioSaveTopico(attrs)
        assertNotNull tagLib.renderArgs
        assertNotNull tagLib.renderArgs.template
        assertNotNull tagLib.renderArgs.model
        assertNotNull tagLib.renderArgs.model.topicoInstance
        assertNotNull tagLib.renderArgs.model.mensagemInstance
        assertNotNull tagLib.renderArgs.model.saveAction
        assertNotNull tagLib.renderArgs.model.visualizarAction
        assertNotNull tagLib.renderArgs.model.message

        assertEquals 5, tagLib.renderArgs.model.size()
        assertEquals "/templates/saveTopicoTemplate", tagLib.renderArgs.template
        assertEquals topicoInstance, tagLib.renderArgs.model.topicoInstance
        assertEquals mensagemInstance, tagLib.renderArgs.model.mensagemInstance
        assertEquals saveAction, tagLib.renderArgs.model.saveAction
        assertEquals visualizarAction, tagLib.renderArgs.model.visualizarAction
        assertEquals message, tagLib.renderArgs.model.message
    }

    void testHistoricoMensagens() {
         def mensagemList = ["a", "b"]
         def diretorioImagem = "abc"
         def mensagemTotal = 2
         def topicoInstance = new Topico(id:1)
         def attrs = [mensagemList: mensagemList, diretorioImagem:diretorioImagem,mensagemTotal:mensagemTotal, topicoInstance:topicoInstance]
         tagLib.historicoMensagens(attrs)
         assertNotNull tagLib.renderArgs
         assertNotNull tagLib.renderArgs.template
         assertNotNull tagLib.renderArgs.model
         assertNotNull tagLib.renderArgs.model.mensagemList
         assertNotNull tagLib.renderArgs.model.diretorioImagem
         assertNotNull tagLib.renderArgs.model.mensagemTotal
         assertNotNull tagLib.renderArgs.model.topicoInstance

         assertEquals 4, tagLib.renderArgs.model.size()
         assertEquals "/templates/historicoMensagemTemplate", tagLib.renderArgs.template
         assertEquals mensagemList, tagLib.renderArgs.model.mensagemList
         assertEquals diretorioImagem, tagLib.renderArgs.model.diretorioImagem
         assertEquals mensagemTotal, tagLib.renderArgs.model.mensagemTotal
         assertEquals topicoInstance, tagLib.renderArgs.model.topicoInstance
    }
}
