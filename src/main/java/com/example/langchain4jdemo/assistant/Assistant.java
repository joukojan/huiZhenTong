package com.example.langchain4jdemo.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,//手动指定cahtmodel，为防止上下文中不同的chatmodel不知道选哪个
        chatModel = "qwenChatModel",
        chatMemory = "chatMemory"//通过config类注入该属性中
)
public interface Assistant {//Assistant用于大模型整合工具、RAG等增强自身能力
    //用户提示词要求chat方法里只有一个参数，我们不用SeparateAssistant测试
    @UserMessage("你是我的好朋友，请用宁波话回答问题，{{it}}")//未来userMessage会替换it
    String chat(String userMessage);//返回大模型的回答
}
