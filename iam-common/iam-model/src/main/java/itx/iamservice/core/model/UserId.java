package itx.iamservice.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import itx.iamservice.core.model.keys.Id;

public final class UserId extends Id {

    @JsonCreator
    public UserId(@JsonProperty("id") String id) {
        super(id);
    }

    public static UserId from(String id) {
        return new UserId(id);
    }

}