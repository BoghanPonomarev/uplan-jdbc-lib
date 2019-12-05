package com.uplan.jdbc.selector;

import com.uplan.jdbc.common.EntityParametersMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateEntityParametersMapperImpl implements EntityParametersMapper<TemplateEntityComposite> {

    @Override
    public Map<String, Object> mapParameters(TemplateEntityComposite composite) {
        Map<String, Object> notNullMainParameters = removeNullValues(composite.getMainParameters());
        Map<String, List<? extends Enum>> notNullEnumParameters = removeNullAndEmptyValues(composite.getEnumListParameters());
        composite.setMainParameters(notNullMainParameters);
        composite.setEnumListParameters(notNullEnumParameters);
        return constructFinalParameterMap(composite);
    }

    private <P> Map<String, P> removeNullValues(Map<String, P> allParameters) {
        return allParameters.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, List<? extends Enum>> removeNullAndEmptyValues(Map<String, List<? extends Enum>> allParameters) {
        return allParameters.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, Object> constructFinalParameterMap(TemplateEntityComposite parameterComposite) {
        Map<String, Object> resultParamMap = parameterComposite.getMainParameters();

        for (Map.Entry<String, List<? extends Enum>> enumParameter : parameterComposite.getEnumListParameters().entrySet()) {
            resultParamMap.put(enumParameter.getKey(), toEnumNames(enumParameter.getValue()));
        }
        return resultParamMap;
    }

    private List<String> toEnumNames(List<? extends Enum> enumList) {
        return enumList.stream().map(Enum::name).collect(Collectors.toList());
    }

}
