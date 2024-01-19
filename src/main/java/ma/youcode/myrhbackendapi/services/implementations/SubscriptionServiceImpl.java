package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.SubscriptionRequest;
import ma.youcode.myrhbackendapi.dto.responses.SubscriptionResponse;
import ma.youcode.myrhbackendapi.entities.Pack;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import ma.youcode.myrhbackendapi.entities.Subscription;
import ma.youcode.myrhbackendapi.exceptions.ResourceAlreadyExistException;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.repositories.PackRepository;
import ma.youcode.myrhbackendapi.repositories.RecruiterRepository;
import ma.youcode.myrhbackendapi.repositories.SubscriptionRepository;
import ma.youcode.myrhbackendapi.services.SubscriptionService;
import ma.youcode.myrhbackendapi.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final RecruiterRepository recruiterRepository;
    private final PackRepository packRepository;
    private final ModelMapper mapper;

    @Override
    public List<SubscriptionResponse> getAll() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        if (subscriptions.isEmpty()) throw new ResourceNotFoundException("No Subscriptions Found");
        return subscriptions.stream().map(subscription -> mapper.map(subscription, SubscriptionResponse.class)).toList();
    }

    @Override
    public Page<SubscriptionResponse> getAll(Pageable pageable) {
        Page<Subscription> subscriptions = subscriptionRepository.findAll(pageable);
        if (subscriptions.isEmpty()) throw new ResourceNotFoundException("No Subscriptions Found");
        return subscriptions.map(subscription -> mapper.map(subscription, SubscriptionResponse.class));
    }

    @Override
    public Optional<SubscriptionResponse> find(String id) {
        Subscription subscription = subscriptionRepository.findById(Utils.pareseStringToUUID(id))
                .orElseThrow(() -> new ResourceNotFoundException("No Subscription Found with ID: " + id));
        return Optional.of(mapper.map(subscription, SubscriptionResponse.class));
    }

    @Override
    public Optional<SubscriptionResponse> create(SubscriptionRequest subscriptionRequest) {
        Recruiter recruiter = recruiterRepository.findById(Utils.pareseStringToUUID(subscriptionRequest.getRecruiter()))
                .orElseThrow(() -> new ResourceNotFoundException("No Recruiter Found with ID: " + subscriptionRequest.getRecruiter()));
        Pack pack = packRepository.findById(Utils.pareseStringToUUID(subscriptionRequest.getPack()))
                .orElseThrow(() -> new ResourceNotFoundException("No Pack Found with ID: " + subscriptionRequest.getPack()));
        Optional<Subscription> subscription = subscriptionRepository.findSubscriptionByRecruiterAndPack(recruiter, pack);
        if (subscription.isPresent()) throw new ResourceAlreadyExistException("You Already have a Subscription in this Plan");
        Subscription subscriptionToSave = mapper.map(subscriptionRequest, Subscription.class);
        Subscription savedSubscription = subscriptionRepository.save(subscriptionToSave);
        return Optional.of(mapper.map(savedSubscription, SubscriptionResponse.class));
    }

    @Override
    public Optional<SubscriptionResponse> update(SubscriptionRequest subscriptionRequest, String id) {
        Subscription subscription = subscriptionRepository.findById(Utils.pareseStringToUUID(id))
                .orElseThrow(() -> new ResourceNotFoundException("No Subscription Found with ID: " + id));
        Subscription subscriptionToUpdate = mapper.map(subscriptionRequest, Subscription.class);
        subscriptionToUpdate.setId(subscription.getId());
        Subscription savedSubscription = subscriptionRepository.save(subscriptionToUpdate);
        return Optional.of(mapper.map(savedSubscription, SubscriptionResponse.class));
    }

    @Override
    public boolean destroy(String id) {
        Subscription subscription = subscriptionRepository.findById(Utils.pareseStringToUUID(id))
                .orElseThrow(() -> new ResourceNotFoundException("No Subscription Found with ID: " + id));
        subscriptionRepository.delete(subscription);
        return true;
    }
}
