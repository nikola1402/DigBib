package model;

/**
 *
 * @author nikol
 */
public class Bibliotekar {
 
    private String name, lastName, username, password;
    private boolean processing, inventory, circulation, admin;
    
    public void Bibliotekar (String name, String lastName, String username, String password, boolean circulation, boolean processing, boolean inventory, boolean admin){
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.processing = processing;
        this.inventory = inventory;
        this.circulation = circulation;
        this.admin = admin;
    }

    public void Bibliotekar(){
        
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

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public boolean isInventory() {
        return inventory;
    }

    public void setInventory(boolean inventory) {
        this.inventory = inventory;
    }

    public boolean isCirculation() {
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
    
    
}