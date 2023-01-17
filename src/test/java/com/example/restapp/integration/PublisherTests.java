package com.example.restapp.integration;

import com.example.restapp.RestAppApplication;
import com.example.restapp.model.Publisher;
import com.example.restapp.repository.PublisherRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = RestAppApplication.class)
@AutoConfigureMockMvc
class PublisherTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    void findPublisherTest() throws Exception {

        Publisher publisher = new Publisher();
        publisher.setName("publisherTestName");
        publisher.setAddress("publisherTestAddress");

        UUID createdCarId = publisherRepository.save(publisher).getId();

        ResultActions resultActions = mvc.perform(get("/publisher/all")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andDo(print())
                .andExpect(status().isOk());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(contentAsString.contains(publisher.getName()))
                .isTrue();
        softAssertions.assertThat(contentAsString.contains(publisher.getAddress()))
                .isTrue();
        softAssertions.assertThat(contentAsString.contains(createdCarId.toString()))
                .isTrue();
        softAssertions.assertAll();
    }

}
