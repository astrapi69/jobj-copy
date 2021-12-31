package io.github.astrapi69.copy.object;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class PrimitiveObjectClassArrays
{
	Boolean[] booleanArray;
	Byte[] byteArray;
	Character[] charArray;
	Short[] shortArray;
	Integer[] intArray;
	Long[] longArray;
	Float[] floatArray;
	Double[] doubleArray;
}
