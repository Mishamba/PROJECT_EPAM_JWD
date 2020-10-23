package com.mishamba.project.tag.former;

import com.mishamba.project.model.Hometask;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class HometaskForListFormer extends TagSupport {
    private Hometask hometask;

    public void setHometask(Hometask hometask) {
        this.hometask = hometask;
    }
    @Override
    public int doStartTag() throws JspException {
        HometaskFormerTag hometaskFormerTag = new HometaskFormerTag();
        hometaskFormerTag.setHometask(hometask);
        HometaskResponseFormer hometaskResponseFormer = new HometaskResponseFormer();
        hometaskFormerTag.setHometask(hometask);

        hometaskFormerTag.doStartTag();
        hometaskResponseFormer.doStartTag();

        return SKIP_BODY;
    }
}
