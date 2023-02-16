package kz.learning;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
//        while (true) {
//            Thread.sleep(1000);
//            context.getBean(Quoter.class).say();
//        }
    }
}

// BeanDefinition - объекты, которые хранят информацию о бине.
// Обычная Map, id бина - декларация(из какого класса создавать, имеет ли init метод, какие property)

// Setter - обязательны для property, чтобы spring понимал куда вставлять значения
// ClassPathXmlApplicationContext сканируется XmlBeanDefinitionReader

// BeanFactory, создает объекты из наших классов и складывает их в контейнер.
// В начале он смотрит на те бины которые имплементируют BPP, и с помощью их настраивает остальные "простые" бины
// BeanFactory, для настройки бина проходится два раза по BPP, до и после инит метода, из-за этого BPP имеет два метода

// Singleton создается на этапе поднятия контекста
// Prototype создаются когда они нужны, spring создаст, настроит, отдаст и забудет про него (из-за этого destroy методы не будут работать)

// BeanPostProcessor - настраивает наши бины до того как они попадают в контейнер
// второй метод в BPP нужен, если мы меняем что-то в классе. Так мы уверены что получаем именно тот бин который нам нужен
// id бину дают если мы хотим его использовать где-то в коде
// Если в конструкторе вызвать то что настраивает Spring, то это не сработает. Для этого нужны init методы
// Объекты настраиваются поле создания
// Так же init методы - это двухуровневые конструкторы
// Аннотации обрабатываются с помощью beanPostProcessor

// Есть два способа создания бина на лету: dynamicProxy(имплементировать те жи методы, что и класс) и CGLib(наследовать на прямую от класса(медленнее, есть ограничения(final методы ...)))
// BBP который отвечает за логику Transactional инициализирует логику во втором методе(после init метода)

// Listener слушает context Spring (все event которые там происходит)
// contextStarted - контекст начал свое построение(но еще не построился)
// contextRefresh - делается всегда после построения (в большинстве случаях слушается именно этот event)