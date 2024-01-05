package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.AgentRequest;
import ma.youcode.myrhbackendapi.dto.responses.AgentResponse;
import ma.youcode.myrhbackendapi.entities.Agent;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.repositories.AgentRepository;
import ma.youcode.myrhbackendapi.services.AgentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final ModelMapper mapper;
    @Override
    public List<AgentResponse> getAll() {
        List<Agent> agents = agentRepository.findAll();
        if (agents.isEmpty())
            throw new ResourceNotFoundException("No Agents Found");
        return agents.stream().map(agent -> mapper.map(agent, AgentResponse.class)).toList();
    }

    @Override
    public Page<AgentResponse> getAll(Pageable pageable) {
        Page<Agent> agentPage = agentRepository.findAll(pageable);
        if (agentPage.isEmpty())
            throw new ResourceNotFoundException("No Agents Found");
        return agentPage.map(agent -> mapper.map(agent, AgentResponse.class));
    }

    @Override
    public Optional<AgentResponse> find(UUID id) {
        Optional<Agent> agent = agentRepository.findById(id);
        if (agent.isEmpty())
            throw new ResourceNotFoundException("No Agent Found with ID: " + id);
        return Optional.of(mapper.map(agent, AgentResponse.class));
    }

    @Override
    public Optional<AgentResponse> create(AgentRequest agentRequest) {
        Agent agent = mapper.map(agentRequest, Agent.class);
        Agent savedAgent = agentRepository.save(agent);
        // TODO: add some validation if needed
        return Optional.of(mapper.map(savedAgent, AgentResponse.class));
    }

    @Override
    public Optional<AgentResponse> update(AgentRequest agentRequest, UUID id) {
        Optional<Agent> agent = agentRepository.findById(id);
        if (agent.isEmpty())
            throw new ResourceNotFoundException("No Agent Found with ID: " + id);
        // TODO: this is wrong fix it later!!!! by setting the request info to the found agent
        Agent agentToUpdate = mapper.map(agentRequest, Agent.class);
        Agent updatedAgent = agentRepository.save(agentToUpdate);
        return Optional.of(mapper.map(updatedAgent, AgentResponse.class));
    }

    @Override
    public boolean destroy(UUID id) {
        Optional<Agent> agent = agentRepository.findById(id);
        if (agent.isPresent()) {
            agentRepository.delete(agent.get());
            return true;
        }else throw new ResourceNotFoundException("No Agent Found with ID: " + id);
    }
}
