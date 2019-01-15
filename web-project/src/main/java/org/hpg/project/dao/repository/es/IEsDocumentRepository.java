/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.dao.repository.es;

import org.hpg.common.dao.repository.IPagingAndSortingRepository;
import org.hpg.project.model.entity.es.EsDocument;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wws2003
 */
@Repository
public interface IEsDocumentRepository extends IPagingAndSortingRepository<EsDocument, Long> {

}
