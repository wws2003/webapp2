/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.dao.repository.es;

import javax.inject.Inject;
import org.hpg.project.ProjectTestBase;
import org.hpg.project.model.entity.es.EsDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test for document Spring Data-ES repository
 *
 * @author trungpt
 */
@RunWith(SpringRunner.class)
public class IEsDocumentRepositoryTest extends ProjectTestBase {

    @Inject
    private IEsDocumentRepository documentRepository;

    public IEsDocumentRepositoryTest() {
    }

    public void testCreateIndex() {

    }

    @Test
    public void testPutSampleDocument() {
        EsDocument document = new EsDocument();
        document.setContent("Test content... Should be longer ?");
        document.setProjectId(1);
        document.setDocumentId(1);
        document.setPageId(1);

        EsDocument doc = documentRepository.save(document);
        assert (doc.getId() > 0);
    }

    public void testSearch1() {

    }
}
