package com.oleske.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oleske.restaurant.Restaurant;
import com.oleske.restaurant.RestaurantController;
import com.oleske.restaurant.RestaurantDto;
import com.oleske.restaurant.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantControllerTest {
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    @Mock
    private RestaurantRepository mockRestaurantRepository;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        mvc = MockMvcBuilders
                .standaloneSetup(new RestaurantController(mockRestaurantRepository))
                .build();
    }

    @Test
    public void createRestaurant_returns201() throws Exception {
        Restaurant request = new Restaurant(
                null,
                "name",
                "ownerName",
                "headChefName",
                "cuisineType",
                "shortDescription",
                "fullDescription",
                "websiteUrl",
                0,
                1,
                2
        );

        Restaurant restaurant = new Restaurant(
                1L,
                "name",
                "ownerName",
                "headChefName",
                "cuisineType",
                "shortDescription",
                "fullDescription",
                "websiteUrl",
                0,
                1,
                2
        );
        when(mockRestaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
        mvc.perform(post("/newRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.ownerName").value("ownerName"))
                .andExpect(jsonPath("$.headChefName").value("headChefName"))
                .andExpect(jsonPath("$.cuisineType").value("cuisineType"))
                .andExpect(jsonPath("$.shortDescription").value("shortDescription"))
                .andExpect(jsonPath("$.fullDescription").value("fullDescription"))
                .andExpect(jsonPath("$.websiteUrl").value("websiteUrl"))
                .andExpect(jsonPath("$.rating").value("0"))
                .andExpect(jsonPath("$.michelinStarRating").value("1"))
                .andExpect(jsonPath("$.zagatRating").value("2"))
        ;
        verify(mockRestaurantRepository).save(request);
    }

}