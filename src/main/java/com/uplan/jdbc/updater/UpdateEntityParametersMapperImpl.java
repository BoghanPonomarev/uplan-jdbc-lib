package com.uplan.jdbc.updater;

import com.uplan.jdbc.common.EntityParametersMapper;

import java.util.Map;
import java.util.stream.Collectors;

public class UpdateEntityParametersMapperImpl implements EntityParametersMapper<UpdateEntityComposite> {

    @Override
    public Map<String, Object> mapParameters(UpdateEntityComposite composite) {
        Map<String, Object> mainParameters = composite.getMainParameters();
        mainParameters.put(composite.getKeyName(),composite.getKeyValue());
        return composite.getWithNullIncluding() ? mainParameters : removeNullValues(mainParameters);
    }

    private <P> Map<String, P> removeNullValues(Map<String, P> allParameters) {
        return allParameters.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
