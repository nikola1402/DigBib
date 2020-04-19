/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author nikol
 */
public class Knjiga {
   
    String _id, pismo, stanjeSloga, vrstaSloga, bibliografskiNivo, isbn, jezikKatalogizacije, jezikTeksta, zemljaIzdavanja, 
            kodoviZaIlustracije, glavniStvarniNaslov, prviPodatakOdg, mestoIzdavanja, izdavac, gradjaObim, dimenzije, napomena,
            prviElementImeOdr, deoImenaOdr, prviElementTemaOdr, invBroj, tematskaPododr, geografskaPododr, udk, udkGrupa, prviElementImeOdg, deoImenaOdg, 
            obradio, datumInventarisanja, inventator, nacinNabavke, cena, povez, datumZaduzenja, datumRazduzenja, sadrzaj, vrstaGradje, fizickiOpis;
    Long godinaIzdavanjaObrada, godinaIzdavanja, vrstaAutorstva, hijerarhijskiNivo;
    ArrayList<Long> rezervacije;
    
    private void Knjiga (String glavniStvarniNaslov, String prviPodatakOdg, Long godinaIzdavanja,
            String izdavac, String invBroj, String datumZaduzenja, String datumRazduzenja){
        this.glavniStvarniNaslov = glavniStvarniNaslov;
        this.prviPodatakOdg = prviPodatakOdg;
        this.godinaIzdavanja = godinaIzdavanja;
        this.izdavac = izdavac;
        this.invBroj = invBroj;
        this.datumZaduzenja = datumZaduzenja;
        this.datumRazduzenja = datumRazduzenja;
    }
    
    private void Knjiga(){
        
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

    
    // Proveravanje da li je objekat prazan
    public boolean isValid(){
        return glavniStvarniNaslov != null && prviPodatakOdg != null && godinaIzdavanja != null && izdavac != null && invBroj != null;
    }
    
}
