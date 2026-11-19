import java.util.Scanner;
/*
Menu for a ChocAn operator: add, edit and delete members and providers.

Author: Thomas Koska
*/
public class OperatorMenu {
private Scanner scanner;
private DataCenter data;
public OperatorMenu(Scanner scanner, DataCenter data) {
this.scanner = scanner;
this.data = data;
}
public void run() {
while (true) {
System.out.println();
System.out.println("=== Operator Menu ===");
System.out.println("1. Add member");
System.out.println("2. Edit member");
System.out.println("3. Delete member");
System.out.println("4. List members");
System.out.println("5. Add provider");
System.out.println("6. Edit provider");
System.out.println("7. Delete provider");
System.out.println("8. List providers");
System.out.println("0. Log out");
String choice = ask("Choice: ");
if (choice.equals("1")) {
addMember();
} else if (choice.equals("2")) {
editMember();
} else if (choice.equals("3")) {
deleteMember();
} else if (choice.equals("4")) {
for (Member m : data.getMembers()) {
System.out.println(m);
}
} else if (choice.equals("5")) {
addProvider();
} else if (choice.equals("6")) {
editProvider();
} else if (choice.equals("7")) {
deleteProvider();
} else if (choice.equals("8")) {
for (Provider p : data.getProviders()) {
System.out.println(p);
}
} else if (choice.equals("0")) {
return;
} else {
System.out.println("Invalid choice.");
}
}
}
// ---------- Members ----------
private void addMember() {
String name = readText("Name", 25, null);
String street = readText("Street address", 25, null);
String city = readText("City", 14, null);
String state = readState(null);
String zip = readZip(null);
Member m = data.addMember(name, street, city, state, zip);
System.out.println("Member added. Member number: " + m.getNumber());
}
private void editMember() {
Member m = data.findMember(ask("Member number: "));
if (m == null) {
System.out.println("Member not found.");
return;
}
System.out.println("Press Enter to keep the current value.");
m.setName(readText("Name", 25, m.getName()));
m.setStreet(readText("Street address", 25, m.getStreet()));
m.setCity(readText("City", 14, m.getCity()));
m.setState(readState(m.getState()));
m.setZip(readZip(m.getZip()));
String answer = ask("Suspended? (y/n) [" + (m.isSuspended() ? "y" : "n") + "]: ");
if (answer.equalsIgnoreCase("y")) {
m.setSuspended(true);
} else if (answer.equalsIgnoreCase("n")) {
m.setSuspended(false);
}
System.out.println("Member updated: " + m);
}
private void deleteMember() {
String number = ask("Member number: ");
if (data.deleteMember(number)) {
System.out.println("Member deleted.");
} else {
System.out.println("Member not found.");
}
}
// ---------- Providers ----------
private void addProvider() {
String name = readText("Name", 25, null);
String street = readText("Street address", 25, null);
String city = readText("City", 14, null);
String state = readState(null);
String zip = readZip(null);
Provider p = data.addProvider(name, street, city, state, zip);
System.out.println("Provider added. Provider number: " + p.getNumber());
}
private void editProvider() {
Provider p = data.findProvider(ask("Provider number: "));
if (p == null) {
System.out.println("Provider not found.");
return;
}
System.out.println("Press Enter to keep the current value.");
p.setName(readText("Name", 25, p.getName()));
p.setStreet(readText("Street address", 25, p.getStreet()));
p.setCity(readText("City", 14, p.getCity()));
p.setState(readState(p.getState()));
p.setZip(readZip(p.getZip()));
System.out.println("Provider updated: " + p);
}
private void deleteProvider() {
String number = ask("Provider number: ");
if (data.deleteProvider(number)) {
System.out.println("Provider deleted.");
} else {
System.out.println("Provider not found.");
}
}
// ---------- Input helpers ----------
// Check if inputs are valid
// If oldValue is not null (editing), pressing Enter keeps the old value.
private String readText(String label, int maxLength, String oldValue) {
while (true) {
String prompt = oldValue == null ? label + ": " : label + " [" + oldValue + "]: ";
String text = ask(prompt);
if (text.isEmpty() && oldValue != null) {
return oldValue;
}
if (!text.isEmpty() && text.length() <= maxLength) {
return text;
}
System.out.println(label + " must be 1 to " + maxLength + " characters.");
}
}
private String readState(String oldValue) {
while (true) {
String state = readText("State (2 letters)", 2, oldValue);
if (state.length() == 2 && Character.isLetter(state.charAt(0))
&& Character.isLetter(state.charAt(1))) {
return state.toUpperCase();
}
System.out.println("State must be 2 letters.");
}
}
private String readZip(String oldValue) {
while (true) {
String zip = readText("ZIP code (5 digits)", 5, oldValue);
boolean allDigits = zip.length() == 5;
for (int i = 0; i < zip.length(); i++) {
if (!Character.isDigit(zip.charAt(i))) {
allDigits = false;
}
}
if (allDigits) {
return zip;
}
System.out.println("ZIP code must be 5 digits.");
}
}
/** Shows a prompt and returns what the user typed. */
private String ask(String prompt) {
System.out.print(prompt);
return scanner.nextLine().trim();
}
}
