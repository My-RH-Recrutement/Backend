package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.requests.SubscriptionRequest;
import ma.youcode.myrhbackendapi.dto.responses.SubscriptionResponse;
import ma.youcode.myrhbackendapi.interfaces.CrudInterface;

public interface SubscriptionService extends CrudInterface<SubscriptionResponse, SubscriptionRequest, String> {
}
