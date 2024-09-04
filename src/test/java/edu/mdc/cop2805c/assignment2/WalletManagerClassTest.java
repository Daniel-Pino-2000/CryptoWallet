package edu.mdc.cop2805c.assignment2;

import edu.mdc.cop2805c.assignment2.assets.CryptoCurrencyHolding;
import edu.mdc.cop2805c.assignment2.assets.NFT;
import edu.mdc.cop2805c.assignment2.base.CryptoCurrency;
import edu.mdc.cop2805c.assignment2.wallet.WalletManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

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
public class WalletManagerClassTest {

   
    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOutput;
    private InputStream originalIn;

 
    @Test
    @Order(4)
    public void testWalletManagerClass() throws IOException {

      
        WalletManager walletManager = new WalletManager();

        walletManager.createCryptoCurrencyWallet(
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

        walletManager.getCryptoCurrencyWallet(0).buy(tetherHolding, "fromAddress1");
        walletManager.getCryptoCurrencyWallet(0).buy(largeBitcoinHolding, "fromAddress2");

        CryptoCurrencyHolding smallBitcoinHolding = new CryptoCurrencyHolding(
                bitcoin,
                0.001
        );
        walletManager.getCryptoCurrencyWallet(0).sell(smallBitcoinHolding, "toAddress1");
        
        walletManager.processAllTransactions();

        double expResult = 13541.92;
        double result = walletManager.getCryptoCurrencyWallet(0).getTotalValueInUSD();
        assertEquals(
                expResult,
                 result,
                 1,
                 "\tgetTotalValueInUSD(): Your result: " + result + "\n\tExpected result: " + expResult
        );
        
         walletManager.createNFTWallet(
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

        walletManager.getNFTWallet(0).buy(myBAYCArtNFT, "fromAddress");
        walletManager.getNFTWallet(0).buy(myConcertTicket, "fromAddress");
        walletManager.getNFTWallet(0).sell(myConcertTicket, "toAddress");
        
        walletManager.processAllTransactions();

        double expResult2 = 120000;
        double result2 = walletManager.getNFTWallet(0).getTotalValueInUSD();
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
