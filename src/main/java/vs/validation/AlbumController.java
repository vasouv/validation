package vs.validation;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("albums")
public class AlbumController {

    private static final Logger log = LoggerFactory.getLogger(AlbumController.class);

    @PostMapping
    public void save(@Valid @RequestBody Album album) {
        log.info("Saving album: " + album);
    }

    @GetMapping("{year}")
    public Album year(@PathVariable @Min(value = 1980, message = "Not earlier than 1980") int year) {
        return new Album("Some title", "Some band", year);
    }
    
    @GetMapping
    public Album band(@RequestParam("band") @NotBlank(message = "Must provide a band") String band) {
        return new Album("Nightfall in Middle Earth", band, 1998);
    }

}
