package com.example.online_shopping.service;

import com.example.online_shopping.entity.User;
import com.example.online_shopping.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void saveUser() {
        //given
        User user = new User();
        user.setEmail("new@mail.ru");

        //when
        boolean isUserCreated = userService.saveUser(user);

        //then
        Assertions.assertTrue(isUserCreated);
    }

    @Test
    void saveUserFail() {
        //given
        User user = new User();
        user.setEmail("new@mail.ru");

        Mockito.doReturn(new User())
                .when(userRepository)
                .findByEmail("new@mail.ru");
        //when
        boolean isUserCreated = userService.saveUser(user);

        //then
        Assertions.assertFalse(isUserCreated);
    }

    @Test
    void loadUserByUsernameException() {
        //given
        Mockito.doReturn(null)
                .when(userRepository)
                .findByEmail("new@mail.ru");

        //when
        //then
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("new@mail.ru"));
    }
}