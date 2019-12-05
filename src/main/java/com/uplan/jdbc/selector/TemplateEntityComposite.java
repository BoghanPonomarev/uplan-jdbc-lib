package com.uplan.jdbc.selector;

import com.uplan.jdbc.common.EntityComposite;

import java.util.List;
import java.util.Map;

public class TemplateEntityComposite extends EntityComposite {

    private Map<String, Object> mainParameters;
    private Map<String, List<? extends Enum>> enumListParameters;

    public TemplateEntityComposite(Map<String, Object> mainParameters, Map<String, List<? extends Enum>> enumListParameters) {
        this.mainParameters = mainParameters;
        this.enumListParameters = enumListParameters;
    }

    public Map<String, Object> getMainParameters() {
        return mainParameters;
    }

    public Map<String, List<? extends Enum>> getEnumListParameters() {
        return enumListParameters;
    }

    public void setMainParameters(Map<String, Object> mainParameters) {
        this.mainParameters = mainParameters;
    }

    public void setEnumListParameters(Map<String, List<? extends Enum>> enumListParameters) {
        this.enumListParameters = enumListParameters;
    }

}
