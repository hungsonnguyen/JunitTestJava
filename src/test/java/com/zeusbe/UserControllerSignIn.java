package com.zeusbe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeusbe.controller.UserController;
import com.zeusbe.dto.request.SignInForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerSignIn {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    ObjectMapper objectMapper;

SignInForm signInForm =new SignInForm() ;

    @Test
    public void signIn_userName_1() throws Exception {
        signInForm.setUserName(null);
        signInForm.setPassword("123123");
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signIn")
                        .content(this.objectMapper.writeValueAsString(signInForm))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void signIn_userName_2() throws Exception {
        signInForm.setUserName("");
        signInForm.setPassword("123123");
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signIn")
                        .content(this.objectMapper.writeValueAsString(signInForm))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }



    @Test
    public void signIn_password_1() throws Exception {
        signInForm.setUserName("sonhung2");
        signInForm.setPassword(null);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signIn")
                        .content(this.objectMapper.writeValueAsString(signInForm))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void signIn_password_2() throws Exception {
        signInForm.setUserName("sonhung2");
        signInForm.setPassword("");
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signIn")
                        .content(this.objectMapper.writeValueAsString(signInForm))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void signIn_3() throws Exception {
        signInForm.setUserName("sonhung2");
        signInForm.setPassword("123123");
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signIn")
                        .content(this.objectMapper.writeValueAsString(signInForm))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

}
