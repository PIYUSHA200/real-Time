import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static final AtomicBoolean running = new AtomicBoolean(true); // Shared running flag

    public static void main(String[] args) {
        System.out.println("Welcome to the Real-Time Ticketing System!");

        // Collect user inputs and initialize Configuration
        Configuration config = Configuration.getConfigurationFromUser();

        // Save inputs to a text file
        saveInputsToTextFile(config);

        // Initialize the ticket pool
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        // Start vendor threads (5 vendors)
        Vendor[] vendors = new Vendor[5];
        Thread[] vendorThreads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            vendors[i] = new Vendor(ticketPool, 1000, config.getTicketReleaseCount(), i + 1, running);
            vendorThreads[i] = new Thread(vendors[i], "Vendor-" + (i + 1));
            vendorThreads[i].start();
        }

        // Start customer threads (5 customers)
        Customer[] customers = new Customer[5];
        Thread[] customerThreads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            customers[i] = new Customer(ticketPool, 1500, config.getCustomerRetrievalCount(), i + 1, running);
            customerThreads[i] = new Thread(customers[i], "Customer-" + (i + 1));
            customerThreads[i].start();
        }

        Scanner scanner = new Scanner(System.in);

        // Monitor user input to stop the system
        while (true) {
            System.out.println("\nSystem is running. Type '1' to stop:");
            int choice = scanner.nextInt();
            if (choice == 1) {
                stopSystem(vendorThreads, customerThreads);
                System.out.println("System stopped.");
                break;
            } else {
                System.out.println("Invalid choice! Please type '1' to stop.");
            }
        }

        scanner.close();
    }

    private static void stopSystem(Thread[] vendorThreads, Thread[] customerThreads) {
        running.set(false); // Signal threads to stop

        // Interrupt all vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }

        // Interrupt all customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }
    }

    private static void saveInputsToTextFile(Configuration config) {
        String data = String.format(
                "Total Tickets: %d%nTickets Released Per Cycle: %d%nTickets Retrieved Per Cycle: %d%nMaximum Ticket Pool Capacity: %d",
                config.getTotalTickets(), config.getTicketReleaseCount(),
                config.getCustomerRetrievalCount(), config.getMaxTicketCapacity());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_inputs.txt"))) {
            writer.write(data);
            System.out.println("\nUser inputs have been saved to 'user_inputs.txt'.");
        } catch (IOException e) {
            System.err.println("Error saving user inputs to file: " + e.getMessage());
        }
    }
}

