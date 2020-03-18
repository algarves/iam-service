package itx.iamservice.services.impl;

import itx.iamservice.services.HealthCheckService;
import itx.iamservice.services.dto.HealthCheckResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {

    private static final Logger LOG = LoggerFactory.getLogger(HealthCheckServiceImpl.class);

    @Override
    public HealthCheckResponse getStatus() {
        LOG.info("getStatus");
        return new HealthCheckResponse("OK", "1.0.0", System.currentTimeMillis());
    }

}
