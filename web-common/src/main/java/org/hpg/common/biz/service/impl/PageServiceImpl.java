/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.List;
import java.util.Optional;
import org.hpg.common.biz.service.abstr.IPageService;
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.dao.repository.IPageRepository;
import org.hpg.common.model.dto.document.MendelPage;
import org.hpg.common.model.entity.PageEntity;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Implementation for page service
 *
 * @author wws2003
 */
public class PageServiceImpl implements IPageService {

    private final IPageRepository pageRepository;

    private final IEntityDtoMapper<PageEntity, MendelPage> pageEntityDtoMapper;

    public PageServiceImpl(IPageRepository pageRepository, IEntityDtoMapper<PageEntity, MendelPage> pageEntityDtoMapper) {
        this.pageRepository = pageRepository;
        this.pageEntityDtoMapper = pageEntityDtoMapper;
    }

    @Override
    public MendelPage createPage(MendelPage page) throws MendelRuntimeException {
        PageEntity savedEntity = pageRepository.save(pageEntityDtoMapper.getEntityFromDto(page));
        return Optional.ofNullable(savedEntity).map(pageEntityDtoMapper::getDtoFromEntity).orElse(null);
    }

    @Override
    public MendelPage updatePage(MendelPage page) throws MendelRuntimeException {
        PageEntity savedEntity = pageRepository.save(pageEntityDtoMapper.getEntityFromDto(page));
        return Optional.ofNullable(savedEntity).map(pageEntityDtoMapper::getDtoFromEntity).orElse(null);
    }

    @Override
    public void deletePage(MendelPage page) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletePages(long documentId) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MendelPage> getDocumentPages(long documentId) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MendelPage> getDocumentPages(long documentId, List<Long> pageNos) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
