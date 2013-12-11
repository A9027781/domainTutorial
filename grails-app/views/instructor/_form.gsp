<%@ page import="eregister.Instructor" %>



<div class="fieldcontain ${hasErrors(bean: instructorInstance, field: 'classes', 'error')} ">
	<label for="classes">
		<g:message code="instructor.classes.label" default="Classes" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${instructorInstance?.classes?}" var="c">
    <li><g:link controller="regClass" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="regClass" action="create" params="['instructor.id': instructorInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'regClass.label', default: 'RegClass')])}</g:link>
</li>
</ul>

</div>

