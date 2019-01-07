/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.user.biz.search.impl;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.common.xcontent.XContentBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.hpg.common.model.dto.document.Document;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.user.biz.search.abstr.IDocumentSearchService;

/**
 * Implementation for document search service
 *
 * @author trungpt
 */
public class DocumentSearchServiceImpl implements IDocumentSearchService {

    @Override
    public void indexDocument(Document document) throws MendelRuntimeException {
        try {
//            TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("host1"), 9300))
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("host2"), 9300));

            // TODO Get pages
            XContentBuilder xContentBuilder = jsonBuilder().startObject()
                    .field("content", "xxxxx")
                    .field("cdate", new Date())
                    .endObject();

        } catch (IOException ex) {
            Logger.getLogger(DocumentSearchServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
