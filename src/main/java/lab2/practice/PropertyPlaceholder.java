package lab2.practice;


import lab1.practice.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.propertyeditors.FileEditor;
import org.springframework.core.convert.Property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс должен содержать логику подмены значений филдов заданых по умолчанию в контексте.
 * Заменяет строковые значение в бинах типа
 *
 * @see Printer
 * на значения в
 * @see PropertyRepository
 * Использует изначальные значения как ключи для поиска в PropertyRepository
 */

public class PropertyPlaceholder implements BeanFactoryPostProcessor {

    private Properties properties = new Properties();

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            properties.load(new FileInputStream("src/main/resources/lab2/practice/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName.equals("lab2.practice.MessagePrinter")) {
                PropertyValue propertyValue = beanDefinition.getPropertyValues().getPropertyValue("message");
                beanDefinition.getPropertyValues().clearProcessedProperty("message");
                beanDefinition.getPropertyValues().addPropertyValue("message",
                        properties.get(((TypedStringValue) propertyValue.getValue()).getValue()));
            }
        }



    }
}
