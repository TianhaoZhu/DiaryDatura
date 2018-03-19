package zth.com.gezhi.okhttp.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import zth.com.gezhi.okhttp.NetWorkEvent;

public class ResponseHandlerUtil {
	
	public static void invokeResponseHandle(Object instance, String url, NetWorkEvent event){
		int index = url.indexOf("?");
		if (index > 0){
			url = url.substring(0, index);
		}

		Method method = getMethod(instance.getClass(), url);
		if (method != null) {
			try {
				method.invoke(instance, event);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public  static Method getMethod(Class clazz, String url) {
		Method[] methods = clazz.getMethods();
		
		for (Method method : methods) {
			NetWorkAnnotation annotation = getAnnotation(method);
			if (annotation != null && annotation.url().equals(url)) {
				return method;
			}
		}
		
		return null;
	}
	
	public static NetWorkAnnotation getAnnotation(Method method){
		Annotation[] annotations = method.getAnnotations();
		
		for (Annotation annotation : annotations) {
			if (annotation instanceof NetWorkAnnotation) {
                return (NetWorkAnnotation) annotation;
            }
		}
		
		return null;
	}

}
