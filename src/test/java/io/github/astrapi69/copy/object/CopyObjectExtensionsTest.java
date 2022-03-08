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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.astrapi69.date.CreateDateExtensions;
import io.github.astrapi69.io.Serializer;
import io.github.astrapi69.random.object.RandomObjectFactory;
import io.github.astrapi69.test.object.Employee;
import io.github.astrapi69.test.object.Member;
import io.github.astrapi69.test.object.Person;
import io.github.astrapi69.test.object.PremiumMember;
import io.github.astrapi69.test.object.enumtype.Gender;

/**
 * The unit test class for the class {@link CopyObjectExtensions}
 */
public class CopyObjectExtensionsTest
{

	/**
	 * Test method for
	 * {@link CopyObjectExtensions#copyBase64EncodedStringMapToObject(String, Class)}
	 */
	@Test
	public void testCopyBase64EncodedStringMapToObject()
	{
		Person actual;
		Person expected;
		String objectAsBase64EncodedStringMap;
		// new scenario
		objectAsBase64EncodedStringMap = "rO0ABXNyABFqYXZhLnV0aWwuSGFzaE1hcAUH2sHDFmDRAwACRgAKbG9hZEZhY3RvckkACXRocmVzaG9sZHhwP0AAAAAAAAx3CAAAABAAAAAFdAAGZ2VuZGVyfnIAL2lvLmdpdGh1Yi5hc3RyYXBpNjkudGVzdC5vYmplY3QuZW51bXR5cGUuR2VuZGVyAAAAAAAAAAASAAB4cgAOamF2YS5sYW5nLkVudW0AAAAAAAAAABIAAHhwdAAETUFMRXQABWFib3V0dAAAdAAEbmFtZXQAB2FzdGVyaXh0AAhuaWNrbmFtZXEAfgAIdAAHbWFycmllZHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAB4";
		expected = Person.builder().gender(Gender.MALE).name("asterix").build();
		actual = CopyObjectExtensions
			.copyBase64EncodedStringMapToObject(objectAsBase64EncodedStringMap, Person.class);
		assertEquals(expected, actual);
	}

