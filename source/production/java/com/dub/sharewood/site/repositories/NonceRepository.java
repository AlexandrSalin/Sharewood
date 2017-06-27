package com.dub.sharewood.site.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dub.sharewood.site.entities.Nonce;

public interface NonceRepository extends CrudRepository<Nonce, Long>
{
    Nonce getByValueAndTimestamp(String value, long timestamp);

    @Modifying
    @Query("DELETE FROM Nonce n WHERE n.timestamp < :timestamp")
    void deleteWhereTimestampLessThan(@Param("timestamp") long timestamp);
}


