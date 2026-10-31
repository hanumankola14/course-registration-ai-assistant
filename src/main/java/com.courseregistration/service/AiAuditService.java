package com.courseregistration.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiAuditService {

    private final ChatClient chatClient;

    public AiAuditService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String reviewWaitlistLogic() {

        return chatClient.prompt()
                .user("""
                      Review FIFO waitlist logic.
                      Identify edge cases.
                      """)
                .call()
                .content();
    }
}