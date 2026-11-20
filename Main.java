import java.util.Scanner;

/**
 * Starts the ChocAn program: shows the login screen and opens the menu
 * that matches the user's role.
 */
public class Main {
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        DataCenter data = new DataCenter();
        ReportGenerator reports = new ReportGenerator(data);
        // show the main menu
        while (true) {
            System.out.println();
            System.out.println("=== ChocAn Login ===");
            System.out.println("1. Provider");
            System.out.println("2. Manager");
            System.out.println("3. Operator");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();
            // Provider menu
            if (choice.equals("1")) {
                System.out.print("Provider number: ");
                Provider provider = data.findProvider(scanner.nextLine().trim());
                if (provider == null) {
                    System.out.println("Invalid provider number.");
                } else {
                    new ProviderMenu(scanner, data, reports, provider).run();
                }
            } else if (choice.equals("2")) { // Manager menu
                System.out.print("Manager ID: ");
                if (scanner.nextLine().trim().equals(DataCenter.MANAGER_ID)) {
                    new ManagerMenu(scanner, reports).run();
                } else {
                    System.out.println("Invalid manager ID.");
                }
            } else if (choice.equals("3")) { // Operator menu
                System.out.print("Operator ID: ");
                if (scanner.nextLine().trim().equals(DataCenter.OPERATOR_ID)) {
                    new OperatorMenu(scanner, data).run();
                } else {
                    System.out.println("Invalid operator ID.");
                }
            } else if (choice.equals("0")) { //exit
                System.out.println("Goodbye.");
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
