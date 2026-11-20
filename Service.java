//A service listed in the Provider Directory

//Author: Will Little

public class Service {
    private String code; // 6 digits
    private String name; // up to 20 characters
    private double fee; // up to $999.99
    public Service(String code, String name, double fee) {
        this.code = code;
        this.name = name;
        this.fee = fee;
    }
    public String getCode() { 
        return code; 
    }
    public String getName() { 
        return name; 
    }
    public double getFee() { 
        return fee; 
    }
}
