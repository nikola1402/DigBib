package model;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import controller.DBBroker;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Arrays;

public class Book {

    private DBBroker dbb;

    private String _id, recordIdentifier, isbnNum, cataloguingLang, textLang, publicationCountry, illustrationCode, properTitle, firstStmntResp,
            publicationPlace, publisherName, materialDesignation, dimensions, bibliographyNote, personalNameSubject,
            partNameSubject, topicalNameSubject, inventoryNum, topicalSubdiv, geographicalSubdiv, udcNum, personalNameResp, partNameResp,
            recordCreator, inventoryDate, inventoryCreator, procurementMethod, price, binding, dateBorrowed, dateReturned, summary;
    private Long publicationDate, relatorCode;

    private void Book(){
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getRecordIdentifier(){
        return recordIdentifier;
    }

    public void setRecordIdentifier(String recordIdentifier){
        this.recordIdentifier = recordIdentifier;
    }

    public void setCataloguingLang(String cataloguingLang) {
        this.cataloguingLang = cataloguingLang;
    }

    public String getTextLang() {
        return textLang;
    }

    public void setTextLang(String textLang) {
        this.textLang = textLang;
    }

    public void setPublicationCountry(String publicationCountry) {
        this.publicationCountry = publicationCountry;
    }

    public void setIllustrationCode(String illustrationCode) {
        this.illustrationCode = illustrationCode;
    }

    public String getProperTitle() {
        return properTitle;
    }

    public void setProperTitle(String properTitle) {
        this.properTitle = properTitle;
    }

    public String getFirstStmntResp() {
        return firstStmntResp;
    }

    public void setFirstStmntResp(String firstStmntResp) {
        this.firstStmntResp = firstStmntResp;
    }

    public void setPublicationPlace(String publicationPlace) {
        this.publicationPlace = publicationPlace;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public void setMaterialDesignation(String materialDesignation) {
        this.materialDesignation = materialDesignation;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public void setBibliographyNote(String bibliographyNote) {
        this.bibliographyNote = bibliographyNote;
    }

    public void setPersonalNameSubject(String personalNameSubject) {
        this.personalNameSubject = personalNameSubject;
    }

    public void setPartNameSubject(String partNameSubject) {
        this.partNameSubject = partNameSubject;
    }

    public void setTopicalNameSubject(String topicalNameSubject) {
        this.topicalNameSubject = topicalNameSubject;
    }

    public String getTopicalSubdiv() {
        return topicalSubdiv;
    }

    public void setTopicalSubdiv(String topicalSubdiv) {
        this.topicalSubdiv = topicalSubdiv;
    }

    public void setGeographicalSubdiv(String geographicalSubdiv) {
        this.geographicalSubdiv = geographicalSubdiv;
    }

    public void setUdcNum(String udcNum) {
        this.udcNum = udcNum;
    }

    public void setPersonalNameResp(String personalNameResp) {
        this.personalNameResp = personalNameResp;
    }

    public void setPartNameResp(String partNameResp) {
        this.partNameResp = partNameResp;
    }

    public void setRecordCreator(String recordCreator) {
        this.recordCreator = recordCreator;
    }

    public String getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(String dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public String getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(String dateReturned) {
        this.dateReturned = dateReturned;
    }

    public String getIsbnNum() {
        return isbnNum;
    }

    public void setIsbnNum(String isbnNum) {
        this.isbnNum = isbnNum;
    }

    public Long getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Long publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public void setRelatorCode(Long relatorCode) {
        this.relatorCode = relatorCode;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    // Checking if the object is valid
    public boolean isValid(){
        return properTitle != null && firstStmntResp != null && publicationDate != null && publisherName != null && inventoryNum != null;
    }

    public boolean processBook(ArrayList<String> bookData){

        // Creating documents with data inputted by the librarian
        // TODO check 001 subfield. It shouldn't exist, but data should be there
            Document recordIdentifierDoc = new Document("_id", "001")
                    .append("001", bookData.get(0));
            Document isbnDoc = new Document("_id", "010")
                    .append("a", bookData.get(1));
            Document generalProcessingDataDoc = new Document("_id", "100")
                    .append("c", Long.valueOf(bookData.get(2)))
                    .append("h", bookData.get(3));
            Document itemLanguageDoc = new Document("_id", "101")
                    .append("a", bookData.get(4));
            Document publicationCountryDoc = new Document("_id", "102")
                    .append("a", bookData.get(5));
            Document codedDataFieldDoc = new Document("_id", "105")
                    .append("a", bookData.get(6));
            Document titleStmntRespDoc = new Document("_id", "200")
                    .append("a", bookData.get(7))
                    .append("f", bookData.get(8));
            Document publicationDoc = new Document("_id", "210")
                    .append("a", bookData.get(9))
                    .append("c", bookData.get(10))
                    .append("d", Long.valueOf(bookData.get(11)));
            Document physicalDescriptionDoc = new Document("_id", "215")
                    .append("a", bookData.get(12))
                    .append("d", bookData.get(13));
            Document internalBibliographiesDoc = new Document("_id", "320")
                    .append("a", bookData.get(14));
            Document summaryDoc = new Document("_id", "330")
                    .append("a", bookData.get(15));
            Document personalNameSubjectDoc = new Document("_id", "600")
                    .append("a", bookData.get(16))
                    .append("b", bookData.get(17));
            Document topicalNameSubjectDoc = new Document("_id", "606")
                    .append("a", bookData.get(18))
                    .append("x", bookData.get(19))
                    .append("y", bookData.get(20));
            Document udcNumDoc = new Document("_id", "675")
                    .append("a", bookData.get(21));
            Document personalNameRespDoc = new Document("_id", "700")
                    .append("4", Long.valueOf(bookData.get(22)))
                    .append("a", bookData.get(23))
                    .append("b", bookData.get(24));
            Document localUseDoc = new Document("_id", "992")
                    .append("b", bookData.get(25));

        // Combining previously created documents inside one new document
        Document insertBook = new Document()
                .append("recordIdentifier", recordIdentifierDoc)
                .append("isbnNum", isbnDoc)
                .append("generalProcessingData", generalProcessingDataDoc)
                .append("itemLanguage", itemLanguageDoc)
                .append("publicationCountry", publicationCountryDoc)
                .append("codedDataField", codedDataFieldDoc)
                .append("stvarniNaslovOdgovornost", titleStmntRespDoc)
                .append("publication", publicationDoc)
                .append("physicalDescription", physicalDescriptionDoc)
                .append("internalBibliographies", internalBibliographiesDoc)
                .append("summary", summaryDoc)
                .append("personalNameSubject", personalNameSubjectDoc)
                .append("topicalNameSubject", topicalNameSubjectDoc)
                .append("udcNum", udcNumDoc)
                .append("personalNameResp", personalNameRespDoc)
                .append("localUse", localUseDoc);

        // Query that inserts previously created document combined with the
        // information about inventory status of the processed book
        Document query = new Document().
                append("inventory", false).
                append("processed", insertBook);

        return dbb.createBook(query);
    }

    public boolean addInventoryData(ArrayList<String> inventoryBookData, String id){

        // New object with inventory data for the already processed book
        // provided by the librarian.
        BasicDBObject inv = new BasicDBObject().
                append("inventoryBook", inventoryBookData.get(0)).
                append("inventoryNum", inventoryBookData.get(1)).
                append("inventoryDate", inventoryBookData.get(2)).
                append("inventoryCreator", inventoryBookData.get(3)).
                append("procurementMethod", inventoryBookData.get(5)).
                append("binding", inventoryBookData.get(6)).
                append("price", inventoryBookData.get(7));

        // Object that combines previosly created object and
        // updated information about inventory status of the processed book
        BasicDBObject update = new BasicDBObject().
                append("inventory", true).
                append("inventoryData", inv);

        // Creating new object with provided id number
        // It is used for locating document that needs to be updated
        BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));

        // Object that contains update instruction ($set) and new data to insert
        BasicDBObject command = new BasicDBObject();
        command.put("$set", update);

        ArrayList<BasicDBObject> queries = new ArrayList<>();
        queries.add(query);
        queries.add(command);

        return dbb.updateBookData(queries);
    }

    public ArrayList<Book> findBooksToProcess(){

        // List that will be returned by the method
        ArrayList<Book> books = new ArrayList<>();

        BasicDBObject query = new BasicDBObject("inventory", false);
        ArrayList<BasicDBObject> booksFound = dbb.findBooksToProcess(query);

        // Getting objects from the list, converting them to json Strings and parsing to JSONObject
        // Filling the 'Book' object with parsed JSON objects
        Book book = new Book();
        Gson gson = new Gson();
        JSONParser parser = new JSONParser();
        try{
            for (BasicDBObject temp : booksFound) {
                String bookJsonString = gson.toJson(temp);
                JSONObject bookJsonObject = (JSONObject) parser.parse(bookJsonString);
                JSONObject processed = (JSONObject) bookJsonObject.get("processed");
                JSONObject recordIdentifier = (JSONObject) processed.get("recordIdentifier");
                JSONObject isbnNum = (JSONObject) processed.get("isbnNum");
                JSONObject generalProcessingData = (JSONObject) processed.get("generalProcessingData");
                JSONObject textLang = (JSONObject) processed.get("textLang");
                JSONObject publicationCountry = (JSONObject) processed.get("publicationCountry");
                JSONObject codedDataField = (JSONObject) processed.get("codedDataField");
                JSONObject titleStmntResp = (JSONObject) processed.get("titleStmntResp");
                JSONObject publication = (JSONObject) processed.get("publication");
                JSONObject physicalDescription = (JSONObject) processed.get("physicalDescription");
                JSONObject internalBibliographies = (JSONObject) processed.get("internalBibliographies");
                JSONObject summary = (JSONObject) processed.get("summary");
                JSONObject personalNameSubject = (JSONObject) processed.get("personalNameSubject");
                JSONObject topicalNameSubject = (JSONObject) processed.get("topicalNameSubject");
                JSONObject udcNum = (JSONObject) processed.get("udcNum");
                JSONObject personalNameResp = (JSONObject) processed.get("personalNameResp");
                JSONObject localUse = (JSONObject) processed.get("localUse");


                //TODO Isprobati novi metod koji bi koristio BasicDBObject i time izbacio sve ove JSONObjecte
                // Da li je sve ovo neophodno? Planirano je da se koristi za unosenje izmena u obradu, a te funkcionalnosti mozda nece biti
                book.setId(((ObjectId) temp.get("_id")).toHexString());
                book.setIsbnNum((String) isbnNum.get("a"));
                book.setCataloguingLang((String) generalProcessingData.get("h"));
                book.setTextLang((String) textLang.get("a"));
                book.setPublicationCountry((String) publicationCountry.get("a"));
                book.setIllustrationCode((String) codedDataField.get("a"));
                book.setProperTitle((String) titleStmntResp.get("a"));
                book.setFirstStmntResp((String) titleStmntResp.get("f"));
                book.setPublicationPlace((String) publication.get("a"));
                book.setPublisherName((String) publication.get("c"));
                book.setPublicationDate((Long) publication.get("d"));
                book.setMaterialDesignation((String) physicalDescription.get("a"));
                book.setDimensions((String) physicalDescription.get("d"));
                book.setBibliographyNote((String) internalBibliographies.get("a"));
                book.setSummary((String) summary.get("a"));
                book.setPersonalNameSubject((String) personalNameSubject.get("a"));
                book.setPartNameSubject((String) personalNameSubject.get("b"));
                book.setTopicalNameSubject((String) topicalNameSubject.get("a"));
                book.setTopicalSubdiv((String) topicalNameSubject.get("x"));
                book.setGeographicalSubdiv((String) topicalNameSubject.get("y"));
                book.setUdcNum((String) udcNum.get("a"));
                book.setRelatorCode((Long) personalNameResp.get("4"));
                book.setPersonalNameResp((String) personalNameResp.get("a"));
                book.setPartNameResp((String) personalNameResp.get("b"));
                book.setRecordCreator((String) localUse.get("b"));

                books.add(book);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return books;
    }

    // Finding a book that user wants to borrowBook
    // Change method 'findBookByInventoryNum' to return ArrayList
    public Book borrowBook(String invNum){

        BasicDBObject query = new BasicDBObject("inventoryData.inventoryNum", invNum).
                append("inventory", true);

        Book book = new Book();
        Object foundBook = dbb.findBookByInventoryNum(query);

        // Parsing JSON file, filling the 'Book' object
        Gson gson = new Gson();
        JSONParser parser = new JSONParser();
        try {
            String bookJsonString = gson.toJson(foundBook);
            JSONObject bookJsonObject = (JSONObject) parser.parse(bookJsonString);
            JSONObject processed = (JSONObject) bookJsonObject.get("processed");
            JSONObject inventoryData = (JSONObject) bookJsonObject.get("inventoryData");
            JSONObject titleStmntResp = (JSONObject) processed.get("titleStmntResp");
            JSONObject publication = (JSONObject) processed.get("publication");

            book.setProperTitle((String) titleStmntResp.get("a"));
            book.setFirstStmntResp((String) titleStmntResp.get("f"));
            book.setPublicationDate((Long) publication.get("d"));
            book.setPublisherName((String) publication.get("c"));
            book.setInventoryNum((String) inventoryData.get("inventoryNum"));
        } catch(Exception e){
            e.printStackTrace();
        }
        return book;
    }

    // TODO use just array instead of ArrayList
    // Type can be 'title', 'author' or 'publisher'
    public ArrayList<Book> findBooksByTitleAuthorPublisher(String query, String type){

        // Separating the query into multiple Strings and putting them into Array
        ArrayList<String> queryToArray = new ArrayList<>();
        String[] arr = query.split(" ");
        queryToArray.addAll(Arrays.asList(arr));
        Integer wordCount = queryToArray.size();

        ArrayList<Object> booksFound = dbb.findBooksByTitleAuthorPublisher(type, queryToArray, wordCount);
        return parseBooks(booksFound);
    }

    public ArrayList<Book> findBooksByYear(Long year){
        BasicDBObject query = new BasicDBObject();
        query.put("processed.publication.d", year);
        query.put("inventory", true);

        ArrayList<Object> booksFound = dbb.findBooksByParameter(query);
        return parseBooks(booksFound);
    }

    public ArrayList<Book> findBookByInventoryNum(String invNum){

        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book();
        BasicDBObject query = new BasicDBObject();
        query.put("inventoryData.inventoryNum", invNum);
        query.put("inventory", true);

        Gson gson = new Gson();
        JSONParser parser = new JSONParser();
        Object foundBook = dbb.findBookByInventoryNum(query);
        try {
            String bookJsonString = gson.toJson(foundBook);
            JSONObject bookJsonObject = (JSONObject) parser.parse(bookJsonString);
            JSONObject processed = (JSONObject) bookJsonObject.get("processed");
            JSONObject inventoryData = (JSONObject) bookJsonObject.get("inventoryData");
            JSONObject titleStmntResp = (JSONObject) processed.get("titleStmntResp");
            JSONObject publication = (JSONObject) processed.get("publication");
            JSONObject topicalNameSubject = (JSONObject) processed.get("topicalNameSubject");
            JSONObject summary = (JSONObject) processed.get("summary");
            JSONObject isbnNum = (JSONObject) processed.get("isbnNum");
            JSONObject physicalDescription = (JSONObject) processed.get("physicalDescription");
            JSONObject generalProcessingData = (JSONObject) processed.get("generalProcessingData");


            book.setProperTitle((String) titleStmntResp.get("a"));
            book.setFirstStmntResp((String) titleStmntResp.get("f"));
            book.setPublicationDate((Long) publication.get("d"));
            book.setPublisherName((String) publication.get("c"));
            book.setInventoryNum((String) inventoryData.get("inventoryNum"));
            book.setTopicalSubdiv((String) topicalNameSubject.get("x"));
            book.setSummary((String) summary.get("a"));
            book.setIsbnNum((String) isbnNum.get("a"));
            book.setTextLang((String) generalProcessingData.get("h"));

            books.add(book);
        } catch(Exception e){
            e.printStackTrace();
        }
        return books;
    }

    public ArrayList<Book> findReservations(Long userId) {
        BasicDBObject query = new BasicDBObject("reservations", userId);
        ArrayList<Object> booksFound = dbb.findBooksByParameter(query);
        return parseBooks(booksFound);
    }

    private ArrayList<Book> parseBooks(ArrayList<Object> booksToParse){

        ArrayList<Book> books = new ArrayList<>();

        // Getting the object from the list, converting it to json Strings and parsing to JSONObject
        // Filling the 'Book' object with parsed JSON objects
        Book book = new Book();
        Gson gson = new Gson();
        JSONParser parser = new JSONParser();
        try {
            for (Object temp : booksToParse) {
                String bookJsonString = gson.toJson(temp);
                JSONObject bookJsonObject = (JSONObject) parser.parse(bookJsonString);
                JSONObject processed = (JSONObject) bookJsonObject.get("processed");
                JSONObject inventoryData = (JSONObject) bookJsonObject.get("inventoryData");
                JSONObject titleStmntResp = (JSONObject) processed.get("titleStmntResp");
                JSONObject publication = (JSONObject) processed.get("publication");

                book.setProperTitle((String) titleStmntResp.get("a"));
                book.setFirstStmntResp((String) titleStmntResp.get("f"));
                book.setPublicationDate((Long) publication.get("d"));
                book.setPublisherName((String) publication.get("c"));
                book.setInventoryNum((String) inventoryData.get("inventoryNum"));

                books.add(book);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return books;
    }

    private ArrayList<String> split(String toSplit){
        return new ArrayList<>();
    }
}