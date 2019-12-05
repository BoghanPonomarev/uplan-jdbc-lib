package com.uplan.jdbc.updater.executor;

import java.util.List;

public class JoinUpdateOperationsExecutorImpl<ID> implements JoinUpdateOperationsExecutor<ID> {

    @Override
    public boolean execute(List<JoinUpdateOperation<?, ID>> joinUpdateOperations, ID entityId) {
        boolean isAllOperationsSuccess = true;

        for(JoinUpdateOperation<?, ID> joinUpdateOperation : joinUpdateOperations) {
            isAllOperationsSuccess &= executeSingleOperation(joinUpdateOperation, entityId);
        }
        return isAllOperationsSuccess;
    }

    private <T> boolean  executeSingleOperation(JoinUpdateOperation<T, ID> operation, ID entityId) {
        T parameter = operation.getUpdateParameter();
        return parameter == null && (!operation.getWithNullIncluding() || operation.getUpdateFunction().test(parameter, entityId))
                || parameter != null &&  operation.getUpdateFunction().test(parameter, entityId) ;
    }

}
