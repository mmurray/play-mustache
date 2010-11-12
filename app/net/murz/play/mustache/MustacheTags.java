package net.murz.play.mustache;

import groovy.lang.Closure;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.sampullara.mustache.*;

import play.Logger;
import play.Play;
import play.cache.Cache;
import play.templates.FastTags;
import play.templates.JavaExtensions;
import play.templates.GroovyTemplate.ExecutableTemplate;
import play.vfs.VirtualFile;
import play.modules.mustache.*;
import play.mvc.Router;

@FastTags.Namespace("mustache")
public class MustacheTags extends FastTags {
    
    public static void _template(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) throws MustacheException {
        String key = args.get("arg").toString();
        MustacheSession session = MustachePlugin.session();
        session.addFromString(key, JavaExtensions.toString(body));
    }
    
    public static void _print(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) throws MustacheException, IOException {
        String key = args.get("arg").toString();
        Object context = args.get("context");
        MustacheSession session = MustachePlugin.session();
        out.print(session.toHtml(key, context));
    }
    
    public static void _scripts(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) throws MustacheException, IOException {
//        StringBuilder result = new StringBuilder();
        String libSrc = Router.reverse(VirtualFile.open(Play.applicationPath+"/public/javascripts/mustache.min.js"));
        String moduleSrc = Router.reverse("mustache.Mustache.moduleJavascript").url;
        out.print("<script type=\"text/javascript\" src=\""+libSrc+"\"></script>");
        out.print("<script type=\"text/javascript\" src=\""+moduleSrc+"\"></script>");
    }
    
}