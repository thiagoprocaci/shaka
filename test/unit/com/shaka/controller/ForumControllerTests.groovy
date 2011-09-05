package com.shaka.controller

import grails.test.*

import com.shaka.Forum
import com.shaka.ForumController

class ForumControllerTests extends ControllerUnitTestCase {

    ForumControllerTests(){
        super(ForumController.class)
    }

    protected void setUp() {
        super.setUp()
        mockDomain(Forum)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
        def paramsMock = [id : 1]
        controller.params.putAll(paramsMock)
        controller.index()
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.action
        assertNotNull controller.redirectArgs.params

        assertEquals 2, controller.redirectArgs.size()
        assertEquals "list", controller.redirectArgs.action
        assertEquals paramsMock, controller.redirectArgs.params
    }

    void testList() {
        def forum = new Forum(nome:"nome", descricao: "descricao")
        forum.save()
        def forum2 = new Forum(nome:"nome2", descricao: "descricao2")
        forum2.save()
        def map  = controller.list()
        assertNotNull map
        assertNotNull map.forumList
        assertTrue forum in map.forumList
        assertTrue forum2 in map.forumList
        assertEquals 2, map.forumList.size()
    }

    void testDetailErro() {
        def map  = controller.detail()
        assertNull map
        assertNotNull controller.redirectArgs
        assertNotNull controller.redirectArgs.uri
        assertEquals "/", controller.redirectArgs.uri
        assertEquals 1, controller.redirectArgs.size()
    }

    void testDetailSucesso() {
        def forum = new Forum(nome:"nome", descricao: "descricao")
        forum.save()
        def paramsMock = [id : forum.id]
        controller.params.putAll(paramsMock)
        def map = controller.detail()
        assertNotNull map
        assertNotNull map.forumInstance
        assertEquals forum, map.forumInstance
        assertNotNull controller.redirectArgs
        assertEquals 0, controller.redirectArgs.size()
    }
}
