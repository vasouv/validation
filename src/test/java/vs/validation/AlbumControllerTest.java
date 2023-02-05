package vs.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Save new album - correct data - returns 200")
    public void testSaveNewAlbumCorrectData() throws Exception {

        Album album = new Album("Random Title", "Random Band", 2005);
        String body = mapper.writeValueAsString(album);

        mvc.perform(post("/albums")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Save new album - null title - returns 400")
    public void testSaveNewAlbumNullTitle() throws Exception {
        Album album = new Album("Random Title", null, 2005);
        String body = mapper.writeValueAsString(album);

        mvc.perform(post("/albums")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Save new album - empty band - returns 400")
    public void testSaveNewAlbumEmptyBand() throws Exception {
        Album album = new Album("", "Random Band", 2005);
        String body = mapper.writeValueAsString(album);

        mvc.perform(post("/albums")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isBadRequest());
    }

}
