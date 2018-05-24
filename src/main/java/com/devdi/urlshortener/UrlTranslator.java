package com.devdi.urlshortener;

import java.util.HashMap;
import java.util.Map;

public class UrlTranslator {

    private Map<String,String>  dictionary;
    private Map<String,String>  reverseDictionary;
    private Map<String,Integer> reverseTranslations;
    
    private final String DOMAIN;
    private int counter;
    
    public UrlTranslator(String domain) {
        dictionary          = new HashMap();
        reverseDictionary   = new HashMap();
        reverseTranslations = new HashMap();
        DOMAIN = domain;
        counter = 0;
    }

    public void putUrl(String url) {
        String existingTranslation = dictionary.get(url);
        System.out.println("putting");
        System.out.println("existing translation: " +existingTranslation);
        if(existingTranslation==null) { 
            String newTranslation = String.format("%s/%s",DOMAIN,++counter);
            dictionary.put(url, newTranslation);
            reverseDictionary.put(newTranslation, url);
        }else {
            System.out.println("translation already exists, does not put");
        }
    }
    
    public void putUrl(String... urls) {
        for(String url: urls) {
            putUrl(url);
        }
    }

    public String getTranslatedUrl(String url) {
        System.out.println("translateURL!");
        String translatedUrl = dictionary.get(url);
        if(translatedUrl==null) return url;
        return translatedUrl;
    }

    String getReverseTranslatedUrl(String translatedUrl) {
        System.out.println("reverse-translateURL!");
        String originalUrl = reverseDictionary.get(translatedUrl);
        
        if(originalUrl==null) {
            return translatedUrl;
        }
        else {
            Integer nrOfPolls = reverseTranslations.getOrDefault(translatedUrl, 0);
            nrOfPolls++;
            reverseTranslations.put(translatedUrl,nrOfPolls);
            return originalUrl;
        }
    }

    Integer getReverseTranslationCount(String translatedUrl) {
        System.out.println("getReverseTranslationCount!");
        
        Integer nrOfPolls = reverseTranslations.get(translatedUrl);
        if(null == nrOfPolls) return 0;
        return nrOfPolls;
    }
    
}