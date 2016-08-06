package com.juster.data.api.database.rawmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.juster.data.api.database.user.model.GuidesDetail;

import java.util.ArrayList;

/**
 * Created by deepakj on 6/8/16.
 */
public class GuideRespose {

    @SerializedName("GuidesDetail")
    @Expose
    private ArrayList<GuidesDetail> guidesDetail = new ArrayList<GuidesDetail>();

    /**
     *
     * @return
     *     The guidesDetail
     */
    public ArrayList<GuidesDetail> getGuidesDetail() {
        return guidesDetail;
    }

    /**
     * @param guidesDetail
     *     The GuidesDetail
     */
    public void setGuidesDetail(ArrayList<GuidesDetail> guidesDetail) {
        this.guidesDetail = guidesDetail;
    }
}
