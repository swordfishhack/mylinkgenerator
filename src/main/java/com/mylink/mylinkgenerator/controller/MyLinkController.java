package com.mylink.mylinkgenerator.controller;

import com.mylink.mylinkgenerator.common.TinyUrl;
import com.mylink.mylinkgenerator.service.MyLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class MyLinkController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyLinkController.class);

    private final MyLinkService myLinkService;

    public MyLinkController(MyLinkService myLinkService) {

        this.myLinkService = myLinkService;

    }



    @RequestMapping(value = "/mytinylink", method=RequestMethod.POST, consumes = {"application/json"})
/**
 * Method to shorten the URL
 */
    public String shortenUrl(@RequestBody @Valid final TinyUrl tinyUrlRequest, HttpServletRequest request) throws Exception {

        LOGGER.info("url recieved: " + tinyUrlRequest.getUrl());

        String localURL = request.getRequestURL().toString();
        //Pass in the current request URL and the Long url that has to be shortened
        String shortenedUrl = myLinkService.tinyURL(localURL, tinyUrlRequest.getUrl());
        LOGGER.info("tinyUrl: " + shortenedUrl);
        return shortenedUrl;

    }



    @RequestMapping(value = "/{id}", method=RequestMethod.GET)
/**
 * Method to extract the actual URL and redirect
 */
    public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {

        LOGGER.info("Received shortened url to redirect: " + id);
        //retriving long url by using the ID
        String redirectUrlString = myLinkService.getOriginalURLFromID(id);

        LOGGER.info("Original URL: " + redirectUrlString);
        //Redirecting the user to the original URL
        RedirectView redirectView = new RedirectView();

        redirectView.setUrl(redirectUrlString);

        return redirectView;

    }
    
    @RequestMapping(value = "/test")
	public ResponseEntity<Object> getUsers(){
		return new ResponseEntity<>("Test success", HttpStatus.OK);
	}
}
