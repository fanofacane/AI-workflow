package com.sky.skyaviation.config;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.sky.skyaviation.service.serviceImpl.ToolsService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import java.time.Duration;

@Configuration
public class ChatAIConfig {
    @Bean
    public ChatMemoryRepository chatMemoryRepository() {
        return new InMemoryChatMemoryRepository();
    }

    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder().chatMemoryRepository(chatMemoryRepository).build();
    }

    @Bean
    public ChatClient chatClient(@Autowired DashScopeChatModel dashScopeChatModel,
                                 @Autowired ChatMemory chatMemory,
//                                 @Value("classpath:/travel-prompt.txt") Resource systemPrompt,
                                 @Autowired ToolsService toolsService) {
        return ChatClient
                .builder(dashScopeChatModel)
                .defaultAdvisors(PromptChatMemoryAdvisor.builder(chatMemory).build())
//                .defaultTools(toolsService)
//                .defaultSystem(systemPrompt)
                .build();
    }

    @Bean
    public RestClient.Builder restClientBuilder() {
        var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000); // 10秒连接超时
        requestFactory.setReadTimeout(60000);    // 60秒读取超时
        return RestClient.builder()
                .requestFactory(requestFactory);
    }

    @Bean
    public VectorStore vectorStore(DashScopeEmbeddingModel dashScopeEmbedding) {
        return SimpleVectorStore.builder(dashScopeEmbedding).build();
    }

}
