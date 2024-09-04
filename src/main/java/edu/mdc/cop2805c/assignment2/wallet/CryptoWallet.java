package edu.mdc.cop2805c.assignment2.wallet;

import edu.mdc.cop2805c.assignment2.assets.CryptoCurrencyHolding;
import edu.mdc.cop2805c.assignment2.assets.NFT;
import edu.mdc.cop2805c.assignment2.base.WalletStorable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CryptoWallet<T extends WalletStorable> {

    private String niceName;
    private String address;
    private String privateKey;
    private List<T> holdings;
    private double totalValueInUSD;
    private Queue<Transaction<T>> transactionQueue;

    // Constructor
    public CryptoWallet(String niceName, String address, String privateKey) {
        this.niceName = niceName;
        this.address = address;
        this.privateKey = privateKey;
        this.holdings = new ArrayList<>();
        this.totalValueInUSD = calculateTotalValueInUSD();
        this.transactionQueue = new LinkedList<>();
    }

    // Method to add a transaction to the queue
    public void addTransaction(Transaction<T> transaction) {
        transactionQueue.add(transaction);
    }

    // Method to get the next transaction from the queue
    public Transaction<T> getNextTransaction() {
        return transactionQueue.poll();
    }

    // Method to check if there are any transactions in the queue
    public boolean hasTransactions() {
        return !transactionQueue.isEmpty();
    }

    // Method to get the number of transactions in the queue
    public int getTransactionCount() {
        return transactionQueue.size();
    }

    @Override
    public String toString() {
        return "TransactionManager{"
                + "transactionQueue=" + transactionQueue
                + '}';
    }

    // Getters and Setters
    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public List<T> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<T> holdings) {
        this.holdings = holdings;
    }

    public double getTotalValueInUSD() {
        return totalValueInUSD;
    }

    public void setTotalValueInUSD(double totalValueInUSD) {
        this.totalValueInUSD = totalValueInUSD;
    }

    // Helper methods
    public String getShortDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Wallet: ").append(niceName).append("\n");
        sb.append("Address: ").append(address).append("\n");
        sb.append("Total Value in USD: $").append(totalValueInUSD).append("\n");
        sb.append("Holdings:\n");
        for (T holding : holdings) {
            sb.append("- ").append(holding.getShortDescription()).append("\n");
        }
        return sb.toString();
    }

    public String getLongDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Wallet: ").append(niceName).append("\n");
        sb.append("Address: ").append(address).append("\n");
        sb.append("Total Value in USD: $").append(totalValueInUSD).append("\n");
        sb.append("Holdings:\n");
        for (T holding : holdings) {
            sb.append("- ").append(holding.getLongDescription()).append("\n");
        }
        return sb.toString();
    }

    // Method to sell a holding
    public void sell(T holding, String toAddress) {

        if (holding instanceof CryptoCurrencyHolding) {
            System.out.print("Test 1");
            Transaction<CryptoCurrencyHolding> transactionCCH = new Transaction<>(TransactionType.SELL, (CryptoCurrencyHolding) holding, address, toAddress);
            addTransaction((Transaction<T>) transactionCCH);
        } else if (holding instanceof NFT) {
            System.out.println("1 Time");
            CryptoWallet<NFT> nftWallet = new CryptoWallet<>("MyNFTWallet", "address", "privateKey");
            Transaction<NFT> transactionNFT = new Transaction<>(TransactionType.SELL, (NFT) holding, address, toAddress);
            addTransaction((Transaction<T>) transactionNFT);

        } else {
            System.out.println("Holding not found in the wallet.");
        }
    }

    // Method to buy a holding
    public void buy(T holding, String fromAddress) {
        if (holding instanceof CryptoCurrencyHolding) {
            Transaction<CryptoCurrencyHolding> transactionCCH = new Transaction<>(TransactionType.BUY, (CryptoCurrencyHolding) holding, fromAddress, address);
            addTransaction((Transaction<T>) transactionCCH);
        } else if (holding instanceof NFT) {
            Transaction<NFT> transactionNFT = new Transaction<>(TransactionType.BUY, (NFT) holding, fromAddress, address);
            addTransaction((Transaction<T>) transactionNFT);
        } else {
            System.out.println("Unsupported holding type.");
        }
    }

    public void processTransactions() {
        System.out.println("Transactions: " + getTransactionCount());
        while (hasTransactions()) {
            Transaction<T> transaction = getNextTransaction();
            if (transaction == null) {
                continue;
            }

            T holding = transaction.getHoldings();

            if (transaction.getType() == TransactionType.SELL) {
                if (holding instanceof CryptoCurrencyHolding) {
                    sellCryptoCurrency((CryptoCurrencyHolding) holding);
                } else if (holding instanceof NFT) {
                    sellNFT((NFT) holding);
                } else {
                    System.out.println("Unsupported holding type.");
                }
            } else if (transaction.getType() == TransactionType.BUY) {
                if (holding instanceof CryptoCurrencyHolding) {
                    buyCryptoCurrency((CryptoCurrencyHolding) holding, transaction.getFromAddress());
                } else if (holding instanceof NFT) {
                    buyNFT((NFT) holding, transaction.getFromAddress());
                } else {
                    System.out.println("Unsupported holding type.");
                }

            }

        }
    }

    // Method to transfer a holding to another wallet
    public void transferTo(T holding, CryptoWallet<T> toWallet) {
        if (holdings.contains(holding)) {
            if (holding instanceof CryptoCurrencyHolding) {
                transferCryptoCurrency((CryptoCurrencyHolding) holding, toWallet);
            } else if (holding instanceof NFT) {
                transferNFT((NFT) holding, toWallet);
            } else {
                System.out.println("Unsupported holding type.");
            }
        } else {
            System.out.println("Holding not found in the wallet.");
        }
    }

    // Helper method to sell CryptoCurrencyHolding
    private void sellCryptoCurrency(CryptoCurrencyHolding cryptoHolding) {
        System.out.print("Test 2");
        //int index = findHoldingIndex(cryptoHolding);
        //T foundHolding = holdings.get(index);
        T foundHolding = findHolding(cryptoHolding);
        if (foundHolding != null && foundHolding instanceof CryptoCurrencyHolding) {
            // Calculate total value in USD to be reduced
            double valueInUSD = cryptoHolding.getValueInUSD();
            CryptoCurrencyHolding foundCryptoHolding = (CryptoCurrencyHolding) foundHolding;

            // Update total value in USD of the wallet
            totalValueInUSD -= valueInUSD;

            foundCryptoHolding.setAmount(foundCryptoHolding.getAmount() - cryptoHolding.getAmount());

            // Remove the holding from the wallet (assuming selling means updating amount to 0)
            holdings.remove(foundHolding);

            System.out.println("Successfully sold CryptoCurrency holding.");
        } else {
            System.out.println("CryptoCurrency holding not found in the wallet.");
        }
    }

    // Helper method to sell NFT
    private void sellNFT(NFT nft) {
        T foundHolding = findHolding(nft);
        if (foundHolding != null && foundHolding instanceof NFT) {
            // Calculate total value in USD to be reduced
            double valueInUSD = nft.getValueInUSD();

            // Update total value in USD of the wallet
            totalValueInUSD -= valueInUSD;

            // Remove the NFT from the wallet (assuming selling means removing completely)
            holdings.remove(foundHolding);

            System.out.println("Successfully sold NFT.");
        } else {
            System.out.println("NFT not found in the wallet.");
        }
    }

    // Helper method to buy CryptoCurrencyHolding
    private void buyCryptoCurrency(CryptoCurrencyHolding cryptoHolding, String fromAddress) {
        // Check if the wallet already contains the same type of CryptoCurrencyHolding
        boolean found = false;
        for (T existing : holdings) {
            if (existing instanceof CryptoCurrencyHolding) {
                CryptoCurrencyHolding existingCrypto = (CryptoCurrencyHolding) existing;
                if (existingCrypto.getCryptoCurrency().equals(cryptoHolding.getCryptoCurrency())) {
                    // Update the existing holding amount
                    existingCrypto.setAmount(existingCrypto.getAmount() + cryptoHolding.getAmount());
                    found = true;
                    break;
                }

            }
        }

        // If not found, add the new holding
        if (!found) {
            holdings.add((T) cryptoHolding); // Explicit casting to T
        }

        // Update total value in USD of the wallet
        totalValueInUSD += cryptoHolding.getValueInUSD();

        System.out.println("Successfully bought CryptoCurrency holding.");
    }

    // Helper method to buy NFT
    private void buyNFT(NFT nft, String fromAddress) {
        holdings.add((T) nft); // Explicit casting to T

        // Update total value in USD of the wallet
        totalValueInUSD += nft.getValueInUSD();

        System.out.println("Successfully bought NFT.");
    }

    // Helper method to transfer CryptoCurrencyHolding to another wallet
    private void transferCryptoCurrency(CryptoCurrencyHolding cryptoHolding, CryptoWallet<T> toWallet) {
        T foundHolding = findHolding(cryptoHolding);
        if (foundHolding != null && foundHolding instanceof CryptoCurrencyHolding) {
            // Calculate total value in USD to be transferred
            double valueInUSD = cryptoHolding.getValueInUSD();

            // Update total value in USD of both wallets
            totalValueInUSD -= valueInUSD;
            toWallet.setTotalValueInUSD(toWallet.getTotalValueInUSD() + valueInUSD);

            // Remove the holding from this wallet
            holdings.remove(foundHolding);

            // Add the holding to the target wallet
            toWallet.getHoldings().add(foundHolding);

            System.out.println("Successfully transferred CryptoCurrency holding.");
        } else {
            System.out.println("CryptoCurrency holding not found in the wallet.");
        }
    }

    // Helper method to transfer NFT to another wallet
    private void transferNFT(NFT nft, CryptoWallet<T> toWallet) {
        T foundHolding = findHolding(nft);
        if (foundHolding != null && foundHolding instanceof NFT) {
            // Calculate total value in USD to be transferred
            double valueInUSD = nft.getValueInUSD();

            // Update total value in USD of both wallets
            totalValueInUSD -= valueInUSD;
            toWallet.setTotalValueInUSD(toWallet.getTotalValueInUSD() + valueInUSD);

            // Remove the NFT from this wallet
            holdings.remove(foundHolding);

            // Add the NFT to the target wallet
            toWallet.getHoldings().add(foundHolding);

            System.out.println("Successfully transferred NFT.");
        } else {
            System.out.println("NFT not found in the wallet.");
        }
    }

    // Helper method to find a specific holding in the wallet
    private T findHolding(WalletStorable holdingToFind) {
        System.out.println("Test 3");
        for (T holding : holdings) {
            System.out.println("Candela");
            if (holdingToFind instanceof CryptoCurrencyHolding) {
                if (((CryptoCurrencyHolding) holdingToFind).getCryptoCurrency().equals(((CryptoCurrencyHolding) holding).getCryptoCurrency())) {
                    System.out.print("Test 3a");
                    return holding;
                }
            } else if (holdingToFind instanceof NFT) {
                if (holdingToFind.equals(holding)) {
                    System.out.println("Test 3b");
                    return holding;
                }
            }

        }
        return null;
    }

    private int findHoldingIndex(CryptoCurrencyHolding holdingToFind) {
        for (int i = 0; i < holdings.size(); i++) {
            T holding = holdings.get(i);
            if (holding instanceof CryptoCurrencyHolding) {
                CryptoCurrencyHolding currentHolding = (CryptoCurrencyHolding) holding;
                if (currentHolding.getCryptoCurrency().equals(holdingToFind.getCryptoCurrency())) {
                    System.out.println("Found matching holding at index: " + i);
                    return i;
                }
            }
        }
        return -1; // Return -1 if no matching holding is found
    }

    // Method to calculate total value in USD based on holdings
    private double calculateTotalValueInUSD() {
        double totalValue = 0.0;
        for (T holding : holdings) {
            totalValue += holding.getValueInUSD();
        }
        return totalValue;
    }

    public Queue<Transaction<T>> getTransactionQueue() {
        return this.transactionQueue;
    }

    public void setTransactionQueue(Queue<Transaction<T>> value) {
        this.transactionQueue = value;
    }
}
