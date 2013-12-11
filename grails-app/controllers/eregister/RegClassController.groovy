package eregister



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RegClassController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond RegClass.list(params), model:[regClassInstanceCount: RegClass.count()]
    }

    def show(RegClass regClassInstance) {
        respond regClassInstance
    }

    def create() {
        respond new RegClass(params)
    }

    @Transactional
    def save(RegClass regClassInstance) {
        if (regClassInstance == null) {
            notFound()
            return
        }

        if (regClassInstance.hasErrors()) {
            respond regClassInstance.errors, view:'create'
            return
        }

        regClassInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'regClassInstance.label', default: 'RegClass'), regClassInstance.id])
                redirect regClassInstance
            }
            '*' { respond regClassInstance, [status: CREATED] }
        }
    }

    def edit(RegClass regClassInstance) {
        respond regClassInstance
    }

    @Transactional
    def update(RegClass regClassInstance) {
        if (regClassInstance == null) {
            notFound()
            return
        }

        if (regClassInstance.hasErrors()) {
            respond regClassInstance.errors, view:'edit'
            return
        }

        regClassInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'RegClass.label', default: 'RegClass'), regClassInstance.id])
                redirect regClassInstance
            }
            '*'{ respond regClassInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(RegClass regClassInstance) {

        if (regClassInstance == null) {
            notFound()
            return
        }

        regClassInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'RegClass.label', default: 'RegClass'), regClassInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'regClassInstance.label', default: 'RegClass'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
