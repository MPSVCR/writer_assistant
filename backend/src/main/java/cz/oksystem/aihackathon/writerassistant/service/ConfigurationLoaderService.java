package cz.oksystem.aihackathon.writerassistant.service;

import com.azure.ai.openai.models.ChatRequestAssistantMessage;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.oksystem.aihackathon.writerassistant.dto.Deployment;
import cz.oksystem.aihackathon.writerassistant.dto.Model;
import cz.oksystem.aihackathon.writerassistant.dto.ModelsConfiguration;
import cz.oksystem.aihackathon.writerassistant.repository.PersonInfoRepository;
import cz.oksystem.aihackathon.writerassistant.repository.SessionDataRepository;
import cz.oksystem.aihackathon.writerassistant.utils.OpenAiDocumentCreationRequest;
import cz.oksystem.aihackathon.writerassistant.utils.PersonInfoTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

@Service
public class ConfigurationLoaderService {

    private ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private SessionDataRepository sessionDataRepository;

    private PersonInfoRepository personInfoRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ModelsConfiguration loadConfiguration() {
        try {
            InputStream inputStream = ConfigurationLoaderService.class.getClassLoader().getResourceAsStream("models.yaml");

            if (inputStream == null) {
                System.out.println("Could not find config.yaml in resources.");
                throw new FileNotFoundException("Could not find config.yaml in resources.");
            }
            var res = mapper.readValue(inputStream, ModelsConfiguration.class);
            logger.info("Loaded configuration with {} models. {}", res.getModels().size(), res.getModels().stream().map(m -> m.values()).toList());
            return res;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public OpenAiDocumentCreationRequest loadConfigurationToRequest(UUID session, Optional<String> model, String input, Long id) {
        OpenAiDocumentCreationRequest request = new OpenAiDocumentCreationRequest();
        Optional<Model> loadedModel = Optional.empty();
        if (model.isPresent()) {
            loadedModel = getModelByName(model.get());
        }

        if (loadedModel.isPresent()) {
            request.setSystemText(loadedModel.get().getSystemPrompt());
            request.setResponseN(loadedModel.get().getResponseN());
            request.setDeployment(loadedModel.get().getDeployment());
        } else {
            logger.info("No model loaded. Using empty system propmpt and model {}", Deployment.GPT4o.getFullName());
            request.setSystemText("");
        }

        PersonInfoTemplate template = personInfoRepository.getPersonInfoTemplate(id);
        logger.info("""
                Retrieved following info about person:
                {}
                """, template.getOsobaMetadataPreprompt());
        request.setSystemText(request.getSystemText().concat(template.getOsobaMetadataPreprompt()));

        if (request.getDeployment() == null) {
            request.setDeployment(Deployment.GPT4o);
        }

        ArrayList<ChatRequestMessage> answerChain = new ArrayList<>();
        answerChain.addAll(getChainStart(loadedModel));
        answerChain.addAll(sessionDataRepository.getSessionHistory(session));
        request.setAnswerChain(answerChain);
        request.setClientPrompt(input);
        return request;
    }

    public List<ChatRequestMessage> getChainStart(Optional<Model> loadedModel) {
        List<ChatRequestMessage> chain = new ArrayList<>();
        loadedModel.ifPresent(model -> model.getExamples().forEach(i ->
                i.values().forEach(example -> {
                    chain.add(new ChatRequestUserMessage("Ukázkový dotaz: " + example.getRequest()));
                    chain.add(new ChatRequestAssistantMessage("Ukázková odpověď: " + example.getResponse()));
                })
        ));
        return chain;
    }

    //n
    public Optional<Model> getModelByName(String modelName) {
        var models = loadConfiguration();
        //todo refactor to map
        List<Model> res = models.getModels().stream()
                .flatMap(model -> model.values().stream().filter(i -> Objects.equals(i.getName(), modelName)))
                .toList();

        if (res.size() > 1) {
            throw new IllegalArgumentException("Multiple models with the same name found.");
        } else if (res.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(res.getFirst());
        }
    }

    public List<String> getAllModels() {
        var models = loadConfiguration();
        return models.getModels().stream().flatMap(i -> i.values().stream().map(Model::getName)).toList();
    }

    @Autowired
    public void setSessionDataRepository(SessionDataRepository sessionDataRepository) {
        this.sessionDataRepository = sessionDataRepository;
    }

    @Autowired
    public void setPersonInfoRepository(PersonInfoRepository personInfoRepository) {
        this.personInfoRepository = personInfoRepository;
    }
}
