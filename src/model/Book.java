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
   
    String _id, pismo, stanjeSloga, vrstaSloga, bibliografskiNivo, isbn, jezikKatalogizacije, jezikTeksta, zemljaIzdavanja, 
            kodoviZaIlustracije, glavniStvarniNaslov, prviPodatakOdg, mestoIzdavanja, izdavac, gradjaObim, dimenzije, napomena,
            prviElementImeOdr, deoImenaOdr, prviElementTemaOdr, invBroj, tematskaPododr, geografskaPododr, udk, udkGrupa, prviElementImeOdg, deoImenaOdg, 
            obradio, datumInventarisanja, inventator, nacinNabavke, cena, povez, datumZaduzenja, datumRazduzenja, sadrzaj, vrstaGradje, fizickiOpis;
    Long godinaIzdavanjaObrada, godinaIzdavanja, vrstaAutorstva, hijerarhijskiNivo;
    ArrayList<Long> rezervacije;
    
    private void Book(){
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public void setPismo(String pismo) {
        this.pismo = pismo;
    }

    public void setStanjeSloga(String stanjeSloga) {
        this.stanjeSloga = stanjeSloga;
    }

    public void setVrstaSloga(String vrstaSloga) {
        this.vrstaSloga = vrstaSloga;
    }

    public void setBibliografskiNivo(String bibliografskiNivo) {
        this.bibliografskiNivo = bibliografskiNivo;
    }

    public void setHijerarhijskiNivo(Long hijerarhijskiNivo) {
        this.hijerarhijskiNivo = hijerarhijskiNivo;
    }

    public void setJezikKatalogizacije(String jezikKatalogizacije) {
        this.jezikKatalogizacije = jezikKatalogizacije;
    }

    public String getJezikTeksta() {
        return jezikTeksta;
    }

    public void setJezikTeksta(String jezikTeksta) {
        this.jezikTeksta = jezikTeksta;
    }

    public void setZemljaIzdavanja(String zemljaIzdavanja) {
        this.zemljaIzdavanja = zemljaIzdavanja;
    }

    public void setKodoviZaIlustracije(String kodoviZaIlustracije) {
        this.kodoviZaIlustracije = kodoviZaIlustracije;
    }

    public String getGlavniStvarniNaslov() {
        return glavniStvarniNaslov;
    }

    public void setGlavniStvarniNaslov(String glavniStvarniNaslov) {
        this.glavniStvarniNaslov = glavniStvarniNaslov;
    }

    public String getPrviPodatakOdg() {
        return prviPodatakOdg;
    }

    public void setPrviPodatakOdg(String prviPodatakOdg) {
        this.prviPodatakOdg = prviPodatakOdg;
    }

    public void setMestoIzdavanja(String mestoIzdavanja) {
        this.mestoIzdavanja = mestoIzdavanja;
    }

    public String getIzdavac() {
        return izdavac;
    }

    public void setIzdavac(String izdavac) {
        this.izdavac = izdavac;
    }

    public void setGradjaObim(String gradjaObim) {
        this.gradjaObim = gradjaObim;
    }

    public void setDimenzije(String dimenzije) {
        this.dimenzije = dimenzije;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public void setPrviElementImeOdr(String prviElementImeOdr) {
        this.prviElementImeOdr = prviElementImeOdr;
    }

    public void setDeoImenaOdr(String deoImenaOdr) {
        this.deoImenaOdr = deoImenaOdr;
    }

    public void setPrviElementTemaOdr(String prviElementTemaOdr) {
        this.prviElementTemaOdr = prviElementTemaOdr;
    }

    public String getTematskaPododr() {
        return tematskaPododr;
    }

    public void setTematskaPododr(String tematskaPododr) {
        this.tematskaPododr = tematskaPododr;
    }

    public void setGeografskaPododr(String geografskaPododr) {
        this.geografskaPododr = geografskaPododr;
    }

    public void setUdk(String udk) {
        this.udk = udk;
    }

    public String getUdkGrupa() {
        return udkGrupa;
    }

    public void setUdkGrupa(String udkGrupa) {
        this.udkGrupa = udkGrupa;
    }

    public void setPrviElementImeOdg(String prviElementImeOdg) {
        this.prviElementImeOdg = prviElementImeOdg;
    }

    public void setDeoImenaOdg(String deoImenaOdg) {
        this.deoImenaOdg = deoImenaOdg;
    }

    public void setObradio(String obradio) {
        this.obradio = obradio;
    }

    public String getDatumZaduzenja() {
        return datumZaduzenja;
    }

    public void setDatumZaduzenja(String datumZaduzenja) {
        this.datumZaduzenja = datumZaduzenja;
    }

    public String getDatumRazduzenja() {
        return datumRazduzenja;
    }

    public void setDatumRazduzenja(String datumRazduzenja) {
        this.datumRazduzenja = datumRazduzenja;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setGodinaIzdavanjaObrada(Long godinaIzdavanjaObrada) {
        this.godinaIzdavanjaObrada = godinaIzdavanjaObrada;
    }

    public Long getGodinaIzdavanja() {
        return godinaIzdavanja;
    }

    public void setGodinaIzdavanja(Long godinaIzdavanja) {
        this.godinaIzdavanja = godinaIzdavanja;
    }

    public String getInvBroj() {
        return invBroj;
    }

    public void setInvBroj(String invBroj) {
        this.invBroj = invBroj;
    }

    public void setVrstaAutorstva(Long vrstaAutorstva) {
        this.vrstaAutorstva = vrstaAutorstva;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public String getFizickiOpis() {
        return fizickiOpis;
    }

    public void setFizickiOpis(String fizickiOpis) {
        this.fizickiOpis = fizickiOpis;
    }

    // Checking if the object is valid
    public boolean isValid(){
        return glavniStvarniNaslov != null && prviPodatakOdg != null && godinaIzdavanja != null && izdavac != null && invBroj != null;
    }

    public boolean processBook(ArrayList<String> bookData){

        // Creating documents with data inputted by the librarian
        Document identifikatorSloga = new Document("_id", "001").
                append("7", bookData.get(0)).
                append("a", bookData.get(1)).
                append("b", bookData.get(2)).
                append("c", bookData.get(3)).
                append("d", Long.valueOf(bookData.get(4)));
        Document isbn = new Document("_id", "010").
                append("a", bookData.get(5));
        Document opstiPodaciZaObradu = new Document("_id", "100").
                append("c", Long.valueOf(bookData.get(6))).
                append("h", bookData.get(7));
        Document jezikPublikacije = new Document("_id", "101").
                append("a", bookData.get(8));
        Document zemljaIzdavanja = new Document("_id", "102").
                append("a", bookData.get(9));
        Document poljeKodiranihPodataka = new Document("_id", "105").
                append("a", bookData.get(10));
        Document stvarniNaslovOdgovornost = new Document("_id", "200").
                append("a", bookData.get(11)).
                append("f", bookData.get(12));
        Document izdavanje = new Document("_id", "210").
                append("a", bookData.get(13)).
                append("c", bookData.get(14)).
                append("d", Long.valueOf(bookData.get(15)));
        Document materijalniOpis = new Document("_id", "215").
                append("a", bookData.get(16)).
                append("d", bookData.get(17));
        Document napomenaOBibliografijama = new Document("_id", "320").
                append("a", bookData.get(18));
        Document fizickiOpis = new Document("_id", "324").
                append("a", bookData.get(19));
        Document kratakSadrzaj = new Document("_id", "330")
                .append("a", bookData.get(20));
        Document licnoImePredmetnaOdrednica = new Document("_id", "600").
                append("a", bookData.get(21)).
                append("b", bookData.get(22));
        Document tematskaPredmetnaOdrednica = new Document("_id", "606").
                append("a", bookData.get(23)).
                append("x", bookData.get(24)).
                append("y", bookData.get(25));
        Document udk = new Document("_id", "675").
                append("a", bookData.get(26)).
                append("b", bookData.get(27));
        Document licnoImePrimarnaOdgovornost = new Document("_id", "700").
                append("4", Long.valueOf(bookData.get(28))).
                append("a", bookData.get(29)).
                append("b", bookData.get(30));
        Document lokalnePotrebe = new Document("_id", "992").
                append("b", bookData.get(31));

        // Combining previously created documents inside one new document
        Document insertBook = new Document().
                append("identifikatorSloga", identifikatorSloga).
                append("isbn", isbn).
                append("opstiPodaciZaObradu", opstiPodaciZaObradu).
                append("jezikPublikacije", jezikPublikacije).
                append("zemljaIzdavanja", zemljaIzdavanja).
                append("poljeKodiranihPodataka", poljeKodiranihPodataka).
                append("stvarniNaslovOdgovornost", stvarniNaslovOdgovornost).
                append("izdavanje", izdavanje).
                append("materijalniOpis", materijalniOpis).
                append("napomenaOBibliografijama", napomenaOBibliografijama).
                append("fizickiOpis", fizickiOpis).
                append("kratakSadrzaj", kratakSadrzaj).
                append("licnoImePredmetnaOdrednica", licnoImePredmetnaOdrednica).
                append("tematskaPredmetnaOdrednica", tematskaPredmetnaOdrednica).
                append("udk", udk).
                append("licnoImePrimarnaOdgovornost", licnoImePrimarnaOdgovornost).
                append("lokalnePotrebe", lokalnePotrebe);

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
                append("inventarnaKnjiga", inventoryBookData.get(0)).
                append("inventarniBroj", inventoryBookData.get(1)).
                append("datumInventarisanja", inventoryBookData.get(2)).
                append("inventator", inventoryBookData.get(3)).
                append("udkGrupa", inventoryBookData.get(4)).
                append("nacinNabavke", inventoryBookData.get(5)).
                append("povez", inventoryBookData.get(6)).
                append("cena", inventoryBookData.get(7));

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
        try{
            for (BasicDBObject obj : booksFound) {
                Book book = new Book();
                Gson gson = new Gson();
                JSONParser parser = new JSONParser();

                String strObj = gson.toJson(obj);

                JSONObject jObj = (JSONObject) parser.parse(strObj);

                JSONObject proc = (JSONObject) jObj.get("processed");
                JSONObject idSloga = (JSONObject) proc.get("identifikatorSloga");
                JSONObject isbn = (JSONObject) proc.get("isbn");
                JSONObject opstiPodaci = (JSONObject) proc.get("opstiPodaciZaObradu");
                JSONObject jezik = (JSONObject) proc.get("jezikPublikacije");
                JSONObject zemlja = (JSONObject) proc.get("zemljaIzdavanja");
                JSONObject kodPodaci = (JSONObject) proc.get("poljeKodiranihPodataka");
                JSONObject naslovOdgovornost = (JSONObject) proc.get("stvarniNaslovOdgovornost");
                JSONObject izdavanje = (JSONObject) proc.get("izdavanje");
                JSONObject materijalniOpis = (JSONObject) proc.get("materijalniOpis");
                JSONObject bibliografije = (JSONObject) proc.get("napomenaOBibliografijama");
                JSONObject licnoImeOdr = (JSONObject) proc.get("licnoImePredmetnaOdrednica");
                JSONObject tematskaOdr = (JSONObject) proc.get("tematskaPredmetnaOdrednica");
                JSONObject udk = (JSONObject) proc.get("udk");
                JSONObject licnoImePrim = (JSONObject) proc.get("licnoImePrimarnaOdgovornost");
                JSONObject lokalnePotrebe = (JSONObject) proc.get("lokalnePotrebe");


                //TODO Isprobati novi metod koji bi koristio BasicDBObject i time izbacio sve ove JSONObjecte
                // Da li je sve ovo neophodno? Planirano je da se koristi za unosenje izmena u obradu, a te funkcionalnosti mozda nece biti
                book.setId(((ObjectId) obj.get("_id")).toHexString());
                book.setPismo((String) idSloga.get("7"));
                book.setStanjeSloga((String) idSloga.get("a"));
                book.setVrstaSloga((String) idSloga.get("b"));
                book.setBibliografskiNivo((String) idSloga.get("c"));
                book.setHijerarhijskiNivo((Long) idSloga.get("d"));
                book.setIsbn((String) isbn.get("a"));
                book.setGodinaIzdavanjaObrada((Long) opstiPodaci.get("c"));
                book.setJezikKatalogizacije((String) opstiPodaci.get("h"));
                book.setJezikTeksta((String) jezik.get("a"));
                book.setZemljaIzdavanja((String) zemlja.get("a"));
                book.setKodoviZaIlustracije((String) kodPodaci.get("a"));
                book.setGlavniStvarniNaslov((String) naslovOdgovornost.get("a"));
                book.setPrviPodatakOdg((String) naslovOdgovornost.get("f"));
                book.setMestoIzdavanja((String) izdavanje.get("a"));
                book.setIzdavac((String) izdavanje.get("c"));
                book.setGodinaIzdavanja((Long) izdavanje.get("d"));
                book.setGradjaObim((String) materijalniOpis.get("a"));
                book.setDimenzije((String) materijalniOpis.get("d"));
                book.setNapomena((String) bibliografije.get("a"));
                book.setPrviElementImeOdr((String) licnoImeOdr.get("a"));
                book.setDeoImenaOdr((String) licnoImeOdr.get("b"));
                book.setPrviElementTemaOdr((String) tematskaOdr.get("a"));
                book.setTematskaPododr((String) tematskaOdr.get("x"));
                book.setGeografskaPododr((String) tematskaOdr.get("y"));
                book.setUdk((String) udk.get("a"));
                book.setUdkGrupa((String) udk.get("b"));
                book.setVrstaAutorstva((Long) licnoImePrim.get("4"));
                book.setPrviElementImeOdg((String) licnoImePrim.get("a"));
                book.setDeoImenaOdg((String) licnoImePrim.get("b"));
                book.setObradio((String) lokalnePotrebe.get("b"));

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

        BasicDBObject query = new BasicDBObject("inventoryData.inventarniBroj", invNum).
                append("inventory", true);

        Book book = new Book();
        Object foundBook = dbb.findBookByInventoryNum(query);

        // Parsing JSON file, filling the 'Book' object
        try {
            Gson gson = new Gson();
            JSONParser parser = new JSONParser();
            String nameObj = gson.toJson(foundBook);
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
        query.put("processed.izdavanje.d", year);
        query.put("inventory", true);

        ArrayList<Object> booksFound = dbb.findBooksByParameter(query);
        return parseBooks(booksFound);
    }

    public ArrayList<Book> findBookByInventoryNum(String invNum){

        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book();
        BasicDBObject query = new BasicDBObject();
        query.put("inventoryData.inventarniBroj", invNum);
        query.put("inventory", true);

        try {
            Gson gson = new Gson();
            JSONParser parser = new JSONParser();
            Object foundBook = dbb.findBookByInventoryNum(query);
            String nameObj = gson.toJson(foundBook);
            JSONObject jObj = (JSONObject) parser.parse(nameObj);
            JSONObject proc = (JSONObject) jObj.get("processed");
            JSONObject inv = (JSONObject) jObj.get("inventoryData");
            JSONObject naslovOdgovornost = (JSONObject) proc.get("stvarniNaslovOdgovornost");
            JSONObject izdavanje = (JSONObject) proc.get("izdavanje");
            JSONObject tematskaPodOdr = (JSONObject) proc.get("tematskaPredmetnaOdrednica");
            JSONObject sadrzaj = (JSONObject) proc.get("kratakSadrzaj");
            JSONObject isbn = (JSONObject) proc.get("isbn");
            JSONObject fizickiOpis = (JSONObject) proc.get("fizickiOpis");
            JSONObject opstiPodaci = (JSONObject) proc.get("opstiPodaciZaObradu");


            book.setGlavniStvarniNaslov((String) naslovOdgovornost.get("a"));
            book.setPrviPodatakOdg((String) naslovOdgovornost.get("f"));
            book.setGodinaIzdavanja((Long) izdavanje.get("d"));
            book.setIzdavac((String) izdavanje.get("c"));
            book.setInvBroj((String) inv.get("inventarniBroj"));
            book.setTematskaPododr((String) tematskaPodOdr.get("x"));
            book.setSadrzaj((String) sadrzaj.get("a"));
            book.setIsbn((String) isbn.get("a"));
            book.setFizickiOpis((String) fizickiOpis.get("a"));
            book.setJezikTeksta((String) opstiPodaci.get("h"));

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
        try {
            for (Object b : booksToParse) {
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
    }

    private ArrayList<String> split(String toSplit){
        return new ArrayList<>();
    }

}
