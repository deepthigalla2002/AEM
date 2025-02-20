package com.Project.core.models;

import com.adobe.cq.sightly.WCMUsePojo;
import javax.inject.Inject;

public class TextToSpeechModel extends WCMUsePojo {

    @Inject
    private String text;

    @Override
    public void activate() throws Exception {
        // Ensure text is not null
        if (text == null) {
            text = "";
        }
    }

    public String getText() {
        return text;
    }
}
