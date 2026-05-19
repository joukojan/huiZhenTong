package com.example.langchain4jdemo;

import com.example.langchain4jdemo.bean.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoCRUDTest {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testInsert() {
        //mongoTemplate.insert(new ChatMessages(1L, "聊天记录"));
        ChatMessages chatMessages = new ChatMessages();
        chatMessages.setContent("聊天记录列表");
        mongoTemplate.insert(chatMessages);
    }

    @Test
    void testFindById() {
        ChatMessages chatMessages = mongoTemplate.findById("69ea591f51729b4d29a56a90", ChatMessages.class);
        System.out.println(chatMessages);
    }

    @Test
    void testUpdate() {
        Criteria criteria = Criteria.where("_id").is("69ea591f51729b4d29a56a90");//没有就会新增
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", "新的聊天记录");

        mongoTemplate.upsert(query, update, ChatMessages.class);
    }

    @Test
    void testDelete() {
        Criteria criteria = Criteria.where("_id").is("69ea591f51729b4d29a56a90");
        Query query = new Query(criteria);

        mongoTemplate.remove(query, ChatMessages.class);
    }

}
