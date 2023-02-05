package service;

import entity.User;
import repository.UserRepo;

import javax.inject.Inject;
import java.util.List;

public class UserService {

    private final UserRepo userRepo;

    @Inject
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUsers() {
        return userRepo.getUsers();
    }

    public void registerUser(User user) {
        userRepo.addUser(user);
    }

    public User getUserById(Long id) {
        return getUsers()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

}
