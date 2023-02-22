package kz.learning.screensaver;

import org.springframework.context.annotation.*;
import sun.reflect.Reflection;
import sun.reflect.ReflectionFactory;

import java.awt.*;
import java.util.Random;

@Configuration
@ComponentScan(basePackages = "kz.learning.screensaver")
public class Config {
    @Bean
    // вызывать prototype в singleton
    // 1 - способ через proxyMode:
    // при любом вызове будет вызываться новый бин (даже если вызывать в одном месте(методе))
    // @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    // 2 - способ через абстрактный класс (правильный метод)
    // @Scope("prototype")
    //
    // Кастомный scope
    @Scope("periodical")
    public Color color() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    // 2 - способ через абстрактный класс
    @Bean
    public ColorFrame colorFrame() {
        return new ColorFrame() {
            @Override
            protected Color getColor() {
                // это не вызов метода, а именно вызов самого бина
                return color();
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        // Передаем наш конфиг, который попросит просканировать (крота) весь пакет и найдет еще бин проаннотированный аннотацией компонент
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);

        while (true) {
            annotationConfigApplicationContext.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(100);
        }
    }
}
