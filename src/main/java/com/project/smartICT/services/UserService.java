package com.project.smartICT.services;

import com.project.smartICT.entities.User;
import com.project.smartICT.repos.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private IUserRepository iUserRepository;

    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public List<User> getAllUsers() {
        return iUserRepository.findAll();
    }

    public User createOneUser(User newUser) {
        return iUserRepository.save(newUser);
    }

    public User getOneUser(Long userId) {
        return iUserRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User updateUser) {
        Optional<User> user = iUserRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();

            if(updateUser.getUserName() != null){
                foundUser.setUserName(updateUser.getUserName());
            } else{
                foundUser.setUserName(user.get().getUserName());
            }

            if(updateUser.getPassword() != null){
                foundUser.setPassword(updateUser.getPassword());
            }else{
                foundUser.setPassword(user.get().getPassword());
            }

            iUserRepository.save(foundUser);
            return foundUser;
        }
        else{
            return null;
        }
    }

    public void deleteOneUser(Long userId) {
        iUserRepository.deleteById(userId);
    }

    public User getOneUserByUserName(String userName) {
        return iUserRepository.findByUserName(userName);
    }
}