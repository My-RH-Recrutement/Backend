package ma.youcode.myrhbackendapi.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.AgentRequest;
import ma.youcode.myrhbackendapi.dto.responses.AgentResponse;
import ma.youcode.myrhbackendapi.interfaces.ControllerInterface;
import ma.youcode.myrhbackendapi.services.AgentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/agents")
@CrossOrigin("*") // TODO: replace "*" with frontend host only
public class AgentController implements ControllerInterface<AgentRequest, AgentResponse, UUID> {

    private final AgentService agentService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AgentResponse> get(@PathVariable UUID id) {
        Optional<AgentResponse> agent = agentService.find(id);
        assert agent.isPresent();
        return new ResponseEntity<>(agent.get(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<AgentResponse>> getAll() {
        return new ResponseEntity<>(agentService.getAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<AgentResponse>> getAllPaginated(Pageable pageable) {
        return new ResponseEntity<>(agentService.getAll(pageable), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<AgentResponse> create(@Valid @RequestBody AgentRequest agentRequest) {
        Optional<AgentResponse> agent = agentService.create(agentRequest);
        assert agent.isPresent();
        return new ResponseEntity<>(agent.get(), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{id}/update")
    public ResponseEntity<AgentResponse> update(@Valid @RequestBody AgentRequest agentRequest, @PathVariable UUID id) {
        Optional<AgentResponse> agent = agentService.update(agentRequest, id);
        assert agent.isPresent();
        return new ResponseEntity<>(agent.get(), HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> destroy(@PathVariable UUID id) {
        Map<String, String> messages = new HashMap<>();
        if (agentService.destroy(id)) messages.put("message", "Agent Deleted Successfully");
        else messages.put("message", "Agent couldn't be Deleted");
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
