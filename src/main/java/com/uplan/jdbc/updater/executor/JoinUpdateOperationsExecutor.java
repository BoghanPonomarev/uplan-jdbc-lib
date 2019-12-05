package com.uplan.jdbc.updater.executor;

import java.util.List;

public interface JoinUpdateOperationsExecutor<ID> {

    boolean execute(List<JoinUpdateOperation<?, ID>> operations, ID entityId);

}
