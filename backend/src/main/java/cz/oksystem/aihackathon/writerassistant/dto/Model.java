package cz.oksystem.aihackathon.writerassistant.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Model {

    private String name;

    private String systemPrompt;

    private Integer responseN;

    private Deployment deployment;

    private List<Map<String, Example>> examples = new ArrayList<>();

    public Model() {
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

    public void setResponseN(Integer maxResponses) {
        this.responseN = maxResponses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPropt) {
        this.systemPrompt = systemPropt;
    }

    public List<Map<String, Example>> getExamples() {
        return examples;
    }

    public void setExamples(List<Map<String, Example>> examples) {
        this.examples = examples;
    }
}
