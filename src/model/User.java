package model;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import controller.DBBroker;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class User {

    DBBroker dbb;

    String _id, name, lastName, parent, document, joinType, birthDate, joinDate, expireDate, bookTitle, bookAuthor, bookTakenDate, bookReturnDate;
    Long documentID, userID, invNumber, price;
    
    ArrayList<Book> booksBorrowed = new ArrayList<Book>();
    
    public void User(String _id, String name, String lastName, String parent, String document, Long documentID, String joinType, Long price,
                     String birthDate, Long userID, ArrayList booksBorrowed, String joinDate, String expireDate){
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
        this.booksBorrowed = booksBorrowed;
        this.joinDate = joinDate;
        this.expireDate = expireDate;
    }
    
    public void User() {
   
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

    // Checking if the object is valid
    public boolean isValid(){
        return name != null && lastName != null && userID != null;
    }

    public boolean addNewUser(String name, String lastName, String parent, String document, Long documentID,
                              String joinType, Long price, String birthDate, Long userId, String joinDate, String expireDate){


        Document clanarina = new Document("joinDate", joinDate).
                append("expireDate", expireDate).
                append("joinType", joinType).
                append("price", price);

        Document user = new Document("name", name).
                append("lastName", lastName).
                append("birthDate", birthDate).
                append("parent", parent).
                append("document", document).
                append("documentID", documentID).
                append("userID", userId).
                append("membership", clanarina);

        //TODO relocate this method call. Maybe to dbb?
        addUserHistory(userId);

        return dbb.createUser(user);
    }

    public ArrayList<User> findUserByName(String name) {

        // List that method returns as a result
        ArrayList<User> userList = new ArrayList<>();

        // Splitting the User's name into one-word Strings
        ArrayList<String> separatedNameStrings = new ArrayList<>();
        String[] arr = name.split(" ");
        separatedNameStrings.addAll(Arrays.asList(arr));
        Integer wordCount = separatedNameStrings.size();

        ArrayList<Object> usersFound = dbb.findUserByName(separatedNameStrings, wordCount);

        // Getting the object from the list, converting it to json Strings and parsing to JSONObject
        // Filling the 'User' object with parsed JSON objects
        User user;
        try{
            for (Object userObject : usersFound) {
                user = parseUser(userObject);
                userList.add(user);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return userList;
    }

    public User findUserByDocumentId(Long documentId) {
        String type = "documentId";
        Object foundUser = dbb.findUserById(type, documentId);
        User user = parseUser(foundUser);
        return user;
    }

    public User findUserById(Long userId) {
        String type = "userId";
        Object foundUser = dbb.findUserById(type, userId);
        User user = parseUser(foundUser);
        return user;
    }

    public boolean borrowBook(Long userId, Book book){

        // Current date and date a month from now
        Calendar cal = Calendar.getInstance();
        Date bookTake = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date bookReturn = cal.getTime();

        String dateBorrowed = String.valueOf(new SimpleDateFormat("dd.MM.yyyy").format(bookTake));
        String dateToReturn = String.valueOf(new SimpleDateFormat("dd.MM.yyyy").format(bookReturn));

        ArrayList<String> dates = new ArrayList<>();
        dates.add(dateBorrowed);
        dates.add(dateToReturn);

        return dbb.userBorrowBook(userId, book, dates);
    }

    public boolean prolongReturnDate (Long userId, String invNumber){

        // Current date and date a month from now
        Calendar cal = Calendar.getInstance();
        Date bookTake = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date bookReturn = cal.getTime();

        String dateProlonged = String.valueOf(new SimpleDateFormat("dd.MM.yyyy").format(bookTake));
        String dateToReturn = String.valueOf(new SimpleDateFormat("dd.MM.yyyy").format(bookReturn));

        ArrayList<String> dates = new ArrayList<>();
        dates.add(dateProlonged);
        dates.add(dateToReturn);

        return dbb.updateReturnDate(userId, invNumber, dates);
    }

    public boolean returnBook(Long userId, String invNumber){

        // Finding the User and the book by inventory number borrowed by the User
        // '$pull' command deletes the found book from the array
        BasicDBObject queryUser = new BasicDBObject("userID", userId);
        BasicDBObject queryBook = new BasicDBObject("invNumber", invNumber);
        BasicDBObject queryBooks = new BasicDBObject("zaduzenja", queryBook);
        BasicDBObject deleteBook = new BasicDBObject("$pull", queryBooks);

        ArrayList<BasicDBObject> queries = new ArrayList<>();
        queries.add(queryUser);
        queries.add(deleteBook);

        return dbb.userReturnBook(queries);
    }

    public boolean addUserHistory(Long userId) {
        return dbb.createUserHistory(userId);
    }

    public boolean addBookToUserHistory(Long userID, Book book){

        Calendar cal = Calendar.getInstance();
        Date bookTake = cal.getTime();
        String dateBorrowed = String.valueOf(new SimpleDateFormat("dd.MM.yyyy").format(bookTake));

        BasicDBObject push = new BasicDBObject();
        push.put("$push",
                new BasicDBObject("zaduzenja",
                        new BasicDBObject("bookName", book.getGlavniStvarniNaslov()).
                                append("bookAuthor", book.getPrviPodatakOdg()).
                                append("invNumber", book.getInvBroj()).
                                append("bookTaken", dateBorrowed)
                ));

        BasicDBObject query = new BasicDBObject();
        query.put("userID", userID);

        ArrayList<BasicDBObject> queries = new ArrayList<>();
        queries.add(query);
        queries.add(push);

        return dbb.updateUserHistory(queries);
    }

    // TODO what happens with historyQuery? Can this be done with regular user search query?
    public ArrayList<Book> userHistory(Long userID) {

        ArrayList<Book> books = new ArrayList<>();
        BasicDBObject userQuery = new BasicDBObject();
        BasicDBObject historyQuery = new BasicDBObject();
        userQuery.put("userID", userID);
        historyQuery.put("zaduzenja", userQuery);

        Object userObject = dbb.readUserHistory(userQuery);

        if (userObject != null) {
            try{
                Gson gson = new Gson();
                JSONParser parser = new JSONParser();
                String korisnikString = gson.toJson(userObject);
                JSONObject korJ = (JSONObject) parser.parse(korisnikString);

                ArrayList<Object> booksBorrowed = (ArrayList) korJ.get("zaduzenja");

                for (Object b : booksBorrowed) {
                    Book k = new Book();
                    Object obj = b;
                    String nameObj = gson.toJson(obj);
                    JSONObject jObj = (JSONObject) parser.parse(nameObj);

                    k.setGlavniStvarniNaslov((String) jObj.get("bookName"));
                    k.setPrviPodatakOdg((String) jObj.get("bookAuthor"));
                    k.setDatumZaduzenja((String) jObj.get("bookTaken"));
                    k.setDatumRazduzenja((String) jObj.get("bookReturn"));
                    k.setInvBroj((String) jObj.get("invNumber"));

                    books.add(k);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return books;
    }

    public ArrayList<Book> findBorrowedBooks(Long userID) {

        BasicDBObject query = new BasicDBObject();
        query.put("userID", userID);

        Object foundUser = dbb.findBooksBorrowedByUser(query);
//        BasicDBObject zaduzenjaQuery = new BasicDBObject();
//        zaduzenjaQuery.put("zaduzenja", userQuery);

        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Object> booksInHistory;

        if (foundUser != null) {
            try{
                Gson gson = new Gson();
                JSONParser parser = new JSONParser();
                String userJsonString = gson.toJson(foundUser);
                JSONObject userJsonObject = (JSONObject) parser.parse(userJsonString);

                booksInHistory = (ArrayList) userJsonObject.get("zaduzenja");

                for (Object b : booksInHistory) {
                    Book book = new Book();
                    String nameObj = gson.toJson(b);
                    JSONObject jObj = (JSONObject) parser.parse(nameObj);

                    book.setGlavniStvarniNaslov((String) jObj.get("bookName"));
                    book.setPrviPodatakOdg((String) jObj.get("bookAuthor"));
                    book.setDatumZaduzenja((String) jObj.get("bookTaken"));
                    book.setDatumRazduzenja((String) jObj.get("bookReturn"));
                    book.setInvBroj((String) jObj.get("invNumber"));

                    books.add(book);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return books;
    }

    public boolean reservationAction(String type, String invNumber, Long userID){

        String command = "";

        if(type.equals("reserve"))
            command = "$push";
        else if(type.equals("cancel"))
            command = "$pull";

        BasicDBObject book = new BasicDBObject("inventoryData.inventarniBroj", invNumber);
        BasicDBObject query = new BasicDBObject(command, new BasicDBObject("reservations", userID));

        ArrayList<BasicDBObject> queries = new ArrayList<>();
        queries.add(book);
        queries.add(query);

        return dbb.userReservationAction(queries);
    }

    public ArrayList<Book> findReservations(Long userId) {

        ArrayList<Book> books = new ArrayList<>();
//        ArrayList<Book> empty = new ArrayList<>();

        BasicDBObject query = new BasicDBObject("reservations", userId);

        ArrayList<Object> booksFound = dbb.findBooksByParameter(query);

        try {
            for (Object b : booksFound) {
                Book book = new Book();
                Gson gson = new Gson();
                JSONParser parser = new JSONParser();
                String nameObj = gson.toJson(b);
                JSONObject jObj = (JSONObject) parser.parse(nameObj);
                JSONObject proc = (JSONObject) jObj.get("processed");
                JSONObject inv = (JSONObject) jObj.get("inventoryData");
                JSONObject naslovOdgovornost = (JSONObject) proc.get("stvarniNaslovOdgovornost");
                JSONObject izdavanje = (JSONObject) proc.get("izdavanje");

                book.setGlavniStvarniNaslov((String) naslovOdgovornost.get("a"));
                book.setPrviPodatakOdg((String) naslovOdgovornost.get("f"));
                book.setGodinaIzdavanja((Long) izdavanje.get("d"));
                book.setIzdavac((String) izdavanje.get("c"));
                book.setInvBroj((String) inv.get("inventarniBroj"));

                books.add(book);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return books;

//        if(!books.isEmpty())
//            return books;
//        else
//            return empty;
    }

    // TODO place this method in Book class? Rewrite method?
    public User checkIfReserved(String invNumber) {
        // Proverava da li postoji neko ko je rezervisao knjigu nakon sto je vracena

        User user = new User();
        ArrayList<Object> reservations;
        String usrRes;

        BasicDBObject query = new BasicDBObject("inventoryData.inventarniBroj", invNumber);

        Object book = dbb.checkReservations(query);

        try{
            Gson gson = new Gson();
            JSONParser parser = new JSONParser();
            String bookString = gson.toJson(book);
            JSONObject jObj = (JSONObject) parser.parse(bookString);

            reservations = (ArrayList<Object>) jObj.get("reservations");
            Object o = reservations.get(0);
            usrRes = o.toString();

            String strArray[] = usrRes.split(" ");
            String str = strArray[0];
            Long usr = Long.valueOf(str);

            if(!usrRes.isEmpty()) {
                user = findUserById(usr);
            }
            else {
                user = null;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    private User parseUser(Object userToParse){
        Gson gson = new Gson();
        JSONParser parser = new JSONParser();
        User user = new User();
        String nameObj;
        JSONObject jObj = null;

        try{
            nameObj = gson.toJson(userToParse);
            jObj = (JSONObject) parser.parse(nameObj);
        } catch (Exception e){
            e.printStackTrace();
        }

        JSONObject mObj = (JSONObject) jObj.get("membership");
        user.setName((String) jObj.get("name"));
        user.setLastName((String) jObj.get("lastName"));
        user.setParent((String) jObj.get("parent"));
        user.setBirthDate((String) jObj.get("birthDate"));
        user.setUserID((Long) jObj.get("userID"));
        user.setDocument((String) jObj.get("document"));
        user.setDocumentID((Long) jObj.get("documentID"));
        user.setPrice((Long) mObj.get("price"));
        user.setJoinType((String) mObj.get("joinType"));
        user.setJoinDate((String) mObj.get("joinDate"));
        user.setExpireDate((String) mObj.get("expireDate"));

        return user;
    }
    
}
