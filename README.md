# play-mustache

This module allows you to define logic-less template snippets that can be used server-side in your [Play](http://playframework.org) as well as client-side in your JavaScript. 

The template snippets use [Mustache](http://mustache.github.com), which is a logic-less template language. For a complete reference of the language see the [manual](http://mustache.github.com/mustache.5.html).

The play-mustache module allows you to define template snippets (either inline in your play view or in a seperate file) that can be printed out server-side in your view as well as client-side in your JavaScript.

# Inline Template Snippets

You can define Mustache templates inline in your views using the `mustache.template` tag like this:

	#{mustache.template 'task_item'}
		<li class="task" id="task_{{id}}">
			<span class="name">Task {{id}}</span>
			<span class="details">{{details}}</span>
			<span class="priority">{{priority}}</span>
		</li>
	#{/mustache.template}
	
You can then use the template within your view using the `mustache.print` tag like this:

	<ul id="tasks">
	#{list tasks, as: 'task'}
		#{mustache.print 'task_item', context:task /}
	#{/list}
	</ul>
	
And you can use the template within your JavaScript using the `PlayMustache.to_html` method like this:

	var data = {
		name: 'Task 34',
		details: 'Finish the project',
		priority: 'High'
	}
	var html = PlayMustache.to_html("task_item", data);
	$('#tasks').append($(html));

# External Template Snippets

If you don't want to specify your snippet inline, you can move it into an external file. By default the module will look in `app/views/mustaches` for template files, but you can configure this path by specifying `mustache.dir` in your `application.conf`.

To reference an external snippet, just use the relative filename as the template name. For example, if your file is called `app/views/mustaches/my_template.html` then you would use it server-side like this:

	#{mustache.print 'my_template.html', context:data /}

And client-side like this:

	PlayMustache.to_html('my_template.html', data);
	
