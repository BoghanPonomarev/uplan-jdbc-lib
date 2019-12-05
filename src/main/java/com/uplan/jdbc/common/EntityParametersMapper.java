package com.uplan.jdbc.common;

import java.util.Map;

public interface EntityParametersMapper<T> {

    Map<String,Object> mapParameters(T composite);

}
