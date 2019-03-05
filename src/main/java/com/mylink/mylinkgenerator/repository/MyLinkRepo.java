package com.mylink.mylinkgenerator.repository;

import com.mylink.mylinkgenerator.common.TinyURLNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * Repository Class for accessing/storing the key/Value in the redis
 */
@Repository
public class MyLinkRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyLinkRepo.class);

    private final Jedis jedisClient;
    private final String idKey;
    private final String urlKey;

    public MyLinkRepo() {

        this.jedisClient = new Jedis();
        this.idKey = "id";
        this.urlKey = "url:";

    }

    public MyLinkRepo(Jedis jedis, String idKey, String urlKey) {
        this.jedisClient = jedis;
        this.idKey = idKey;
        this.urlKey = urlKey;
    }

    public Long autoIncreamentID() {
        Long id = jedisClient.incr(idKey);
        LOGGER.info("Next value of ID: {}", id-1);
        return id - 1;
    }

    public void saveOriginalUrl(String maskedVal, String orgUrl) {
        LOGGER.info("Masking URl: {} to {}", orgUrl, maskedVal);
        jedisClient.hset(urlKey, maskedVal, orgUrl);

    }

    public String getOriginalUrl(Long id) throws Exception {
        LOGGER.info("value at {}", id);
        String url = jedisClient.hget(urlKey, "url:"+id);
        LOGGER.info("Original Url {} at {}", url ,id);
        if (url == null) {
            throw new TinyURLNotFoundException("URL at key" + id + " does not exist");
        }
        return url;

    }
}
