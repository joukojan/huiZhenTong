package com.example.langchain4jdemo.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,//手动指定cahtmodel，为防止上下文中不同的chatmodel不知道选哪个
        chatModel = "qwenChatModel",
        //通过@Bean 注入该属性中，chatMemory和chatMemoryProvide有一个就行了
        chatMemoryProvider = "chatMemoryProvider",//用于隔离不同用户的聊天记忆
        tools = "calculatorTools"
)
public interface SeparateAssistant {//Assistant用于大模型整合工具、RAG等增强自身能力
    //@SystemMessage("你是我的好朋友，请用宁波话回答问题。今天是{{current_date}}")//current_date是系统提示词中内置变量
    @SystemMessage(fromResource = "my-prompt.txt")
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);//返回大模型的回答

    @UserMessage("你是我的好朋友，请用宁波话回答问题。{{message}}")
    String chat2(@MemoryId int memoryId, @V("message") String userMessage);

    @SystemMessage(fromResource = "my-prompt2.txt")
    String chat3(@MemoryId int memoryId,
                 @UserMessage String userMessage,
                 @V("username") String username,
                 @V("age") int age);
}
