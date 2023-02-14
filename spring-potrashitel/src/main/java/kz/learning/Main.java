package kz.learning;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        Quoter quoter = context.getBean("terminatorQuoter", Quoter.class);

        quoter.say();
    }
}

// BeanDefinition - объекты, которые хранят информацию о бине. Обычная Map, id бина - декларация(из какого класса создавать, имеет ли init метод, какие property)
// Setter - обязательны для property, чтобы spring понимал куда вставлять значения
// ClassPathXmlApplicationContext сканируется XmlBeanDefinitionReader
// BeanFactory, создает объекты из наших классов и складывает их в контейнер
// Singleton создается на этапе поднятия контекста
// Prototype создаются когда они нужны, spring создаст, настроит, отдаст и забудет про него (из-за этого destroy методы не будут работать)
// BeanPostProcessor - настраивает наши бины до того как они попадают в контейнер
// id бину дают если мы хотим его использовать где-то в коде