/**
 * 
 */
package com.sjlh.hotel.common.spring;

import static com.sjlh.hotel.common.spring.RegisterHelper.getFromSpi;
import static com.sjlh.hotel.common.spring.RegisterHelper.getListFromSpi;
import static com.sjlh.hotel.common.spring.RegisterHelper.regist;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.sjlh.hotel.common.net.Invoker;
import com.sjlh.hotel.common.net.InvokerFactory;
import com.sjlh.hotel.common.net.InvokerParamBuilder;

/**
 * @author Administrator
 *
 */
public class CommonRegister implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		regist(registry, getListFromSpi(Invoker.class));
		regist(registry, getFromSpi(InvokerFactory.class));
		regist(registry, getFromSpi(InvokerParamBuilder.class));
	}
}