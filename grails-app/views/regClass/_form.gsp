<%@ page import="eregister.RegClass" %>



<div class="fieldcontain ${hasErrors(bean: regClassInstance, field: 'instructor', 'error')} required">
	<label for="instructor">
		<g:message code="regClass.instructor.label" default="Instructor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="instructor" name="instructor.id" from="${eregister.Instructor.list()}" optionKey="id" required="" value="${regClassInstance?.instructor?.id}" class="many-to-one"/>
</div>

