package com.mylink.mylinkgenerator.service;

import com.mylink.mylinkgenerator.common.IdCharMapper;
import com.mylink.mylinkgenerator.repository.MyLinkRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
@Service
public class MyLinkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyLinkService.class);

    private final MyLinkRepo myLinkRepo;

    @Autowired
    public MyLinkService(MyLinkRepo myLinkRepo) {
        this.myLinkRepo = myLinkRepo;
    }

    public String tinyURL(String localUrl, String originalUrl) {

        LOGGER.info("Original URL {}", originalUrl);

        Long id = myLinkRepo.autoIncreamentID();

        String uniqueID = IdCharMapper.idCharMapper.createKeyFromId(id);

        myLinkRepo.saveOriginalUrl("url:"+id, originalUrl);

        String baseString = formatMaskedUrl(localUrl);

        String shortenedURL = baseString + uniqueID;

        return shortenedURL;

    }



    public String getOriginalURLFromID(String uniqueID) throws Exception {

        Long dictionaryKey = IdCharMapper.idCharMapper.createIdFromKey(uniqueID);

        String longUrl = myLinkRepo.getOriginalUrl(dictionaryKey);

        LOGGER.info("Converting masked val back to {}", longUrl);

        return longUrl;

    }



    private String formatMaskedUrl(String localURL) {

        Pattern pattern = Pattern.compile("(https?://)([^:^/]*)(:\\d*)?(.*)?");
        Matcher matcher = pattern.matcher(localURL);
        matcher.find();

        StringBuilder sb = new StringBuilder();
        sb.append(matcher.group(1));
        sb.append(matcher.group(2));
        sb.append(matcher.group(3));

        sb.append('/');

        return sb.toString();

    }
}
