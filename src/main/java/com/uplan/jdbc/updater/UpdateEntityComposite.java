package com.uplan.jdbc.updater;

import com.uplan.jdbc.common.EntityComposite;

import java.util.Map;

public class UpdateEntityComposite extends EntityComposite {

    private Map<String, Object> mainParameters;
    private String keyName;
    private Object keyValue;

    private Boolean isWithNullIncluding;

    public UpdateEntityComposite(Map<String, Object> mainParameters, String keyName, Object keyValue, Boolean isWithNullIncluding) {
        this.mainParameters = mainParameters;
        this.keyName = keyName;
        this.keyValue = keyValue;
        this.isWithNullIncluding = isWithNullIncluding;
    }

    public UpdateEntityComposite(Map<String, Object> mainParameters, String keyName, Object keyValue) {
        this.mainParameters = mainParameters;
        this.keyName = keyName;
        this.keyValue = keyValue;
    }

    public Map<String, Object> getMainParameters() {
        return mainParameters;
    }

    public String getKeyName() {
        return keyName;
    }

    public Object getKeyValue() {
        return keyValue;
    }

    public void setMainParameters(Map<String, Object> mainParameters) {
        this.mainParameters = mainParameters;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public void setKeyValue(Object keyValue) {
        this.keyValue = keyValue;
    }

    public Boolean getWithNullIncluding() {
        return isWithNullIncluding;
    }

    public void setWithNullIncluding(Boolean withNullIncluding) {
        isWithNullIncluding = withNullIncluding;
    }
}
