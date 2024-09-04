package edu.mdc.cop2805c.assignment2.app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.mdc.cop2805c.assignment2.base.CryptoCurrency;
import edu.mdc.cop2805c.assignment2.assets.CryptoCurrencyHolding;
import edu.mdc.cop2805c.assignment2.assets.NFT;
import edu.mdc.cop2805c.assignment2.wallet.CryptoWallet;
import edu.mdc.cop2805c.assignment2.wallet.WalletManager;

public class Main {

    private static WalletManager walletManager = new WalletManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayMenu();
    }

    private static void displayMenu() {
        boolean exit = false;
        loadWallets(); // Optional: Load wallets from a file

        while (!exit) {
            System.out.println("***********************************************************");
            System.out.println("** Please choose your option: ");
            System.out.println("** 1. View all Wallets");
            System.out.println("** 2. View Wallet");
            System.out.println("** 3. Add Wallet");
            System.out.println("** 4. Buy Crypto Assets");
            System.out.println("** 5. Sell Crypto Assets");
            System.out.println("** 6. Transfer Crypto Assets");
            System.out.println("** 7. Process Transactions");
            System.out.println("** Q. Quit");
            System.out.println("***********************************************************");
            System.out.print("\nEnter your choice: ");

            try {
                if (!scanner.hasNext()) {
                    System.out.println("No input available. Exiting...");
                    break;
                }
                String choice = scanner.next();
                scanner.nextLine(); // Consume newline character

                switch (choice.toUpperCase()) {
                    case "1":
                        viewAllWallets();
                        break;
                    case "2":
                        viewWallet();
                        break;
                    case "3":
                        addWallet();
                        break;
                    case "4":
                        buyCryptoAssets();
                        break;
                    case "5":
                        sellCryptoAssets();
                        break;
                    case "6":
                        transferCryptoAssets();
                        break;
                    case "7":
                        walletManager.processAllTransactions();
                        break;
                    case "Q":
                        exit = true;
                        walletManager.processAllTransactions(); // Ensure all transactions are processed
                        saveWallets();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("No input available. Exiting...");
                exit = true;
            }
        }

        System.out.println("Exiting the program. Thank you!");
    }

    private static void loadWallets() {
        // Optional: Implement loading of wallets from a file (using Serializable interface)
        // Example: walletManager.loadWalletsFromFile("wallets.dat");
    }

    private static void saveWallets() {
        // Optional: Implement saving of wallets to a file (using Serializable interface)
        // Example: walletManager.saveWalletsToFile("wallets.dat");
    }

    private static void viewAllWallets() {
        System.out.println("Your wallets:");

        for (CryptoWallet<CryptoCurrencyHolding> cryptoWallet : walletManager.getCryptoCurrencyWallets()) {
            System.out.println(cryptoWallet.getLongDescription());
        }

        for (CryptoWallet<NFT> nftWallet : walletManager.getNftWallets()) {
            System.out.println(nftWallet.getLongDescription());
        }
    }

    private static void viewWallet() {
        System.out.println(">> Which Wallet?");
        System.out.println(">> - Crypto Currency Wallets:");
        int index = 1;
        for (CryptoWallet<CryptoCurrencyHolding> cryptoWallet : walletManager.getCryptoCurrencyWallets()) {
            System.out.println(" (" + index + ") " + cryptoWallet.getNiceName());
            index++;
        }
        System.out.println(">> - NFT Wallets:");
        for (CryptoWallet<NFT> nftWallet : walletManager.getNftWallets()) {
            System.out.println(" (" + index + ") " + nftWallet.getNiceName());
            index++;
        }

        int choice = readIntInput("Enter your choice: ");
        if (choice > 0 && choice <= walletManager.getNumWallets()) {
            if (choice <= walletManager.getCryptoCurrencyWallets().size()) {
                CryptoWallet<CryptoCurrencyHolding> wallet = walletManager.getCryptoCurrencyWallet(choice - 1);
                displayCryptoCurrencyWalletDetails(wallet);
            } else {
                CryptoWallet<NFT> wallet = walletManager.getNFTWallet(choice - 1 - walletManager.getCryptoCurrencyWallets().size());
                displayNFTWalletDetails(wallet);
            }
        } else {
            System.out.println("Invalid wallet choice.");
        }
    }

    private static void addWallet() {
        System.out.println(">> What type of Asset?");
        System.out.println(" (1) NFT  (2) Crypto Currency");

        int walletType = readIntInput("Enter your choice: ");
        if (walletType == 1) {
            addNewNFTWallet();
        } else if (walletType == 2) {
            addNewCryptoCurrencyWallet();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void addNewNFTWallet() {
        String niceName = readStringInput("Nice Name: ");
        String address = readStringInput("Address (Public Key): ");
        String privateKey = readStringInput("Private Key Filename: ");

        // Assuming walletManager is an instance variable
        walletManager.createNFTWallet(niceName, address, privateKey);

        System.out.println("New NFT Wallet added successfully.");
    }

    private static void addNewCryptoCurrencyWallet() {
        String niceName = readStringInput("Nice Name: ");
        String address = readStringInput("Address (Public Key): ");
        String privateKey = readStringInput("Private Key Filename: ");

        walletManager.createCryptoCurrencyWallet(niceName, address, privateKey);
        System.out.println("New Crypto Currency Wallet added successfully.");
    }

    private static void buyCryptoAssets() {
        System.out.println(">> From which address?");
        String fromAddress = scanner.nextLine();

        System.out.println(">> What type of Asset?");
        System.out.println(" (1) NFT  (2) Crypto Currency");

        int assetType = readIntInput("Enter your choice: ");
        if (assetType == 1) {
            buyNFT(fromAddress);
        } else if (assetType == 2) {
            buyCryptoCurrency(fromAddress);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void buyNFT(String fromAddress) {
        System.out.println(">> Which Wallet?");
        displayNFTWallets();

        int walletIndex = readIntInput("Enter your choice: ");
        if (walletIndex > 0 && walletIndex <= walletManager.getNftWallets().size()) {
            CryptoWallet<NFT> wallet = walletManager.getNFTWallet(walletIndex - 1);
            String niceName = readStringInput("Name of the NFT: ");
            String description = readStringInput("Description of the NFT: ");
            String address = readStringInput("Contract Address of the NFT: ");
            String network = readStringInput("Blockchain network of the NFT: ");
            double valueInUSD = readDoubleInput("Value in USD of the NFT: ");
            // Assuming that amount to buy for NFT is always 1
            NFT nft = new NFT(niceName, description, address, network, valueInUSD);
            wallet.buy(nft, fromAddress);
            System.out.println("NFT Bought!");
            System.out.println("Balance for " + wallet.getNiceName() + ": $" + wallet.getTotalValueInUSD());
        } else {
            System.out.println("Invalid wallet choice.");
        }
    }

    private static void buyCryptoCurrency(String fromAddress) {
        System.out.println(">> Which Wallet?");
        displayCryptoCurrencyWallets();

        int walletIndex = readIntInput("Enter your choice: ");
        if (walletIndex > 0 && walletIndex <= walletManager.getCryptoCurrencyWallets().size()) {
            CryptoWallet<CryptoCurrencyHolding> wallet = walletManager.getCryptoCurrencyWallet(walletIndex - 1);
            String name = readStringInput("Name of the Crypto Currency: ");
            String symbol = readStringInput("Symbol of the Crypto Currency: ");
            String blockchainNetwork = readStringInput("Blockchain Network: ");
            double currentPrice = readDoubleInput("Current Price in USD: ");
            double amount = readDoubleInput("Amount to buy: ");

            CryptoCurrency cryptoCurrency = walletManager.getCryptoCurrency(symbol);
            if (cryptoCurrency == null) {
                cryptoCurrency = new CryptoCurrency(name, symbol, currentPrice, blockchainNetwork);
                walletManager.addCryptoCurrency(cryptoCurrency);
            }

            CryptoCurrencyHolding holding = new CryptoCurrencyHolding(cryptoCurrency, amount);
            wallet.buy(holding, fromAddress);
            System.out.println("Crypto Currency bought successfully.");
            System.out.println("Balance for " + wallet.getNiceName() + ": $" + wallet.getTotalValueInUSD());
        } else {
            System.out.println("Invalid wallet choice.");
        }
    }

    private static void sellCryptoAssets() {
        System.out.println(">> To which address?");
        String toAddress = scanner.nextLine();

        System.out.println(">> What type of Asset?");
        System.out.println(" (1) NFT  (2) Crypto Currency");

        walletManager.processAllTransactions();

        int assetType = readIntInput("Enter your choice: ");
        if (assetType == 1) {
            sellNFT(toAddress);
        } else if (assetType == 2) {
            sellCryptoCurrency(toAddress);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void sellNFT(String toAddress) {
        System.out.println(">> Which Wallet?");
        displayNFTWallets();

        int walletIndex = readIntInput("Enter your choice: ");
        if (walletIndex > 0 && walletIndex <= walletManager.getNftWallets().size()) {
            CryptoWallet<NFT> wallet = walletManager.getNFTWallet(walletIndex - 1);
            int holdingIndex = readIntInput("Enter index of the NFT to sell: ");
            NFT nft = wallet.getHoldings().get(holdingIndex - 1);
            wallet.sell(nft, toAddress);
            System.out.println("NFT Sold!");
            System.out.println("Balance for " + wallet.getNiceName() + ": $" + wallet.getTotalValueInUSD());
        } else {
            System.out.println("Invalid wallet choice.");
        }
    }

    private static void sellCryptoCurrency(String toAddress) {
        System.out.println(">> Which Wallet?");
        displayCryptoCurrencyWallets();

        int walletIndex = readIntInput("Enter your choice: ");
        if (walletIndex > 0 && walletIndex <= walletManager.getCryptoCurrencyWallets().size()) {
            CryptoWallet<CryptoCurrencyHolding> wallet = walletManager.getCryptoCurrencyWallet(walletIndex - 1);

            // Display holdings in the selected wallet
            System.out.println(">> Holdings in Wallet: " + wallet.getNiceName());
            List<CryptoCurrencyHolding> holdings = wallet.getHoldings();
            for (int i = 0; i < holdings.size(); i++) {
                CryptoCurrencyHolding holding = holdings.get(i);
                System.out.println((i + 1) + ": " + holding.getShortDescription() + " - Amount: " + holding.getAmount() + ", Value in USD: $" + holding.getValueInUSD());
            }

            int holdingIndex = readIntInput("Enter the index of the Crypto Currency to sell: ");
            if (holdingIndex > 0 && holdingIndex <= holdings.size()) {
                CryptoCurrencyHolding holding = holdings.get(holdingIndex - 1);

                double amountToSell = readDoubleInput("Enter the amount of " + holding.getShortDescription() + " to sell: ");
                if (amountToSell > 0 && amountToSell <= holding.getAmount()) {
                    System.out.println("Original" + holding.getCryptoCurrency());
                    CryptoCurrencyHolding sellHolding = new CryptoCurrencyHolding(holding.getCryptoCurrency(), amountToSell);
                    System.out.println("Copy" + sellHolding.getCryptoCurrency());

                    wallet.sell(sellHolding, toAddress);

                    System.out.println("Crypto Currency sold successfully.");
                    System.out.println("Balance for " + wallet.getNiceName() + ": $" + wallet.getTotalValueInUSD());
                } else {
                    System.out.println("Invalid amount to sell.");
                }
            } else {
                System.out.println("Invalid holding choice.");
            }
        } else {
            System.out.println("Invalid wallet choice.");
        }
    }

    private static void transferCryptoAssets() {
        System.out.println(">> From which address?");
        String fromAddress = scanner.nextLine();

        System.out.println(">> What type of Asset?");
        System.out.println(" (1) NFT  (2) Crypto Currency");

        int assetType = readIntInput("Enter your choice: ");
        if (assetType == 1) {
            transferNFT(fromAddress);
        } else if (assetType == 2) {
            transferCryptoCurrency(fromAddress);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void transferNFT(String fromAddress) {
        System.out.println(">> Which Wallet?");
        displayNFTWallets();

        int walletIndex = readIntInput("Enter your choice: ");
        if (walletIndex > 0 && walletIndex <= walletManager.getNftWallets().size()) {
            CryptoWallet<NFT> wallet = walletManager.getNFTWallet(walletIndex - 1);
            int holdingIndex = readIntInput("Enter index of the NFT to transfer: ");
            NFT nft = (wallet.getHoldings()).get(holdingIndex - 1);

            System.out.println(">> To which Wallet?");
            displayNFTWallets();
            int toWalletIndex = readIntInput("Enter your choice: ");
            if (toWalletIndex > 0 && toWalletIndex <= walletManager.getNftWallets().size()) {
                CryptoWallet<NFT> toWallet = walletManager.getNFTWallet(toWalletIndex - 1);
                wallet.transferTo(nft, toWallet);
                System.out.println("NFT Transferred!");
                System.out.println("Balance for " + wallet.getNiceName() + ": $" + wallet.getTotalValueInUSD());
            } else {
                System.out.println("Invalid wallet choice.");
            }
        } else {
            System.out.println("Invalid wallet choice.");
        }
    }

    private static void transferCryptoCurrency(String fromAddress) {
        System.out.println(">> Which Wallet?");
        displayCryptoCurrencyWallets();

        int walletIndex = readIntInput("Enter your choice: ");
        if (walletIndex > 0 && walletIndex <= walletManager.getCryptoCurrencyWallets().size()) {
            CryptoWallet<CryptoCurrencyHolding> wallet = walletManager.getCryptoCurrencyWallet(walletIndex - 1);
            int holdingIndex = readIntInput("Enter index of the Crypto Currency to transfer: ");
            CryptoCurrencyHolding holding = wallet.getHoldings().get(holdingIndex - 1);

            System.out.println(">> To which Wallet?");
            displayCryptoCurrencyWallets();
            int toWalletIndex = readIntInput("Enter your choice: ");
            if (toWalletIndex > 0 && toWalletIndex <= walletManager.getCryptoCurrencyWallets().size()) {
                CryptoWallet<CryptoCurrencyHolding> toWallet = walletManager.getCryptoCurrencyWallet(toWalletIndex - 1);
                wallet.transferTo(holding, toWallet);
                System.out.println("Crypto Currency transferred successfully.");
                System.out.println("Balance for " + wallet.getNiceName() + ": $" + wallet.getTotalValueInUSD());
            } else {
                System.out.println("Invalid wallet choice.");
            }
        } else {
            System.out.println("Invalid wallet choice.");
        }
    }

    private static void displayNFTWallets() {
        int index = 1;
        for (CryptoWallet<NFT> nftWallet : walletManager.getNftWallets()) {
            System.out.println(" (" + index + ") " + nftWallet.getNiceName());
            index++;
        }
    }

    private static void displayCryptoCurrencyWallets() {
        int index = 1;
        for (CryptoWallet<CryptoCurrencyHolding> cryptoWallet : walletManager.getCryptoCurrencyWallets()) {
            System.out.println(" (" + index + ") " + cryptoWallet.getNiceName());
            index++;
        }
    }

    private static int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
            }
        }
    }

    // Helper method to read string inputs
    private static String readStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        return scanner.nextLine();
    }

    private static double readDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume invalid input
            }
        }
    }

    private static void displayCryptoCurrencyWalletDetails(CryptoWallet<CryptoCurrencyHolding> wallet) {
        System.out.println(" ***********************************");
        System.out.println(" * Wallet name: " + wallet.getNiceName());
        System.out.println(" * Wallet address: " + wallet.getAddress());
        System.out.println(" * Wallet private Key filename: " + wallet.getPrivateKey());
        for (CryptoCurrencyHolding holding : wallet.getHoldings()) {
            CryptoCurrency crypto = holding.getCryptoCurrency();
            System.out.println(" * Blockchain Network: [" + crypto.getBlockchainNetwork() + "]" + crypto.getName() + " (" + crypto.getSymbol() + "), ");
            System.out.println("\tCurrent Price:" + crypto.getCurrentPrice() + ": " + holding.getAmount());
        }
        System.out.println(" ***********************************");
        System.out.println(" * Balance: USD " + wallet.getTotalValueInUSD());
        System.out.println(" ***********************************");
    }

    private static void displayNFTWalletDetails(CryptoWallet<NFT> wallet) {
        System.out.println(" ***********************************");
        System.out.println(" * Wallet name: " + wallet.getNiceName());
        System.out.println(" * Wallet address: " + wallet.getAddress());
        System.out.println(" * Wallet private Key filename: " + wallet.getPrivateKey());
        for (NFT nft : wallet.getHoldings()) {
            System.out.println(" * Blockchain Network: [" + nft.getBlockchainNetwork() + "]" + nft.getName() + " (" + nft.getContractAddress() + "), ");
            System.out.println("\tCurrent Value in USD: " + nft.getValueInUSD());
        }
        System.out.println(" ***********************************");
        System.out.println(" * Balance: USD " + wallet.getTotalValueInUSD());
        System.out.println(" ***********************************");
    }
}
