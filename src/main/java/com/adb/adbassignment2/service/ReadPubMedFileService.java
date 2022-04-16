package com.adb.adbassignment2.service;

import com.adb.adbassignment2.domain.DateCompleted;
import com.adb.adbassignment2.domain.PubMed;
import com.adb.adbassignment2.domain.PubMedArticle;
import com.adb.adbassignment2.domain.PubmedArticleSet;
import com.adb.adbassignment2.repository.PubMedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * @Author Vaibhav Kale
 * This Service class parse the pubmed xml.gz file using JAXB library.
 */
@Service
public class ReadPubMedFileService {

    @Autowired
    PubMedRepository pubMedRepository;

    public void loadFile() throws Exception {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PubmedArticleSet.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            //Unmarshall the XML file in java Object
            PubmedArticleSet pubmedArticleSet = (PubmedArticleSet) jaxbUnmarshaller.unmarshal(new GZIPInputStream(new FileInputStream("/Users/vaibhavkale/Documents/KSU studies MS /Spring 2022/ADB/assignments/assignment2/pubmed22n0085.xml.gz")));


            List<PubMed> pubMeds = new ArrayList<>();

            //Go through all the citations and insert in pubmed table
            pubmedArticleSet.getPubmedArticle().forEach(pubMedArticle -> {
                try{
                    PubMed pubMed = PubMed.builder()
                            .pmid(pubMedArticle.getMedCitation().getPmid())
                            .article_title( pubMedArticle.getMedCitation().getArticle().getArticleTitle().length() > 255 ?
                                    pubMedArticle.getMedCitation().getArticle().getArticleTitle().substring(0,254) : pubMedArticle.getMedCitation().getArticle().getArticleTitle())
                            .first_author( pubMedArticle.getMedCitation().getArticle().getAuthorList() != null ?
                                    (getAuthor(pubMedArticle) !=null && getAuthor(pubMedArticle).length() > 63 ? getAuthor(pubMedArticle).substring(0,62) : getAuthor(pubMedArticle)) : null)
                            .publisher( pubMedArticle.getMedCitation().getArticle().getJournal().getPublisher().length() > 127 ?
                                    pubMedArticle.getMedCitation().getArticle().getJournal().getPublisher().substring(0,126) : pubMedArticle.getMedCitation().getArticle().getJournal().getPublisher())
                            .publishedDate(getDate(pubMedArticle.getMedCitation().getDateCompleted()))
                            .uploader("Vaibhav Kale")
                            .build();
                    pubMeds.add(pubMed);
                } catch(Exception e){
                    System.out.println("Error in : " + pubMedArticle.getMedCitation().getPmid());
                    e.printStackTrace();
                }

            });

            pubMedRepository.bulkInsert(pubMeds);

            System.out.println("Loading complete");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private String getAuthor(PubMedArticle pubMedArticle) {
        return (pubMedArticle.getMedCitation().getArticle().getAuthorList().getAuthorList().get(0).getForeName() != null) ?
                (pubMedArticle.getMedCitation().getArticle().getAuthorList().getAuthorList().get(0).getForeName().concat(" ")
                .concat(pubMedArticle.getMedCitation().getArticle().getAuthorList().getAuthorList().get(0).getLastName())) :
                (pubMedArticle.getMedCitation().getArticle().getAuthorList().getAuthorList().get(0).getCollectiveName() != null ?
                        pubMedArticle.getMedCitation().getArticle().getAuthorList().getAuthorList().get(0).getCollectiveName() : null);
    }


    public Date getDate(DateCompleted pubDate) {
        if(pubDate == null || pubDate.getYear() == null || pubDate.getMonth() == null || pubDate.getDay() == null ){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, pubDate.getYear());
        cal.set(Calendar.MONTH, pubDate.getMonth() -1);
        cal.set(Calendar.DATE, pubDate.getDay());
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return new java.sql.Date(cal.getTimeInMillis());
    }


}
