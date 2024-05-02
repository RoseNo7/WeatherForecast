package kr.or.bigs.app.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class ForecastControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("단기 예보 조회")
    @Test
    public void testGetForecasts_Success_NoContent() throws Exception {
        mockMvc.perform(get("/forecasts"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @DisplayName("단기 예보 저장")
    @Test
    public void testSetForecasts_Success_OK() throws Exception {
        mockMvc.perform(post("/forecasts"))
                .andExpect(status().isOk());
    }
}