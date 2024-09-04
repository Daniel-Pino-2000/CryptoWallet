package edu.mdc.cop2805c.assignment2;

import edu.mdc.cop2805c.assignment2.assets.CryptoCurrencyHolding;
import edu.mdc.cop2805c.assignment2.assets.NFT;
import edu.mdc.cop2805c.assignment2.base.CryptoCurrency;
import edu.mdc.cop2805c.assignment2.wallet.CryptoWallet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Queue;

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
public class CryptoWalletClassTest {


    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOutput;
    private InputStream originalIn;


   
    
    @Test
    @Order(3)
    public void testCryptoWalletClass() throws IOException, NoSuchMethodException, NoSuchFieldException {

        
         String className = "edu.mdc.cop2805c.assignment2.wallet.CryptoWallet";
        //Class.forName(className); Does not work because is generic!
        Class<?> clazz = edu.mdc.cop2805c.assignment2.wallet.CryptoWallet.class;

          String[] requiredMethodNames = {"processTransactions"};
        Class<?>[][] requiredMethodParameterTypes = {{}};
        String[] requiredFieldNames = {"transactionQueue"};
        Class<?>[] requiredFieldtypes = {Queue.class};
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
                "\tExpected Class: " + className + "\n\tExpected methods: " + Arrays.toString(requiredMethodNames) + "\n\tExpected field: " + Arrays.toString(requiredFieldNames)
        );

        CryptoWallet<CryptoCurrencyHolding> cryptoCurrencyWallet = new CryptoWallet<>(
                "Long-Term Currency Wallet",
                "LM34oNqjFNzJpYUo9Bj5YbCWRvLFYR3zCK",
                "mykeyfile6"
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

        CryptoCurrency bitcoin = new CryptoCurrency(
                "Bitcoin",
                "BTC",
                68000.1, // Replace with actual price (consider using an API)
                "Bitcoin"
        );
        CryptoCurrencyHolding largeBitcoinHolding = new CryptoCurrencyHolding(
                bitcoin,
                0.2
        );

        cryptoCurrencyWallet.buy(tetherHolding, "fromAddress1");
        cryptoCurrencyWallet.buy(largeBitcoinHolding, "fromAddress2");

        CryptoCurrencyHolding smallBitcoinHolding = new CryptoCurrencyHolding(
                bitcoin,
                0.001
        );
        cryptoCurrencyWallet.sell(smallBitcoinHolding, "toAddress1");
        
        cryptoCurrencyWallet.processTransactions();

        double expResult = 13541.92;
        double result = cryptoCurrencyWallet.getTotalValueInUSD();
        assertEquals(
                expResult,
                 result,
                 1,
                 "\tgetTotalValueInUSD(): Your result: " + result + "\n\tExpected result: " + expResult
        );

        CryptoWallet<NFT> nftWallet = new CryptoWallet<>(
                "Long-Term NFT Wallet",
                "GBH4TZYZ4IRCPO44CBOLFUHULU2WGALXTAVESQA6432MBJMABBB4GIYI",
                "mykeyfile8"
        );
        NFT myBAYCArtNFT = new NFT(
                "Laser Eyes Ape",
                "A rare BAYC ape with glowing laser eyes",
                "0x0xb7f7f6cc7749c6e99d8f8aea5b95a7f14b88e16b",
                "Ethereum",
                120000
        );

        NFT myConcertTicket = new NFT(
                "Ticket for John Doe for Live Music Extravaganza on 2024-08-15",
                "Your reserved seat for the concert",
                "0xSAMPLE_CONTRACT_ADDRESS",
                "Ethereum",
                20
        );

        nftWallet.buy(myBAYCArtNFT, "fromAddress");
        nftWallet.buy(myConcertTicket, "fromAddress");
        nftWallet.sell(myConcertTicket, "toAddress");
        
        nftWallet.processTransactions();

        double expResult2 = 120000;
        double result2 = nftWallet.getTotalValueInUSD();
        assertEquals(
                expResult2,
                 result2,
                 1,
                 "\tgetTotalValueInUSD(): Your result: " + result2 + "\n\tExpected result: " + expResult2
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
