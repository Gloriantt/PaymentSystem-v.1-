package by.antonpaulavets.paymentsystem.service;

import by.antonpaulavets.paymentsystem.model.CardInfo;
import by.antonpaulavets.paymentsystem.repository.CardInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardInfoRepository cardRepo;

    public CardInfo createCard(CardInfo card) {
        return cardRepo.save(card);
    }

    public CardInfo getCardById(Long id) {
        return cardRepo.findById(id).orElseThrow(() -> new RuntimeException("Card not found"));
    }

    public List<CardInfo> getAllCards(int page, int size) {
        return cardRepo.findAll(PageRequest.of(page, size)).getContent();
    }

    public CardInfo updateCard(Long id, CardInfo newData) {
        CardInfo existing = getCardById(id);
        existing.setNumber(newData.getNumber());
        existing.setHolder(newData.getHolder());
        existing.setExpirationDate(newData.getExpirationDate());
        return cardRepo.save(existing);
    }
    public void deleteCard(Long id) {
        cardRepo.deleteById(id);
    }
}