	/**
	 * Test method for
	 * {@link CopyObjectExtensions#copyObjectToMapBase64EncodedString(Object, String...)}
	 *
	 * @throws IntrospectionException
	 *             is thrown if an exception occurs during introspection
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 * @throws InvocationTargetException
	 *             is thrown if the underlying method throws an exception
	 */
	@Test
	public void testCopyToMapBase64EncodedString()
		throws IntrospectionException, InvocationTargetException, IllegalAccessException
	{
		Person original;
		String actual;
		String expected;
		// new scenario
		original = Person.builder().gender(Gender.MALE).name("asterix").build();
		actual = CopyObjectExtensions.copyObjectToMapBase64EncodedString(original, "class");
		expected = "rO0ABXNyABFqYXZhLnV0aWwuSGFzaE1hcAUH2sHDFmDRAwACRgAKbG9hZEZhY3RvckkACXRocmVzaG9sZHhwP0AAAAAAAAx3CAAAABAAAAAFdAAGZ2VuZGVyfnIAL2lvLmdpdGh1Yi5hc3RyYXBpNjkudGVzdC5vYmplY3QuZW51bXR5cGUuR2VuZGVyAAAAAAAAAAASAAB4cgAOamF2YS5sYW5nLkVudW0AAAAAAAAAABIAAHhwdAAETUFMRXQABWFib3V0dAAAdAAEbmFtZXQAB2FzdGVyaXh0AAhuaWNrbmFtZXEAfgAIdAAHbWFycmllZHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAB4";
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link CopyObjectExtensions#copyMapToObject(Map, Class)}
	 * 
	 * @throws IntrospectionException
	 *             is thrown if an exception occurs during introspection
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 * @throws InvocationTargetException
	 *             is thrown if the underlying method throws an exception
	 */
	@Test
	public void testCopyMapToObject()
		throws IntrospectionException, InvocationTargetException, IllegalAccessException
	{
		Person original;
		Person copy;
		Map<String, Object> actual;
		Map<String, Object> expected;

		// prepare new scenario
		original = Person.builder().gender(Gender.MALE).name("asterix").build();
		actual = CopyObjectExtensions.copyToMap(original, "class");
		expected = new HashMap<>();
		expected.put("gender", Gender.MALE);
		expected.put("about", "");
		expected.put("name", "asterix");
		expected.put("nickname", "");
		expected.put("married", false);
		assertEquals(expected, actual);
		// test
		copy = CopyObjectExtensions.copyMapToObject(actual, Person.class);
		assertEquals(original, copy);

		// prepare new scenario
		final Person person = Person.builder().gender(Gender.FEMALE).name("Anna").married(true)
			.about("Ha ha ha...").nickname("beast").build();
		Employee employeeAnna = Employee.builder().person(person).id("23").build();

		actual = CopyObjectExtensions.copyToMap(employeeAnna, "class");
		Employee employeeCopy = CopyObjectExtensions.copyMapToObject(actual, Employee.class);
		assertEquals(employeeAnna, employeeCopy);

		// prepare new scenario...
		PremiumMember premiumMember = PremiumMember.buildPremiumMember().credits("10")
			.dateofbirth(CreateDateExtensions.newDate(1979, 2, 24))
			.dateofbirth(CreateDateExtensions.newDate(2029, 2, 24)).about("about")
			.gender(Gender.MALE).married(true).name("Wanne").nickname("wan").build();
		actual = CopyObjectExtensions.copyToMap(premiumMember, "class");
		PremiumMember premiumMemberCopy = CopyObjectExtensions.copyMapToObject(actual,
			PremiumMember.class);
		assertEquals(premiumMember, premiumMemberCopy);
	}

	/**
	 * Test method for {@link CopyObjectExtensions#copyToMap(Object, String...)}
	 *
	 * @throws IntrospectionException
	 *             is thrown if an exception occurs during introspection
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 * @throws InvocationTargetException
	 *             is thrown if the underlying method throws an exception
	 */
	@Test
	public void testCopyToMap()
		throws IntrospectionException, InvocationTargetException, IllegalAccessException
	{
		Person original;
		HashMap<String, Object> actual;
		HashMap<String, Object> expected;

		// new scenario
		original = Person.builder().gender(Gender.MALE).name("asterix").build();
		actual = (HashMap<String, Object>)CopyObjectExtensions.copyToMap(original, "class");
		expected = new HashMap<>();
		expected.put("gender", Gender.MALE);
		expected.put("about", "");
		expected.put("name", "asterix");
		expected.put("nickname", "");
		expected.put("married", false);
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link CopyObjectExtensions#copyToMap(Object, String...)}
	 *
	 * @throws IntrospectionException
	 *             is thrown if an exception occurs during introspection
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 * @throws InvocationTargetException
	 *             is thrown if the underlying method throws an exception
	 */
	@Test
	public void testCopyToMapAndToBase64EncodedString()
		throws IntrospectionException, InvocationTargetException, IllegalAccessException
	{
		String actual;
		String expected;

		Person original;
		HashMap<String, Object> personMap;
		HashMap<String, Object> expectedPerson;

		// new scenario
		original = Person.builder().gender(Gender.MALE).name("asterix").build();
		personMap = (HashMap<String, Object>)CopyObjectExtensions.copyToMap(original, "class");
		expectedPerson = new HashMap<>();
		expectedPerson.put("gender", Gender.MALE);
		expectedPerson.put("about", "");
		expectedPerson.put("name", "asterix");
		expectedPerson.put("nickname", "");
		expectedPerson.put("married", false);
		assertEquals(expectedPerson, personMap);
		// copy map to base64 encoded {@link String} object
		actual = Serializer.toBase64EncodedString(personMap);
		expected = "rO0ABXNyABFqYXZhLnV0aWwuSGFzaE1hcAUH2sHDFmDRAwACRgAKbG9hZEZhY3RvckkACXRocmVzaG9sZHhwP0AAAAAAAAx3CAAAABAAAAAFdAAGZ2VuZGVyfnIAL2lvLmdpdGh1Yi5hc3RyYXBpNjkudGVzdC5vYmplY3QuZW51bXR5cGUuR2VuZGVyAAAAAAAAAAASAAB4cgAOamF2YS5sYW5nLkVudW0AAAAAAAAAABIAAHhwdAAETUFMRXQABWFib3V0dAAAdAAEbmFtZXQAB2FzdGVyaXh0AAhuaWNrbmFtZXEAfgAIdAAHbWFycmllZHNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAB4";
		assertEquals(expected, actual);
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
	public void testCopyWithIgnoreFieldnames()
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
	 *             if the caller does not have access to the property accessor method
	 */
	@Test
	public void testCopy() throws IllegalAccessException
	{
		Person original;
		Person destination;
		Person actual;
		Person expected;

		original = Person.builder().about("about").gender(Gender.MALE).married(false)
			.name("asterix").nickname("wan").build();

		destination = Person.builder().build();

		CopyObjectExtensions.copyObject(original, destination);
		expected = original;
		actual = destination;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link CopyObjectExtensions#copyObject(Object, Object, String...)} with
	 * Member that is a descendant of Person.
	 *
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 */
	@Test
	public void testCopyMember() throws IllegalAccessException
	{
		Member original;
		Member destination;
		Member actual;
		Member expected;

		original = Member.buildMember().dateofbirth(CreateDateExtensions.newDate(1979, 2, 24))
			.dateofbirth(CreateDateExtensions.newDate(2029, 2, 24)).about("about")
			.gender(Gender.MALE).married(true).name("Wanne").nickname("wan").build();

		destination = Member.buildMember().build();

		CopyObjectExtensions.copyObject(original, destination);
		expected = original;
		actual = destination;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link CopyObjectExtensions#copyObject(Object, Object, String...)} with
	 * PremiumMember that is a descendant of Member that is a descendant of Person.
	 *
	 * @throws IllegalAccessException
	 *             if the caller does not have access to the property accessor method
	 */
	@Test
	public void testCopyPremiumMember() throws IllegalAccessException
	{
		PremiumMember original;
		PremiumMember destination;
		PremiumMember actual;
		PremiumMember expected;

		original = PremiumMember.buildPremiumMember().credits("10")
			.dateofbirth(CreateDateExtensions.newDate(1979, 2, 24))
			.dateofbirth(CreateDateExtensions.newDate(2029, 2, 24)).about("about")
			.gender(Gender.MALE).married(true).name("Wanne").nickname("wan").build();

		destination = PremiumMember.buildPremiumMember().build();

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
	 */
	@Test
	public void testCopyObject() throws IllegalAccessException
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
	 */
	@Test
	public void testCopyObjectIgnoreFields() throws IllegalAccessException
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
	 */
	@Test
	public void testCopyString() throws IllegalAccessException
	{
		String actual;
		String expected;

		expected = "Foo bar";
		actual = CopyObjectExtensions.copyObject(expected);
		assertEquals(expected, actual);
	}

}
