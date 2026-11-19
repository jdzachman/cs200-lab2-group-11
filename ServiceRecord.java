//One service that a provider billed ChocAn for.

//Author: Will Little

public class ServiceRecord {
    private String currentDateTime; // when it was entered: MM-DD-YYYY HH:MM:SS
    private String serviceDate; // when the service happened: MM-DD-YYYY
    private String providerNumber;
    private String memberNumber;
    private String serviceCode;
    private String comments; // optional, up to 100 characters
    public ServiceRecord(String currentDateTime, String serviceDate, String providerNumber,
    String memberNumber, String serviceCode, String comments) {
        this.currentDateTime = currentDateTime;
        this.serviceDate = serviceDate;
        this.providerNumber = providerNumber;
        this.memberNumber = memberNumber;
        this.serviceCode = serviceCode;
        this.comments = comments;
    }
    public String getCurrentDateTime() { 
        return currentDateTime; 
    }
    public String getServiceDate() { 
        return serviceDate; 
    }
    public String getProviderNumber() { 
        return providerNumber; 
    }
    public String getMemberNumber() { 
        return memberNumber; 
    }
    public String getServiceCode() { 
        return serviceCode; 
    }
    public String getComments() { 
        return comments; 
    }
}
