package kz.learning.context;

import kz.learning.service.Quoter;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class PropertyFileApplicationContext extends GenericApplicationContext {
    public PropertyFileApplicationContext(String fileName) {
        // такой же, как и xmlBeanDefinitionReader, но сканирует properties файлы
        PropertiesBeanDefinitionReader propertiesBeanDefinitionReader = new PropertiesBeanDefinitionReader(this);
        // загружаем все beanDefinition из файла
        int beanNum = propertiesBeanDefinitionReader.loadBeanDefinitions(fileName);

        System.out.println("found " + beanNum + " beans");

        // последнее, что делает context - это рефреш (когда закончился процесс добавления бинов)
        refresh();
    }

    public static void main(String[] args) {
        PropertyFileApplicationContext propertyFileApplicationContext = new PropertyFileApplicationContext("context.properties");
        propertyFileApplicationContext.getBean(Quoter.class).say();
    }
}
