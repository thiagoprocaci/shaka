package com.shaka

class TopicoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [topicoInstanceList: Topico.list(params), topicoInstanceTotal: Topico.count()]
    }

    def create = {
        def topicoInstance = new Topico()
        topicoInstance.properties = params
        return [topicoInstance: topicoInstance]
    }

    def save = {
        def topicoInstance = new Topico(params)
        if (topicoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'topico.label', default: 'Topico'), topicoInstance.id])}"
            redirect(action: "show", id: topicoInstance.id)
        }
        else {
            render(view: "create", model: [topicoInstance: topicoInstance])
        }
    }

    def show = {
        def topicoInstance = Topico.get(params.id)
        if (!topicoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'topico.label', default: 'Topico'), params.id])}"
            redirect(action: "list")
        }
        else {
            [topicoInstance: topicoInstance]
        }
    }

    def edit = {
        def topicoInstance = Topico.get(params.id)
        if (!topicoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'topico.label', default: 'Topico'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [topicoInstance: topicoInstance]
        }
    }

    def update = {
        def topicoInstance = Topico.get(params.id)
        if (topicoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (topicoInstance.version > version) {
                    
                    topicoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'topico.label', default: 'Topico')] as Object[], "Another user has updated this Topico while you were editing")
                    render(view: "edit", model: [topicoInstance: topicoInstance])
                    return
                }
            }
            topicoInstance.properties = params
            if (!topicoInstance.hasErrors() && topicoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'topico.label', default: 'Topico'), topicoInstance.id])}"
                redirect(action: "show", id: topicoInstance.id)
            }
            else {
                render(view: "edit", model: [topicoInstance: topicoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'topico.label', default: 'Topico'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def topicoInstance = Topico.get(params.id)
        if (topicoInstance) {
            try {
                topicoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'topico.label', default: 'Topico'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'topico.label', default: 'Topico'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'topico.label', default: 'Topico'), params.id])}"
            redirect(action: "list")
        }
    }
}
