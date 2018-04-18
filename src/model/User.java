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

    private DBBroker dbb;

    private String _id, name, lastName, parent, document, joinType, birthDate, joinDate, expireDate, bookTitle, bookAuthor, bookTakenDate, bookReturnDate;
    private Long documentId, userId, inventoryNum, price;

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

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        return name != null && lastName != null && userId != null;
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
                append("documentId", userData.get("documentId")).
                append("userId", userData.get("userId")).
                append("membership", membership);

        addUserHistory((Long) userData.get("userId"));
        return dbb.createUser(user);
    }

    public ArrayList<User> findUsersByName(String name) {

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
            for (Object temp : usersFound) {
                user = parseUser(temp);
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

    public boolean borrowBook(Long userId, Book book, String command){
        ArrayList<String> dates = getDates();
        switch (command) {
            case "borrow":
                return dbb.userBorrowBook(userId, book, dates);
            case "prolong":
                return dbb.updateReturnDate(userId, book.getInventoryNum(), dates);
            default:
                return false;
        }
    }

    // TODO relocate queries to dbb?
    public boolean returnBook(Long userId, String inventoryNum){

        // Finding the User and the book by inventory number borrowed by the User
        // '$pull' command deletes the found book from the array
        BasicDBObject userQuery = new BasicDBObject("userId", userId);
        BasicDBObject bookQuery = new BasicDBObject("inventoryNum", inventoryNum);
        BasicDBObject borrowedQuery = new BasicDBObject("zaduzenja", bookQuery);
        BasicDBObject returnBook = new BasicDBObject("$pull", borrowedQuery);

        ArrayList<BasicDBObject> queries = getQueries(userQuery, returnBook);
        return dbb.userReturnBook(queries);
    }

    private boolean addUserHistory(Long userId) {
        return dbb.createUserHistory(userId);
    }

    // TODO relocate queries to dbb?
    public boolean addBookToUserHistory(Long userId, Book book){

        ArrayList<String> dates = getDates();

        BasicDBObject push = new BasicDBObject();
        push.put("$push",
                new BasicDBObject("borrowedBooks",
                        new BasicDBObject("bookName", book.getProperTitle()).
                                append("bookAuthor", book.getFirstStmntResp()).
                                append("inventoryNum", book.getInventoryNum()).
                                append("bookTaken", dates.get(0))
                ));

        BasicDBObject query = new BasicDBObject();
        query.put("userId", userId);

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
        userQuery.put("userId", userId);
        borrowedQuery.put("borrowedBooks", userQuery);

        String collection = "";
        if(type.equals("current"))
            collection = "users";
        else if(type.equals("history"))
            collection = "borrowedHistory";

        Object foundUser = dbb.findBooksBorrowedByUser(userQuery, collection);

        if (foundUser != null) {
            try{
                Gson gson = new Gson();
                JSONParser parser = new JSONParser();
                String userJsonString = gson.toJson(foundUser);
                JSONObject userJsonObject = (JSONObject) parser.parse(userJsonString);

                // TODO convert JSONObject to string and split to get an array?
                ArrayList<Object> booksInHistory = (ArrayList<Object>) userJsonObject.get("borrowedBooks");

                for (Object temp : booksInHistory) {
                    Book book = new Book();
                    String bookJsonString = gson.toJson(temp);
                    JSONObject bookJsonObject = (JSONObject) parser.parse(bookJsonString);

                    book.setProperTitle((String) bookJsonObject.get("bookName"));
                    book.setFirstStmntResp((String) bookJsonObject.get("bookAuthor"));
                    book.setDateBorrowed((String) bookJsonObject.get("bookTaken"));
                    book.setDateReturned((String) bookJsonObject.get("bookReturn"));
                    book.setInventoryNum((String) bookJsonObject.get("inventoryNum"));

                    books.add(book);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return books;
    }

    // TODO relocate queries to dbb?
    public boolean reservationAction(String type, String inventoryNum, Long userId){

        String command = "";

        if(type.equals("reserve"))
            command = "$push";
        else if(type.equals("cancel"))
            command = "$pull";

        BasicDBObject book = new BasicDBObject("inventoryData.inventoryNum", inventoryNum);
        BasicDBObject query = new BasicDBObject(command, new BasicDBObject("reservations", userId));

        ArrayList<BasicDBObject> queries = getQueries(book, query);
        return dbb.updateBookData(queries);
    }

    // TODO place this method in Book class? Rewrite method?
    // Method checks if the book is reserved by someone after being returned, and returns the User that reserved it
    public User checkIfReserved(String inventoryNum) {

        User user = new User();

        BasicDBObject query = new BasicDBObject("inventoryData.inventarniBroj", inventoryNum);
        Object book = dbb.findBookByInventoryNum(query);

        Gson gson = new Gson();
        JSONParser parser = new JSONParser();
        try{
            String bookJsonString = gson.toJson(book);
            JSONObject bookJsonObject = (JSONObject) parser.parse(bookJsonString);

            Object reservations = bookJsonObject.get("reservations");
            String reservedBy = reservations.toString();

            String[] arr = reservedBy.split(" ");
            Long userId = Long.valueOf(arr[0]);

            if(!reservedBy.isEmpty())
                return findUserById(userId, "userId");
            else
                return null;
        } catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    private User parseUser(Object userToParse){
        Gson gson = new Gson();
        JSONParser parser = new JSONParser();
        User user = new User();
        try{
            String userJsonString = gson.toJson(userToParse);
            JSONObject userJsonObject = (JSONObject) parser.parse(userJsonString);
            JSONObject membershipJsonObject = (JSONObject) userJsonObject.get("membership");
            user.setName((String) userJsonObject.get("name"));
            user.setLastName((String) userJsonObject.get("lastName"));
            user.setParent((String) userJsonObject.get("parent"));
            user.setBirthDate((String) userJsonObject.get("birthDate"));
            user.setUserId((Long) userJsonObject.get("userId"));
            user.setDocument((String) userJsonObject.get("document"));
            user.setDocumentId((Long) userJsonObject.get("documentId"));
            user.setPrice((Long) membershipJsonObject.get("price"));
            user.setJoinType((String) membershipJsonObject.get("joinType"));
            user.setJoinDate((String) membershipJsonObject.get("joinDate"));
            user.setExpireDate((String) membershipJsonObject.get("expireDate"));
        } catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    private ArrayList<String> getDates(){
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

    private ArrayList<BasicDBObject> getQueries(BasicDBObject first, BasicDBObject second){
        ArrayList<BasicDBObject> queries = new ArrayList<>();
        queries.add(first);
        queries.add(second);
        return queries;
    }
}