package controller;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.mongodb.client.MongoCursor;
import java.io.File;
import java.io.IOException;
import java.util.*;

import model.Book;
import org.bson.Document;

public class DBBroker {
    
    Mongo mongo = null;
    
    // Database connection. Needs to be opened only once
    MongoClient client = new MongoClient();
    MongoDatabase db = client.getDatabase("test");
    DB gfsDb = client.getDB("test");


    // Code for object 'Book'

    public boolean createBook(Document query){
        MongoCollection coll = db.getCollection("knjige");
        coll.insertOne(query);
        return true;
    }

    public ArrayList<BasicDBObject> findBooksToProcess(BasicDBObject query){

        // This type of connection is because only through BasicDBObject we can get the correct _id
        MongoCollection<BasicDBObject> coll = db.getCollection("knjige", BasicDBObject.class);

        ArrayList<BasicDBObject> booksFound = new ArrayList<>();

        try (MongoCursor<BasicDBObject> cur = coll.find(query).iterator()) {
            while(cur.hasNext()){
                booksFound.add(cur.next());
            }
        }
        return booksFound;
    }

    public Object findBookByInventoryNum(BasicDBObject query){
        MongoCollection coll = db.getCollection("knjige");
        Object book = coll.find(query).first();
        return book;
    }

    public ArrayList<Object> findBooksByTitleAuthorPublisher(String type, ArrayList<String> param, Integer wordCount){

        MongoCollection coll = db.getCollection("knjige");

        String attribute = "";
        if(type.equals("title")) {
            attribute = "processed.stvarniNaslovOdgovornost.a";
        } else if(type.equals("author")){
            attribute = "processed.stvarniNaslovOdgovornost.f";
        } else if(type.equals("publisher")){
            attribute = "processed.izdavanje.c";
        }

        // List populated with query results
        ArrayList<Object> booksFound = new ArrayList<>();

        // Based on the number of the words in the title, different queries are prepared
        // $regex compares Strings
        // $options i neglects the differences between capital and small letteres in Strings
        BasicDBObject query;

        switch (wordCount) {
            case 1:
                query= new BasicDBObject(attribute, new BasicDBObject("$regex", param.get(0)).
                        append("$options", "i"));
                query.put("inventory", true);
                break;
            case 2:
            {
                ArrayList<String> ls = new ArrayList();
                ls.addAll(param);
                String b = ls.get(0);
                String c = ls.get(1);
                query = new BasicDBObject(attribute, new BasicDBObject("$regex", b).
                        append("$regex", c).
                        append("$options", "i"));
                query.put("inventory", true);
                break;
            }
            default:
            {
                ArrayList<String> ls = new ArrayList();
                ls.addAll(param);
                String b = ls.get(0);
                String c = ls.get(1);
                String d = ls.get(2);
                query = new BasicDBObject(attribute, new BasicDBObject("$regex", b).
                        append("$regex", c).
                        append("$regex", d).
                        append("$options", "i"));
                query.put("inventory", true);
                break;
            }
        }

        try (MongoCursor cur = coll.find(query).iterator()) {
            while(cur.hasNext()){
                booksFound.add(cur.next());
            }
        }
        return booksFound;
    }

    public ArrayList<Object> findBooksByParameter(BasicDBObject query){

        MongoCollection coll = db.getCollection("knjige");

        ArrayList<Object> booksFound = new ArrayList<>();
        try (MongoCursor cur = coll.find(query).iterator()) {
            while(cur.hasNext()){
                booksFound.add(cur.next());
            }
        }
        return booksFound;
    }


    // Code for Digital Library

    public boolean createDigitalBook(Map<String, Object> map) throws IOException {

        // GridFS works only with DBObjects, it doesn't accept MongoDatabase
        // Connecting to GridFS collection 'objects'
        GridFS objects = new GridFS(gfsDb, "objekti");
        
        GridFSInputFile pdf = objects.createFile((File) map.get("file"));

        BasicDBObject meta = new BasicDBObject("inventoryNumber", map.get("inventoryNumber")).
                append("title", map.get("title")).
                append("creator", map.get("creator")).
                append("subject", map.get("subject")).
                append("description", map.get("description")).
                append("publisher", map.get("publisher")).
                append("date", map.get("date")).
                append("format", map.get("format")).
                append("identifier", map.get("identifier")).
                append("source", map.get("source")).
                append("language", map.get("language")).
                append("collection", map.get("collection"));
        
        pdf.setMetaData(meta);
        pdf.save();
        return true;
    }
    
