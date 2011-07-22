package play.modules.mustache;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.sampullara.mustache.Mustache;
import com.sampullara.mustache.MustacheCompiler;
import com.sampullara.mustache.MustacheException;

import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.vfs.VirtualFile;

public class MustachePlugin extends PlayPlugin {
    
    private static MustacheSession session_;
    
    public static MustacheSession session(){
        return session_;
    }
    
    @Override
    public void onConfigurationRead(){
        String root = Play.configuration.containsKey("mustache.dir") ? Play.configuration.getProperty("mustache.dir") : Play.applicationPath+"/app/views/mustaches";
        session_ = new MustacheSession(root);
        try{
            session_.loadFileSystemTemplates(root);
        }catch(Exception e){
            Logger.error("Error initializing Mustache module: "+e.getMessage());
        }
        Logger.info("Mustache module initialized");
    }
    
}