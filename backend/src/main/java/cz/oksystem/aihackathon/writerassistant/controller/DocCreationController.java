package cz.oksystem.aihackathon.writerassistant.controller;

import cz.oksystem.aihackathon.writerassistant.dto.DocumentCreationResponseDto;
import cz.oksystem.aihackathon.writerassistant.dto.LoadedModelsResponseDto;
import cz.oksystem.aihackathon.writerassistant.service.ConfigurationLoaderService;
import cz.oksystem.aihackathon.writerassistant.service.DocCreationService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DocCreationController {

    private DocCreationService docCreationService;

    private ConfigurationLoaderService configurationLoaderService;

    @PostMapping("/createDocument")
    @CrossOrigin
    public ResponseEntity<DocumentCreationResponseDto> fillInfo(@RequestHeader(value = "tralalacek") UUID session,
                                                                @RequestParam(required = false) String model,
                                                                @RequestParam(required = false, defaultValue = "1") Long personId,
                                                                @RequestBody String input) {
        if (session == null) {
            session = UUID.randomUUID();
        }
        Optional<String> inputModel = Optional.empty();
        if (model != null) {
            inputModel = Optional.of(model);
        }
        MDC.put("session", session.toString());
        return ResponseEntity.ok(docCreationService.createDocumentFromPrompt(input, session, inputModel, personId));
    }

    @GetMapping("/models")
    @CrossOrigin
    public ResponseEntity<LoadedModelsResponseDto> getModels() {
        return ResponseEntity.ok(new LoadedModelsResponseDto(configurationLoaderService.getAllModels()));
    }

    @Autowired
    public void setDocCreationService(DocCreationService docCreationService) {
        this.docCreationService = docCreationService;
    }

    @Autowired
    public void setConfigurationLoaderService(ConfigurationLoaderService configurationLoaderService) {
        this.configurationLoaderService = configurationLoaderService;
    }
}
