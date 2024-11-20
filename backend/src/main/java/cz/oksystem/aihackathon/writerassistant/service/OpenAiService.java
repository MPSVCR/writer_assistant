package cz.oksystem.aihackathon.writerassistant.service;

import com.azure.ai.openai.OpenAIClient;

import com.azure.ai.openai.models.*;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import cz.oksystem.aihackathon.writerassistant.dto.Deployment;
import cz.oksystem.aihackathon.writerassistant.utils.MessageInfo;
import cz.oksystem.aihackathon.writerassistant.utils.OpenAiDocumentCreationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OpenAiService {

    private OpenAIClient client;
    private DateService dateService;

    Logger logger = LoggerFactory.getLogger(OpenAiService.class);

    public List<ChatChoice> promptGpt4O(OpenAiDocumentCreationRequest request, String aiModel) {
        var options = new ChatCompletionsOptions(request.toClientRequest());
        options.setN(request.getResponseN());
        ChatCompletions result = client.getChatCompletions(request.getDeployment().getFullName(),options);
        logResponse(result, aiModel, request.getDeployment());
        return result.getChoices();
    }

    public List<ChatChoice> promptGpt4OTools(OpenAiDocumentCreationRequest request, String aiModel) {
        var chatMessages = request.toClientRequest();
        var options = new ChatCompletionsOptions(chatMessages);
        options.setN(request.getResponseN());
        options.setTools(Arrays.asList(dateService.getToolDefinition()));

        ChatCompletions result = client.getChatCompletions(request.getDeployment().getFullName(),options);
        logResponse(result, aiModel, request.getDeployment());

        // tools
        ChatChoice choice = result.getChoices().getFirst();
        if (choice.getFinishReason() == CompletionsFinishReason.TOOL_CALLS) {
            List<ChatRequestMessage> followUpMessages = new ArrayList<>(chatMessages);
            followUpMessages.add(new ChatRequestAssistantMessage("").setToolCalls(choice.getMessage().getToolCalls()));

            for (ChatCompletionsToolCall toolCall : choice.getMessage().getToolCalls()) {
                // tool date
                String dateResult = dateService.getResult(((ChatCompletionsFunctionToolCall) toolCall).getFunction().getArguments());
                // prida výsledek funkce do konverzace
                followUpMessages.add(new ChatRequestToolMessage(dateResult, toolCall.getId()));
            }
            ChatCompletionsOptions followUpOptions = new ChatCompletionsOptions(followUpMessages);
            ChatCompletions followUpCompletions = client.getChatCompletions(request.getDeployment().getFullName(), followUpOptions);

            // vysledek po volani tools
            return followUpCompletions.getChoices();
        }
        return result.getChoices();
    }


    private void logResponse(ChatCompletions result, String model, Deployment deployment) {
        logger.info("""
                \n
                ------------------------------------------------
                Session {}
                Model {}
                Retrieved {} options from {}.
                Price: {}$
                Token usage: {}
                ------------------------------------------------
                """, MDC.get("session"), model, result.getChoices().size(), deployment.getFullName(), MessageInfo.getPrice(result), MessageInfo.getTokenUsage(result));
    }


    @Autowired
    public void setClient(OpenAIClient client) {
        this.client = client;
    }

    @Autowired
    public void setDateService(DateService dateService) {
        this.dateService = dateService;
    }
}
