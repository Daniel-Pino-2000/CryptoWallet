[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/JB7sSWGy)
# Assignment 3: Crypto Management System with Transactions

This assignment builds upon Assignment 3 (Crypto Management System). We will now explore the power of data structures to enhance the functionality of your crypto wallet system.

In this option, you'll introduce a **FIFO queue**to simulate real-world blockchain processing delays. Transactions like buying or selling crypto assets won't be processed instantaneously. Instead, they'll be queued within the wallet and processed later, mimicking how transactions are validated and confirmed on a blockchain network.


# Part 1: Transaction (20 points)

In the <code>edu.mdc.cop2805c.assignment2.wallet</code></strong> package, introduce the following:



* Create an enum:
    *  <code>TransactionType</code></strong> (with values BUY and SELL) 
* Create a new generic class named <strong><code>Transaction </code></strong>that holds information about a crypto transaction. It should be parameterized by  any type that implements <strong><code>WalletStorable</code></strong>.
    * Required data fields:
        * <strong><code>type</code></strong> (<strong><code>TransactionType</code></strong>  enum)
        * <strong><code>holding </code></strong>(T)
        * <strong><code>fromAddress</code></strong> (String): From address:
            * For a buy type of transaction, this is the remote address the crypto asset is bought from.
            * For a sell type of transaction, this is the local crypto wallet address the crypto asset is bought from.
        * <strong><code>toAddress</code></strong> (String): To address:
            * For a buy type of transaction, this is the local crypto wallet address  the crypto asset is added to.
            * For a sell type of transaction, this is the remote address the crypto asset is sold to.
    * Methods:
        * Constructors as appropriate
        * Getters/Setters as appropriate
        * Helper methods as needed (e.g.<strong><code> toString(),</code></strong>  etc.)


# Part 2: Transaction Queue (50 points)



* Modify the <code>CryptoWallet</code></strong> class to maintain a queue of <strong><code>Transaction</code></strong> objects (FIFO policy).: 
    * New data field:
        * <strong><code>transactionQueue</code></strong>    (Queue&lt;Transaction&lt;T>>): This queue represents pending transactions that haven't been processed yet. 
            * Use the Implementation class that you find the most appropriate from the Java Collections Framework
    * Modify/Add methods: 
        * Add Getter
        * Update <strong><code>buy</code></strong> and <strong><code>sell</code></strong> methods to:
            *  create a <strong><code>Transaction</code></strong> object and enqueue it in the wallet's transaction queue.
            * These methods no longer update <strong><code>totalValueInUSD</code></strong> or the list of <strong><code>holdings</code></strong>.
        * Introduce a new method <strong><code>processTransactions()</code></strong> in CryptoWallet that dequeues transactions and simulates processing them 
            * Similar to the previous behaviors of the <strong><code>buy</code></strong>() and <strong><code>sell</code></strong>() methods, this method should:
                * update <strong><code>totalValueInUSD</code></strong> appropriately
                * update the list of <strong><code>holdings </code></strong>appropriately.
* Modify the <strong><code>WalletManager</code></strong> class:
    * Add a method called <strong><code>processAllTransactions()</code></strong> that calls <strong><code>processTransactions()</code></strong> on all the wallets.


# Part 3: Trigger Transaction Processing (30 points)



* Modify the <code>Main</code></strong> class:
    * Add a menu option to process the transactions in the queues of each wallet:
        * User enters ‘7’
        * <strong><code>processAllTransactions()</code></strong>is called.
    * When the user quits (User enters ‘Q’), the program should check if there are any unprocessed transactions in any queue.
        * If unprocessed transactions exist, all pending transactions are processed as described above.
