package com.burakcanaksoy.atomiccountryaggregator.repository;

import com.burakcanaksoy.atomiccountryaggregator.model.RequestLogs;
import org.springframework.data.repository.ListCrudRepository;

public interface RequestLogsRepository extends ListCrudRepository<RequestLogs,Long>,CustomRequestLogsRepository {
}
