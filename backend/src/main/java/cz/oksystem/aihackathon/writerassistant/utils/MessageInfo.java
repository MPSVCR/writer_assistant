package cz.oksystem.aihackathon.writerassistant.utils;

import com.azure.ai.openai.models.ChatCompletions;

import java.math.BigDecimal;

public class MessageInfo {

    public static final BigDecimal PER_MILION_PROMPT = new BigDecimal("0.0000025");
    public static final BigDecimal PER_MILLION_COMPLETION = new BigDecimal("0.000011");

    public static String getPrice(ChatCompletions completions) {
        return (PER_MILION_PROMPT.multiply(new BigDecimal(completions.getUsage().getPromptTokens())).add(PER_MILLION_COMPLETION.multiply(new BigDecimal(completions.getUsage().getCompletionTokens())))).toString().substring(0, 5);
    }

    public static String getTokenUsage(ChatCompletions completions) {
        return "Prompt tokens: " + completions.getUsage().getPromptTokens() + " . Completion tokens: " + completions.getUsage().getCompletionTokens();
    }

    public static void printOpenAiOutput(ChatCompletions completions) {
        System.out.println("-----------------------------------------------");
        System.out.println("Generated " + completions.getChoices().size() + " choices.");
        System.out.println("Price: " + getPrice(completions) + "$");
        System.out.println(getTokenUsage(completions));
        System.out.println("-----------------------------------------------");
        completions.getChoices().forEach(c -> System.out.println(c.getMessage().getContent()));
        System.out.println("-----------------------------------------------");

    }

}
