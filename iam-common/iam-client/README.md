## IAM-Client for Java
This client library makes JWT verification easier for clients and micro-services. 

### Easy to use
1. Add project dependency.
   * maven dependency
     ```
     <dependevcy>
       <groupId>itx.iamservice</groupId>
       <artifactId>iam-client</artifactId>
       <version>1.0.0-SNAPSHOT</version>
     <dependevcy/>
     ```
   * gradle dependency
     ```
     implementation 'itx.iamservice:iam-client:1.0.0-SNAPSHOT'
     ```
2. Create client instance programmatically.
   ```
   IAMClient iamClient = IAMClientBuilder.builder()
            .setBaseUrl(new URL("http://localhost:8080/services/authentication"))
            .setOrganizationId("org-01")
            .setProjectId("project-01")
            .withHttpProxy(10L, TimeUnit.SECONDS)
            .build();
   ```
3. Verify and validate incoming JWT(s).
   ```
   while(iamClient.waitForInit(10L, TimeUnit.SECONDS)) {
   }
   HttpServletRequest httpServletRequest = ...;
   String jwt = httpServletRequest.getHeader("Authorization").split(" ")[1];
   iamClient.validate(jwt);
   ```
4. Check [AIMClient API](src/main/java/itx/iamservice/client/IAMClient.java) for other validation options.   