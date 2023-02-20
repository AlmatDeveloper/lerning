package kz.learning;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class DeprecatedAnnotationBeanFactoryPostProcessor implements BeanFactoryPostProcessor { // хотим изменить поведение бина до обработки BeanFactory
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) { // Находить по названию бина объект и делать getClass не получится, т.к. это уже прокси
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName); // искать описания бина по имени через фабрику

            String beanClassName = beanDefinition.getBeanClassName(); //найти оригинальное название класса

            try {
                Class<?> originalClass = Class.forName(beanClassName);

                DeprecatedClass annotation = originalClass.getAnnotation(DeprecatedClass.class);

                if (annotation != null) {
                    beanDefinition.setBeanClassName(annotation.newImpl().getName()); //установили название нового класса
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
