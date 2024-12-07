import java.util.Scanner;
import java.io.*;

class Configuration {
    private int totalTickets;
    private int ticketReleaseCount;
    private int customerRetrievalCount;
    private int maxTicketCapacity;

    private Configuration(int totalTickets, int ticketReleaseCount, int customerRetrievalCount, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseCount = ticketReleaseCount;
        this.customerRetrievalCount = customerRetrievalCount;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public static Configuration getConfigurationFromUser() {
        Scanner scanner = new Scanner(System.in);

        File configFile = new File("config.txt");
        if (configFile.exists()) {
            System.out.println("Previous configuration found.");
            System.out.println("1. Use previous configuration");
            System.out.println("2. Enter new configuration");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            if (choice == 1) {
                return loadConfiguration();
            } else if (choice != 2) {
                System.out.println("Invalid choice. Exiting...");
                System.exit(1);
            }
        }

        int totalTickets = getValidIntInput(scanner, "Enter total number of tickets: ");
        int ticketReleaseCount = getValidIntInput(scanner, "Enter tickets released per cycle: ");
        int ticketsRetrievedPerCycle = getValidIntInput(scanner, "Enter tickets retrieved per cycle: ");
        int maxTicketCapacity = getValidIntInput(scanner, "Enter maximum ticket pool capacity: ");

        return new Configuration(totalTickets, ticketReleaseCount, ticketsRetrievedPerCycle, maxTicketCapacity);
    }

    private static int getValidIntInput(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = scanner.nextInt();
                if (value <= 0) {
                    System.out.println("Please enter a positive integer.");
                    continue;
                }
                return value;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static Configuration loadConfiguration() {
        try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {
            int totalTickets = Integer.parseInt(reader.readLine());
            int ticketReleaseCount = Integer.parseInt(reader.readLine());
            int customerRetrievalCount = Integer.parseInt(reader.readLine());
            int maxTicketCapacity = Integer.parseInt(reader.readLine());
            System.out.println("Previous configuration loaded successfully.");
            return new Configuration(totalTickets, ticketReleaseCount, customerRetrievalCount, maxTicketCapacity);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            System.out.println("Falling back to new configuration input.");
            return getConfigurationFromUser();
        }
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseCount() {
        return ticketReleaseCount;
    }

    public int getCustomerRetrievalCount() {
        return customerRetrievalCount;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
}

