package edu.mdc.cop2805c.assignment2.base;

public abstract class CryptoAsset {

    /*
     * This class will serve as a foundation for specific crypto types like cryptocurrency or NFT.
     */
    private String blockchainNetwork;

    public CryptoAsset(String blockchainNetword) {
        /*
         * Public constructor that accepts an argument representing blockchainNetwork 
         * and that initializes the  blockchainNetwork property.
         */
        this.blockchainNetwork = blockchainNetword;
    }

    public String getBlockchainNetwork() {
        return blockchainNetwork;
    }

    public void setBlockChainNetwork(String blockchainN) {
        this.blockchainNetwork = blockchainN;
    }

    // I'll problably need a helper mehtod like toString in this class.
}
