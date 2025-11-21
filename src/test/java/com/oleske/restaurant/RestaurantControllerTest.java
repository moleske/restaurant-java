package com.oleske.restaurant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private RestaurantRepository mockRestaurantRepository;

    private ObjectMapper objectMapper;
    private Restaurant restaurant = new Restaurant(
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

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
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

        when(mockRestaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        mvc.perform(post("/newRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.ownerName").value(request.getOwnerName()))
                .andExpect(jsonPath("$.headChefName").value(request.getHeadChefName()))
                .andExpect(jsonPath("$.cuisineType").value(request.getCuisineType()))
                .andExpect(jsonPath("$.shortDescription").value(request.getShortDescription()))
                .andExpect(jsonPath("$.fullDescription").value(request.getFullDescription()))
                .andExpect(jsonPath("$.websiteUrl").value(request.getWebsiteUrl()))
                .andExpect(jsonPath("$.rating").value(request.getRating()))
                .andExpect(jsonPath("$.michelinStarRating").value(request.getMichelinStarRating()))
                .andExpect(jsonPath("$.zagatRating").value(request.getZagatRating()));

        verify(mockRestaurantRepository).save(request);
    }

    @Test
    void getRestaurants_returnsData() throws Exception {
        when(mockRestaurantRepository.findAll()).thenReturn(List.of(restaurant));

        mvc.perform(get("/restaurant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value(restaurant.getName()))
                .andExpect(jsonPath("$[0].ownerName").value(restaurant.getOwnerName()))
                .andExpect(jsonPath("$[0].headChefName").value(restaurant.getHeadChefName()))
                .andExpect(jsonPath("$[0].cuisineType").value(restaurant.getCuisineType()))
                .andExpect(jsonPath("$[0].shortDescription").value(restaurant.getShortDescription()))
                .andExpect(jsonPath("$[0].fullDescription").value(restaurant.getFullDescription()))
                .andExpect(jsonPath("$[0].websiteUrl").value(restaurant.getWebsiteUrl()))
                .andExpect(jsonPath("$[0].rating").value(restaurant.getRating()))
                .andExpect(jsonPath("$[0].michelinStarRating").value(restaurant.getMichelinStarRating()))
                .andExpect(jsonPath("$[0].zagatRating").value(restaurant.getZagatRating()));

        verify(mockRestaurantRepository).findAll();
    }
}