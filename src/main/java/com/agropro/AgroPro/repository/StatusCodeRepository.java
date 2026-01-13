package com.agropro.AgroPro.repository;

import java.util.Optional;

public interface StatusCodeRepository {

    Optional<Long> findStatusIdByStatusCode(String statusCode);

}
