package com.example.langchain4jdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_messages")
public class ChatMessages {
    @Id
    private ObjectId messageId;
    //因为mongoDB本身有一个自动生成的_id字段，所以我们再自己增加一个易于辨认的Id
    private String memoryId;
    private String content;//存储聊天记录的json字符串
}
