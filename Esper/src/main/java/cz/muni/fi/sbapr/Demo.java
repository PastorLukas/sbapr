package cz.muni.fi.sbapr;

import cz.muni.fi.sbapr.util.EventGenerator;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {

    public static void main(String[] args) throws InterruptedException, IOException {
                 
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"application-context.xml"});
        BeanFactory factory = (BeanFactory) appContext;
        
        File file = new File("D:\\Projects\\Esper\\src\\main\\resources\\source.csv");
        EventGenerator generator = (EventGenerator) factory.getBean("eventGenerator");
        
        generator.startGeneratingEvents(file, 0L, 1L, TimeUnit.SECONDS);        
    }     
}