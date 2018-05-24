package com.example.hellospringboot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class URLtranslatorController {
    private static final String DOMAIN = "localhost:8080";
    private final UrlTranslator translator = new UrlTranslator(DOMAIN);
    
    @RequestMapping("/service/shorten")
    public String translate(@RequestParam(value="url", defaultValue="longurl.com/longPath/to/be/translated") String url) {
        System.out.println("Url to translate: " + url);
        translator.putUrl(url);
        System.out.println("Translated to: " + translator.getTranslatedUrl(url));
        return translator.getTranslatedUrl(url);
    }

    @RequestMapping("/service/count")
    public Integer count(@RequestParam(value="url", defaultValue=DOMAIN+"/1") String url) {
        System.out.println("short-Url to count: " + url);
        return translator.getReverseTranslationCount(url);
    }
    
    @RequestMapping(value = "/{key}", method=RequestMethod.GET)
    public RedirectView redirectUrl(@PathVariable String key, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Received shortened url to redirect: " + key);
        String translatedUrl = translator.
            getReverseTranslatedUrl(String.format("%s/%s",DOMAIN,key));
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://" + translatedUrl);
        return redirectView;
    }
    
}