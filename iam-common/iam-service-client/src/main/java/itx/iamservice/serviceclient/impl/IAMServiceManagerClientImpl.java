package itx.iamservice.serviceclient.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import itx.iamservice.core.model.OrganizationId;
import itx.iamservice.core.model.ProjectId;
import itx.iamservice.core.model.utils.ModelUtils;
import itx.iamservice.core.services.dto.OrganizationInfo;
import itx.iamservice.core.services.dto.SetupOrganizationRequest;
import itx.iamservice.core.services.dto.SetupOrganizationResponse;
import itx.iamservice.serviceclient.IAMAuthorizerClient;
import itx.iamservice.serviceclient.IAMServiceManagerClient;
import itx.iamservice.serviceclient.IAMServiceProjectManagerClient;
import itx.iamservice.serviceclient.IAMServiceStatusClient;
import itx.iamservice.serviceclient.IAMServiceUserManagerClient;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class IAMServiceManagerClientImpl implements IAMServiceManagerClient {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String APPLICATION_JSON = "application/json";

    private final URL baseURL;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public IAMServiceManagerClientImpl(URL baseURL, Long timeoutDuration, TimeUnit timeUnit) {
        this.baseURL = baseURL;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(timeoutDuration, timeUnit)
                .build();
        this.mapper = new ObjectMapper();
    }

    @Override
    public SetupOrganizationResponse setupOrganization(String accessToken, SetupOrganizationRequest setupOrganizationRequest) throws AuthenticationException {
        try {
            Request request = new Request.Builder()
                    .url(baseURL + "/services/admin/organization/setup")
                    .addHeader(AUTHORIZATION, BEARER_PREFIX + accessToken)
                    .post(RequestBody.create(mapper.writeValueAsString(setupOrganizationRequest), MediaType.parse(APPLICATION_JSON)))
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return mapper.readValue(response.body().string(), SetupOrganizationResponse.class);
            }
            throw new AuthenticationException("Authentication failed: " + response.code());
        } catch (IOException e) {
            throw new AuthenticationException(e);
        }
    }

    @Override
    public void deleteOrganizationRecursively(String accessToken, OrganizationId organizationId) throws AuthenticationException {
        try {
            Request request = new Request.Builder()
                    .url(baseURL + "/services/admin/organization/" + organizationId.getId())
                    .addHeader(AUTHORIZATION, BEARER_PREFIX + accessToken)
                    .delete()
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return;
            }
            throw new AuthenticationException("Authentication failed: " + response.code());
        } catch (IOException e) {
            throw new AuthenticationException(e);
        }
    }

    @Override
    public IAMServiceProjectManagerClient getIAMServiceProject(String accessToken, OrganizationId organizationId, ProjectId projectId) {
        return new IAMServiceProjectManagerClientImpl(accessToken, baseURL, client, mapper, organizationId, projectId);
    }

    @Override
    public IAMServiceStatusClient getIAMServiceStatusClient(OrganizationId organizationId, ProjectId projectId) {
        return new IAMServiceStatusClientImpl(baseURL, client, mapper, organizationId, projectId);
    }

    @Override
    public IAMAuthorizerClient getIAMAuthorizerClient(OrganizationId organizationId, ProjectId projectId) {
        return new IAMAuthorizerClientImpl(baseURL, client, mapper, organizationId, projectId);
    }

    @Override
    public IAMAuthorizerClient getIAMAdminAuthorizerClient() {
        return new IAMAuthorizerClientImpl(baseURL, client, mapper, ModelUtils.IAM_ADMINS_ORG, ModelUtils.IAM_ADMINS_PROJECT);
    }

    @Override
    public IAMServiceUserManagerClient getIAMServiceUserManagerClient(String accessToken, OrganizationId organizationId, ProjectId projectId) {
        return new IAMServiceUserManagerClientImpl(accessToken, baseURL, client, mapper, organizationId, projectId);
    }

    @Override
    public Collection<OrganizationInfo> getOrganizations() throws IOException {
         Request request = new Request.Builder()
                 .url(baseURL + "/services/discovery")
                 .get()
                 .build();
         Response response = client.newCall(request).execute();
         if (response.code() == 200) {
             return mapper.readValue(response.body().string(), new TypeReference<List<OrganizationInfo>>(){});
         } else {
             return Collections.emptyList();
         }
    }

    @Override
    public OrganizationInfo getOrganization(OrganizationId organizationId) throws IOException {
        Request request = new Request.Builder()
                .url(baseURL + "/services/discovery/" + organizationId.getId())
                .get()
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            return mapper.readValue(response.body().string(), OrganizationInfo.class);
        }
        throw new IOException();
    }

    @Override
    public String getActuatorInfo() throws IOException {
        Request request = new Request.Builder()
                .url(baseURL + "/actuator/info")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            return response.body().string();
        }
        throw new IOException();
    }

}