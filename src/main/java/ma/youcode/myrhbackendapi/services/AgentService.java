package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.requests.AgentRequest;
import ma.youcode.myrhbackendapi.dto.responses.AgentResponse;
import ma.youcode.myrhbackendapi.interfaces.CrudInterface;

import java.util.UUID;

public interface AgentService extends CrudInterface<AgentResponse, AgentRequest, UUID> {
}
