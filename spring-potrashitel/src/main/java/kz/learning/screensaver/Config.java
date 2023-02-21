package kz.learning.screensaver;

import org.springframework.context.annotation.*;

import java.awt.*;
import java.util.Random;

@Configuration
@ComponentScan(basePackages = "kz.learning.screensaver")
public class Config {
    @Bean
//    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS) // при любом вызове будет вызываться новый бин (даже если вызывать в одном месте(методе))
    public Color color() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public static void main(String[] args) throws InterruptedException {
        // Передаем наш конфиг, который попросит просканировать (крота) весь пакет и найдет еще бин проаннотированный аннотацией компонент
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);

        while (true) {
            annotationConfigApplicationContext.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(50);
        }
    }
}
