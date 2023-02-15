package com.zeusbe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeusbe.controller.UserController;
import com.zeusbe.dto.request.SignInForm;
import com.zeusbe.dto.request.UserDto;
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


public class UserControllerUpdate {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    ObjectMapper objectMapper;

    SignInForm signInForm =new SignInForm() ;

    UserDto userDto = new UserDto();
    @Test
    public void signUp_name_1() throws Exception {
        userDto.setUserName(null);
        userDto.setFirstName("son");
        userDto.setLastName("hung");
        userDto.setPassword("123123");
        userDto.setEmail("hungson2.star@gmail.com");
        userDto.setPhone("0987465622");
        userDto.setCountryId(1l);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signUp")
                        .content(this.objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void signUp_name_2() throws Exception {
        userDto.setUserName("");
        userDto.setFirstName("son");
        userDto.setLastName("hung");
        userDto.setPassword("123123");
        userDto.setEmail("hungson2.star@gmail.com");
        userDto.setPhone("0987465622");
        userDto.setCountryId(1l);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signUp")
                        .content(this.objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void signUp_name_6() throws Exception {
        userDto.setUserName("sonhung2");
        userDto.setFirstName("son");
        userDto.setLastName("hung");
        userDto.setPassword("123123");
        userDto.setEmail("hungson2.star@gmail.com");
        userDto.setPhone("0987465622");
        userDto.setCountryId(1l);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signUp")
                        .content(this.objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void signUp_password_1() throws Exception {
        userDto.setUserName("sonhung22");
        userDto.setFirstName("son");
        userDto.setLastName("hung");
        userDto.setPassword(null);
        userDto.setEmail("hungson2.star@gmail.com");
        userDto.setPhone("0987465622");
        userDto.setCountryId(1l);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signUp")
                        .content(this.objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void signUp_password_2() throws Exception {
        userDto.setUserName("sonhung22");
        userDto.setFirstName("son");
        userDto.setLastName("hung");
        userDto.setPassword("");
        userDto.setEmail("hungson2.star@gmail.com");
        userDto.setPhone("0987465622");
        userDto.setCountryId(1l);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signUp")
                        .content(this.objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void signUp_password_4() throws Exception {
        userDto.setUserName("sonhung22");
        userDto.setFirstName("son");
        userDto.setLastName("hung");
        userDto.setPassword("123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123123");
        userDto.setEmail("hungson2.star@gmail.com");
        userDto.setPhone("0987465622");
        userDto.setCountryId(1l);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signUp")
                        .content(this.objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void signUp_password_5() throws Exception {
        userDto.setUserName("sonhung22");
        userDto.setFirstName("son");
        userDto.setLastName("hung");
        userDto.setPassword("123");
        userDto.setEmail("hungson2.star@gmail.com");
        userDto.setPhone("0987465622");
        userDto.setCountryId(1l);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signUp")
                        .content(this.objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void signUp_email_1() throws Exception {
        userDto.setUserName("sonhung22");
        userDto.setFirstName("son");
        userDto.setLastName("hung");
        userDto.setPassword("123");
        userDto.setEmail(null);
        userDto.setPhone("0987465622");
        userDto.setCountryId(1l);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signUp")
                        .content(this.objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void signUp_email_2() throws Exception {
        userDto.setUserName("sonhung22");
        userDto.setFirstName("son");
        userDto.setLastName("hung");
        userDto.setPassword("123");
        userDto.setEmail("");
        userDto.setPhone("0987465622");
        userDto.setCountryId(1l);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signUp")
                        .content(this.objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void signUp_email_3() throws Exception {
        userDto.setUserName("sonhung22");
        userDto.setFirstName("son");
        userDto.setLastName("hung");
        userDto.setPassword("123");
        userDto.setEmail("aaa");
        userDto.setPhone("0987465622");
        userDto.setCountryId(1l);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signUp")
                        .content(this.objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void signUp_email_6() throws Exception {
        userDto.setUserName("sonhung22");
        userDto.setFirstName("son");
        userDto.setLastName("hung");
        userDto.setPassword("123123");
        userDto.setEmail("phucpro746@gmail.com"); //error":"userName is existing"
        userDto.setPhone("0987465625");
        userDto.setCountryId(1l);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/user/signUp")
                        .content(this.objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

   @Test
        public void signUp_country_1() throws Exception {
            userDto.setUserName("sonhung22");
            userDto.setFirstName("son");
            userDto.setLastName("hung");
            userDto.setPassword("123123");
            userDto.setEmail("hungson2.star@gmail.com");
            userDto.setPhone("0987465625");
            userDto.setCountryId(null);
            this.mockMvc
                    .perform(MockMvcRequestBuilders
                            .post("/api/user/signUp")
                            .content(this.objectMapper.writeValueAsString(userDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
    }

//    @Test
//    public void signUp_7() throws Exception {
//        userDto.setUserName("sonhung22");
//        userDto.setFirstName("son");
//        userDto.setLastName("hung");
//        userDto.setPassword("123123");
//        userDto.setEmail("hungson22.star@gmail.com");
//        userDto.setPhone("0987465625");
//        userDto.setCountryId(null);
//        this.mockMvc
//                .perform(MockMvcRequestBuilders
//                        .post("/api/user/signUp")
//                        .content(this.objectMapper.writeValueAsString(userDto))
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andDo(print())
//                .andExpect(status().is4xxClientError());
//    }



}
