package edu.mdc.cop2805c.assignment2.assets;

import edu.mdc.cop2805c.assignment2.base.CryptoAsset;
import edu.mdc.cop2805c.assignment2.base.WalletStorable;

public class NFT extends CryptoAsset implements WalletStorable {

    /*
     * This class will represent Non-Fungible Tokens (NFTs) within your crypto wallet system.
     */
    public String name; // Stores the human-readable name of the NFT (e.g., "Cryptopunk #1234", "Bored Ape Yacht Club #7452").
    public String description;
    public String contractAddress; // Stores the unique address on the blockchain where the NFT contract resides.
    public double valueInUSD;

    public NFT(String name, String description, String contractAddress, String blockchainNetwork, double valueInUSD) {
        super(blockchainNetwork);
        this.name = name;
        this.description = description;
        this.contractAddress = contractAddress;
        this.valueInUSD = valueInUSD;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getContractAddress() {
        return this.contractAddress;
    }

    public void setContractAddress(String value) {
        this.contractAddress = value;
    }

    @Override
    public double getValueInUSD() {
        return this.valueInUSD;
    }

    public void setValueInUSD(double value) {
        this.valueInUSD = value;
    }

    // Implementing abstract methods from WalletStorable interface
    @Override
    public String getBlockchainNetwork() {
        return super.getBlockchainNetwork();
    }

    @Override
    public String getShortDescription() {
        return name + ": $" + valueInUSD;
    }

    @Override
    public String getLongDescription() {
        return "NFT Name: " + name + "\nDescription: " + description + "\nContract Address: " + contractAddress
                + "\nBlockchain Network: " + getBlockchainNetwork() + "\nCurrent Value: $" + valueInUSD;
    }

}
