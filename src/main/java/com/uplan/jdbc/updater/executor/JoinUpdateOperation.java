package com.uplan.jdbc.updater.executor;

import java.util.function.BiPredicate;

public class JoinUpdateOperation<P,ID> {

    private P updateParameter;
    private BiPredicate<P,ID> updateFunction;
    private Boolean isWithNullIncluding;

    public JoinUpdateOperation(P updateParameter, Boolean isWithNullIncluding, BiPredicate<P, ID> updateFunction) {
        this.updateParameter = updateParameter;
        this.isWithNullIncluding = isWithNullIncluding;
        this.updateFunction = updateFunction;
    }

    public P getUpdateParameter() {
        return updateParameter;
    }

    public BiPredicate<P, ID> getUpdateFunction() {
        return updateFunction;
    }

    public Boolean getWithNullIncluding() {
        return isWithNullIncluding;
    }
}
