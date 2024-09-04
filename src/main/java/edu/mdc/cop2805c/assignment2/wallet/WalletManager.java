package edu.mdc.cop2805c.assignment2.wallet;

import java.util.ArrayList;
import java.util.List;

import edu.mdc.cop2805c.assignment2.assets.CryptoCurrencyHolding;
import edu.mdc.cop2805c.assignment2.assets.NFT;
import edu.mdc.cop2805c.assignment2.base.CryptoCurrency;

public class WalletManager {

    private List<CryptoWallet<CryptoCurrencyHolding>> cryptoCurrencyWallets;
    private List<CryptoWallet<NFT>> nftWallets;
    private List<CryptoCurrency> cryptoCurrencies;

    // Constructor
    public WalletManager() {
        this.cryptoCurrencyWallets = new ArrayList<>();
        this.nftWallets = new ArrayList<>();
        this.cryptoCurrencies = new ArrayList<>();
    }

    // Getters and Setters
    public List<CryptoWallet<CryptoCurrencyHolding>> getCryptoCurrencyWallets() {
        return cryptoCurrencyWallets;
    }

    public void setCryptoCurrencyWallets(List<CryptoWallet<CryptoCurrencyHolding>> cryptoCurrencyWallets) {
        this.cryptoCurrencyWallets = cryptoCurrencyWallets;
    }

    public List<CryptoWallet<NFT>> getNftWallets() {
        return nftWallets;
    }

    public void setNftWallets(List<CryptoWallet<NFT>> nftWallets) {
        this.nftWallets = nftWallets;
    }

    public List<CryptoCurrency> getCryptoCurrencies() {
        return cryptoCurrencies;
    }

    public void setCryptoCurrencies(List<CryptoCurrency> cryptoCurrencies) {
        this.cryptoCurrencies = cryptoCurrencies;
    }

    // Helper methods
    public void createCryptoCurrencyWallet(String niceName, String address, String privateKeyFilename) {
        CryptoWallet<CryptoCurrencyHolding> wallet = new CryptoWallet<>(niceName, address, privateKeyFilename);
        cryptoCurrencyWallets.add(wallet);
    }

    public void createNFTWallet(String niceName, String address, String privateKeyFilename) {
        CryptoWallet<NFT> wallet = new CryptoWallet<>(niceName, address, privateKeyFilename);
        nftWallets.add(wallet);
    }

    public CryptoWallet<CryptoCurrencyHolding> getCryptoCurrencyWallet(int index) {
        return cryptoCurrencyWallets.get(index);
    }

    public CryptoWallet<NFT> getNFTWallet(int index) {
        return nftWallets.get(index);
    }

    public int getNumWallets() {
        return cryptoCurrencyWallets.size() + nftWallets.size();
    }

    public double getCombinedValueInUSD() {
        double combinedValue = 0.0;

        // Calculate combined value from crypto currency wallets
        for (CryptoWallet<CryptoCurrencyHolding> wallet : cryptoCurrencyWallets) {
            for (CryptoCurrencyHolding holding : wallet.getHoldings()) {
                combinedValue += holding.getValueInUSD();
            }
        }

        // Calculate combined value from NFT wallets
        for (CryptoWallet<NFT> wallet : nftWallets) {
            for (NFT nft : wallet.getHoldings()) {
                combinedValue += nft.getValueInUSD();
            }
        }

        return combinedValue;
    }

    public String getShortDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total number of wallets: ").append(getNumWallets()).append("\n");
        sb.append("Combined USD value of all assets: $").append(getCombinedValueInUSD()).append("\n");
        return sb.toString();
    }

    public CryptoCurrency getCryptoCurrency(String symbol) {
        for (CryptoCurrency crypto : cryptoCurrencies) {
            if (crypto.getSymbol().equalsIgnoreCase(symbol)) {
                return crypto;
            }
        }
        return null; // Not found
    }

    public void addCryptoCurrency(CryptoCurrency newCryptoCurrency) {
        cryptoCurrencies.add(newCryptoCurrency);
    }

    public void processAllTransactions() {
        // Process transactions for all crypto currency wallets
        for (CryptoWallet<CryptoCurrencyHolding> wallet : cryptoCurrencyWallets) {
            wallet.processTransactions();
        }

        // Process transactions for all NFT wallets
        for (CryptoWallet<NFT> wallet : nftWallets) {
            wallet.processTransactions();
        }
    }
}
