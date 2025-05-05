package cl.perfulandia.users.service;

import cl.perfulandia.users.dto.UserRequest;
import cl.perfulandia.users.dto.UserResponse;
import cl.perfulandia.users.model.User;
import cl.perfulandia.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest req) {
        User u = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .email(req.getEmail())
                .build();
        u = repo.save(u);
        return toDto(u);
    }

    public List<UserResponse> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserResponse findById(Long id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Optional<UserResponse> findByUsername(String username) {
        return repo.findByUsername(username)
                .map(this::toDto);
    }

    public UserResponse update(Long id, UserRequest req) {
        User u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole(req.getRole());
        u.setEmail(req.getEmail());
        return toDto(repo.save(u));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    private UserResponse toDto(User u) {
        return new UserResponse(
                u.getId(),
                u.getUsername(),
                u.getRole(),
                u.getPassword(),
                u.getEmail()
        );
    }
}
