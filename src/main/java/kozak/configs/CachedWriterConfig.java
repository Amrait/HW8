package kozak.configs;

import kozak.classes.CachedWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Олексій on 17.03.2017.
 */
@Configuration
public class CachedWriterConfig {
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public CachedWriter cachedWriter(){
        return new CachedWriter(5,new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()),"storage.txt");
    }
}