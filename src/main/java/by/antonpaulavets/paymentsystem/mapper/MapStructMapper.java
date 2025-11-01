package by.antonpaulavets.paymentsystem.mapper;


import by.antonpaulavets.paymentsystem.dto.CardRequestDTO;
import by.antonpaulavets.paymentsystem.dto.CardResponseDTO;
import by.antonpaulavets.paymentsystem.dto.UserRequestDTO;
import by.antonpaulavets.paymentsystem.dto.UserResponseDTO;
import by.antonpaulavets.paymentsystem.model.CardInfo;
import by.antonpaulavets.paymentsystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    @Mapping(target = "cards", source = "cards")
    UserResponseDTO userToUserResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cards", ignore = true)
    User userRequestDtoToUser(UserRequestDTO userRequestDto);

    List<UserResponseDTO> usersToUserResponseDtos(List<User> users);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "number", expression = "java(maskCardNumber(cardInfo.getNumber()))")
    CardResponseDTO cardInfoToCardResponseDto(CardInfo cardInfo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    CardInfo cardRequestDtoToCardInfo(CardRequestDTO cardRequestDto);

    List<CardResponseDTO> cardInfosToCardResponseDtos(List<CardInfo> cards);

    default String maskCardNumber(String number) {
        if (number == null || number.length() <= 4) return number;
        return "**** **** **** " + number.substring(number.length() - 4);
    }
}
