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

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import org.apache.commons.beanutils.PropertyUtils;

import io.github.astrapi69.check.Check;
import io.github.astrapi69.lang.ClassType;
import io.github.astrapi69.lang.ObjectExtensions;
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
		T destination = ReflectionExtensions.newInstanceWithObjenesis(clazz);
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
	 * fields.
	 *
	 * @param <ORIGINAL>
	 *            the generic type of the original object.
	 * @param <DESTINATION>
	 *            the generic type of the destination object.
	 * @param field
	 *            the field
	 * @param original
	 *            the original object.
	 * @param destination
	 *            the destination object.
	 * @return true if the field is null or final otherwise false
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 */
	@SuppressWarnings("unchecked")
	public static <ORIGINAL, DESTINATION> boolean copyField(final @NonNull Field field,
		final @NonNull ORIGINAL original, final @NonNull DESTINATION destination)
		throws IllegalAccessException
	{
		field.setAccessible(true);
		Object value = field.get(original);
		if (value == null || Modifier.isFinal(field.getModifiers()))
		{
			return true;
		}
		Class<?> fieldType = field.getType();
		ClassType classType = ObjectExtensions.getClassType(fieldType);
		switch (classType)
		{
			case ANNOTATION :
			case ANONYMOUS :
			case COLLECTION :
			case LOCAL :
			case PRIMITIVE :
			case MAP :
			case MEMBER :
			case SYNTHETIC :
			case INTERFACE :
			case DEFAULT :
				field.set(destination, value);
				break;
			case ARRAY :
				field.set(destination, copyOfArray(value));
				break;
			case ENUM :
				field.set(destination, copyOfEnumValue(value, fieldType));
				break;
		}
		return false;
	}

	/**
	 * Copy the given enum object over reflection and return a copy of it
	 *
	 * @param value
	 *            the enum object
	 * @param fieldType
	 *            the type of the given field value
	 * @return the new enum object that is a copy of the given enum object
	 */
	public static Object copyOfEnumValue(Object value, Class<?> fieldType)
	{
		ClassType classType = ObjectExtensions.getClassType(fieldType);
		if (classType.equals(ClassType.ENUM))
		{
			Enum<?> enumValue = (Enum<?>)value;
			String name = enumValue.name();
			return Enum.valueOf(fieldType.asSubclass(Enum.class), name);
		}
		return null;
	}

	/**
	 * Copy the given array object over reflection and return a copy of it
	 * 
	 * @param value
	 *            the array object
	 * @return the new array object that is a copy of the given array object
	 */
	public static Object copyOfArray(Object value)
	{
		if (!value.getClass().isArray())
		{
			return null;
		}
		Object destinationArray = null;
		Class<?> arrayType = value.getClass().getComponentType();
		if (arrayType.isPrimitive())
		{
			if ("boolean".equals(arrayType.getName()))
			{
				destinationArray = Arrays.copyOf((boolean[])value, Array.getLength(value));
			}
			if ("byte".equals(arrayType.getName()))
			{
				destinationArray = Arrays.copyOf((byte[])value, Array.getLength(value));
			}
			if ("char".equals(arrayType.getName()))
			{
				destinationArray = Arrays.copyOf((char[])value, Array.getLength(value));
			}
			if ("short".equals(arrayType.getName()))
			{
				destinationArray = Arrays.copyOf((short[])value, Array.getLength(value));
			}
			if ("int".equals(arrayType.getName()))
			{
				destinationArray = Arrays.copyOf((int[])value, Array.getLength(value));
			}
			if ("long".equals(arrayType.getName()))
			{
				destinationArray = Arrays.copyOf((long[])value, Array.getLength(value));
			}
			if ("float".equals(arrayType.getName()))
			{
				destinationArray = Arrays.copyOf((float[])value, Array.getLength(value));
			}
			if ("double".equals(arrayType.getName()))
			{
				destinationArray = Arrays.copyOf((double[])value, Array.getLength(value));
			}
		}
		else
		{
			destinationArray = Array.newInstance(arrayType, Array.getLength(value));
			for (int i = 0; i < Array.getLength(value); i++)
			{
				Array.set(destinationArray, i, Array.get(value, i));
			}
		}
		return destinationArray;
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
		T destination = ReflectionExtensions.newInstanceWithObjenesis(clazz);
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

		final Class<?> targetClass = target.getClass();

		final PropertyDescriptor[] targetPropertyDescriptors = PropertyUtils
			.getPropertyDescriptors(targetClass);

		for (final PropertyDescriptor targetPropertyDescriptor : targetPropertyDescriptors)
		{
			String name = targetPropertyDescriptor.getName();
			if (!Arrays.asList(ignoreFieldNames).contains(name))
			{
				copyProperty(source, target, targetPropertyDescriptor);
			}
		}
		return target;
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


	/**
	 * Copy the given source property to the given target object.
	 *
	 * @param <S>
	 *            the generic type of the source object
	 * @param <T>
	 *            the generic type of the target object
	 * @param source
	 *            the source object
	 * @param target
	 *            the target object
	 * @param propertyDescriptor
	 *            the property descriptor
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 * @throws NoSuchFieldException
	 *             is thrown if no such field exists.
	 * @throws SecurityException
	 *             is thrown if a security manager says no
	 */
	public static <S, T> void copyProperty(final S source, final T target,
		final PropertyDescriptor propertyDescriptor)
		throws IllegalAccessException, NoSuchFieldException, SecurityException
	{
		ReflectionExtensions.copyFieldValue(source, target, propertyDescriptor.getName());
	}

}
