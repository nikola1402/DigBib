package model;

import com.mongodb.gridfs.GridFSDBFile;
import controller.DBBroker;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DigitalBook {

    private DBBroker dbb;

    private String invNumber, title, creator, subject, description,
            publisher, format, identifier, source, language, collection;
    private Long date;
    private File file;

    public DigitalBook() {
    }

    public String getInvNumber() {
        return invNumber;
    }

    public void setInvNumber(String invNumber) {
        this.invNumber = invNumber;
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

    public boolean createNew(String invNumber, String title, String creator, String subject, String description, String publisher, Long date,
                             String format, String identifier, String source, String language, String collection, File file) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("inventoryNumber", invNumber);
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

    public List<GridFSDBFile> findByTitle(String title){

        String type = "title";

        // List containing all the words from the title
        ArrayList<String> wordsInTitle = new ArrayList();

        // Splitting the title into one-word Strings
        String[] arr = title.split(" ");
        wordsInTitle.addAll(Arrays.asList(arr));
        Integer wordCount = wordsInTitle.size();

        return dbb.findDigitalBooksByTitleAuthorPublisher(type, wordsInTitle, wordCount);
    }

    public List<GridFSDBFile> findByAuthor(String author){

        String type = "author";

        // List containing all the words from the title
        ArrayList<String> wordsInAuthor = new ArrayList();

        // Splitting the title into one-word Strings
        String[] arr = title.split(" ");
        wordsInAuthor.addAll(Arrays.asList(arr));
        Integer wordCount = wordsInAuthor.size();

        return dbb.findDigitalBooksByTitleAuthorPublisher(type, wordsInAuthor, wordCount);
    }

    public List<GridFSDBFile> findByPublisher(String publisher){

        String type = "publisher";

        // List containing all the words from the title
        ArrayList<String> wordsInPublisher = new ArrayList();

        // Splitting the title into one-word Strings
        String[] arr = title.split(" ");
        wordsInPublisher.addAll(Arrays.asList(arr));
        Integer wordCount = wordsInPublisher.size();

        return dbb.findDigitalBooksByTitleAuthorPublisher(type, wordsInPublisher, wordCount);
    }

    public List<GridFSDBFile> findByYear(Long year){
        return dbb.findDigitalBooksByYear(year);
    }
}