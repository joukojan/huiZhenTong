package com.example.langchain4jdemo;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RAGTest {

    @Test
    void testReadDocument() {
        //FileSystemDocumentLoader读取指定目录下的知识库文档，默认使用TextDocumentParser对文档进行解析
        //TextDocumentParser只能解析纯文本格式的文件（如TXT、HTML、MD等）
        Document document = FileSystemDocumentLoader.loadDocument(
                "D:\\BaiduNetdiskDownload\\xiaozhi\\knowledge\\测试.txt");
        System.out.println(document.text());
        /***其他加载文档的方式
        //加载单个文档
        Document document2 = FileSystemDocumentLoader.loadDocument("...", new TextDocumentParser());
        //从一个目录中加载所有文档
        List<Document> document3 = FileSystemDocumentLoader.loadDocuments("...", new TextDocumentParser());
        //从一个目录中加载所有的.txt文档
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
        List<Document> document4 = FileSystemDocumentLoader.loadDocuments("...", pathMatcher, new TextDocumentParser());
        //从一个目录及其子目录中加载所有文档
        List<Document> document5 = FileSystemDocumentLoader.loadDocumentsRecursively("...", new TextDocumentParser());
        ***/
    }

    @Test
    void testReadPDF() {
        //尝试使用能够读取PDF的文档解析器解析对文档进行解析
        Document document = FileSystemDocumentLoader.loadDocument(
                "D:\\BaiduNetdiskDownload\\xiaozhi\\knowledge\\医院信息.pdf",
                new ApachePdfBoxDocumentParser());
        System.out.println(document.metadata());
        System.out.println(document.text());
    }

    @Test
    void testReadAndStore() {
        //完成文档的加载与解析
        Document document = FileSystemDocumentLoader.loadDocument(
                "D:\\BaiduNetdiskDownload\\xiaozhi\\knowledge\\人工智能.md");
        //存储进向量数据库，此处基于内存
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //默认使用递归分割器分割文档，而后使用默认向量模型 向量化每个文本片段，将原始文本和向量存储到向量数据库中
        EmbeddingStoreIngestor.ingest(document, embeddingStore);
        System.out.println(embeddingStore);
    }

    @Test
    void testCustomRAG() {//使用自定义的文档分割器
        //完成文档的加载与解析
        Document document = FileSystemDocumentLoader.loadDocument(
                "D:\\BaiduNetdiskDownload\\xiaozhi\\knowledge\\人工智能.md");
        //存储进向量数据库，此处基于内存
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(
                //第三个参数决定单位，此处是token分词器：按token计算
                300, 30, new HuggingFaceTokenizer());
        EmbeddingStoreIngestor
                .builder()
                .embeddingStore(embeddingStore)
                .documentSplitter(documentSplitter)
                .build()
                .ingest(document);
        System.out.println(embeddingStore);
    }

}
