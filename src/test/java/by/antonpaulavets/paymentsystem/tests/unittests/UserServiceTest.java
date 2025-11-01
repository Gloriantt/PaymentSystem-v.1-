package by.antonpaulavets.paymentsystem.tests.unittests;


import by.antonpaulavets.paymentsystem.exception.ResourseNotFoundException;
import by.antonpaulavets.paymentsystem.model.User;
import by.antonpaulavets.paymentsystem.repository.UserRepository;
import by.antonpaulavets.paymentsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "Anton", "Paulavets", LocalDate.of(1999, 06, 20), "AntonPaulavets@gmail.com", new ArrayList<>());
    }

    @Test
    void shouldReturnUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("AntonPaulavets@gmail.com");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(99L)).isInstanceOf(ResourseNotFoundException.class).hasMessageContaining("User not found");
    }

    @Test
    void shouldCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(user);

        assertThat(result.getName()).isEqualTo("Anton");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        user.setName("Updated");
        User updated = userService.updateUser(1L, user);

        assertThat(updated.getName()).isEqualTo("Updated");
        verify(userRepository).save(user);
    }

    @Test
    void shouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}
