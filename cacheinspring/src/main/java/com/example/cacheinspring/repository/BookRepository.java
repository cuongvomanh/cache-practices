package com.example.cacheinspring.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByTenantId(int tenantId);
    List<Book> findAllByTenantIdAndStatus(int tenantId, int status);
}
