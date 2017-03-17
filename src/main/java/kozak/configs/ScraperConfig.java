package kozak.configs;

import kozak.classes.CachedWriter;
import kozak.classes.Scraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Олексій on 16.03.2017.
 */
@Configuration
public class ScraperConfig {
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Scraper scraper(){
        return new Scraper(cachedWriter());
    }
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public CachedWriter cachedWriter(){
        return new CachedWriter(5,new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()),"storage.txt");
    }

}
