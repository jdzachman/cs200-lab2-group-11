import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;

//Menu for a provider's terminal: validate members, bill services,
//and request the Provider Directory.

//Author: James Zachman

public class ProviderMenu {
    private Scanner scanner;
    private DataCenter data;
    private ReportGenerator reports;
    private Provider provider; // the provider who is logged in
    public ProviderMenu(Scanner scanner, DataCenter data, ReportGenerator reports, Provider provider) {
        this.scanner = scanner;
        this.data = data;
        this.reports = reports;
        this.provider = provider;
    }

    public void run() {
        while (true){
            System.out.println();
            System.out.println("=== Provider Menu (" + provider.getName() + ") ===");
            System.out.println("1. Validate member");
            System.out.println("2. Bill for a service");
            System.out.println("3. Request Provider Directory");
            System.out.println("0. Log out");
            String choice = ask("Choice: ");
            if(choice.equals("1")){
                validateMember();
            } 
            else if(choice.equals("2")){
                billService();
            } 
            else if(choice.equals("3")){
                reports.writeProviderDirectory();
            } 
            else if(choice.equals("0")){
                return;
            } 
            else{
                System.out.println("Invalid choice.");
            }
        }
    }

    private void validateMember(){
        String number = ask("Enter member number: ");
        System.out.println(data.validateMember(number));
    }

    private void billService(){
        // Step 1: Validate the Member
        String memberNumber = ask("Enter member number: ");
        String result = data.validateMember(memberNumber);
        System.out.println(result);
        if (!result.equals("Validated")){
            return;
        }

        // Step 2: Date of Service
        String serviceDate = ask("Date of service (MM-DD-YYYY): ");
        while (!isValidDate(serviceDate)){
            System.out.println("Invalid date.");
            serviceDate = ask("Date of service (MM-DD-YYYY): ");
        }

        // Step 3: Service Code, confirmed by showing the service name
        Service service = null;
        while (service == null){
            String code = ask("Service code (6 digits): ");
            service = data.findService(code);
            if (service == null) {
                System.out.println("Error: that service code does not exist.");
            } 
            else{
                System.out.println("Service: " + service.getName());
                String answer = ask("Is this correct? (y/n): ");
                if (!answer.equalsIgnoreCase("y")){
                    service = null;
                }
            }
        }

        // Step 4: Optional Comments
        String comments = ask("Comments (optional, max 100 characters): ");
        while (comments.length() > 100){
            System.out.println("Too long.");
            comments = ask("Comments (optional, max 100 characters): ");
        }

        // Step 5: Save the record and show the fee
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
        ServiceRecord record = new ServiceRecord(now, serviceDate, provider.getNumber(),
        memberNumber, service.getCode(), comments);
        data.addRecord(record);
        System.out.println("Service recorded.");
        System.out.println("Fee to be paid: $" + String.format("%.2f", service.getFee()));
    }

    //Shows a prompt and returns what the user typed. 
    private String ask(String prompt){
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    //Returns true if text is a real date in MM-DD-YYYY format.
    private boolean isValidDate(String text){
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(text, format);
            return true;
        } 
        catch (Exception e){
            return false;
        }
    }
    
}