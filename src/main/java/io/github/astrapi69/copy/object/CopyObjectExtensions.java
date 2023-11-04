/**
 * The MIT License
 *
 * Copyright (C) 2021 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.copy.object;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.astrapi69.check.Check;
import io.github.astrapi69.io.Serializer;
import io.github.astrapi69.reflection.InstanceFactory;
import io.github.astrapi69.reflection.ReflectionExtensions;

/**
 * The class {@link CopyObjectExtensions} provide methods for copy an original object to a given
 * destination object.
 */
@UtilityClass
public final class CopyObjectExtensions
{


	/**
	 * Copy the given original object.
	 *
	 * @param <T>
	 *            the generic type of the given object
	 * @param original
	 *            the original object
	 * @param ignoreFieldNames
	 *            optional field names to ignore
	 * @return a copy of the given original object
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copyObject(@NonNull T original, final String... ignoreFieldNames)
		throws IllegalAccessException
	{
		Class<T> clazz = (Class<T>)original.getClass();
		if (String.class.equals(clazz))
		{
			return (T)String.valueOf(original);
		}
		T destination = InstanceFactory.newInstance(clazz);
		return copyObject(original, destination, ignoreFieldNames);
	}

	/**
	 * Copy the given original object to the given destination object. This also works on private
	 * fields.
	 *
	 * @param <ORIGINAL>
	 *            the generic type of the original object.
	 * @param <DESTINATION>
	 *            the generic type of the destination object.
	 * @param original
	 *            the original object.
	 * @param destination
	 *            the destination object.
	 * @param ignoreFieldNames
	 *            optional field names to ignore
	 * @return a copy of the given original object
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 */
	public static <ORIGINAL, DESTINATION> DESTINATION copyObject(final @NonNull ORIGINAL original,
		final @NonNull DESTINATION destination, final String... ignoreFieldNames)
		throws IllegalAccessException
	{
		Field[] allDeclaredFields = ReflectionExtensions.getAllDeclaredFields(original.getClass(),
			ignoreFieldNames);
		for (Field field : allDeclaredFields)
		{
			if (Arrays.asList(ignoreFieldNames).contains(field.getName())
				|| copyField(field, original, destination))
			{
				continue;
			}
		}
		return destination;
	}

	/**
	 * Copy the given original object to the given destination object. This also works on private
	 * fields. <br>
	 * Note: This method is moved to the class <code>ReflectionExtensions</code> so it will be
	 * removed.
	 *
	 * @param <ORIGINAL>
	 *            the generic type of the original object.
	 * @param <DESTINATION>
	 *            the generic type of the destination object.
	 * @param field
	 *            the field
	 * @param original
	 *            the original object.
	 * @param target
	 *            the destination object.
	 * @return true if the field is null or final otherwise false
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 */
	public static <ORIGINAL, DESTINATION> boolean copyField(final @NonNull Field field,
		final @NonNull ORIGINAL original, final @NonNull DESTINATION target)
		throws IllegalAccessException
	{
		field.setAccessible(true);
		Object newValue = field.get(original);
		if (newValue == null || Modifier.isFinal(field.getModifiers()))
		{
			return true;
		}
		ReflectionExtensions.setFieldValue(target, field, newValue);
		return false;
	}

	/**
	 * Copy the given object over reflection and return a copy of it object.
	 *
	 * @param <T>
	 *            the generic type of the given object.
	 * @param original
	 *            the original object.
	 * @param ignoreFieldNames
	 *            optional field names to ignore
	 * @return the new object that is a copy of the given object
	 * @throws IllegalAccessException
	 *             is thrown if the class or its default constructor is not accessible
	 * @throws NoSuchFieldException
	 *             is thrown if no such field exists
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copyPropertiesWithReflection(final @NonNull T original,
		final String... ignoreFieldNames) throws IllegalAccessException, NoSuchFieldException
	{
		Class<T> clazz = (Class<T>)original.getClass();
		T destination = InstanceFactory.newInstance(clazz);
		String[] allDeclaredFieldNames = ReflectionExtensions.getAllDeclaredFieldNames(clazz,
			ignoreFieldNames);
		for (String fieldName : allDeclaredFieldNames)
		{
			ReflectionExtensions.copyFieldValue(original, destination, fieldName);
		}
		return destination;
	}

	/**
	 * Copy the given original object to the given destination object. This also works on private
	 * fields.
	 *
	 * @param <ORIGINAL>
	 *            the generic type of the original object.
	 * @param <DESTINATION>
	 *            the generic type of the destination object.
	 * @param original
	 *            the original object.
	 * @param destination
	 *            the destination object.
	 * @param fieldName
	 *            the field name
	 * @return the destination object
	 * @throws NoSuchFieldException
	 *             is thrown if no such field exists.
	 * @throws SecurityException
	 *             is thrown if a security manager says no.
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 */
	public static <ORIGINAL, DESTINATION> DESTINATION copyPropertyWithReflection(
		final @NonNull ORIGINAL original, final @NonNull DESTINATION destination,
		final @NonNull String fieldName)
		throws NoSuchFieldException, SecurityException, IllegalAccessException
	{
		ReflectionExtensions.copyFieldValue(original, destination, fieldName);
		return destination;
	}

