package com.sky.skyaviation.controller;

import com.sky.skyaviation.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping
public class ChatController {
    @Autowired
    private ChatService chatService;
    // 注意：produces 改为 text/event-stream，符合 SSE 协议
    @GetMapping(value = "/ai/chat", produces = "text/event-stream;charset=utf-8")
    public Flux<String> chatAI(@RequestParam String message) {
        return chatService.AiService(message);
    }
}
