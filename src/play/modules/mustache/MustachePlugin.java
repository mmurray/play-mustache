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
    
    private static ThreadLocal<MustacheSession> session_ = new ThreadLocal<MustacheSession>();
    private static Map<String, String> fsTemplates_ = new HashMap<String, String>();
    
    public static MustacheSession session(){
        return session_.get();
    }
    
    @Override
    public void onConfigurationRead(){
        String root = Play.configuration.containsKey("mustache.dir") ? Play.configuration.getProperty("mustache.dir") : Play.applicationPath+"/app/views/mustaches";
        MustacheCompiler compiler = new MustacheCompiler(new File(root));
        session_.set(new MustacheSession(compiler, root));        
        try{
            this.loadFileSystemTemplates(root);
        }catch(Exception e){
            Logger.error("Error initializing Mustache module: "+e.getMessage());
        }
        Logger.info("Mustache module initialized");
    }
    
    private void loadFileSystemTemplates(String root)  throws MustacheException, IOException {
        File dir = VirtualFile.open(root).getRealFile();
        this.addFilesRecursively(dir, root);
    }
    
    private void addFilesRecursively(File file, String root) throws MustacheException, IOException {
        File[] children = file.listFiles();
        if(children != null){
            for(File child : children){
                if(child.isDirectory()){
                    addFilesRecursively(child, root);
                }else{
                    String path = child.getAbsolutePath();
                    String key = path.replace(root+"/", "");
                    session().addFromFile(key, path);
                }
            }
        }
    }
}