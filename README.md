# play-mustache

This project is a module for using [Mustache](http://mustache.github.com) templates within the [Play! Framework](http://playframework.org). 

[Mustache](http://mustache.github.com) is a logic-less template language. For a complete reference of the language see the [manual](http://mustache.github.com/mustache.5.html).

The play-mustache module allows you to define template snippets (either inline in your play view or in a seperate file) that can be printed out server-side in your view as well as client-side in your JavaScript.

# Quick Usage Example

	#{extends 'main.html' /}
	
	
	<h1>Todo List</h1>
	
	#{mustache.template 'task_item'}
		<li class="task" id="task_{{id}}">{{details}}</li>
	#{/mustache.template}
	
	<ul id="tasks">
	#{list tasks, as: 'task'}
		#{mustache.print 'task_item', context:task /}
	#{/list}
	</ul>
	
	<form id="add_task" action="/tasks/" method="post">
		<div><input type="text" name="task.details" /></div>
		<div><input type="submit" value="Add Task"></div>
	</form>
	
	<script type="text/javascript">
	$('#add_task').submit(function(e){
		e.preventDefault();
		$.ajax({
			url: '/tasks.json',
			type: 'POST',
			data: $('#add_task').serialize(),
			success: function(data){
				var task_item = PlayMustache.to_html("task_item", data);
				$(task_item).appendTo($('#tasks'));
			}
		});
	});
	</script>
	 
 