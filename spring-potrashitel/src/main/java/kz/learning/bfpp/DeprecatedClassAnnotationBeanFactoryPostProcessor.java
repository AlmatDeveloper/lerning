package kz.learning.bfpp;

import kz.learning.annotation.DeprecatedClass;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

// ����� �������� ��������� ���� �� ��������� BeanFactory, ������ �������� � BeanDefinition
public class DeprecatedClassAnnotationBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            // ������ �������� ���� �� ����� ����� �������
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);

            // ����� ������������ �������� ������
            String beanClassName = beanDefinition.getBeanClassName();

            try {
                Class<?> originalClass = Class.forName(beanClassName);

                DeprecatedClass annotation = originalClass.getAnnotation(DeprecatedClass.class);

                if (annotation != null) {
                    beanDefinition.setBeanClassName(annotation.newImpl().getName()); // ���������� �������� ������ ������
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
