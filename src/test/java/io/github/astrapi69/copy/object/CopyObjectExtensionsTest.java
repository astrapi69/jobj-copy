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

import static org.testng.AssertJUnit.assertArrayEquals;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.meanbean.test.BeanTestException;
import org.meanbean.test.BeanTester;
import org.testng.annotations.Test;

import io.github.astrapi69.random.object.RandomObjectFactory;
import io.github.astrapi69.test.objects.Employee;
import io.github.astrapi69.test.objects.Person;
import io.github.astrapi69.test.objects.enums.Gender;

/**
 * The unit test class for the class {@link CopyObjectExtensions}.
 */
public class CopyObjectExtensionsTest
{

	/**
	 * Test method for {@link CopyObjectExtensions#copyOfEnumValue(Object, Class)}
	 */
	@Test
	public void testCopyOfEnumValue()
	{
		Object expected;
		Object actual;
		// new scenario ...
		expected = Gender.FEMALE;
		actual = CopyObjectExtensions.copyOfEnumValue(expected, Gender.class);
		assertEquals(expected, actual);

		expected = new Person();
		actual = CopyObjectExtensions.copyOfEnumValue(expected, Person.class);
		assertNull(actual);
	}

	/**
	 * Test method for {@link CopyObjectExtensions#copyOfArray(Object)}
	 */
	@Test
	public void testCopyOfArray()
	{
		Object expected;
		Object actual;

		expected = new Person();
		actual = CopyObjectExtensions.copyOfArray(expected);
		assertNull(actual);
		// new scenario ...
		expected = new boolean[] { false, true };
		actual = CopyObjectExtensions.copyOfArray(expected);
		assertArrayEquals((boolean[])expected, (boolean[])actual);
		// new scenario ...
		expected = new byte[] { 1, 2 };
		actual = CopyObjectExtensions.copyOfArray(expected);
		assertArrayEquals((byte[])expected, (byte[])actual);
		// new scenario ...
		expected = new char[] { 1, 2 };
		actual = CopyObjectExtensions.copyOfArray(expected);
		assertArrayEquals((char[])expected, (char[])actual);
		// new scenario ...
		expected = new short[] { 1, 2 };
		actual = CopyObjectExtensions.copyOfArray(expected);
		assertArrayEquals((short[])expected, (short[])actual);
		// new scenario ...
		expected = new int[] { 1, 2 };
		actual = CopyObjectExtensions.copyOfArray(expected);
		assertArrayEquals((int[])expected, (int[])actual);
		// new scenario ...
		expected = new long[] { 1, 2 };
		actual = CopyObjectExtensions.copyOfArray(expected);
		assertArrayEquals((long[])expected, (long[])actual);
		// new scenario ...
		expected = new float[] { 1.0f, 2.0f };
		actual = CopyObjectExtensions.copyOfArray(expected);
		assertArrayEquals((float[])expected, (float[])actual, 0);
		// new scenario ...
		expected = new double[] { 1.0d, 2.0d };
		actual = CopyObjectExtensions.copyOfArray(expected);
		assertArrayEquals((double[])expected, (double[])actual, 0);

		expected = new Double[] { 1.0d, 2.0d };
		actual = CopyObjectExtensions.copyOfArray(expected);
		assertArrayEquals((Double[])expected, (Double[])actual);
	}

	/**
	 * Test method for {@link CopyObjectExtensions#copy(Object, Object, String...)}
	 *
	 * @throws IllegalAccessException
	 *             is thrown if the class or its default constructor is not accessible.
	 * @throws InstantiationException
	 *             is thrown if this {@code Class} represents an abstract class, an interface, an
	 *             array class, a primitive type, or void; or if the class has no default
	 *             constructor; or if the instantiation fails for some other reason.
	 * @throws NoSuchFieldException
	 *             is thrown if no such field exists
	 */
	@Test
	public void testCopyNotEqualType()
		throws IllegalAccessException, NoSuchFieldException, InstantiationException
	{
		Person original;
		Person destination;
		Person actual;
		Person expected;
		// new scenario
		original = Person.builder().gender(Gender.MALE).name("asterix").build();

		destination = Person.builder().build();

		CopyObjectExtensions.copy(original, destination);
		expected = original;
		actual = destination;
		assertEquals(expected, actual);
		// new scenario
		destination = Person.builder().build();

		CopyObjectExtensions.copy(original, destination, "gender");
		expected = Person.builder().name("asterix").build();
		actual = destination;
		assertEquals(expected, actual);
		// new scenario
		original = RandomObjectFactory.newRandomObject(Person.class, "$jacocoData");

		CopyObjectExtensions.copy(original, destination);
		expected = original;
		actual = destination;
		assertEquals(expected, actual);

	}


	/**
	 * Test method for {@link CopyObjectExtensions#copyObject(Object, Object, String...)}
	 *
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	@Test
	public void testCopy()
		throws IllegalAccessException, ClassNotFoundException, InstantiationException
	{
		Person original;
		Person destination;
		Person actual;
		Person expected;

		original = Person.builder().gender(Gender.MALE).name("asterix").build();

		destination = Person.builder().build();

		CopyObjectExtensions.copyObject(original, destination);
		expected = original;
		actual = destination;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link CopyObjectExtensions#copyObject(Object, String...)}
	 *
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 * @throws ClassNotFoundException
	 *             is thrown if the class cannot be located
	 * @throws InstantiationException
	 *             Thrown if one of the following reasons: the class object
	 *             <ul>
	 *             <li>represents an abstract class</li>
	 *             <li>represents an interface</li>
	 *             <li>represents an array class</li>
	 *             <li>represents a primitive type</li>
	 *             <li>represents {@code void}</li>
	 *             <li>has no nullary constructor</li>
	 *             </ul>
	 */
	@Test
	public void testCopyObject()
		throws IllegalAccessException, InstantiationException, ClassNotFoundException
	{
		Person actual;
		Person expected;

		expected = Person.builder().gender(Gender.MALE).name("asterix").build();
		actual = CopyObjectExtensions.copyObject(expected, "$jacocoData");
		assertEquals(expected, actual);

		final Person person = Person.builder().gender(Gender.FEMALE).name("Anna").married(true)
			.about("Ha ha ha...").nickname("beast").build();

		Employee original = Employee.builder().person(person).id("23").build();
		Employee employee = CopyObjectExtensions.copyObject(original, "$jacocoData");
		assertEquals(original, employee);
	}

