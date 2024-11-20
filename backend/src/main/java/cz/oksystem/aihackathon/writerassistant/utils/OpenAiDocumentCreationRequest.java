package cz.oksystem.aihackathon.writerassistant.utils;

import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestSystemMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import cz.oksystem.aihackathon.writerassistant.dto.Deployment;

import java.util.ArrayList;
import java.util.List;

public class OpenAiDocumentCreationRequest {

    private String systemText;

    private List<ChatRequestMessage> answerChain;

    private String clientPrompt;

    private Integer responseN;

    private Deployment deployment;

    public List<ChatRequestMessage> toClientRequest() {
        List<ChatRequestMessage> requests = new ArrayList<>();
        requests.add(new ChatRequestSystemMessage(systemText));
        requests.addAll(this.answerChain);
        requests.add(new ChatRequestUserMessage(clientPrompt));
        return requests;
    }

    public OpenAiDocumentCreationRequest() {
    }

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    public Integer getResponseN() {
        return responseN;
    }

    public void setResponseN(Integer maxResults) {
        this.responseN = maxResults;
    }

    public String getSystemText() {
        return systemText;
    }

    public void setSystemText(String systemText) {
        this.systemText = systemText;
    }

    public List<ChatRequestMessage> getAnswerChain() {
        return answerChain;
    }

    public void setAnswerChain(List<ChatRequestMessage> answerChain) {
        this.answerChain = answerChain;
    }

    public String getClientPrompt() {
        return clientPrompt;
    }

    public void setClientPrompt(String clientPrompt) {
        this.clientPrompt = clientPrompt;
    }
}
