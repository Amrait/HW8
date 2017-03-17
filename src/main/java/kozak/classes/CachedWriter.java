package kozak.classes;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Олексій on 16.03.2017.
 */
@Component
public class CachedWriter {
    private File file;
    private String fileName;
    private DateFormat df;
    private int cacheSize;
    List<String> cache;

    public void init() throws IOException {
    }

    public void destroy() {
        if (!cache.isEmpty()) {
            try {
                writeAllCache();
            } catch (IOException e) {
                System.out.println("Проблема з записом в файл.");
            }
        }
    }
    public CachedWriter(){
    }
    public CachedWriter(int cacheSize, DateFormat df, String fileName) {
        this.df = df;
        this.fileName = fileName;
        this.file = new File(fileName);
        this.cacheSize = cacheSize;
        this.cache = new ArrayList<>(cacheSize);
    }

    public void write(String message) throws IOException {
        cache.add(message);
        if (cache.size() == cacheSize) {
            writeAllCache();
            cache.clear();
        }
    }

    public void writeAllCache() throws IOException {
        for (String line : cache
                ) {
                FileUtils.write(this.file, df.format(new Date()) + ": " + line + "\n\n","UTF-8", true);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DateFormat getDf() {
        return df;
    }

    public void setDf(DateFormat df) {
        this.df = df;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