	/**
	 * Test method for {@link CopyObjectExtensions#copyObject(Object, String...)}
	 *
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 * @throws InstantiationException
	 *             Thrown if one of the following reasons: the class object
	 *             <ul>
	 *             <li>represents an abstract class</li>
	 *             <li>represents an interface</li>
	 *             <li>represents an array class</li>
	 *             <li>represents a primitive type</li>
	 *             <li>represents {@code void}</li>
	 *             <li>has no nullary constructor</li>
	 *             </ul>
	 * @throws ClassNotFoundException
	 *             is thrown if the class cannot be located
	 */
	@Test
	public void testCopyObjectIgnoreFields()
		throws IllegalAccessException, InstantiationException, ClassNotFoundException
	{
		Person actual;
		Person expected;

		expected = Person.builder().gender(Gender.MALE).name("asterix").build();
		actual = CopyObjectExtensions.copyObject(expected, "$jacocoData");
		assertEquals(expected, actual);

		final Person person = Person.builder().gender(Gender.FEMALE).name("Anna").married(true)
			.about("Ha ha ha...").nickname("beast").build();

		Employee original = Employee.builder().person(person).id("23").build();
		Employee destination = Employee.builder().build();
		Employee employee = CopyObjectExtensions.copyObject(original, destination, "$jacocoData");
		assertEquals(original, employee);
		// new scenario with ignore the id...
		destination = Employee.builder().build();
		employee = CopyObjectExtensions.copyObject(original, destination, "id", "$jacocoData");
		original.setId(null);
		assertEquals(original, employee);
	}

	/**
	 * Test method for {@link CopyObjectExtensions#copyPropertiesWithReflection(Object, String...)}
	 *
	 * @throws IllegalAccessException
	 *             is thrown if the class or its default constructor is not accessible
	 * @throws NoSuchFieldException
	 *             is thrown if no such field exists
	 */
	@Test
	public void testCopyPropertiesWithReflection()
		throws IllegalAccessException, NoSuchFieldException
	{
		Employee expected;
		Employee actual;

		final Person person = Person.builder().gender(Gender.FEMALE).name("Anna").married(true)
			.about("Bla foo bar ...").nickname("beast").build();

		expected = Employee.builder().person(person).id("23").build();

		actual = CopyObjectExtensions.copyPropertiesWithReflection(expected, "serialVersionUID");

		assertEquals(expected, actual);

	}

	/**
	 * Test method for
	 * {@link CopyObjectExtensions#copyPropertyWithReflection(Object, Object, String)}.
	 */
	@Test
	public void testCopyPropertyWithReflection() throws NoSuchFieldException, SecurityException,
		IllegalArgumentException, IllegalAccessException
	{

		Person expected;
		Person actual;

		final Person person = Person.builder().gender(Gender.FEMALE).name("Anna").married(true)
			.about("Ha ha ha...").nickname("beast").build();

		Employee original = Employee.builder().person(person).id("23").build();

		Employee destination = Employee.builder().build();

		CopyObjectExtensions.copyPropertyWithReflection(original, destination, "person");
		expected = original.getPerson();
		actual = destination.getPerson();
		assertEquals(expected, actual);

	}

	/**
	 * Test method for {@link CopyObjectExtensions#copySerializedObject(java.io.Serializable)}.
	 */
	@Test
	public void testCopySerializedObject() throws ClassNotFoundException, IOException
	{
		Employee expected;
		Employee actual;

		final Person person = Person.builder().gender(Gender.FEMALE).name("Anna").married(true)
			.about("Ha ha ha...").nickname("beast").build();

		expected = Employee.builder().person(person).id("23").build();

		actual = CopyObjectExtensions.copySerializedObject(expected);
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link CopyObjectExtensions#copyObject(Object, String...)}
	 *
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 * @throws ClassNotFoundException
	 *             is thrown if the class cannot be located
	 * @throws InstantiationException
	 *             Thrown if one of the following reasons: the class object
	 *             <ul>
	 *             <li>represents an abstract class</li>
	 *             <li>represents an interface</li>
	 *             <li>represents an array class</li>
	 *             <li>represents a primitive type</li>
	 *             <li>represents {@code void}</li>
	 *             <li>has no nullary constructor</li>
	 *             </ul>
	 */
	@Test
	public void testCopyString()
		throws IllegalAccessException, InstantiationException, ClassNotFoundException
	{
		String actual;
		String expected;

		expected = "Foo bar";
		actual = CopyObjectExtensions.copyObject(expected);
		assertEquals(expected, actual);

	}

	/**
	 * Test method for {@link CopyObjectExtensions} with {@link BeanTester}
	 */
	@Test(expectedExceptions = { BeanTestException.class, InvocationTargetException.class,
			UnsupportedOperationException.class })
	public void testWithBeanTester()
	{
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(CopyObjectExtensions.class);
	}

}
