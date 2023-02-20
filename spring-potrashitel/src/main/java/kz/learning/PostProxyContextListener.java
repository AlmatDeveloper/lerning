package kz.learning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;

// »нжектит Spring в Spring - нормально, инжектит Spring в свои бины - плохо
public class PostProxyContextListener implements ApplicationListener<ContextRefreshedEvent> { //можно выбирать нужный Event, чтобы не провер€ть каждый раз (instanceof)

    @Autowired
    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) { // Ќаходить по названию бина объект и делать getClass не получитс€, т.к. это уже прокси
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);// искать описани€ бина по имени через фабрику

            String beanClassName = beanDefinition.getBeanClassName();//найти оригинальное название класса

            try {
                Class<?> originalClass = Class.forName(beanClassName);

                for (Method method : originalClass.getMethods()) {
                    if (method.isAnnotationPresent(PostProxy.class)) {
                        // method.invoke(); не сработает т.к. это вызов метода оригинального класса, нам нужен proxy класс (бин)
                        Object bean = applicationContext.getBean(beanDefinitionName);
                        bean.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(bean); // это прокси, т.к. вызываем из бина
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
