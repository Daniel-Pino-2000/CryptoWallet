package edu.mdc.cop2805c.assignment2.base;

public class CryptoCurrency extends CryptoAsset {

    private String name;
    private String symbol;
    private double currentPrice;

    public CryptoCurrency(String name, String symbol, double currentPrice, String blockchainNetwork) {
        super(blockchainNetwork);
        this.name = name;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String value) {
        this.symbol = value;
    }

    public double getCurrentPrice() {
        return this.currentPrice;
    }

    public void setCurrentPrice(double value) {
        this.currentPrice = value;
    }

    // Helper methods as needed.
}
