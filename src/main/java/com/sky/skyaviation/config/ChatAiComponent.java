package com.sky.skyaviation.config;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatAiComponent {
    @Autowired VectorStore vectorStore;

    @Value("classpath:/doc/航空政策说明.md")
    private Resource resource;
    public void initialize() {
        try {
            storeEmbeddingsFromTextFile();
            System.out.println("文档嵌入初始化完成");
        } catch (Exception e) {
            System.err.println("文档嵌入初始化失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void storeEmbeddingsFromTextFile() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader("classpath:/doc/航空政策说明.md");
        // 一个文档只有一个Document
        List<Document> documentList = tikaDocumentReader.read();
        vectorStore.add(documentList);
    }
}
