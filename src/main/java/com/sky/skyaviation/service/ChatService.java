package com.sky.skyaviation.service;

import reactor.core.publisher.Flux;

public interface ChatService {
    Flux<String> AiService(String msg);
}
