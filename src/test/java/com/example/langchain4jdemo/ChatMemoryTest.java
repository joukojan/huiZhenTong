package com.example.langchain4jdemo;

import com.example.langchain4jdemo.assistant.Assistant;
import com.example.langchain4jdemo.assistant.SeparateAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatMemoryTest {

    @Autowired
    private OpenAiChatModel chatModel;

    @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    void chatMemoryTest1() {

        MessageWindowChatMemory ChatMemory = MessageWindowChatMemory.withMaxMessages(10);

        Assistant assistant1 = AiServices //与AiServices.create效果相同
                .builder(Assistant.class)
                .chatLanguageModel(qwenChatModel)
                .chatMemory(ChatMemory) //这样就会拼接历史消息，就具有聊天记忆了
                .build();
        String ans1 = assistant1.chat("我是屠贾浩");
        System.out.println(ans1);
        String ans2 = assistant1.chat("我是谁");
        System.out.println(ans2);
    }

    @Autowired
    private Assistant memoryAssistant;

    @Test
    void chatMemoryTest2() {
        String ans1 = memoryAssistant.chat("我是屠贾浩");
        System.out.println(ans1);
        String ans2 = memoryAssistant.chat("我是谁");
        System.out.println(ans2);
    }

    @Autowired
    private SeparateAssistant separateAssistant;

    @Test
    void chatMemoryTest3() {
        String ans1 = separateAssistant.chat(1,"我是屠贾浩");
        System.out.println(ans1);
        String ans2 = separateAssistant.chat(1,"我是谁");
        System.out.println(ans2);

        String ans3 = separateAssistant.chat(2,"我是谁");
        System.out.println(ans3);
    }

}
