package model;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import controller.DBBroker;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.*;

public class User {

    DBBroker dbb;

    String _id, name, lastName, parent, document, joinType, birthDate, joinDate, expireDate, bookTitle, bookAuthor, bookTakenDate, bookReturnDate;
    Long documentID, userID, invNumber, price;
    
    ArrayList<Book> booksBorrowed = new ArrayList<Book>();
    
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

    public boolean addNewUser(Map<String, Object> userData){

        Document membership = new Document("joinDate", userData.get("joinDate")).
                append("expireDate", userData.get("expireDate")).
                append("joinType", userData.get("joinType")).
                append("price", userData.get("price"));

        Document user = new Document("name", userData.get("name")).
                append("lastName", userData.get("lastName")).
                append("birthDate", userData.get("birthDate")).
                append("parent", userData.get("parent")).
                append("document", userData.get("document")).
                append("documentID", userData.get("documentId")).
                append("userID", userData.get("userId")).
                append("membership", membership);

        addUserHistory((Long) userData.get("userId"));
        return dbb.createUser(user);
    }

    public ArrayList<User> findUsersByName(String name) {

        // List that method returns as a result
        ArrayList<User> users = new ArrayList<>();

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
                users.add(user);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }

    // Type can be userId or documentId
    public User findUserById(Long userId, String type) {
        Object foundUser = dbb.findUserById(type, userId);
        return parseUser(foundUser);
    }

    // TODO make return type boolean?
    public void borrowBook(Long userId, Book book, String command){
        ArrayList<String> dates = getDates();
        if(command.equals("borrow"))
            dbb.userBorrowBook(userId, book, dates);
        else if (command.equals("prolong"))
            dbb.updateReturnDate(userId, book.getInvBroj(), dates);
    }

    public boolean returnBook(Long userId, String invNumber){

        // Finding the User and the book by inventory number borrowed by the User
        // '$pull' command deletes the found book from the array
        BasicDBObject queryUser = new BasicDBObject("userID", userId);
        BasicDBObject queryBook = new BasicDBObject("invNumber", invNumber);
        BasicDBObject queryBooks = new BasicDBObject("zaduzenja", queryBook);
        BasicDBObject deleteBook = new BasicDBObject("$pull", queryBooks);

        ArrayList<BasicDBObject> queries = getQueries(queryUser, deleteBook);
        return dbb.userReturnBook(queries);
    }

    public boolean addUserHistory(Long userId) {
        return dbb.createUserHistory(userId);
    }

    public boolean addBookToUserHistory(Long userID, Book book){

        ArrayList<String> dates = getDates();

        BasicDBObject push = new BasicDBObject();
        push.put("$push",
                new BasicDBObject("zaduzenja",
                        new BasicDBObject("bookName", book.getGlavniStvarniNaslov()).
                                append("bookAuthor", book.getPrviPodatakOdg()).
                                append("invNumber", book.getInvBroj()).
                                append("bookTaken", dates.get(0))
                ));

        BasicDBObject query = new BasicDBObject();
        query.put("userID", userID);

        ArrayList<BasicDBObject> queries = getQueries(query, push);
        return dbb.updateUserHistory(queries);
    }

    // TODO what happens with borrowed? Can this be done with regular user search query?

    // String collection can contain 'istorijaZaduzenja' or 'korisnici'
    // It is used to find currently borrowed books or entire history of them for the given user
    public ArrayList<Book> findBorrowedBooks(Long userId, String type) {

        ArrayList<Book> books = new ArrayList<>();
        BasicDBObject userQuery = new BasicDBObject();
        BasicDBObject borrowedQuery = new BasicDBObject();
        userQuery.put("userID", userId);
        borrowedQuery.put("zaduzenja", userQuery);

        String collection = "";
        if(type.equals("current"))
            collection = "korisnici";
        else if(type.equals("history"))
            collection = "istorijaZaduzenja";

        Object foundUser = dbb.findBooksBorrowedByUser(userQuery, collection);

        if (foundUser != null) {
            try{
                Gson gson = new Gson();
                JSONParser parser = new JSONParser();
                String userJsonString = gson.toJson(foundUser);
                JSONObject userJsonObject = (JSONObject) parser.parse(userJsonString);

                ArrayList<Object> booksInHistory = (ArrayList) userJsonObject.get("zaduzenja");

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

        ArrayList<BasicDBObject> queries = getQueries(book, query);
        return dbb.updateBookData(queries);
    }

    // TODO place this method in Book class? Rewrite method?
    // Method checks if the book is reserved by someone after being returned, and returns the User that reserved it
    public User checkIfReserved(String invNumber) {

        User user = new User();
        ArrayList<Object> reservations;
        String usrRes;

        BasicDBObject query = new BasicDBObject("inventoryData.inventarniBroj", invNumber);

        Object book = dbb.findBookByInventoryNum(query);

        try{
            Gson gson = new Gson();
            JSONParser parser = new JSONParser();
            String bookJsonString = gson.toJson(book);
            JSONObject bookJsonObject = (JSONObject) parser.parse(bookJsonString);

            reservations = (ArrayList<Object>) bookJsonObject.get("reservations");
            Object o = reservations.get(0);
            usrRes = o.toString();

            String strArray[] = usrRes.split(" ");
            String str = strArray[0];
            Long usr = Long.valueOf(str);

            if(!usrRes.isEmpty()) {
                user = findUserById(usr, "userId");
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

    public ArrayList<String> getDates(){
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

        return dates;
    }

    public ArrayList<BasicDBObject> getQueries(BasicDBObject first, BasicDBObject second){
        ArrayList<BasicDBObject> queries = new ArrayList<>();
        queries.add(first);
        queries.add(second);
        return queries;
    }
}