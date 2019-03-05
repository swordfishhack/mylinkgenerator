package com.mylink.mylinkgenerator;

import com.mylink.mylinkgenerator.repository.MyLinkRepo;
import com.mylink.mylinkgenerator.service.MyLinkService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import ai.grakn.redismock.RedisServer;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MylinkgeneratorApplicationTests {
    private static RedisServer server;
    private static MyLinkRepo myLinkRepo;
    @Test
    public void contextLoads() {
    }

   @Before
   public void setupServer() throws IOException {
       server = RedisServer.newRedisServer(6790);
       server.start();

    }


    @After
    public void shutdownServer() throws IOException {
        server.stop();
    }



    @Test
    public void testAutoIncrements() {

        myLinkRepo = new MyLinkRepo(new Jedis(server.getHost(), server.getBindPort())
                , "id", "url:");

        for (long expectedId = 0L; expectedId < 50L; ++expectedId) {
            long actualId = myLinkRepo.autoIncreamentID();
            assertEquals(expectedId, actualId);

        }

    }

    /*@Test
    public void checkMaskUrlGeneration() throws Exception {
        myLinkRepo = new MyLinkRepo(new Jedis(server.getHost(), server.getBindPort())
                , "id", "url:");
        MyLinkService myLinkService = new MyLinkService(myLinkRepo);
        //myLinkRepo.autoIncreamentID();
        String originalURL = "http://www.google.co.in";
        String localURL = "http://localhost:8080/tinyurl";
        String hashURL = myLinkService.tinyURL(localURL,originalURL);
        String returnedOrginalURL = myLinkService.getOriginalURLFromID(hashURL);
        assertEquals(returnedOrginalURL,originalURL);

    }*/

}
