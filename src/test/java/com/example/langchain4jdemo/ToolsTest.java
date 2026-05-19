package com.example.langchain4jdemo;

import com.example.langchain4jdemo.assistant.SeparateAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToolsTest {

    @Autowired
    private SeparateAssistant separateAssistant;

    @Test
    void testV() {
        String ans1 = separateAssistant.chat2(1,"我是屠贾浩");
        System.out.println(ans1);
        String ans2 = separateAssistant.chat2(1,"我是谁");
        System.out.println(ans2);
    }

    @Test
    void testUserInfo() {
        String username = "屠贾浩";
        int age = 18;
        String ans1 = separateAssistant.chat3(1,"我是谁，我多大了", username, age);
        System.out.println(ans1);
    }

    @Test
    void testTools() {
        String ans1 = separateAssistant.chat(2,"1 + 2等于几");
        System.out.println(ans1);
    }

}
