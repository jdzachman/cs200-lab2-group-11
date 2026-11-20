import java.util.Scanner;
/*
Menu for a ChocAn manager: run the accounting procedure and reports.

Author: Thomas Koska
*/
public class ManagerMenu {
private Scanner scanner;
private ReportGenerator reports;
public ManagerMenu(Scanner scanner, ReportGenerator reports) {
this.scanner = scanner;
this.reports = reports;
}
public void run() {
while (true) {
System.out.println();
System.out.println("=== Manager Menu ===");
System.out.println("1. Run main accounting procedure (all reports)");
System.out.println("2. Member reports");
System.out.println("3. Provider reports");
System.out.println("4. Summary report");
System.out.println("5. Provider Directory");
System.out.println("0. Log out");
System.out.print("Choice: ");
String choice = scanner.nextLine().trim();
if (choice.equals("1")) {
reports.runAllReports();
} else if (choice.equals("2")) {
reports.writeMemberReports();
} else if (choice.equals("3")) {
reports.writeProviderReports();
} else if (choice.equals("4")) {
reports.writeSummaryReport();
} else if (choice.equals("5")) {
reports.writeProviderDirectory();
} else if (choice.equals("0")) {
return;
} else {
System.out.println("Invalid choice.");
}
}
}
}
