/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface for paging and sorting repository (do not create instance at
 * runtime)
 *
 * @author trungpt
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface IPagingAndSortingRepository<T, ID extends Serializable> extends ICRUDRepository<T, ID> {

    /**
     * Get all records (sorted)
     *
     * @param sort
     * @return
     */
    public Iterable<T> findAll(Sort sort);

    /**
     * Get all records (paged)
     *
     * @param pageable
     * @return
     */
    Page<T> findAll(Pageable pageable);
}
