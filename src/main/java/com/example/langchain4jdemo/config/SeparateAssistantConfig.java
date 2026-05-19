package com.example.langchain4jdemo.config;

import com.example.langchain4jdemo.store.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeparateAssistantConfig {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        //返回值是函数，函数的输入是memoryId，输出是ChatMemory 实例
        //SeparateAssistant会调用chatMemoryProvider.apply(传入的memoryId)来区分不同用户
        return memoryId -> MessageWindowChatMemory
                .builder()
                .id(memoryId)
                .maxMessages(10)
                //默认是singleSlotChatMemoryStore，还有一种就是InMemoryChatMemoryStore
                //前者将聊天记忆放在list中，后者放在HashMap中
                //.chatMemoryStore(new InMemoryChatMemoryStore())
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }

}
