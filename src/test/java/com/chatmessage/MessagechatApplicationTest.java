package com.chatmessage;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


//@RunWith(SpringRunner.class)
//@EnableMongoRepositories
@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
class MessagechatApplicationTest {

    @Test
    public void main() {
        MessagechatApplication.main(new String[] {});
    }
}