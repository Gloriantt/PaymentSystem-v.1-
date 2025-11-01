package by.antonpaulavets.paymentsystem.service;



import by.antonpaulavets.paymentsystem.exception.ResourseNotFoundException;
import by.antonpaulavets.paymentsystem.mapper.MapStructMapper;
import by.antonpaulavets.paymentsystem.model.User;
import by.antonpaulavets.paymentsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private MapStructMapper mapper;
    @CachePut(value = "users", key = "#result.id")
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Cacheable(value = "users",key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("User not found"));
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    @Cacheable(value = "usersByEmail", key = "#email")
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourseNotFoundException("User not found"));
    }
    @CachePut(value = "users", key = "#id")
    @Transactional
    public User updateUser(Long id, User updatedInfoUser) {
        User existing = getUserById(id);
        existing.setName(updatedInfoUser.getName());
        existing.setSurname(updatedInfoUser.getSurname());
        existing.setBirthDate(updatedInfoUser.getBirthDate());
        existing.setEmail(updatedInfoUser.getEmail());
        return userRepository.save(existing);
    }
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