	/**
	 * Copy the given source object to the given target object.
	 *
	 * @param <S>
	 *            the generic type of the source object
	 * @param <T>
	 *            the generic type of the target object
	 * @param source
	 *            the source object
	 * @param target
	 *            the target object
	 * @return the target object
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 * @throws NoSuchFieldException
	 *             is thrown if no such field exists.
	 * @throws SecurityException
	 *             is thrown if a security manager says no
	 */
	public static <S, T> T copy(final @NonNull S source, final @NonNull T target,
		final String... ignoreFieldNames)
		throws IllegalAccessException, NoSuchFieldException, SecurityException
	{
		Check.get().notNull(source, "source").notNull(target, "target");

		final Class<?> sourceClass = source.getClass();
		final Class<?> targetClass = target.getClass();

		List<String> fieldNames = ReflectionExtensions.getFieldNames(sourceClass);


		for (String fieldName : fieldNames)
		{
			if (!Arrays.asList(ignoreFieldNames).contains(fieldName))
			{
				ReflectionExtensions.copyFieldValue(source, target, fieldName);
			}
		}

		return target;
	}

	/**
	 * Copy the given source object to the given target object.
	 *
	 * @param <T>
	 *            the generic type of the source object
	 * @param source
	 *            the source object
	 * @return the generated map from the given source object
	 * @throws IntrospectionException
	 *             is thrown if an exception occurs during introspection
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 * @throws InvocationTargetException
	 *             is thrown if the underlying method throws an exception
	 */
	public static <T> Map<String, Object> copyToMap(T source, final String... ignoreFieldNames)
		throws IntrospectionException, IllegalAccessException, InvocationTargetException
	{
		Check.get().notNull(source, "source");
		Map<String, Object> stringObjectMap = new HashMap<>();
		BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
		for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors())
		{
			String name = propertyDescriptor.getName();
			if (!Arrays.asList(ignoreFieldNames).contains(name))
			{
				Method reader = propertyDescriptor.getReadMethod();
				if (reader != null)
				{
					Object value = reader.invoke(source);
					stringObjectMap.put(name, value);
				}
			}
		}
		return stringObjectMap;
	}

	/**
	 * Copy the given source object first to a map and then to a base64 encoded {@link String}
	 * object
	 *
	 * @param <T>
	 *            the generic type of the source object
	 * @param source
	 *            the source object
	 * @return the generated base64 encoded {@link String} object
	 * @throws IntrospectionException
	 *             is thrown if an exception occurs during introspection
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 * @throws InvocationTargetException
	 *             is thrown if the underlying method throws an exception
	 */
	public static <T> String copyObjectToMapBase64EncodedString(T source,
		final String... ignoreFieldNames)
		throws IntrospectionException, IllegalAccessException, InvocationTargetException
	{
		return Serializer
			.toBase64EncodedString((HashMap<String, Object>)copyToMap(source, ignoreFieldNames));
	}

	/**
	 * Copies the given base64 encoded {@link String} object that represents a map to a new object
	 * from the given class.
	 *
	 * @param <T>
	 *            the generic type of the returned object
	 * @param base64EncodedStringMap
	 *            the base64 encoded {@link String} object that represents a map
	 * @param cls
	 *            the class object
	 * @return a new object from the given class that is filled from the given base64 encoded
	 *         {@link String} object that represents a map
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copyBase64EncodedStringMapToObject(@NonNull String base64EncodedStringMap,
		@NonNull Class<T> cls)
	{
		Map<String, Object> stringObjectMapap = (HashMap<String, Object>)Serializer
			.toObject(base64EncodedStringMap);
		return copyMapToObject(stringObjectMapap, cls);
	}

	/**
	 * Copies all fields from the given map to a new object from the given class.
	 *
	 * @param <T>
	 *            the generic type of the returned object
	 * @param map
	 *            the map with the fields
	 * @param cls
	 *            the class object
	 * @return a new object from the given class that is filled from the given map
	 */
	public static <T> T copyMapToObject(@NonNull Map<String, Object> map, @NonNull Class<T> cls)
	{
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.convertValue(map, cls);
	}

	/**
	 * Copys the given Object and returns the copy from the object or null if the object can't be
	 * serialized.
	 *
	 * @param <T>
	 *            the generic type of the given object
	 * @param orig
	 *            The object to copy.
	 * @return Returns a copy from the original object.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             is thrown when a class is not found in the classloader or no definition for the
	 *             class with the specified name could be found.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T copySerializedObject(final @NonNull T orig)
		throws IOException, ClassNotFoundException
	{
		try (ByteArrayInputStream bis = new ByteArrayInputStream(toByteArray(orig));
			ObjectInputStream ois = new ObjectInputStream(bis))
		{
			return (T)ois.readObject();
		}
	}

	/**
	 * Copies the given object to a byte array
	 *
	 * @param <T>
	 *            the generic type of the given object
	 * @param object
	 *            The object to copy
	 * @return the byte array
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static <T extends Serializable> byte[] toByteArray(final @NonNull T object)
		throws IOException
	{
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream))
		{
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			return byteArrayOutputStream.toByteArray();
		}
	}

}
