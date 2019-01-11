/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.user.model.entity.es;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Entity class for document (used by Spring data elastic search)
 *
 * @author trungpt
 */
@Document(indexName = "mendel", createIndex = false)
public class EsDocument {

    private long projectID;

    private long docID;

    private long pageID;

    private String content;
}
