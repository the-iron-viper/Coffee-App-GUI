//package ui;
//
//import model.Order;
//import model.Drink;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//import javax.swing.*;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Scanner;
//
//// Coffee order application
//public class OrderApp extends JFrame {
//    private static final String JSON_STORE = "./data/currentOrder.json";
//    private Scanner input;
//    private Order currentOrder;
//    boolean toGo;
//    private JsonWriter jsonWriter;
//    private JsonReader jsonReader;
//
//    //EFFECTS: Runs the coffee order application
//    public OrderApp() throws CloneNotSupportedException {
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);
//        runOrder();
//    }
//
//    //MODIFIES: this
//    //EFFECTS: processes user input
//    //NOTE: runOrder() method is loosely based on runTeller() from the Teller application
//    private void runOrder() throws CloneNotSupportedException {
//        input = new Scanner(System.in);
//        boolean orderInProgress = true;
//        String command;
//        customizeToGo();
//        currentOrder = new Order(toGo);
//        commandOptions();
//
//        while (orderInProgress) {
//            command = input.nextLine();
//            command = command.toLowerCase();
//
//            if (command.equals("order")) {
//                orderInProgress = false;
//            } else {
//                processCommand(command);
//            }
//        }
//        System.out.println("ORDER COMPLETE");
//    }
//
//    //EFFECTS: Displays action options to user
//    private void commandOptions() {
//        System.out.println("SELECT ONE OF THE FOLLOWING");
//        System.out.println("   - VIEW MENU: SELECT (MENU)");
//        System.out.println("   - ADD AN ITEM TO ORDER: SELECT (ADD)");
//        System.out.println("   - REMOVE AN ITEM FROM ORDER: SELECT (REMOVE)");
//        System.out.println("   - VIEW AN ITEM IN ORDER: SELECT (VIEW DRINK)");
//        System.out.println("   - EDIT AN ITEM IN ORDER: SELECT (EDIT ITEM)");
//        System.out.println("   - INCREMENT ITEM IN ORDER: SELECT (+)");
//        System.out.println("   - VIEW ORDER: SELECT (VIEW ORDER)");
//        System.out.println("   - COMPLETE ORDER: SELECT (ORDER)");
//        System.out.println("   - SAVE CURRENT ORDER TO FILE (SAVE)");
//        System.out.println("   - LOAD CURRENT ORDER FROM FILE (LOAD)");
//    }
//
//    //MODIFIES: this
//    //EFFECTS: processes user command
//    private void processCommand(String command) throws CloneNotSupportedException {
//        switch (command) {
//            case "menu":
//                displayMenu();
//                break;
//            case "add":
//                doAddToOrder();
//                break;
//            case "remove":
//                doRemoveFromOrder();
//                break;
//            case "view drink":
//                viewDrink();
//                break;
//            case "edit item":
//                editDrinkInOrder();
//                break;
//            case "+":
//                doIncrementDrink();
//                break;
//            case "view order":
//                displayOrderInfo();
//                break;
//            case "save":
//                saveOrder();
//                break;
//            case "load":
//                loadOrder();
//                break;
//        }
//    }
//
//    //EFFECTS: Displays drink menu to user
//    private void displayMenu() {
//        System.out.println("HOT COFFEE DRINKS MENU" + '\n');
//        System.out.printf("%-20s%-20s%-20s%n%n", "Drip Coffee", "Americano", "Espresso");
//        System.out.printf("%-20s%-20s%-20s%n%n", "Macchiato", "Cappuccino", "Latte");
//    }
//
//    //MODIFIES: this
//    //EFFECTS: Prompts user to choose a drink and adds it to current order
//    private void doAddToOrder() {
//        Drink drink;
//
//        System.out.println("SELECT DRINK TO ADD FROM MENU");
//        String drinkName = input.nextLine().toLowerCase();
//        while (!checkName(drinkName)) {
//            System.out.println("THAT DRINK IS NOT ON THE MENU");
//            drinkName = input.nextLine().toLowerCase();
//        }
//
//        drink = new Drink(drinkName, currentOrder.isToGo());
//        currentOrder.addToOrder(drink);
//        customizeDrinkSize(drinkName);
//        includeAddOn(drinkName);
//        currentOrder.addPriceToDrink(drink);
//        System.out.println(drink.getDrinkName() + " HAS BEEN ADDED TO YOUR ORDER");
//    }
//
//    //EFFECTS: Returns true if drinkName is a valid drink name, returns false otherwise
//    public boolean checkName(String drinkName) {
//        String[] drinkNames = {"drip coffee", "americano", "latte", "cappuccino", "macchiato", "espresso"};
//        for (String item: drinkNames) {
//            if (item.equals(drinkName)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    //MODIFIES: this
//    //EFFECTS: Prompts user to choose a drink from current order and removes it
//    private void doRemoveFromOrder() {
//        String drinkToRemove;
//        System.out.println("SELECT DRINK TO REMOVE");
//        drinkToRemove = input.nextLine();
//        if (currentOrder.removeFromOrder(drinkToRemove)) {
//            System.out.println(drinkToRemove.toUpperCase() + " HAS BEEN REMOVED FROM YOUR ORDER");
//        } else {
//            System.out.println(drinkToRemove.toUpperCase() + " IS NOT IN YOUR ORDER");
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: Prompts user to choose a drink from current order and duplicates it
//    private void doIncrementDrink() throws CloneNotSupportedException {
//        String drinkName;
//
//        System.out.println("SELECT DRINK TO INCREMENT");
//        drinkName = input.nextLine().toLowerCase();
//        if (currentOrder.incrementDrink(drinkName)) {
//            System.out.println(drinkName + " IN ORDER HAS BEEN INCREASED BY 1");
//        } else {
//            System.out.println(drinkName + " IS NOT IN YOUR ORDER");
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: Prompts user to customize drink size for drink in current order
//    private void customizeDrinkSize(String drinkName) {
//        String drinkSize;
//        if (!(drinkName.equals("macchiato") || drinkName.equals("espresso"))) {
//            System.out.printf("SELECT DRINK SIZE ( %-10s%-10s%-6s)%n", "Small", "Medium", "Large");
//            drinkSize = input.nextLine();
//        } else {
//            drinkSize = "3 oz demitasse cup";
//        }
//        currentOrder.getDrink(drinkName).setDrinkSize(drinkSize);
//    }
//
//    //MODIFIES: this
//    //EFFECTS: Prompts user to customize add-ons for drink in current order
//    private void includeAddOn(String drinkName) {
//        System.out.println("ADD-ONS? (YES / NO)");
//        String includeAddOns = input.nextLine().toLowerCase();
//
//        if (includeAddOns.equals("yes")) {
//            do {
//                System.out.println("SELECT AN ADD-ON");
//                System.out.printf("%-15s%-15s%-15s%n", "Milk", "Cream", "Espresso shot");
//                System.out.printf("%-15s%-15s%n", "Honey", "Sugar");
//
//                String addOnChoice = input.nextLine();
//                currentOrder.getDrink(drinkName).addOn(addOnChoice);
//                System.out.println("1 " + addOnChoice.toUpperCase() + " HAS BEEN ADDED TO YOUR DRINK");
//                System.out.println("ANY MORE ADD-ONS? (YES / NO)");
//                includeAddOns = input.nextLine().toLowerCase();
//
//            } while (includeAddOns.equals("yes"));
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: Prompts user to customize to-go status for order
//    private void customizeToGo() {
//        String hereOrGo;
//        System.out.println("(FOR HERE) OR (TO GO)?");
//        hereOrGo = input.nextLine().toLowerCase();
//        if (hereOrGo.equals("to go")) {
//            toGo = true;
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: prompts user to select a drink from current order and customize it
//    private void editDrinkInOrder() {
//        String drinkToChange;
//        System.out.println("SELECT DRINK IN YOUR ORDER TO EDIT");
//        drinkToChange = input.nextLine();
//
//        System.out.println("SELECT EDIT OPTION: ( Drink size     To-go     Add-on )");
//        String editChoice = input.nextLine().toLowerCase();
//
//        if (editChoice.equals("drink size")) {
//            customizeDrinkSize(drinkToChange);
//            System.out.println("DRINK '" + drinkToChange.toUpperCase() + "' UPDATED");
//        } else if (editChoice.equals("to-go") || editChoice.equals("to go")) {
//            customizeToGo();
//            System.out.println("DRINK '" + drinkToChange.toUpperCase() + "' UPDATED");
//        } else {
//            includeAddOn(drinkToChange);
//            System.out.println("DRINK '" + drinkToChange.toUpperCase() + "' UPDATED");
//        }
//    }
//
//    //EFFECTS: Prompts user to select a drink in current order and displays its info
//    private void viewDrink() {
//        System.out.println("SELECT DRINK TO VIEW");
//        String drinkToView = input.nextLine().toLowerCase();
//        if (currentOrder.checkIfInOrder(drinkToView)) {
//            displayDrinkInfo(currentOrder.getDrink(drinkToView));
//        } else {
//            System.out.println(drinkToView + " IS NOT IN YOUR ORDER");
//        }
//    }
//
//    //EFFECTS: Displays info for drink in current order
//    private void displayDrinkInfo(Drink drink) {
//        System.out.println("Drink: " + " " + drink.getDrinkName());
//        System.out.println("   Cup type: " + drink.getCupType());
//        System.out.println("   Drink Size: " + drink.getDrinkSize());
//        System.out.println("   Price: $" + drink.getDrinkPrice());
//
//        for (int i = 0; i < 5; i++) {
//            String addOnName = drink.getAddOn(i).getAddOnName();
//            int addOnNum = drink.getAddOn(i).getAddOnNum();
//            if (addOnNum != 0) {
//                System.out.println("   Add-on: " + addOnName + " x" + addOnNum);
//            }
//        }
//    }
//
//    //EFFECTS: Displays order info for current order
//    private void displayOrderInfo() {
//        for (int i = 0; i < currentOrder.getNumberOfItems(); i++) {
//            displayDrinkInfo(currentOrder.getDrink(i));
//            System.out.println();
//        }
//        if (currentOrder.isToGo()) {
//            System.out.println("Ordered to go");
//        } else {
//            System.out.println("Ordered for here");
//        }
//        System.out.println("total: $" + currentOrder.getTotal());
//        System.out.println("Tax: $" + currentOrder.getTax());
//        System.out.println("Subtotal: $" + currentOrder.getSubtotal());
//    }
//
//    // EFFECTS: saves the order to file
//    private void saveOrder() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(currentOrder);
//            jsonWriter.close();
//            System.out.println("Saved current order to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads order from file
//    private void loadOrder() {
//        try {
//            currentOrder = jsonReader.read();
//            System.out.println("Loaded current order from " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }
//}