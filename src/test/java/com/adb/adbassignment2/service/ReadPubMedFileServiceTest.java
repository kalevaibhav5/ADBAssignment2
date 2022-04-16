package com.adb.adbassignment2.service;

import com.adb.adbassignment2.domain.PubmedArticleSet;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.zip.GZIPInputStream;

import static org.junit.jupiter.api.Assertions.*;

class ReadPubMedFileServiceTest {

    @Test
    public void loadFile() throws Exception {

        String xmlString = "<PubmedArticleSet>" +
                "<PubmedArticle></PubmedArticle>" +
                "<PubmedArticle></PubmedArticle>" +
                "</PubmedArticleSet>";

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PubmedArticleSet.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            System.setProperty(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD, "all");
//            PubmedArticleSet que= (PubmedArticleSet) jaxbUnmarshaller.unmarshal(new GZIPInputStream(new FileInputStream("/Users/vaibhavkale/Documents/KSU studies MS /Spring 2022/ADB/assignments/assignment2/pubmed22n1114.xml.gz")));
            PubmedArticleSet que= (PubmedArticleSet) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));

            System.out.println("Unmarshalled :" + que.getPubmedArticle().size());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}