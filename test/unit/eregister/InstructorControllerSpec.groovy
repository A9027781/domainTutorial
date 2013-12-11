package eregister



import grails.test.mixin.*
import spock.lang.*

@TestFor(InstructorController)
@Mock(Instructor)
class InstructorControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.instructorInstanceList
            model.instructorInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.instructorInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def instructor = new Instructor()
            instructor.validate()
            controller.save(instructor)

        then:"The create view is rendered again with the correct model"
            model.instructorInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            instructor = new Instructor(params)

            controller.save(instructor)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/instructor/show/1'
            controller.flash.message != null
            Instructor.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def instructor = new Instructor(params)
            controller.show(instructor)

        then:"A model is populated containing the domain instance"
            model.instructorInstance == instructor
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def instructor = new Instructor(params)
            controller.edit(instructor)

        then:"A model is populated containing the domain instance"
            model.instructorInstance == instructor
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/instructor/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def instructor = new Instructor()
            instructor.validate()
            controller.update(instructor)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.instructorInstance == instructor

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            instructor = new Instructor(params).save(flush: true)
            controller.update(instructor)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/instructor/show/$instructor.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/instructor/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def instructor = new Instructor(params).save(flush: true)

        then:"It exists"
            Instructor.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(instructor)

        then:"The instance is deleted"
            Instructor.count() == 0
            response.redirectedUrl == '/instructor/index'
            flash.message != null
    }
}