    public GridFSDBFile findDigitalBookByInventoryNum(String invNum){
        
        GridFS gf = new GridFS(gfsDb, "objekti");

        BasicDBObject query = new BasicDBObject();
        query.put("metadata.inventoryNumber", invNum);

        return gf.findOne(query);
    }
    
    public List<GridFSDBFile> findDigitalBooksByTitleAuthorPublisher(String type, ArrayList<String> param, Integer wordCount){
        
        GridFS gf = new GridFS(gfsDb, "objekti");

        String attribute = "";
        if(type.equals("title")) {
            attribute = "metadata.title";
        } else if(type.equals("author")){
            attribute = "metadata.creator";
        } else if(type.equals("publisher")){
            attribute = "metadata.publisher";
        }

        BasicDBObject query;

        switch (wordCount) {
            case 1:
                query = new BasicDBObject(attribute, new BasicDBObject("$regex", param.get(0))
                        .append("$options", "i"));
                break;
            case 2:
                {
                    ArrayList<String> ls = new ArrayList();
                    ls.addAll(param);

                    String s = ls.get(0);
                    String s1 = ls.get(1);
                    query = new BasicDBObject(attribute, new BasicDBObject("$regex", s)
                            .append("$regex", s1)
                            .append("$options", "i"));
                    break;
                }
            default:
                {
                    ArrayList<String> ls = new ArrayList();
                    ls.addAll(param);

                    String s = ls.get(0);
                    String s1 = ls.get(1);
                    String s2 = ls.get(2);
                    query = new BasicDBObject(attribute, new BasicDBObject("$regex", s)
                            .append("$regex", s1)
                            .append("$regex", s2)
                            .append("$options", "i"));
                    break;
                }
        }
        
        return gf.find(query);
    }
    
    public List<GridFSDBFile> findDigitalBooksByYear(Long year){
        
        GridFS gf = new GridFS(gfsDb, "objekti");
        
        // Querying the database
        BasicDBObject query = new BasicDBObject("metadata.date", year);
        return gf.find(query);
    }

    // Code for working withe the User

    public boolean createUser(Document user){
        MongoCollection coll = db.getCollection("korisnici");
        coll.insertOne(user);
        return true;
    }

    public ArrayList<Object> findUserByName(ArrayList<String> name, Integer wordCount){

        MongoCollection coll = db.getCollection("korisnici");

        // List populated by query results
        ArrayList<Object> usersFound = new ArrayList<>();

        BasicDBObject query;

        // Based on the number of the words in User's name, different queries are prepared
        // $regex compares Strings
        // $options i neglects the differences between capital and small letteres in Strings
        switch (wordCount) {
            case 1:
                query = new BasicDBObject("name", new BasicDBObject("$regex", name).
                        append("$options", "i"));
                break;
            case 2:
            {
                ArrayList<String> ls = new ArrayList();
                ls.addAll(name);

                String b = ls.get(0);
                String c = ls.get(1);
                query = new BasicDBObject("name", new BasicDBObject("$regex", b).
                        append("$regex", c).
                        append("$options", "i"));
                break;
            }
            default:
            {
                ArrayList<String> ls = new ArrayList();
                ls.addAll(name);

                String b = ls.get(0);
                String c = ls.get(1);
                String d = ls.get(2);
                query = new BasicDBObject("name", new BasicDBObject("$regex", b).
                        append("$regex", c).
                        append("$regex", d).
                        append("$options", "i"));
                break;
            }
        }

        // Querying the database
        try (MongoCursor cur = coll.find(query).iterator()) {
            while(cur.hasNext()){
                usersFound.add(cur.next());
            }
        }

        return usersFound;
    }

