package model;

import com.google.gson.Gson;
import controller.DBBroker;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class Librarian {

    private DBBroker dbb;
 
    private String name, lastName, username, password;
    private boolean processing, inventory, circulation, admin;

    public void Librarian(){
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean doesProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public boolean doesInventory() {
        return inventory;
    }

    public void setInventory(boolean inventory) {
        this.inventory = inventory;
    }

    public boolean doesCirculation() {
        return circulation;
    }

    public void setCirculation(boolean circulation) {
        this.circulation = circulation;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean addNewLibrarian(String name, String lastName, String username, String password,
                                   boolean circulation, boolean processing, boolean inventory, boolean admin){

        Document librarian = new Document("name", name).
                append("lastName", lastName).
                append("username", username).
                append("password", password).
                append("circulation", circulation).
                append("processing", processing).
                append("inventory", inventory).
                append("admin", admin);

        return dbb.createLibrarian(librarian);
    }

    //TODO Check how this method works. Why does it need a 'name' argument?
    public ArrayList<Librarian> findAllLibrarians(String name){

        ArrayList<Object> foundLibrarians = dbb.findAllLibrarians(name);

        ArrayList<Librarian> librarians = new ArrayList<>();
        Librarian librarian = new Librarian();

        Gson gson = new Gson();
        JSONParser parser = new JSONParser();

        for(Object temp : foundLibrarians){
            String librarianJsonString = gson.toJson(temp);
            try {
                JSONObject librarianJsonObject = (JSONObject) parser.parse(librarianJsonString);
                librarian.setName((String) librarianJsonObject.get("name"));
                librarian.setLastName((String) librarianJsonObject.get("lastName"));
                librarian.setCirculation((Boolean) librarianJsonObject.get("circulation"));
                librarian.setProcessing((Boolean) librarianJsonObject.get("processing"));
                librarian.setInventory((Boolean) librarianJsonObject.get("inventory"));
                librarian.setAdmin((Boolean) librarianJsonObject.get("admin"));
                librarians.add(librarian);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return librarians;
    }

    public Librarian findLibrarian(String name){

        Object foundLibrarian = dbb.findLibrarian(name);
        Librarian librarian = new Librarian();

        Gson gson = new Gson();
        JSONParser parser = new JSONParser();

        try {
            String librarianJsonString = gson.toJson(foundLibrarian);
            JSONObject librarianJsonObject = (JSONObject) parser.parse(librarianJsonString);
            librarian.setName((String) librarianJsonObject.get("name"));
            librarian.setLastName((String) librarianJsonObject.get("lastName"));
            librarian.setCirculation((Boolean) librarianJsonObject.get("circulation"));
            librarian.setProcessing((Boolean) librarianJsonObject.get("processing"));
            librarian.setInventory((Boolean) librarianJsonObject.get("inventory"));
            librarian.setAdmin((Boolean) librarianJsonObject.get("admin"));
        } catch(Exception e){
            e.printStackTrace();
        }
        return librarian;
    }

    //TODO Check this method and the method it calls.
    public boolean librarianLogin(String username, String password){
        return dbb.loginLibrarian(username, password);
    }
}