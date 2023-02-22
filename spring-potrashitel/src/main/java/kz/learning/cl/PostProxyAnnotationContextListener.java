package kz.learning.cl;

import kz.learning.annotation.PostProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;

// ��������� Spring � Spring - ���������, ��������� Spring � ���� ���� - �����
// ����� �������� ������ Event, ����� �� ��������� ������ ��� (instanceof)
public class PostProxyAnnotationContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        // �������� �� �������� ���� ������ � ������ getClass �� ���������, �.�. ��� ��� ������
        for (String beanDefinitionName : beanDefinitionNames) {
            // ������ �������� ���� �� ����� ����� �������
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);

            // ����� ������������ �������� ������
            String beanClassName = beanDefinition.getBeanClassName();

            try {
                Class<?> originalClass = Class.forName(beanClassName);

                for (Method method : originalClass.getMethods()) {
                    if (method.isAnnotationPresent(PostProxy.class)) {
                        // method.invoke(); �� ��������� �.�. ��� ����� ������ ������������� ������, ��� ����� proxy ����� (���)
                        Object bean = applicationContext.getBean(beanDefinitionName);
                        // ��� ������, �.�. �������� �� ����
                        bean.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(bean);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
