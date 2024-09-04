package edu.mdc.cop2805c.assignment2.assets;

import edu.mdc.cop2805c.assignment2.base.CryptoCurrency;
import edu.mdc.cop2805c.assignment2.base.WalletStorable;

public class CryptoCurrencyHolding implements WalletStorable {

    /*
     * This class represents a specific holding of a cryptocurrency. It tracks the amount of a particular cryptocurrency.
     */

    public CryptoCurrency cryptoCurrency;
    public double amount;

    public CryptoCurrencyHolding(CryptoCurrency cryptoCurrency, double amount) {
        this.cryptoCurrency = cryptoCurrency;
        this.amount = amount;
    }

    public CryptoCurrency getCryptoCurrency() {
        return this.cryptoCurrency;
    }

    public void setCryptoCurrency(CryptoCurrency value) {
        this.cryptoCurrency = value;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double value) {
        this.amount = value;
    }

    // Implementing abstract methods from CryptoAsset
    @Override
    public String getBlockchainNetwork() {
        return cryptoCurrency.getBlockchainNetwork();
    }

    // Implementing abstract methods from WalletStorable interface
    @Override
    public double getValueInUSD() {
        return amount * cryptoCurrency.getCurrentPrice();
    }

    @Override
    public String getShortDescription() {
        return cryptoCurrency.getName() + " (" + cryptoCurrency.getSymbol() + "): " + amount + " units";
    }

    @Override
    public String getLongDescription() {
        return "Crypto Currency Holding:\n"
                + "Name: " + cryptoCurrency.getName() + "\n"
                + "Symbol: " + cryptoCurrency.getSymbol() + "\n"
                + "Blockchain Network: " + getBlockchainNetwork() + "\n"
                + "Amount: " + amount + " units\n"
                + "Current Value: $" + getValueInUSD();
    }

}
