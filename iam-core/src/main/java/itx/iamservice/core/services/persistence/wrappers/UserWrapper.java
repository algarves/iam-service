package itx.iamservice.core.services.persistence.wrappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import itx.iamservice.core.model.User;
import itx.iamservice.core.model.keys.ModelKey;

public class UserWrapper {

    private final ModelKey<User> key;
    private final User value;

    @JsonCreator
    public UserWrapper(@JsonProperty("key") ModelKey<User> key,
                       @JsonProperty("value") User value) {
        this.key = key;
        this.value = value;
    }

    public ModelKey<User> getKey() {
        return key;
    }

    public User getValue() {
        return value;
    }

}
