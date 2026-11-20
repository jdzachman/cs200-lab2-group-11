import java.io.File; 
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Creates the report files: member reports, provider reports, the summary
 * report, the EFT file and the Provider Directory. All files go in "reports".
 */
public class ReportGenerator {

    private DataCenter data;
    public ReportGenerator(DataCenter data) {
        this.data = data;
    }

    // The main accounting procedure: runs every weekly report.
    public void runAllReports(){
        writeMemberReports();
        writeProviderReports();
        writeSummaryReport();
    }

    // One report per member who received services, sorted by service date. 
    public void writeMemberReports() {
        for (Member m : data.getMembers()){
            ArrayList<ServiceRecord> list = new ArrayList<>();
            for (ServiceRecord r : data.getRecords()) {
                if (r.getMemberNumber().equals(m.getNumber())) {
                    list.add(r);
                }
            }
            if (list.isEmpty()) {
                continue; 
            }
            list.sort((a, b) -> sortableDate(a.getServiceDate()).compareTo(sortableDate(b.getServiceDate())));

            String text ="MEMBER REPORT\n"
                    + "Member name:    " + m.getName() + "\n"
                    + "Member number:  " + m.getNumber() + "\n"
                    + "Street address: " + m.getStreet() + "\n"
                    + "City:           " + m.getCity() + "\n"
                    + "State:          " + m.getState() + "\n"
                    + "ZIP code:       " + m.getZip() + "\n\n"
                    + "Services:\n";
            for (ServiceRecord r : list) {
                text += "  " + r.getServiceDate()
                        + "  " + providerName(r.getProviderNumber())
                        + "  " + serviceName(r.getServiceCode()) + "\n";
            }
            writeFile(m.getName() + "_" + today()+ ".txt", text);
        }
    }

    // One report per provider who billed ChocAn, in the order services were entered.
    public void writeProviderReports() {
        for (Provider p : data.getProviders()) {
            String text = "PROVIDER REPORT\n"
                    + "Provider name:   " + p.getName() + "\n"
                    + "Provider number: " + p.getNumber() + "\n"
                    + "Street address:  " + p.getStreet() + "\n"
                    + "City:            " + p.getCity() + "\n"
                    + "State:           " + p.getState() + "\n"
                    + "ZIP code:        " + p.getZip() + "\n\n"
                    + "Services:\n";
            int count = 0;
            double total = 0;
            for (ServiceRecord r : data.getRecords() ) {
                if (r.getProviderNumber().equals(p.getNumber())) {
                    double fee = serviceFee(r.getServiceCode());
                    text += "  Date of service: " + r.getServiceDate()
                            + "  Received: " + r.getCurrentDateTime()
                            + "  Member: " + memberName(r.getMemberNumber())
                            + " (" + r.getMemberNumber() + ")"
                            + "  Code: " + r.getServiceCode()
                            + "  Fee: $" + String.format("%.2f", fee) + "\n";
                    count++;
                    total += fee;
                }
            }
            if (count == 0){
                continue; 
            }
            text += "\nTotal consultations: " + count + "\n";
            text += "Total fee for week: $" + String.format("%.2f", total) + "\n";
            writeFile(p.getName() + "_" + today() + ".txt", text);
        }
    }

    // Summary for the manager: each provider's consultations and fees, plus totals.
    public void writeSummaryReport() {
        String text = "SUMMARY REPORT (Accounts Payable)\n\n";
        int providerCount = 0;
        int totalConsultations = 0;
        double overallTotal = 0;

        for (Provider p : data.getProviders()) {
            int count = countServices(p);
            if (count >  0) {
                double fee = totalFee(p);
                text += p.getName() + " (" + p.getNumber() + "): " + count
                        + " consultations, $" + String.format("%.2f", fee) + "\n";
                providerCount++;
                totalConsultations += count;
                overallTotal += fee;
            }
        }
        text += "\nTotal providers: " + providerCount + "\n";
        text += "Total consultations: "+ totalConsultations + "\n";
        text += "Overall fee total: $" + String.format("%.2f", overallTotal) + "\n";

        System.out.println(text);
        writeFile("Summary_Report_" + today() + ".txt", text);
    }

    //Alphabetical list of services with codes and fees. 
    public void writeProviderDirectory(){
        ArrayList<Service> list = new ArrayList<>(data.getServices());
        list.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        String text = "PROVIDER DIRECTORY\n";
        for (Service s : list) {
            text += s.getName() + "  " + s.getCode() + "  $" + String.format("%.2f", s.getFee()) + "\n";
        }
        System.out.println(text);
        writeFile("Provider_Directory_" + today() + ".txt", text);
    }

    // Helper methods
    // count services for a provider
    private int countServices(Provider p) {
        int count = 0;
        for (ServiceRecord r : data.getRecords()){
            if (r.getProviderNumber().equals(p.getNumber())) {
                count++;
            }
        }
        return count;
    }

    // calculate fee for a provider
    private double totalFee(Provider p) {
        double total = 0;
        for (ServiceRecord r : data.getRecords()) {
            if (r.getProviderNumber().equals(p.getNumber())) {
                total += serviceFee(r.getServiceCode());
            }
        }
        return total;
    }

    // find a members name from their number
    private String memberName(String number) {
        Member m = data.findMember(number);
        return m == null ? "(deleted member)" : m.getName();
    }

    // find a provider's name from their number
    private String providerName(String number){
        Provider p = data.findProvider(number);
        return p == null ? "(deleted provider)" : p.getName();
    }

    // find the name of a service from its code
    private String serviceName(String code) {
        Service s = data.findService(code);
        return s == null ? "(unknown service)" : s.getName();
    }

    // find fee price of a service from its code
    private double serviceFee(String code) {
        Service s = data.findService(code);
        return s == null ? 0 : s.getFee();
    }

    // Turns MM-DD-YYYY into YYYYMMDD so dates sort correctly as text. 
    private String sortableDate(String date) {
        return date.substring(6) + date.substring(0, 2) + date.substring(3, 5);
    }

    // give current date
    private String today() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }

    // Writes text to a file in the "reports" folder. 
    private void writeFile(String fileName, String text) {
        File folder = new File("reports");
        folder.mkdirs();
        File file = new File(folder, fileName.replace(" ", "_"));
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(text);
            System.out.println("Saved: " + file.getPath());
        } catch (IOException e) {
            System.out.println("Could not write " + file.getPath());
        }
    }
}
