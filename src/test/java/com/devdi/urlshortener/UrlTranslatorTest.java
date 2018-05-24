/*
 * Test the functionality of the URL translator, 
 * Also works as a documentation of the functionality 
 * 
 */
package com.devdi.urlshortener;

import com.devdi.urlshortener.UrlTranslator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Diana
 */
public class UrlTranslatorTest {
    
    private static final String DOMAIN = "localhost:8080";
    
    public UrlTranslatorTest() {
    }

    @Test
    public void putAndTranslateDomain() {
        System.out.println("putAndTranslateDomain");
        String originalURL = DOMAIN;

        UrlTranslator translator = new UrlTranslator(DOMAIN);
        translator.putUrl(originalURL);
        String translatedUrl = translator.getTranslatedUrl(originalURL);
        
        assertTrue(translatedUrl.startsWith(DOMAIN));
    }
    
    @Test
    public void putAndTranslateUrlTest() {
        System.out.println("putAndTranslateUrlTest");
        String originalURL = "facebook.com/just/an/examleUrl";

        UrlTranslator translator = new UrlTranslator(DOMAIN);
        translator.putUrl(originalURL);
        String translatedUrl = translator.getTranslatedUrl(originalURL);
        
        assertTrue(translatedUrl.startsWith(DOMAIN));
    }

    @Test
    public void testDontPutURL() {
        System.out.println("testDontPutURL");
        String originalURL = "new.com/unknown";
        String expResult   = "new.com/unknown";
        UrlTranslator translator = new UrlTranslator(DOMAIN);
        //this time we don't put the URL it in the URLtranslator
        String result = translator.getTranslatedUrl(originalURL);
        assertEquals(expResult, result);
    }
    
    @Test
    public void longUrlConfirmsToRegexpTest() {
        System.out.println("longUrlConfirmsToRegexpTest");
        
        UrlTranslator translator = new UrlTranslator(DOMAIN);
        
        String longURL1  = "dianadevelopment.com/somelongfilename.java";
        String expectedRegexp = DOMAIN+"/.*";
        
        translator.putUrl(longURL1);
        String translatedUrl = translator.getTranslatedUrl(longURL1);
        
        assertTrue(translatedUrl.matches(expectedRegexp));
    }
    
    @Test
    public void putMultipleLongUrlsTest() {
        System.out.println("putMultipleLongUrlsTest");
        UrlTranslator translator = new UrlTranslator(DOMAIN);
        
        String longURL1  = "facebook.com/somelongpath";
        String expectedTranslation1 = DOMAIN+"/1";
        String longURL2  = "youtube.com/somelongpath";
        String expectedTranslation2 = DOMAIN+"/2";
        String longURL3  = "twitter.com/somelongpath";
        String expectedTranslation3 = DOMAIN+"/3";
        String longURL4  = "google.com/somelongpath";
        String expectedTranslation4 = DOMAIN+"/4";
        
        translator.putUrl(longURL1,longURL2,longURL3,longURL4);
        String translatedUrl1 = translator.getTranslatedUrl(longURL1);
        String translatedUrl2 = translator.getTranslatedUrl(longURL2);
        String translatedUrl3 = translator.getTranslatedUrl(longURL3);
        String translatedUrl4 = translator.getTranslatedUrl(longURL4);
        
        assertEquals(expectedTranslation1, translatedUrl1);
        assertEquals(expectedTranslation2, translatedUrl2);
        assertEquals(expectedTranslation3, translatedUrl3);
        assertEquals(expectedTranslation4, translatedUrl4);
    }

    @Test
    public void putMultipleLongDuplicateUrlsTest() {
        System.out.println("putMultipleLongDuplicateUrlsTest");
        //in case of conflicts, the additional puts should just be ignored
        UrlTranslator translator = new UrlTranslator(DOMAIN);
        
        String longURL1  = "linkedin.com";
        String expectedTranslation1 = DOMAIN+"/1";
        String longURL2  = "linkedin.com";
        String expectedTranslation2 = DOMAIN+"/1";
        String longURL3  = "linkedin.com";
        String expectedTranslation3 = DOMAIN+"/1";
        
        translator.putUrl(longURL1,longURL2,longURL3);
        String translatedUrl1 = translator.getTranslatedUrl(longURL1);
        String translatedUrl2 = translator.getTranslatedUrl(longURL2);
        String translatedUrl3 = translator.getTranslatedUrl(longURL3);
        
        assertEquals(expectedTranslation1, translatedUrl1);
        assertEquals(expectedTranslation2, translatedUrl2);
        assertEquals(expectedTranslation3, translatedUrl3);
    }
    
    @Test
    public void putTranslateAndReverseTranslateUrls() {
        System.out.println("putTranslateAndReverseTranslateUrls");
        //We expect to get the same result back after a reverse translation 
        //performed on a translated url
        
        UrlTranslator t = new UrlTranslator(DOMAIN);
        
        String longURL1  = "linkedin.com";
        String longURL2  = "google.com/ape/bana";
        String longURL3  = "twitter.com/index.html";
        
        t.putUrl(longURL1,longURL2,longURL3);
        String translatedUrl1 = t.getTranslatedUrl(longURL1);
        String translatedUrl2 = t.getTranslatedUrl(longURL2);
        String translatedUrl3 = t.getTranslatedUrl(longURL3);
        
        String reverseTrans1 = t.getReverseTranslatedUrl(translatedUrl1);
        String reverseTrans2 = t.getReverseTranslatedUrl(translatedUrl2);
        String reverseTrans3 = t.getReverseTranslatedUrl(translatedUrl3);
        
        assertEquals(longURL1, reverseTrans1);
        assertEquals(longURL2, reverseTrans2);
        assertEquals(longURL3, reverseTrans3);
    }

    @Test
    public void checkNumberOfUses() {
        System.out.println("checkNumberOfUses");
        
        UrlTranslator t = new UrlTranslator(DOMAIN);
        
        String longURL  = "gmail.com";
        
        t.putUrl(longURL);
        String translatedUrl = t.getTranslatedUrl(longURL);
        Integer nrOfPolls = t.getReverseTranslationCount(translatedUrl);
        
        assertEquals(new Integer(0), nrOfPolls);
        
        String reverseTrans1 = t.getReverseTranslatedUrl(translatedUrl);
        nrOfPolls = t.getReverseTranslationCount(translatedUrl);
        assertEquals(new Integer(1), nrOfPolls);

        String reverseTrans2 = t.getReverseTranslatedUrl(translatedUrl);
        nrOfPolls = t.getReverseTranslationCount(translatedUrl);
        assertEquals(new Integer(2), nrOfPolls);

    }
    
}
