package model;

import com.mongodb.gridfs.GridFSDBFile;
import controller.DBBroker;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DigitalBook {

    private DBBroker dbb;

    private String inventoryNum, title, creator, subject, description,
            publisher, format, identifier, source, language, collection;
    private Long date;
    private File file;

    public DigitalBook() {
    }

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    // TODO get hashmap as an input parameter?
    public boolean createNew(String inventoryNum, String title, String creator, String subject, String description, String publisher, Long date,
                             String format, String identifier, String source, String language, String collection, File file) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("inventoryNum", inventoryNum);
        map.put("title", title);
        map.put("creator", creator);
        map.put("subject", subject);
        map.put("description", description);
        map.put("publisher", publisher);
        map.put("date", date);
        map.put("format", format);
        map.put("identifier", identifier);
        map.put("source", source);
        map.put("language", language);
        map.put("collection", collection);
        map.put("file", file);

        return dbb.createDigitalBook(map);
    }

    public GridFSDBFile findOneByInventoryNumber(String invNum){
        return dbb.findDigitalBookByInventoryNum(invNum);
    }

    public List<GridFSDBFile> findByTitleAuthorPublisher(String query, String type){

        // Splitting the title into one-word Strings
        ArrayList<String> queryToArray = new ArrayList<>();
        String[] arr = query.split(" ");
        queryToArray.addAll(Arrays.asList(arr));
        Integer wordCount = queryToArray.size();

        return dbb.findDigitalBooksByTitleAuthorPublisher(type, queryToArray, wordCount);
    }

    public List<GridFSDBFile> findByYear(Long year){
        return dbb.findDigitalBooksByYear(year);
    }
}