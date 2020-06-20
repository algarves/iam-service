package itx.iamservice.core.model.builders;

import itx.iamservice.core.model.PKIException;
import itx.iamservice.core.model.RoleId;
import itx.iamservice.core.model.User;
import itx.iamservice.core.model.UserId;
import itx.iamservice.core.model.extensions.authentication.up.UPCredentials;
import itx.iamservice.core.services.caches.ModelCache;

public final class UserBuilder {

    private final ProjectBuilder projectBuilder;
    private final User user;

    public UserBuilder(ProjectBuilder projectBuilder, User user) {
        this.projectBuilder = projectBuilder;
        this.user = user;
    }

    public UserBuilder addRole(RoleId roleId) {
        user.addRole(roleId);
        return this;
    }

    public UserBuilder addUserNamePasswordCredentials(UserId userId, String password) throws PKIException {
        UPCredentials upCredentials = new UPCredentials(userId, password);
        user.addCredentials(upCredentials);
        return this;
    }

    public UserBuilder addUserNamePasswordCredentials(String userName, String password) throws PKIException {
        UserId userId = UserId.from(userName);
        UPCredentials upCredentials = new UPCredentials(userId, password);
        user.addCredentials(upCredentials);
        return this;
    }

    public ProjectBuilder and() {
        return projectBuilder;
    }

    public ModelCache build() {
        return projectBuilder.build();
    }

}
