package qlsl.androiddesign.util.commonutil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectUtils {

	/**
	 * 创建对象副本<br/>
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T copy(T t) {
		T obj = null;
		try {
			Class classType = t.getClass();
			obj = (T) classType.getConstructor(new Class[] {}).newInstance(new Object[] {});
			Field[] fields = classType.getDeclaredFields();
			for (Field field : fields) {
				String name = field.getName();
				String firstLetter = name.substring(0, 1).toUpperCase();
				String getMethodName = "get" + firstLetter + name.substring(1);
				String setMethodName = "set" + firstLetter + name.substring(1);
				Method getMethod = classType.getMethod(getMethodName, new Class[] {});
				Method setMethod = classType.getMethod(setMethodName, new Class[] { field.getType() });
				Object value = getMethod.invoke(t, new Object[] {});
				setMethod.invoke(obj, new Object[] { value });
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
