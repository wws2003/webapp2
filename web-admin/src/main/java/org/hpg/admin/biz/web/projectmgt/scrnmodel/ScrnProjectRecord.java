/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.scrnmodel;

import java.io.Serializable;
import org.hpg.common.model.dto.project.MendelProject;
import org.hpg.libcommon.DateFormatConst;
import org.hpg.libcommon.DateUtil;

/**
 * Record for one project in the project management screen
 *
 * @author trungpt
 */
public class ScrnProjectRecord implements Serializable {

    private final long id;

    private final String code;

    private final String displayedName;

    /**
     * Created timestamp in YYYY/MM/DD format
     */
    private final String cDateTimeStamp;

    /**
     * Updated timestamp in YYYY/MM/DD format
     */
    private final String mDateTimeStamp;

    /**
     * Constructor. Almost info retrieved from the base model
     *
     * @param project
     */
    public ScrnProjectRecord(MendelProject project) {
        this.id = project.getId();
        this.code = project.getCode();
        this.displayedName = project.getDisplayedName();
        this.cDateTimeStamp = DateUtil.dateTime2String(project.getcDate(), DateFormatConst.DATE_A);
        this.mDateTimeStamp = DateUtil.dateTime2String(project.getmDate(), DateFormatConst.DATE_A);
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public String getcDateTimeStamp() {
        return cDateTimeStamp;
    }

    public String getmDateTimeStamp() {
        return mDateTimeStamp;
    }

    @Override
    public String toString() {
        return "ScrnProjectRecord{" + "id=" + id + ", code=" + code + ", displayedName=" + displayedName + ", cDateTimeStamp=" + cDateTimeStamp + ", mDateTimeStamp=" + mDateTimeStamp + '}';
    }
}
