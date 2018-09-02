/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.io.Serializable;
import org.hpg.common.biz.service.abstr.IPagingService;
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.dao.repository.IPagingAndSortingRepository;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Implementation of paging service with entity and DTO
 *
 * @author wws2003
 * @param <ET>
 * @param <DT>
 * @param <ID>
 */
public class PagingServiceImpl<ET, DT, ID extends Serializable> implements IPagingService<DT> {

    /**
     * Entity-DTO mapper
     */
    private final IEntityDtoMapper<ET, DT> mapper;

    /**
     * Repository to access entities
     */
    private final IPagingAndSortingRepository<ET, ID> repository;

    /**
     * Constructor
     *
     * @param mapper
     * @param repository
     */
    public PagingServiceImpl(IEntityDtoMapper<ET, DT> mapper, IPagingAndSortingRepository<ET, ID> repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Page<DT> getPage(Pageable pageRequest) throws MendelRuntimeException {
        return repository.findAll(pageRequest)
                .map(mapper::getDtoFromEntity);
    }
}
