package com.example.langchain4jdemo.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingStoreConfig {

    @Autowired
    private EmbeddingModel embeddingModel;//用于拿到向量维度配置给EmbeddingStore

    @Bean//bean会将这个方法的返回对象放入容器，类型是EmbeddingStore<TextSegment>，名字是embeddingStore
    //java泛型在运行时会被擦除，所以Autowired时不填泛型不会影响注入
    public EmbeddingStore<TextSegment> embeddingStore(){
        //创建向量存储
        PineconeEmbeddingStore embeddingStore = PineconeEmbeddingStore.builder()
                .apiKey(System.getenv("PINECONE-API-KEY"))
                .index("xiaozhi")
                .nameSpace("xiaozhiyiliao")
                .createIndex(PineconeServerlessIndexConfig.builder()//如果没有就创建，创建在哪创建
                        .cloud("AWS")
                        .region("us-east-1")
                        .dimension(embeddingModel.dimension())
                        .build())
                .build();
        return embeddingStore;
    }

}
