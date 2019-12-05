package com.uplan.jdbc.updater;

public interface EntityUpdater {

    boolean updateEntity(UpdateEntityComposite entityUpdateComposite, String tableName);

}
