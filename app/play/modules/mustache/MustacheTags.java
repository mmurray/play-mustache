package play.modules.mustache;

import groovy.lang.Closure;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import net.murz.play.mustache.MustachePlugin;
import net.murz.play.mustache.MustacheSession;

import com.google.gson.Gson;
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
    
    public static void _meta(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) throws MustacheException, IOException {
        StringBuilder templates = new StringBuilder("{\"templates\":{");
        Iterator it = MustachePlugin.session().getRawTemplates().entrySet().iterator();
        
        while(it.hasNext()){
            Map.Entry pairs = (Map.Entry)it.next();
            templates.append("\""+JavaExtensions.escapeJavaScript(pairs.getKey().toString())+"\":\""+JavaExtensions.escapeJavaScript(pairs.getValue().toString())+"\"");
            if(it.hasNext()) templates.append(",");
        }
        templates.append("}}");
        out.println("<meta id=\"play-mustache-templates\" name=\"play-mustache-templates\" content=\""+JavaExtensions.escapeHtml(templates.toString())+"\"></script>");
    }
    
}