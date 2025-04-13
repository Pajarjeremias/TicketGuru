package projekti.demo.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class LipunKayttohetki {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime used;

    public LocalDateTime getUsed() {
        return used;
    }

    public void setUsed(LocalDateTime used){
        this.used = used;
    }
}
