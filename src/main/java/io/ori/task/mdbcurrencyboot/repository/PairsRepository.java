package io.ori.task.mdbcurrencyboot.repository;

import io.ori.task.mdbcurrencyboot.service.entity.Pairs;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PairsRepository extends MongoRepository<Pairs, String> {
    @Query(value = "{'pair': '?0'}", fields = "{'_id': 1, 'low': 1}")
    List<Pairs> findMinPrice(String name);
    @Query(value = "{'pair':  '?0'}", fields = "{'_id': 1, 'high': 1}")
    List<Pairs> findMaxPrice(String name);
    Page<Pairs> findByPair(String name, Pageable pageable);
    void insert(Document stringPairsHttpClientSync);
    List<Pairs> findAllByPair(String name);
}
