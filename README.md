# play-mustache

This module allows you to define logic-less template snippets that can be used server-side in your [Play](http://playframework.org) views as well as client-side in your JavaScript. 

The template snippets use [Mustache](http://mustache.github.com), which is a logic-less template language. For a complete reference of the language see the [manual](http://mustache.github.com/mustache.5.html).

## Installation

### Install the module

Install the Mustache module from the modules repository:

	play install mustache

### Enable the module

After installing the module, add the following to your `application.conf`:

	# The Mustache module
	module.mustache=${play.path}/modules/mustache

## Usage

### Meta and Script Tags

The first step is to put the `mustache.meta` tag somewhere in your document. This tag outputs an HTML meta tag containing the snippets you have defined for the current request.

After your meta tag you should include `play-mustache.min.js`, which is in the play-mustache `lib` directory. The file contains a JavaScript Mustache compiler and a mechanism to parse the templates out of the play-mustache meta tag. Copy this file to your public directory or to your CDN and include it in your document.  

	<html>
	    <head>
	        <title>#{get 'title' /}</title>
	        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	        <link rel="stylesheet" type="text/css" media="screen" href="@{'/public/stylesheets/main.css'}">
	        <link rel="shortcut icon" type="image/png" href="@{'/public/images/favicon.png'}">
	        #{mustache.meta /}
	        <script type="text/javascript" src="@{'/public/javascripts/play-mustache.min.js'}"></script>
	    </head>
	    <body>
	        #{doLayout /}
	    </body>
	</html> 

### Inline Template Snippets

You can define Mustache templates inline in your views using the `mustache.template` tag:

	#{mustache.template 'task_item'}
		<li class="task" id="task_{{id}}">
			<span class="name">Task {{id}}</span>
			<span class="details">{{details}}</span>
			<span class="priority">{{priority}}</span>
		</li>
	#{/mustache.template}
	
You can then use the template within your view using the `mustache.print` tag:

	<ul id="tasks">
	#{list tasks, as: 'task'}
		#{mustache.print 'task_item', context:task /}
	#{/list}
	</ul>
	
And you can use the template within your JavaScript using the `PlayMustache.to_html` method:

	var data = {
		name: 'Task 34',
		details: 'Finish the project',
		priority: 'High'
	}
	var html = PlayMustache.to_html("task_item", data);
	$('#tasks').append($(html));

### External Template Snippets

If you don't want to specify your snippet inline, you can move it into an external file. By default the module will look in `app/views/mustaches` for template files, but you can configure this path by specifying `mustache.dir` in your `application.conf`.

To reference an external snippet, just use the relative filename as the template name. For example, if your file is called `app/views/mustaches/my_template.html` then you would use it server-side like this:

	#{mustache.print 'my_template.html', context:data /}

And client-side like this:

	PlayMustache.to_html('my_template.html', data);
	
