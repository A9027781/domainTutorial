package eregister



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class InstructorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Instructor.list(params), model:[instructorInstanceCount: Instructor.count()]
    }

    def show(Instructor instructorInstance) {
        respond instructorInstance
    }

    def create() {
        respond new Instructor(params)
    }

    @Transactional
    def save(Instructor instructorInstance) {
        if (instructorInstance == null) {
            notFound()
            return
        }

        if (instructorInstance.hasErrors()) {
            respond instructorInstance.errors, view:'create'
            return
        }

        instructorInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'instructorInstance.label', default: 'Instructor'), instructorInstance.id])
                redirect instructorInstance
            }
            '*' { respond instructorInstance, [status: CREATED] }
        }
    }

    def edit(Instructor instructorInstance) {
        respond instructorInstance
    }

    @Transactional
    def update(Instructor instructorInstance) {
        if (instructorInstance == null) {
            notFound()
            return
        }

        if (instructorInstance.hasErrors()) {
            respond instructorInstance.errors, view:'edit'
            return
        }

        instructorInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Instructor.label', default: 'Instructor'), instructorInstance.id])
                redirect instructorInstance
            }
            '*'{ respond instructorInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Instructor instructorInstance) {

        if (instructorInstance == null) {
            notFound()
            return
        }

        instructorInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Instructor.label', default: 'Instructor'), instructorInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'instructorInstance.label', default: 'Instructor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
