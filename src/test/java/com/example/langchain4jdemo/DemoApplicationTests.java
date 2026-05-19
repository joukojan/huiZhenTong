package com.example.langchain4jdemo;

import com.example.langchain4jdemo.assistant.Assistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private OpenAiChatModel chatModel;

    @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    void testGPTDemo() {
        /*OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();//langchain4j自带的模型，有使用配额限制*/

        //String ans = chatmodel.chat("你好，你是谁");
        String ans = qwenChatModel.chat("你好，你是谁");
        System.out.println(ans);
    }

    @Autowired
    private Assistant assistant;

    @Test
    void AIServiceTest() {
        //Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        String ans = assistant.chat("你好，你是谁");
        System.out.println(ans);
    }

    @Test
    void testUserMessage() {//UserMessage每次用户发送都会携带，而SystemMessage只会开始时携带
        String ans1 = assistant.chat("我是屠贾浩");
        System.out.println(ans1);
        String ans2 = assistant.chat("你知道我是谁吗");
        System.out.println(ans2);
    }

}
