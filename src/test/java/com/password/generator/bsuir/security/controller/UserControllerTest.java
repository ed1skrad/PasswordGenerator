package com.password.generator.bsuir.security.controller;

import com.password.generator.bsuir.security.controller.UserController;
import com.password.generator.bsuir.security.domain.model.User;
import com.password.generator.bsuir.security.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

 class UserControllerTest {

    private UserController userController;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(authenticationService);
    }

    @Test
     void testDeleteUser() {
        Long userIdToDelete = 1L;

        doNothing().when(authenticationService).deleteUserById(userIdToDelete);

        userController.deleteUser(userIdToDelete);

        verify(authenticationService, times(1)).deleteUserById(userIdToDelete);
    }

    @Test
     void testDeleteUserSuccessMessage() {
        Long userIdToDelete = 1L;
        doNothing().when(authenticationService).deleteUserById(anyLong());

        ResponseEntity<String> result = userController.deleteUser(userIdToDelete);

        assertEquals(ResponseEntity.ok("User deleted successfully"), result);
        verify(authenticationService, times(1)).deleteUserById(userIdToDelete);
    }

    @Test
     void testUpdateUser() {
        Long userIdToUpdate = 1L;
        User updatedUser = new User();

        doNothing().when(authenticationService).updateUser(userIdToUpdate, updatedUser);

        userController.updateUser(userIdToUpdate, updatedUser);

        verify(authenticationService, times(1)).updateUser(userIdToUpdate, updatedUser);
    }

    @Test
     void testUpdateUserSuccessMessage() {
        Long userIdToUpdate = 1L;
        User updatedUser = new User();
        doNothing().when(authenticationService).updateUser(anyLong(), any(User.class));

        ResponseEntity<String> result = userController.updateUser(userIdToUpdate, updatedUser);

        assertEquals(ResponseEntity.ok("User updated successfully"), result);
        verify(authenticationService, times(1)).updateUser(userIdToUpdate, updatedUser);
    }
}
