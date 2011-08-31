package com.shaka

/**
 * Controller para manipular os forums
 */
class ForumController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        return [forumList:Forum.list()]
    }

    def detail = {
        def forum = Forum.get(params.id)
        if(forum){
            return [forumInstance:forum]
        } else {
            redirect(uri:"/")
        }
    }
}
