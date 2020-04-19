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

    // Database connection. Needs to be opened only once
    private MongoClient client = new MongoClient();
    private MongoDatabase db = client.getDatabase("test");
    private DB gfsDb = client.getDB("test");

    public boolean createBook(Document query){
        MongoCollection<Document> coll = db.getCollection("book");
        coll.insertOne(query);
        return true;
    }

    public ArrayList<BasicDBObject> findBooksToProcess(BasicDBObject query){

        // This type of connection is because only through BasicDBObject we can get the correct _id
        MongoCollection<BasicDBObject> coll = db.getCollection("book", BasicDBObject.class);

        ArrayList<BasicDBObject> booksFound = new ArrayList<>();

        try (MongoCursor<BasicDBObject> cur = coll.find(query).iterator()) {
            while(cur.hasNext()){
                booksFound.add(cur.next());
            }
        }
        return booksFound;
    }


    public Object findBookByInventoryNum(BasicDBObject query){
        MongoCollection<Document> coll = db.getCollection("book");
        return coll.find(query).first();
    }

    public ArrayList<Object> findBooksByTitleAuthorPublisher(String type, ArrayList<String> param, Integer wordCount){

        MongoCollection<Document> coll = db.getCollection("book");

        String attribute = "";
        switch (type) {
            case "title":
                attribute = "processed.titleStmntResp.a";
                break;
            case "author":
                attribute = "processed.titleStmntResp.f";
                break;
            case "publisher":
                attribute = "processed.publication.c";
                break;
        }

        BasicDBObject query = prepareQuery(attribute, param, wordCount);
        query.put("inventory", true);
        return iterateDB(coll, query);
    }

    public ArrayList<Object> findBooksByParameter(BasicDBObject query){
        MongoCollection<Document> coll = db.getCollection("book");
        return iterateDB(coll, query);
    }

    public boolean createDigitalBook(Map<String, Object> map) throws IOException {

        // GridFS works only with DBObjects, it doesn't accept MongoDatabase
        // Connecting to GridFS collection 'objects'
        GridFS objects = new GridFS(gfsDb, "digitalObject");

        GridFSInputFile pdf = objects.createFile((File) map.get("file"));

        BasicDBObject meta = new BasicDBObject("inventoryNum", map.get("inventoryNum"))
                .append("title", map.get("title"))
                .append("creator", map.get("creator"))
                .append("subject", map.get("subject"))
                .append("description", map.get("description"))
                .append("publisher", map.get("publisher"))
                .append("date", map.get("date"))
                .append("format", map.get("format"))
                .append("identifier", map.get("identifier"))
                .append("source", map.get("source"))
                .append("language", map.get("language"))
                .append("collection", map.get("collection"));

        pdf.setMetaData(meta);
        pdf.save();
        return true;
    }

    public GridFSDBFile findDigitalBookByInventoryNum(String inventoryNum){
        GridFS gf = new GridFS(gfsDb, "digitalObject");
        BasicDBObject query = new BasicDBObject();
        query.put("metadata.inventoryNum", inventoryNum);
        return gf.findOne(query);
    }

    public List<GridFSDBFile> findDigitalBooksByTitleAuthorPublisher(String type, ArrayList<String> param, Integer wordCount){

        GridFS gf = new GridFS(gfsDb, "digitalObject");

        String attribute = "";
        switch (type) {
            case "title":
                attribute = "metadata.title";
                break;
            case "author":
                attribute = "metadata.creator";
                break;
            case "publisher":
                attribute = "metadata.publisher";
                break;
        }

        BasicDBObject query = prepareQuery(attribute, param, wordCount);
        return gf.find(query);
    }

    public List<GridFSDBFile> findDigitalBooksByYear(Long year){
        GridFS gf = new GridFS(gfsDb, "digitalObject");
        BasicDBObject query = new BasicDBObject("metadata.date", year);
        return gf.find(query);
    }

    public boolean createUser(Document user){
        MongoCollection<Document> coll = db.getCollection("user");
        coll.insertOne(user);
        return true;
    }

    public ArrayList<Object> findUserByName(ArrayList<String> param, Integer wordCount){
        MongoCollection<Document> coll = db.getCollection("user");
        // Based on the number of the words in User's name, different queries are prepared
        // $regex compares Strings
        // $options i neglects the differences between capital and small letteres in Strings
        BasicDBObject query;
        switch (wordCount) {
            case 1:
                query = new BasicDBObject("name", new BasicDBObject("$regex", param.get(0)).
                        append("$options", "i"));
                break;
            case 2:
            {
                query = new BasicDBObject("name", new BasicDBObject("$regex", param.get(0)).
                        append("$regex", param.get(1)).
                        append("$options", "i"));
                break;
            }
            default:
            {
                query = new BasicDBObject("name", new BasicDBObject("$regex", param.get(0)).
                        append("$regex", param.get(1)).
                        append("$regex", param.get(2)).
                        append("$options", "i"));
                break;
            }
        }

        return iterateDB(coll, query);
    }

    public Object findUserById(String type, Long id){

        String attribute = "";
        if(type.equals("userId"))
            attribute = "userId";
        else if(type.equals("documentId"))
            attribute = "documentId";

        MongoCollection<Document> coll = db.getCollection("user");
        BasicDBObject query = new BasicDBObject(attribute, id);
        return coll.find(query).first();
    }

    public boolean userBorrowBook(Long userId, Book book, ArrayList<String> dates){

        MongoCollection<Document> coll = db.getCollection("user");

        BasicDBObject push = new BasicDBObject();
        // '$push' command adds an object to an Array
        push.put("$push",
                new BasicDBObject("borrowedBooks",
                        new BasicDBObject("bookName", book.getProperTitle()).
                                append("bookAuthor", book.getFirstStmntResp()).
                                append("inventoryNum", book.getInventoryNum()).
                                append("bookTaken", dates.get(0)).
                                append("bookReturn", dates.get(1))
                ));

        BasicDBObject query = new BasicDBObject();
        query.put("userId", userId);
        coll.updateOne(query, push);
        return true;
    }

    public boolean updateReturnDate(Long userId, String invNumber, ArrayList<String> dates){

        MongoCollection<Document> coll = db.getCollection("user");

        // Querying the database for book that was borrowed by the specific user
        BasicDBObject query = new BasicDBObject();
        query.put("userId", userId);
        query.put("borrowedBooks.inventoryNum", invNumber);

        // Choosing the data to be updated
        BasicDBObject update = new BasicDBObject();
        update.put("borrowedBooks.$.bookTaken", dates.get(0));
        update.put("borrowedBooks.$.bookReturn", dates.get(1));

        // '$set' command changes the desired value
        BasicDBObject command = new BasicDBObject();
        command.put("$set", update);

        coll.updateOne(query, command);
        return true;
    }

    public boolean userReturnBook(ArrayList<BasicDBObject> queries){
        MongoCollection<Document> coll = db.getCollection("user");
        coll.updateOne(queries.get(0), queries.get(1));
        return true;
    }

    public boolean createUserHistory(Long userId){
        MongoCollection<Document> coll = db.getCollection("borrowedHistory");
        coll.insertOne(new Document("userId", userId));
        return true;
    }

    public boolean updateUserHistory(ArrayList<BasicDBObject> queries){
        MongoCollection<Document> coll = db.getCollection("borrowedHistory");
        coll.updateOne(queries.get(0), queries.get(1));
        return true;
    }

    public boolean updateBookData(ArrayList<BasicDBObject> queries){
        MongoCollection<Document> coll = db.getCollection("book");
        coll.updateOne(queries.get(0), queries.get(1));
        return true;
    }

    // TODO Is this needed? Can it be extracted from the user?
    public Object findBooksBorrowedByUser(BasicDBObject query, String collection){
        MongoCollection<Document> coll = db.getCollection(collection);
        return coll.find(query).first();
    }

    public boolean createLibrarian(Document librarian){
        MongoCollection<Document> coll = db.getCollection("librarian");
        coll.insertOne(librarian);
        return true;
    }

    // TODO does it find all librarians or just one?
    public ArrayList<Object> findAllLibrarians(String name){
        MongoCollection<Document> coll = db.getCollection("librarian");
        ArrayList<Object> librarians = new ArrayList<>();
        BasicDBObject query = new BasicDBObject("userId", name);
        Object obj = coll.find(query).first();
        return librarians;
    }

    public Object findLibrarian(String name){
        MongoCollection<Document> coll = db.getCollection("librarian");
        BasicDBObject query = new BasicDBObject("name", name);
        return coll.find(query).first();
    }

    public boolean loginLibrarian(String username, String password){

        MongoCollection<Document> coll = db.getCollection("librarian");

        List<BasicDBObject> credentials = new ArrayList<>();
        BasicDBObject query = new BasicDBObject();

        credentials.add(new BasicDBObject("username", username));
        credentials.add(new BasicDBObject("password", password));
        query.put("$and", credentials);

        Object librarian = coll.find(query).first();
        return librarian != null;
    }

    private ArrayList<Object> iterateDB(MongoCollection<Document> coll, BasicDBObject query){
        ArrayList<Object> booksFound = new ArrayList<>();
        try (MongoCursor<Document> cur = coll.find(query).iterator()) {
            while(cur.hasNext()){
                booksFound.add(cur.next());
            }
        }
        return booksFound;
    }

    private BasicDBObject prepareQuery(String attribute, ArrayList<String> param, Integer wordCount){

        // Based on the number of the words in the title, different queries are prepared
        // $regex compares Strings
        // $options i neglects the differences between capital and small letteres in Strings
        BasicDBObject query;
        switch (wordCount) {
            case 1:
                query = new BasicDBObject(attribute, new BasicDBObject("$regex", param.get(0))
                        .append("$options", "i"));
                break;
            case 2:
            {
                query = new BasicDBObject(attribute, new BasicDBObject("$regex", param.get(0))
                        .append("$regex", param.get(1))
                        .append("$options", "i"));
                break;
            }
            default:
            {
                query = new BasicDBObject(attribute, new BasicDBObject("$regex", param.get(0))
                        .append("$regex", param.get(1))
                        .append("$regex", param.get(2))
                        .append("$options", "i"));
                break;
            }
        }
        return query;
    }
    }