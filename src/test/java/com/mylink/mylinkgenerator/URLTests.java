package com.mylink.mylinkgenerator;

import com.mylink.mylinkgenerator.common.TinyUrl;
import com.mylink.mylinkgenerator.repository.MyLinkRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class URLTests {

    @MockBean
    private MyLinkRepo myLinkRepo;



    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void checkMaskUrlGeneration() throws Exception {

        ResponseEntity<String> test = restTemplate.postForEntity("/mytinylink",
                new TinyUrl("http://www.google.co.in"), String.class);

        assertEquals(test.getStatusCode(),HttpStatus.OK);

    }
}
