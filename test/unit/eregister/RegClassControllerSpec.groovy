package eregister



import grails.test.mixin.*
import spock.lang.*

@TestFor(RegClassController)
@Mock(RegClass)
class RegClassControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.regClassInstanceList
            model.regClassInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.regClassInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def regClass = new RegClass()
            regClass.validate()
            controller.save(regClass)

        then:"The create view is rendered again with the correct model"
            model.regClassInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            regClass = new RegClass(params)

            controller.save(regClass)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/regClass/show/1'
            controller.flash.message != null
            RegClass.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def regClass = new RegClass(params)
            controller.show(regClass)

        then:"A model is populated containing the domain instance"
            model.regClassInstance == regClass
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def regClass = new RegClass(params)
            controller.edit(regClass)

        then:"A model is populated containing the domain instance"
            model.regClassInstance == regClass
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/regClass/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def regClass = new RegClass()
            regClass.validate()
            controller.update(regClass)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.regClassInstance == regClass

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            regClass = new RegClass(params).save(flush: true)
            controller.update(regClass)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/regClass/show/$regClass.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/regClass/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def regClass = new RegClass(params).save(flush: true)

        then:"It exists"
            RegClass.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(regClass)

        then:"The instance is deleted"
            RegClass.count() == 0
            response.redirectedUrl == '/regClass/index'
            flash.message != null
    }
}
