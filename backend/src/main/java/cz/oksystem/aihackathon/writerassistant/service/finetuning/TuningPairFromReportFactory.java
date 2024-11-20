package cz.oksystem.aihackathon.writerassistant.service.finetuning;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.models.*;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

class TuningPairFromReportFactory {

    private final String modelName;
    private final OpenAIClient chat;
    private final String inputSystemPrompt;
    private final String outputSystemPrompt;

    TuningPairFromReportFactory(@Nonnull String modelName,
                                @Nonnull OpenAIClient llm,
                                @Nonnull String inputSystemPrompt,
                                @Nonnull String outputSystemPrompt
    ) {
        this.modelName = modelName;
        this.chat = llm;
        this.inputSystemPrompt = inputSystemPrompt;
        this.outputSystemPrompt = outputSystemPrompt;
    }

    public TuningPair generateTuningPair(String rawReport) {
        return new TuningPair(generateTuningSingleton(rawReport, inputSystemPrompt), generateTuningSingleton(rawReport, outputSystemPrompt));
    }

    private ChatResponseMessage generateTuningSingleton(String rawReport, String systemPrompt) {
        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage(systemPrompt));
        chatMessages.add(new ChatRequestUserMessage(rawReport));

        ChatCompletions response = chat.getChatCompletions(modelName,
                new ChatCompletionsOptions(chatMessages));
        return response.getChoices().getFirst().getMessage();
    }

    public record TuningPair(@Nonnull ChatResponseMessage input, @Nonnull ChatResponseMessage output) {

    }

}