    public Object findUserById(String type, Long id){

        String attribute = "";
        if(type.equals("userId"))
            attribute = "userId";
        else if(type.equals("documentId"))
            attribute = "documentId";

        MongoCollection coll = db.getCollection("korisnici");
        BasicDBObject korQuery = new BasicDBObject(attribute, id);
        Object user = coll.find(korQuery).first();
        return user;
    }

    public boolean userBorrowBook(Long userId, Book book, ArrayList<String> dates){

        MongoCollection coll = db.getCollection("korisnici");

        BasicDBObject push = new BasicDBObject();
        // '$push' command adds an object to an Array
        push.put("$push",
                new BasicDBObject("zaduzenja",
                        new BasicDBObject("bookName", book.getGlavniStvarniNaslov()).
                                append("bookAuthor", book.getPrviPodatakOdg()).
                                append("invNumber", book.getInvBroj()).
                                append("bookTaken", dates.get(0)).
                                append("bookReturn", dates.get(1))
                ));

        BasicDBObject query = new BasicDBObject();
        query.put("userID", userId);
        coll.updateOne(query, push);

        return true;
    }

    public boolean updateReturnDate(Long userId, String invNumber, ArrayList<String> dates){

        MongoCollection coll = db.getCollection("korisnici");

        // Querying the database for book that was borrowed by the specific user
        BasicDBObject query = new BasicDBObject();
        query.put("userID", userId);
        query.put("zaduzenja.invNumber", invNumber);

        // Choosing the data to be updated
        BasicDBObject update = new BasicDBObject();
        update.put("zaduzenja.$.bookTaken", dates.get(0));
        update.put("zaduzenja.$.bookReturn", dates.get(1));

        // '$set' command changes the desired value
        BasicDBObject command = new BasicDBObject();
        command.put("$set", update);

        coll.updateOne(query, command);

        return true;
    }

    public boolean userReturnBook(ArrayList<BasicDBObject> queries){
        MongoCollection coll = db.getCollection("korisnici");
        coll.updateOne(queries.get(0), queries.get(1));
        return true;
    }

    public boolean createUserHistory(Long userId){
        MongoCollection coll = db.getCollection("istorijaZaduzenja");
        Document user = new Document("userID", userId);
        coll.insertOne(user);
        return true;
    }

    public boolean updateUserHistory(ArrayList<BasicDBObject> queries){
        MongoCollection coll = db.getCollection("istorijaZaduzenja");
        coll.updateOne(queries.get(0), queries.get(1));
        return true;
    }

    public boolean updateBookData(ArrayList<BasicDBObject> queries){
        MongoCollection coll = db.getCollection("knjige");
        coll.updateOne(queries.get(0), queries.get(1));
        return true;
    }

    // TODO Is this needed? Can it be extracted from the user?
    public Object findBooksBorrowedByUser(BasicDBObject query, String collection){
        MongoCollection coll = db.getCollection(collection);
        return coll.find(query).first();
    }




    // Code for working with the Librarian

    public boolean createLibrarian(Document librarian){
        MongoCollection coll = db.getCollection("bibliotekar");
        coll.insertOne(librarian);
        return true;
    }

    // TODO does it find all librarians or just one?
    public ArrayList<Object> findAllLibrarians(String name){
        MongoCollection coll = db.getCollection("bibliotekar");
        ArrayList<Object> librarians = new ArrayList<>();
        BasicDBObject query = new BasicDBObject("userID", name);
        Object obj = coll.find(query).first();
        return librarians;
    }

    public Object findLibrarian(String name){
        MongoCollection coll = db.getCollection("bibliotekar");
        BasicDBObject query = new BasicDBObject("name", name);
        Object librarianObject = coll.find(query).first();
        return librarianObject;
    }

    public boolean loginLibrarian(String username, String password){

        MongoCollection coll = db.getCollection("bibliotekar");

        List<BasicDBObject> objList = new ArrayList<>();
        BasicDBObject loginQuery = new BasicDBObject();

        objList.add(new BasicDBObject("username", username));
        objList.add(new BasicDBObject("password", password));
        loginQuery.put("$and", objList);

        Object loginObj = coll.find(loginQuery).first();

        return loginObj != null;
    }

}