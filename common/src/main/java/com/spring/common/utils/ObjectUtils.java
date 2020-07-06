package com.spring.common.utils;

import java.util.ArrayList;
import java.util.List;

public class ObjectUtils {

	public static String toString(Object object) {
		return String.valueOf(object);
	}

	public static Number toNumber(Object object) {
		if (object != null && object instanceof Number) {
			return (Number) object;
		}
		return 0;
	}

	public static double toDouble(Object object) {
		if (object != null && object instanceof Number) {
			return ((Number) object).doubleValue();
		}
		return 0;
	}

	public static int toInt(Object object) {
		if (object != null && object instanceof Number) {
			return ((Number) object).intValue();
		}
		return 0;
	}

	public static long toLong(Object object) {
		if (object != null && object instanceof Number) {
			return ((Number) object).longValue();
		}
		return 0;
	}

	public static float toFloat(Object object) {
		if (object != null && object instanceof Number) {
			return ((Number) object).floatValue();
		}
		return 0;
	}

	public static short toShort(Object object) {
		if (object != null && object instanceof Number) {
			return ((Number) object).shortValue();
		}
		return 0;
	}

	public static <T> List<T> as(List<?> list, Class<T> clazz) {
		List<T> reslut = new ArrayList<T>();
		for (Object o : list) {
			if (clazz.isInstance(o)) {
				reslut.add(clazz.cast(o));
			}
		}
		return reslut;
	}

	public static <T> T cast(Object o, Class<T> clazz) {
		if (clazz.isInstance(o)) {
			return clazz.cast(o);
		}
		return null;
	}

	public static Integer[] toArray(List<Integer> list) {
		if (list != null) {
			int length = list.size();
			Integer[] result = new Integer[length];
			for (int i = 0; i < length; i++) {
				result[i] = list.get(i);
			}
			return result;
		}
		return null;
	}

}