DELIMITER //

CREATE PROCEDURE migrate_users_logical_delete_index()
BEGIN
  DECLARE legacy_username_index_name VARCHAR(64);

  IF NOT EXISTS (
    SELECT 1
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'users'
      AND COLUMN_NAME = 'deleted'
  ) THEN
    ALTER TABLE users ADD COLUMN deleted BIGINT NOT NULL DEFAULT 0 AFTER status;
  ELSE
    ALTER TABLE users MODIFY COLUMN deleted BIGINT NOT NULL DEFAULT 0;
  END IF;

  SELECT INDEX_NAME
  INTO legacy_username_index_name
  FROM information_schema.STATISTICS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'users'
    AND NON_UNIQUE = 0
  GROUP BY INDEX_NAME
  HAVING SUM(CASE WHEN COLUMN_NAME = 'username' THEN 1 ELSE 0 END) = 1
     AND COUNT(*) = 1
  LIMIT 1;

  IF legacy_username_index_name IS NOT NULL THEN
    SET @drop_legacy_username_index_sql =
      CONCAT('ALTER TABLE users DROP INDEX ', legacy_username_index_name);
    PREPARE drop_legacy_username_index_statement FROM @drop_legacy_username_index_sql;
    EXECUTE drop_legacy_username_index_statement;
    DEALLOCATE PREPARE drop_legacy_username_index_statement;
  END IF;

  IF NOT EXISTS (
    SELECT 1
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'users'
      AND INDEX_NAME = 'uk_users_username_deleted'
  ) THEN
    ALTER TABLE users ADD UNIQUE KEY uk_users_username_deleted (username, deleted);
  END IF;
END//

CALL migrate_users_logical_delete_index()//

DROP PROCEDURE migrate_users_logical_delete_index//

DELIMITER ;
