package com.example.langchain4jdemo.config;

import com.example.langchain4jdemo.store.MongoChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class xiaozhiAgentConfig {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore embeddingStore;

    @Bean
    public ChatMemoryProvider chatxiaozhi(){
        return memoryId -> MessageWindowChatMemory
                .builder()
                .id(memoryId)
                .maxMessages(20)
                //默认是singleSlotChatMemoryStore，还有一种就是InMemoryChatMemoryStore
                //前者将聊天记忆放在list中，后者放在HashMap中
                //.chatMemoryStore(new InMemoryChatMemoryStore())
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }

    @Bean
    ContentRetriever xiaozhiContentRetriever(){
        Document document1 = FileSystemDocumentLoader.loadDocument(
                "D:\\BaiduNetdiskDownload\\xiaozhi\\knowledge\\医院信息.md");
        Document document2 = FileSystemDocumentLoader.loadDocument(
                "D:\\BaiduNetdiskDownload\\xiaozhi\\knowledge\\科室信息.md");
        Document document3 = FileSystemDocumentLoader.loadDocument(
                "D:\\BaiduNetdiskDownload\\xiaozhi\\knowledge\\神经内科.md");
        List<Document> documents = Arrays.asList(document1, document2, document3);
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
        //从embeddingStore里检索和查询内容相关的信息
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }

    @Bean
    ContentRetriever xiaozhiContentRetrieverPinecone(){

        return EmbeddingStoreContentRetriever
                .builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .maxResults(1)
                .minScore(0.8)//最小得分阈值只有得分大于等于0.8的结果才会被返回
                .build();
    }
}
