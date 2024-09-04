package edu.mdc.cop2805c.assignment2;

import edu.mdc.cop2805c.assignment2.assets.CryptoCurrencyHolding;
import edu.mdc.cop2805c.assignment2.assets.NFT;
import edu.mdc.cop2805c.assignment2.base.CryptoCurrency;
import edu.mdc.cop2805c.assignment2.wallet.Transaction;
import edu.mdc.cop2805c.assignment2.wallet.TransactionType;
import java.lang.reflect.TypeVariable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author ndelessy
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionClassTest {

    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOutput;
    private InputStream originalIn;

  

    
    @Test
    @Order(2)
    public void TransactionClassExists() throws ClassNotFoundException,
            IOException,
            NoSuchMethodException,
            NoSuchFieldException {
 

        String className = "edu.mdc.cop2805c.assignment2.wallet.Transaction";
        Class.forName(className);
        Class<?> clazz = edu.mdc.cop2805c.assignment2.wallet.Transaction.class;

        NFT myBAYCArtNFT = new NFT(
                "Laser Eyes Ape",
                "A rare BAYC ape with glowing laser eyes",
                "0x0xb7f7f6cc7749c6e99d8f8aea5b95a7f14b88e16b",
                "Ethereum",
                120000
        );
        Transaction transaction = new Transaction(
                TransactionType.SELL,
                myBAYCArtNFT,
                "fromAddress",
                "toAddress"
        );
        
        CryptoCurrency tether = new CryptoCurrency(
                "Tether",
                "USDT",
                0.99,
                "Ethereum"
        );

        CryptoCurrencyHolding tetherHolding = new CryptoCurrencyHolding(
                tether,
                10.0
        );
        
        Transaction transaction2 = new Transaction(
                TransactionType.BUY,
                tetherHolding,
                "fromAddress",
                "toAddress"
        );
        
        Class<?> expectedUpperBoundClass = edu.mdc.cop2805c.assignment2.base.WalletStorable.class;
        TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
        boolean hasUpperBoundTypeParameter = false;
        if (typeParameters.length > 0) {
            for (TypeVariable<?> typeParam : typeParameters) {
                Type[] bounds = typeParam.getBounds();
                for (Type bound : bounds) {
                    System.out.println("   * " + bound.getTypeName());
                    if (bound instanceof Class && expectedUpperBoundClass.isAssignableFrom((Class<?>) bound)) {
                        hasUpperBoundTypeParameter = true;
                        break;
                    }
                }
            }
        }

        assertTrue(
                hasUpperBoundTypeParameter,
                "\n\tClass is not Generic, or does not have expected upper bound parameter " + expectedUpperBoundClass
        );

        String[] requiredMethodNames = {};
        Class<?>[][] requiredMethodParameterTypes = {};
        String[] requiredFieldNames = {"type", "fromAddress", "toAddress"};
        Class<?>[] requiredFieldtypes = {
                        edu.mdc.cop2805c.assignment2.wallet.TransactionType.class,
                        String.class, String.class};
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
                "\tExpected Class: " + className
                + "\n\tExpected methods: " + Arrays.toString(requiredMethodNames)
                + "\n\tExpected field: " + Arrays.toString(requiredFieldNames)
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
