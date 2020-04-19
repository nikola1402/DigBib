package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author nikol
 */
public class Korisnik {

    
    String _id, name, lastName, parent, document, joinType, birthDate, joinDate, expireDate, bookTitle, bookAuthor, bookTakenDate, bookReturnDate;
    Long documentID, userID, invNumber, price;
    
    ArrayList<Knjiga> booksLoaned = new ArrayList<Knjiga>();
    
    public void Korisnik (String _id, String name, String lastName, String parent, String document, Long documentID, String joinType, Long price,
            String birthDate, Long userID, ArrayList booksLoaned, String joinDate, String expireDate){
        this._id = _id;
        this.name = name;
        this.lastName = lastName;
        this.parent = parent;
        this.document = document;
        this.documentID = documentID;
        this.joinType = joinType;
        this.price = price;
        this.birthDate = birthDate;
        this.userID = userID;
        this.booksLoaned = booksLoaned;
        this.joinDate = joinDate;
        this.expireDate = expireDate;
    }
    
    public void Korisnik() {
   
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public Long getDocumentID() {
        return documentID;
    }

    public void setDocumentID(Long documentID) {
        this.documentID = documentID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
    
    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
    

    
    // Proveravanje da li je objekat prazan
    public boolean isValid(){
        return name != null && lastName != null && userID != null;
    }
    
}
