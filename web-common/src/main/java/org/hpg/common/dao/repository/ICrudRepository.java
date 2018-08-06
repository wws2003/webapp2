/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import java.io.Serializable;
import java.util.Optional;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.data.repository.Repository;

/**
 * Basic repository with CRUD operations
 *
 * @author trungpt
 * @param <T>
 * @param <ID>
 */
public interface ICrudRepository<T, ID extends Serializable> extends Repository<T, ID> {

    /**
     * Save entiry
     *
     * @param <S>
     * @param entity The persisted entiry
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
    Optional<T> findByPK(ID key) throws MendelRuntimeException;

    /**
     * Delete entity
     *
     * @param entity
     * @return Number of record deleted
     * @throws MendelRuntimeException When delete failed
     */
    int delete(T entity) throws MendelRuntimeException;

    /**
     * Update entity
     *
     * @param entity
     * @return Number of record updated
     * @throws MendelRuntimeException When update failed
     */
    int update(T entity) throws MendelRuntimeException;

    /**
     * Count all records
     *
     * @return
     * @throws MendelRuntimeException When couting failed
     */
    long count() throws MendelRuntimeException;

    // TODO Add methods
}
