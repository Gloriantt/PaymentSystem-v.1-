package by.antonpaulavets.paymentsystem.controller;

import by.antonpaulavets.paymentsystem.dto.CardRequestDTO;
import by.antonpaulavets.paymentsystem.dto.CardResponseDTO;
import by.antonpaulavets.paymentsystem.mapper.MapStructMapper;
import by.antonpaulavets.paymentsystem.model.CardInfo;
import by.antonpaulavets.paymentsystem.model.User;
import by.antonpaulavets.paymentsystem.service.CardService;
import by.antonpaulavets.paymentsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private CardService cardService;
    private UserService userService;
    private MapStructMapper mapper;

    @PostMapping
    public ResponseEntity<CardResponseDTO> create(@RequestBody CardRequestDTO dto) {
        User user = userService.getUserById(dto.getUserId());
        CardInfo card = mapper.cardRequestDtoToCardInfo(dto);
        card.setUser(user);
        CardInfo saved = cardService.createCard(card);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.cardInfoToCardResponseDto(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> getById(@PathVariable Long id) {
        CardInfo card = cardService.getCardById(id);
        return ResponseEntity.ok(mapper.cardInfoToCardResponseDto(card));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}