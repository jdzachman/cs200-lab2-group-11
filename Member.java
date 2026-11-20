
//A ChocAn member.

//Author: James Zachman

public class Member {
    private String number; // 9 digits
    private String name; // up to 25 characters
    private String street; // up to 25 characters
    private String city; // up to 14 characters
    private String state; // 2 letters
    private String zip; // 5 digits
    private boolean suspended; // true if fees are owed
    
    public Member(String number, String name, String street, String city, String state, String zip){
        this.number = number;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.suspended = false;
    }

    public String getNumber(){
        return number;
    }

    public String getName(){ 
        return name;
    }

    public String getStreet(){
        return street;
    }

    public String getCity(){
        return city;
    }

    public String getState(){
        return state;
    }

    public String getZip(){
        return zip;
    }

    public boolean isSuspended(){
        return suspended;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setStreet(String street){
        this.street = street;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setState(String state){
        this.state = state;
    }

    public void setZip(String zip){
        this.zip = zip;
    }

    public void setSuspended(boolean suspended){
        this.suspended = suspended;
    }

    public String toString(){
        String status = suspended ? "SUSPENDED" : "ACTIVE";

        return number + " " + name + ", " + street + ", " + city + ", " + state + " " + zip
       + " [" + status + "]";
    }
    
}