package edu.mdc.cop2805c.assignment2;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author ndelessy
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionTypeEnumTest {

    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOutput;
    private InputStream originalIn;


    @Test
    @Order(1)
    public void TransactionTypeEnumExists() throws ClassNotFoundException,
            IOException,
            NoSuchMethodException,
            NoSuchFieldException {
        //Class exists?
        String className = "edu.mdc.cop2805c.assignment2.wallet.TransactionType";
        Class.forName(className);
        Class<?> clazz = edu.mdc.cop2805c.assignment2.wallet.TransactionType.class;

        String[] requiredMethodNames = {};
        Class<?>[][] requiredMethodParameterTypes = {{}};
        String[] requiredFieldNames = {};
        Class<?>[] requiredFieldtypes = {};
        //Test Properties, etc. using reflection
        ClassTester classTester = new ClassTester(
                false,
                false,
                requiredMethodNames,
                requiredMethodParameterTypes,
                requiredFieldNames,
                requiredFieldtypes
        );

        assertTrue(
                classTester.testClass(clazz),
                "\tExpected Enum: " + className
        );

    }


    @BeforeEach
    public void redirectSystemOut() {
        originalOut = System.out;
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @BeforeEach
    public void redirectSystemIn() {
        originalIn = System.in;
    }

    @AfterEach
    public void restoreSystemIn() {
        System.setIn(originalIn);
    }

 
}
