/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import java.io.Serializable;
import java.util.Optional;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * Basic repository with CRUD operations
 *
 * @author trungpt
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface ICRUDRepository<T, ID extends Serializable> extends Repository<T, ID> {

    /**
     * Save entity
     *
     * @param <S>
     * @param entity The persisted entity
     * @return
     * @throws MendelRuntimeException When save failed
     */
    <S extends T> S save(S entity) throws MendelRuntimeException;

    /**
     * Find record by PK
     *
     * @param key
     * @return
     * @throws MendelRuntimeException When find failed
     */
    public Optional<T> findById(ID key) throws MendelRuntimeException;

    /**
     * Delete entity
     *
     * @param entity
     * @throws MendelRuntimeException When delete failed
     */
    public void delete(T entity) throws MendelRuntimeException;

    /**
     * Count all records
     *
     * @return
     * @throws MendelRuntimeException When counting failed
     */
    public long count() throws MendelRuntimeException;

    /**
     * Existence check
     *
     * @param primaryKey
     * @return
     * @throws MendelRuntimeException When check failed
     */
    public boolean existsById(ID primaryKey) throws MendelRuntimeException;
    // TODO Add methods
}
