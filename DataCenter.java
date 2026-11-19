import java.util.ArrayList;
//Holds all of ChocAn's data: members, providers, services and service records.

//Author: Will Little

public class DataCenter {
    public static final String MANAGER_ID = "900000001";
    public static final String OPERATOR_ID = "900000002";
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private ArrayList<Service> services = new ArrayList<>();
    private ArrayList<ServiceRecord> records = new ArrayList<>();
    private int nextMemberNumber = 100000001;
    private int nextProviderNumber = 500000001;

 // Starts with some sample data so the program can be tried right away
    public DataCenter() {
        addMember("Alice Johnson", "12 Cocoa Lane", "Tuscaloosa", "AL", "35401");
        addMember("Bob Smith", "450 Fudge Street", "Birmingham", "AL", "35203");
        Member carla = addMember("Carla Diaz", "9 Truffle Court", "Huntsville", "AL", "35801");
        carla.setSuspended(true);
        addProvider("Diane Dietitian", "100 Health Way", "Tuscaloosa", "AL", "35401");
        addProvider("Evan Exercise", "22 Fitness Blvd", "Birmingham", "AL", "35205");
        services.add(new Service("598470", "Dietitian session", 75.00));
        services.add(new Service("883948", "Aerobics session", 50.00));
        services.add(new Service("123456", "Internist consult", 150.00));
    }

 //Members
    public Member addMember(String name, String street, String city, String state, String zip) {
        String number = String.valueOf(nextMemberNumber);
        nextMemberNumber++;
        Member member = new Member(number, name, street, city, state, zip);
        members.add(member);
        return member;
    }

 // Returns the member with this number, or null if not found. /
    public Member findMember(String number) {
        for (Member m : members) {
            if (m.getNumber().equals(number)) {
                return m;
            }
        }

        return null;
    }

    public boolean deleteMember(String number) {
        Member m = findMember(number);
        if (m == null) {
            return false;
        }

        members.remove(m);
        return true;
    }

 // Returns "Validated", "Invalid number", or "Member suspended". /
    public String validateMember(String number) {
        Member m = findMember(number);
        if (m == null) {
            return "Invalid number";
        }

        if (m.isSuspended()) {
            return "Member suspended";
        }

        return "Validated";
    }

 //Providers
    public Provider addProvider(String name, String street, String city, String state, String zip) {
        String number = String.valueOf(nextProviderNumber);
        nextProviderNumber++;
        Provider provider = new Provider(number, name, street, city, state, zip);
        providers.add(provider);
        return provider;
    }

 // Returns the provider with this number, or null if not found. /
    public Provider findProvider(String number) {
        for (Provider p : providers) {
            if (p.getNumber().equals(number)) {
                return p;
            }
        }
        return null;
    }

    public boolean deleteProvider(String number) {
        Provider p = findProvider(number);
        if (p == null) {
            return false;
        }
        providers.remove(p);
        return true;
    }

 //Services and records
 // Returns the service with this code, or null if not found. /
    public Service findService(String code) {
        for (Service s : services) {
            if (s.getCode().equals(code)) {
                return s;
            }
        }
        return null;
    }

    public void addRecord(ServiceRecord record) {
        records.add(record);
    }

    public ArrayList<Member> getMembers() { 
        return members; 
    }

    public ArrayList<Provider> getProviders() { 
        return providers; 
    }

    public ArrayList<Service> getServices() { 
        return services; 
    }

    public ArrayList<ServiceRecord> getRecords() { 
        return records;
    }
}