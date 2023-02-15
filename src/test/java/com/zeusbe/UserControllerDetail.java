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
@SpringBootTest(classes = SwapnowZeusbeApplication.class)
@AutoConfigureMockMvc
public class UserControllerDetail {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    ObjectMapper objectMapper;

SignInForm signInForm =new SignInForm() ;

    @Test
    public void getDetail_userName_1() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/user/detail/{username}","null")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getDetail_userName_2() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/user/detail/{username}","")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


}
