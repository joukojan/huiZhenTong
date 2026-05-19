package com.example.langchain4jdemo.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTools {

    @Tool(name = "加法运算", value = "将两个参数a和b相加并返回运算结果")
    double sum(
            @ToolMemoryId int memoryId,//chat中的@MemoryId会传递到此
            @P(value = "加数1", required = true) double a,
            @P(value = "加数2", required = true)double b){
        System.out.println("调用加法运算 memoryId" + memoryId);
        return a + b;
    }

}
