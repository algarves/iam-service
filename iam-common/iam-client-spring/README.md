## IAM-Client for Spring
This client library makes JWT verification easier for springboot micro-services (resource-servers). 

### Easy to use
1. Add project dependency.
   * maven dependency
     ```
     <dependency>
       <groupId>one.microproject.iamservice</groupId>
       <artifactId>iam-client-spring</artifactId>
       <version>1.2.0-SNAPSHOT</version>
     <dependency/>
     ```
   * gradle dependency
     ```
     implementation 'one.microproject.iamservice:iam-client-spring:1.2.0-SNAPSHOT'
     ```
2. See [this spring-method-security example](../../iam-examples/spring-method-security)
   * How to configure http security.
   * How to enable method security.
   * How to enable OcePerRequest security filter.
   * How to secure @Controller and @Service method calls.
    