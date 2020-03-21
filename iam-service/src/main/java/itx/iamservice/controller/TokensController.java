package itx.iamservice.controller;

import itx.iamservice.core.model.OrganizationId;
import itx.iamservice.core.model.ProjectId;
import itx.iamservice.core.services.ClientService;
import itx.iamservice.core.services.ResourceServerService;
import itx.iamservice.core.services.dto.JWToken;
import itx.iamservice.services.dto.TokenRevokeResponse;
import itx.iamservice.services.dto.TokenVerificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/services/tokens")
public class TokensController {

    private final ResourceServerService resourceServerService;
    private final ClientService clientService;

    public TokensController(@Autowired ResourceServerService resourceServerService,
                            @Autowired ClientService clientService) {
        this.resourceServerService = resourceServerService;
        this.clientService = clientService;
    }

    @PostMapping(path = "/{organization-id}/{project-id}/verify", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<TokenVerificationResponse> verify(@PathVariable("organization-id") String organizationId,
                                                            @PathVariable("project-id") String projectId,
                                                            @RequestParam("token") String token) {
        boolean valid = resourceServerService.verify(OrganizationId.from(organizationId), ProjectId.from(projectId), JWToken.from(token));
        return ResponseEntity.ok(new TokenVerificationResponse(valid));
    }

    @PostMapping(path = "/{organization-id}/{project-id}/revoke", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<TokenRevokeResponse> revoke(@PathVariable("organization-id") String organizationId,
                                                      @PathVariable("project-id") String projectId,
                                                      @RequestParam("token") String token) {
        boolean valid = clientService.revoke(OrganizationId.from(organizationId), ProjectId.from(projectId), JWToken.from(token));
        return ResponseEntity.ok(new TokenRevokeResponse(valid));
    }

}