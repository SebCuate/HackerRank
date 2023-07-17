package javaStuff.javaExams.exam_whizlabs_test_1;

public class Question_02 {

	byte b = 100; // Line 1
	short s = 'A'; // Line 2
//	char c = "A"; // Line 3
//	float f = 1.0; // Line 4
	double d = 1; // Line 5
	
//A: Line 1
//B: Line 2
//C: Line 3
//D: Line 4
//E: Line 5
//Answer: C and D
//
//As per the Oracle Java Tutorials, an integral literal is of typelongif it ends with the letterLorl; otherwise it is of typeint. Therefore, the literal values on line 1 and line 5 are of type int.
//
//On line 1, an int value is assigned to a variable of type byte. This assignment is allowed and doesn't require a casting operator because the given value is within the space of the byte type - from -128 to 127. According to section 5.2 of the Java Language Specification, a narrowing primitive conversion may be used if the variable is of typebyte,short, orchar, and the value of the constant expression is representable in the type of the variable.
//Likewise, the conversion on line 2 is a narrowing one, but the constant 65 (the Unicode value of letter A) is well within the value space of the short data type. Hence no casting is needed.
//On line 5, the assignment of an int value to a double variable is just a simple widening primitive conversion. This conversion is listed in section 5.1.2 of the Java Language Specification.
//
//On line 3, the assignment is invalid because the value is a String object rather than a char value.
//On line 4, the constant value doesn't have a suffix. Notice a floating-point literal is considered a double number if it ends with D, d or no suffix. Unlike integral conversions, a narrowing conversion of a floating-point number always requires the casting operator.
//
//Reference:
//https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html</a></li>
//https://docs.oracle.com/javase/specs/jls/se11/html/jls-5.html#jls-5.1</a></li>
//https://docs.oracle.com/javase/specs/jls/se11/html/jls-5.html#jls-5.2</a></li>

}
