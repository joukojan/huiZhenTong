package com.example.langchain4jdemo;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class EmbeddingTest {

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore embeddingStore;

    @Test
    void testEmbeddingModel() {
        Response<Embedding> embed = embeddingModel.embed("你好");
        System.out.println("向量维度" + embed.content().vector().length);
        System.out.println("向量输出" + embed.toString());
    }

    @Test
    void testPineconeEmbedding() {
        //TextSegment除了包含文本，还有元数据。使向量数据库的信息更加完整
        TextSegment segment1 = TextSegment.from("我喜欢羽毛球");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);
        TextSegment segment2 = TextSegment.from("今天天气很好");
        Embedding embedding2 = embeddingModel.embed(segment2).content();
        embeddingStore.add(embedding2, segment2);
    }

    @Test
    void testEmbeddingSearch() {
        Embedding queryEmbedding = embeddingModel.embed("最喜欢的运动是什么").content();
        EmbeddingSearchRequest search = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(1)
                //.minScore(0.8)
                .build();
        EmbeddingSearchResult<TextSegment> result = embeddingStore.search(search);
        EmbeddingMatch<TextSegment> embeddingMatch = result.matches().get(0);
        System.out.println(embeddingMatch.score());
        System.out.println(embeddingMatch.embedded().text());
    }

    @Test
    void testUploadKnowledge() {
        Document document1 = FileSystemDocumentLoader.loadDocument(
                "D:\\BaiduNetdiskDownload\\xiaozhi\\knowledge\\医院信息.md");
        Document document2 = FileSystemDocumentLoader.loadDocument(
                "D:\\BaiduNetdiskDownload\\xiaozhi\\knowledge\\科室信息.md");
        Document document3 = FileSystemDocumentLoader.loadDocument(
                "D:\\BaiduNetdiskDownload\\xiaozhi\\knowledge\\神经内科.md");
        List<Document> documents = Arrays.asList(document1, document2, document3);
        EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build()
                .ingest(documents);
    }

}
