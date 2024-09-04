package edu.mdc.cop2805c.assignment2;

import edu.mdc.cop2805c.assignment2.app.Main;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author ndelessy
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainClassTest {

  

    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOutput;
    private InputStream originalIn;

 
 
   
   
  
    @Test
    @Order(5)
    public void testMainClass() throws IOException {
        

        String testInput = "1\nQ\n";
        byte[] inputBytes = testInput.getBytes();
        InputStream testIn = new ByteArrayInputStream(inputBytes);
        System.setIn(testIn);
        //Starting
        String[] args = {};
        Main.main(args);


        testInput = "7\nQ\n";
        inputBytes = testInput.getBytes();
        InputStream testIn3 = new ByteArrayInputStream(inputBytes);
        System.setIn(testIn3);

        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));

         //Restarting               
        Main.main(args);  

  

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
