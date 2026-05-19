package com.example.langchain4jdemo.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        //chatModel = "qwenChatModel",
        streamingChatModel = "qwenStreamingChatModel",
        chatMemoryProvider = "chatxiaozhi",
        tools = "appointmentTools",
        contentRetriever = "xiaozhiContentRetrieverPinecone"
)
public interface xiaozhiAgent {

    @SystemMessage(fromResource = "xiaozhi-prompt.txt")
    Flux<String> chat(@MemoryId Long memoryId, @UserMessage String userMessage);

}
