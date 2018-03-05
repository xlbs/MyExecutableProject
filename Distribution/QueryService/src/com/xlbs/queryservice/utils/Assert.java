package com.xlbs.queryservice.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @since 0.0
 * 所有判断
 * @author 易明
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public abstract class Assert {
	/**
	 * 是真判断
	 * @param expression 3元表达式
	 * @param message 提示信息
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 是真判断
	 * @param expression 3元表达式
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	/**
	 * 自定义提示信息判断空对象
	 * @param object 判断对象
	 * @param message 提示信息
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 默认提示信息判断空对象
	 * @param object
	 */
	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}
	/**
	 * 自定义提示信息判断非空对象
	 * @param object
	 * @param message
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 默认提示信息判断非空对象
	 * @param object
	 * @param message
	 */
	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it cannot be null");
	}

	/**
	 * 字符串必须有长度--自定义提示信息
	 * @param text
	 * @param message
	 */
	public static void hasLength(String text, String message) {
		if (!StringUtils.hasLength(text)) {
			throw new IllegalArgumentException(message);
		}
	}
	/**
	 * 字符串必须有长度--默认提示信息
	 * @param text
	 */
	public static void hasLength(String text) {
		hasLength(text,
				"[Assertion failed] - this String argument must have length; it cannot be <code>null</code> or empty");
	}

	
	public static void hasText(String text, String message) {
		if (!StringUtils.hasText(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void hasText(String text) {
		hasText(text,
				"[Assertion failed] - this String argument must have text; it cannot be <code>null</code>, empty, or blank");
	}


	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
				textToSearch.indexOf(substring) != -1) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring,
				"[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
	}

	public static void notEmpty(Object[] array, String message) {
		if (ObjectUtils.isEmpty(array)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	public static void notEmpty(Collection collection, String message) {
		if (collection==null||collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}


	public static void notEmpty(Collection collection) {
		notEmpty(collection,
				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	public static void notEmpty(Map map, String message) {
		if (map==null||map.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(Map map) {
		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}


	public static void isInstanceOf(Class clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

	public static void isInstanceOf(Class clazz, Object obj, String message) {
		Assert.notNull(clazz, "The clazz to perform the instanceof assertion cannot be null");
		Assert.isTrue(clazz.isInstance(obj), message +
				"Object of class '" + (obj != null ? obj.getClass().getName() : "[null]") +
				"' must be an instance of '" + clazz.getName() + "'");
	}

	public static void isAssignable(Class superType, Class subType) {
		isAssignable(superType, subType, "");
	}

	public static void isAssignable(Class superType, Class subType, String message) {
		Assert.notNull(superType, "superType cannot be null");
		Assert.notNull(subType, "subType cannot be null");
		Assert.isTrue(superType.isAssignableFrom(subType), message + "Type [" + subType.getName()
						+ "] is not assignable to type [" + superType.getName() + "]");
	}


	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	public static void state(boolean expression) {
		state(expression, "[Assertion failed] - this state invariant must be true");
	}

}
