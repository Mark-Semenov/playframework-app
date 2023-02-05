package repository;

import entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepo {

    private List<User> users = new ArrayList<>();

    public UserRepo() {
        for (int i = 1; i < 6; i++) {
            users.add(new User((long) i, String.format("User %d", i)));
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        Long id = users.get(users.size() - 1).getId();
        user.setId(++id);
        users.add(user);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
