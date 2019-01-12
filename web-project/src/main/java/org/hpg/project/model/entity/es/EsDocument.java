/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.model.entity.es;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 *
 * Model for document
 *
 * @author wws2003
 */
@Document(indexName = "", createIndex = false)
public class EsDocument {

    private long pageId;

    private long documentId;

    private long projectId;
}
