package controllers.mustache;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import net.murz.play.mustache.MustachePlugin;
import net.murz.play.mustache.MustacheSession;
import play.mvc.*;
import play.templates.JavaExtensions;

public class Mustache extends Controller {

    public static void moduleJavascript() throws UnsupportedEncodingException {
        MustacheSession s = MustachePlugin.session();
        StringBuilder sb = new StringBuilder();
        sb.append("var PlayMustache = function() { var _loaded = []; ");
        Iterator it = s.getRawTemplates().entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pairs = (Map.Entry)it.next();
            sb.append("_loaded[\""+JavaExtensions.escapeJavaScript(pairs.getKey().toString())+"\"] = \""+JavaExtensions.escapeJavaScript(pairs.getValue().toString())+"\"; ");
        }
        
        sb.append("return({ to_html: function(key, context) { var tmpl = _loaded[key]; return Mustache.to_html(tmpl,context); } }); }();");
        response.contentType = "text/javascript";
        renderText(sb.toString());
    }
    
}