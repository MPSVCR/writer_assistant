package cz.oksystem.aihackathon.writerassistant.service;

import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatRequestAssistantMessage;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import cz.oksystem.aihackathon.writerassistant.dto.DocumentCreationResponseDto;
import cz.oksystem.aihackathon.writerassistant.repository.SessionDataRepository;
import cz.oksystem.aihackathon.writerassistant.utils.OpenAiDocumentCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocCreationService {

    private OpenAiService openAiService;
    private SessionDataRepository sessionDataRepository;
    private ConfigurationLoaderService configurationLoaderService;

    public DocumentCreationResponseDto createDocumentFromPrompt(String input, UUID session, Optional<String> model, Long personId) {
        OpenAiDocumentCreationRequest request = configurationLoaderService.loadConfigurationToRequest(session, model, input, personId);
        var res = openAiService.promptGpt4O(request, model.orElse("Nebyl nastaven."));

        //Persist prompt and response
        List<ChatRequestMessage> updateHistory = new ArrayList<>();
        updateHistory.add(new ChatRequestUserMessage(request.getClientPrompt()));
        updateHistory.addAll(res.stream().map(it -> (ChatRequestMessage) new ChatRequestAssistantMessage(it.getMessage().getContent())).toList());
        sessionDataRepository.addToSessionHistory(session, updateHistory);
        return mapToResponse(res);
    }

    private DocumentCreationResponseDto mapToResponse (List<ChatChoice> choices) {
        var res = new DocumentCreationResponseDto();
        res.setChoices(choices.stream().filter(c -> c.getMessage().getContent() != null).map(c -> c.getMessage().getContent()).toList());
        return res;
    }

    @Autowired
    public void setOpenAiService(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @Autowired
    public void setSessionDataRepository(SessionDataRepository sessionDataRepository) {
        this.sessionDataRepository = sessionDataRepository;
    }

    @Autowired
    public void setConfigurationLoaderService(ConfigurationLoaderService configurationLoaderService) {
        this.configurationLoaderService = configurationLoaderService;
    }
}
