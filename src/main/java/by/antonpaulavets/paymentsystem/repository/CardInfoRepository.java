package by.antonpaulavets.paymentsystem.repository;


import by.antonpaulavets.paymentsystem.model.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {
}
