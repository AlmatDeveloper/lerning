package kz.learning;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<>();

    private ProfilingController profilingController = new ProfilingController();

    public ProfilingAnnotationBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(profilingController, new ObjectName("profiling", "name", "profilingController"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();

        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }

        return bean;
    }

    // Логику пишем во втором методе, чтобы быть уверенными, что меняем именно тот бин который нам нужен
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);

        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    if (profilingController.isEnabled()) {
                        System.out.println("Профилирую...");

                        long before = System.nanoTime();
                        Object retVal = method.invoke(bean, objects);
                        long after = System.nanoTime();

                        System.out.println(after - before);
                        System.out.println("Все");

                        return retVal;
                    } else {
                        return method.invoke(bean, objects);
                    }
                }
            });
        }
        // статичный метод который сгенерит на лету новый объект
        // имеет 3 аргумента: 1 - classLoader который загрузит в HEAP новый объект
        // 2 - список интерфейсов который должен имплементировать тот класс который сгенерится на лету
        // 3 - инкапсулирует логику которая попадет во все методы класса который сгенерится на лету

        // Любой класс знает какой ClassLoader его загрузил

        return bean;
    }
}
