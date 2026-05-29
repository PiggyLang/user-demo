DELIMITER //

CREATE PROCEDURE add_users_page_query_indexes()
BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'users'
      AND INDEX_NAME = 'idx_users_deleted_id'
  ) THEN
    ALTER TABLE users ADD INDEX idx_users_deleted_id (deleted, id);
  END IF;

  IF NOT EXISTS (
    SELECT 1
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'users'
      AND INDEX_NAME = 'idx_users_deleted_status_id'
  ) THEN
    ALTER TABLE users ADD INDEX idx_users_deleted_status_id (deleted, status, id);
  END IF;
END//

CALL add_users_page_query_indexes()//

DROP PROCEDURE add_users_page_query_indexes//

DELIMITER ;
