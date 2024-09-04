package edu.mdc.cop2805c.assignment2.base;

public interface WalletStorable {

    /*
     * Represents objects that can be stored in a crypto wallet
     */

    String getBlockchainNetwork();

    double getValueInUSD();

    String getShortDescription();

    String getLongDescription();
}
