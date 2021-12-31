package io.github.astrapi69.copy.object;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class PrimitiveArrays
{
	boolean[] booleanArray;
	byte[] byteArray;
	char[] charArray;
	short[] shortArray;
	int[] intArray;
	long[] longArray;
	float[] floatArray;
	double[] doubleArray;
}
