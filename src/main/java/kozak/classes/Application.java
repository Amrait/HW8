package kozak.classes;

import kozak.configs.ScraperConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;

/**
 * Created by Олексій on 16.03.2017.
 */
public class Application {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ScraperConfig.class);
        Scraper scraper = ctx.getBean(Scraper.class);
        try {
            scraper.parse();
            Thread.sleep(10000);
        } catch (IOException e) {
            System.out.println("Проблема з підключенням до веб-ресурсу, спробуємо ще раз...");
        } catch (InterruptedException e) {
            System.out.println("Лишенько, проблема з потоками... ой-йой-ой!");
        }
    }
}
