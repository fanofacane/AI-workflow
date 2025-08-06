package com.sky.skyaviation.service.serviceImpl;

import com.sky.skyaviation.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private VectorStore vectorStore;
    @Value("classpath:/travel-prompt.txt")
    Resource systemPrompt;
    @Value("classpath:/travel-son-prompt.txt")
    Resource sonPromp;
    // 数据记录类
    public record Task(String type, String description) {}
    public record OrchestratorResponse(String analysis, List<Task> tasks) {}
    /*    @Override
    public Flux<String> AiService(String msg) {
        Boolean isComplain = chatClient.prompt()
                .user(msg)
                .system("""
            用户表面要转人工或你处理不了的问题时返回true
            在返回true一定要先询问用户确认是否转人工,确认后返回true
            只能用 true 或 false 回答，不要输出多余内容
            """)
                .call()
                .entity(Boolean.class);
        if (Boolean.TRUE.equals(isComplain)){
            return Flux.just("sorry！！qwq", "正在转人工", "=====", "请稍等");
        }else {
            return chatClient.prompt()
                    .user(msg)
                    .advisors(new SimpleLoggerAdvisor(),
                            QuestionAnswerAdvisor.builder(vectorStore).searchRequest(
                                    SearchRequest.builder().query(msg).topK(5).similarityThreshold(0.3).build()).build())
                    .stream()
                    .content();
        }
    }*/
        @Override
        public Flux<String> AiService(String msg) {
            String recommend = recommend2(msg);
            return chatClient.prompt()
                    .system("""  
                    将以下内容格式化为专业文档:
                    请添加适当的标题层级、列表、表格等格式，生成最终的markdown文档。
                    """)
                    .user(recommend)
                    .stream()
                    .content();
        }
    public String recommend2(String taskDescription) {
        System.out.println("=== 开始处理任务 ===");

        // 获取子任务列表
        OrchestratorResponse orchestratorResponse = chatClient.prompt()
                .system(p -> p.param("task", taskDescription))
                .user(systemPrompt)
                .call()
                .entity(OrchestratorResponse.class);

        System.out.println("子任务列表: " + orchestratorResponse);

        // 并行处理所有子任务
        List<CompletableFuture<String>> futures = orchestratorResponse.tasks().stream()
                .map(task -> CompletableFuture.supplyAsync(() -> {
                    System.out.println("-----------------------------------处理子任务: " + task.type() + "--------------------------------");
                    String content = chatClient.prompt()
                            .user(u -> u.text(sonPromp)
                                    .param("original_task", taskDescription)
                                    .param("task_type", task.type())
                                    .param("task_description", task.description()))
                            .call()
                            .content();
                    System.out.println("子任务处理结果: " + content);
                    // 这里可以根据需要更新task的内容
                    return content;
                }))
                .toList();

        // 等待所有并行任务完成并收集结果
        List<String> taskList = futures.stream()
                .map(CompletableFuture::join)
                        .toList();

        System.out.println("=== 所有工作者完成任务 ===");
        System.out.println("最终结果: "+ taskList.toString());
        return taskList.toString();
    }
    public String recommend(String requirements) {
        String currentOutput;
        String totalOutput = "";
        String finalPlan;
        System.out.println("=== 开始文档生成链式流程 ===");

        currentOutput = tourRecommend(requirements);
        totalOutput +=currentOutput;
        System.out.println("推荐旅游景点生成"+currentOutput);

        currentOutput = tripPlanning(currentOutput);
        totalOutput +=currentOutput;
        System.out.println("行程规划生成"+currentOutput);

//        finalPlan = planPlus(totalOutput);
//        System.out.println("计划补充生成"+finalPlan);

//        finalPlan = formatDocument(totalOutput);
//        System.out.println("最终文档生成"+finalPlan);
        return totalOutput;
    }
    private String executeStep(String prompt, String input) {
        return chatClient.prompt()
                .user(u -> u.text(prompt).param("input", input))
                .call()
                .content();
    }

    private String tourRecommend(String content) {
        String optimizePrompt = """  
            向用户推荐你认为最值得去的景点(一定只推荐一个景点):
            原始内容: {input}
            你需要给出旅游景点相关的简介,特色,适合游玩天数,大致预算
            """;
        return executeStep(optimizePrompt, content);
    }

    private String tripPlanning(String content) {
        String formatPrompt = """  
            基于以下的旅游景点，为用户规划详细的行程路线:
            旅游景点: {input}
            """;
        return executeStep(formatPrompt, content);
    }
    private String planPlus(String content) {
        String formatPrompt = """  
            基于这份旅游计划进行完善补充:
            旅游计划: {input}
            """;
        return executeStep(formatPrompt, content);
    }
    private String formatDocument(String content) {
        String formatPrompt = """  
            将以下内容格式化为专业文档：  
            内容: {input}  
            请添加适当的标题层级、列表、表格等格式，生成最终的markdown文档。  
            """;
        return executeStep(formatPrompt, content);
    }

}
