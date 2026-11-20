
//A health care provider (dietitian, internist, exercise expert).

//Author: James Zachman

public class Provider {
    private String number; // 9 digits
    private String name; // up to 25 characters
    private String street; // up to 25 characters
    private String city; // up to 14 characters
    private String state; // 2 letters
    private String zip; // 5 digits

    public Provider(String number, String name, String street, String city, String state, String zip){
        this.number = number;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
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

    public String toString(){
        return number + " " + name + ", " + street + ", " + city + ", " + state + " " + zip;
    }
    
}