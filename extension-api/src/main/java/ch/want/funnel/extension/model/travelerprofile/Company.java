package ch.want.funnel.extension.model.travelerprofile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Company extends ContactInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idInProfileTool;
    private final Map<String, String> additionalData = new HashMap<>();

    public String getIdInProfileTool() {
        return idInProfileTool;
    }

    public void setIdInProfileTool(final String idInProfileTool) {
        this.idInProfileTool = idInProfileTool;
    }

    public Map<String, String> getAdditionalData() {
        return additionalData;
    }
}
