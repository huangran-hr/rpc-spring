/**
 * 
 */
package com.sjlh.hotel.common.spring;

import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.Assert;

import com.sjlh.hotel.common.annotation.DIPrimary;

/**
 * @author Administrator
 *
 */
public class RegisterHelper {
	static public <T> void regist(BeanDefinitionRegistry registry, T object) {
		regist(registry, object, null);
	}
	
	static public <T> void regist(BeanDefinitionRegistry registry, T object, PreInitializer pre) {
		Assert.notNull(object, "object is null");
		Class<T> clazz = (Class<T>)object.getClass();
		if(Iterator.class.isAssignableFrom(clazz)) {
			Iterator<?> ite = (Iterator<?>)object;
			while(ite.hasNext()) {
				regist(registry, ite.next(), pre);
			}
			return;
		}
		String className = clazz.getSimpleName();
		if(registry.containsBeanDefinition(className))return;
		BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(clazz, ()->{return object;}).getBeanDefinition();
		DIPrimary pkanno = clazz.getAnnotation(DIPrimary.class);
		if(pkanno!=null)beanDefinition.setPrimary(true);
		beanDefinition.getPropertyValues();
		if(pre!=null)pre.exec(beanDefinition);
		registry.registerBeanDefinition(className, beanDefinition);
	}
	
	static public <S> S getFromSpi(Class<S> clazz){
		ServiceLoader<S> serviceLoader = ServiceLoader.load(clazz);
		Optional<S> op = serviceLoader.findFirst();
		Assert.notNull(op.isPresent(), "clazz is null");
		S s = op.get();
		return s;
	}
	
	static public <S> Iterator<S> getListFromSpi(Class<S> clazz){
		ServiceLoader<S> serviceLoader = ServiceLoader.load(clazz);
		return serviceLoader.iterator();
	}
	
	@FunctionalInterface
	static public interface PreInitializer{
		void exec(BeanDefinition beanDefinition);
	}
}