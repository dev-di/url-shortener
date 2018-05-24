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
        if(existingTranslation==null) { 
            String newTranslation = String.format("%s/%s",DOMAIN,++counter);
            dictionary.put(url, newTranslation);
            reverseDictionary.put(newTranslation, url);
        }
    }
    
    public void putUrl(String... urls) {
        for(String url: urls) {
            putUrl(url);
        }
    }

    public String getTranslatedUrl(String url) {
        String translatedUrl = dictionary.get(url);
        if(translatedUrl==null) return url;
        return translatedUrl;
    }

    String getReverseTranslatedUrl(String translatedUrl) {
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
        Integer nrOfPolls = reverseTranslations.get(translatedUrl);
        if(null == nrOfPolls) return 0;
        return nrOfPolls;
    }
    
}