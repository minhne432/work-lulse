SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
TRUNCATE TABLE departments;
TRUNCATE TABLE roles;
TRUNCATE TABLE users;
TRUNCATE TABLE activity_logs;
TRUNCATE TABLE daily_summary;

SET FOREIGN_KEY_CHECKS = 1;

-- Băt đầu giao dịch
START TRANSACTION;
-- 1. Phòng ban (departments)
INSERT INTO departments (id, name, description) VALUES
  (1, 'R&D', 'Research & Development'),
  (2, 'Engineering', 'Engineering Team'),
  (3, 'HR', 'Human Resources'),
  (4, 'Dev', 'Development Team');

-- 2. Phân quyền (roles)
INSERT INTO roles (id, name, description) VALUES
  (1, 'admin', 'Administrator'),
  (2, 'employee', 'Employee');

-- 3. Người dùng (users)
-- Lưu ý: dùng {noop} để Spring Security coi password là plain-text (cho demo).
INSERT INTO users (id, username, password_hash, full_name, role_id, department_id, created_at) VALUES
  (1, 'admin',   '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6',  'Super Admin',   1, NULL, '2025-04-01 08:00:00'),
  (2, 'john.doe','$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6',   'John Doe',      2, 1,    '2025-04-10 09:00:00'),
  (3, 'jane.smith','$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'Jane Smith',    2, 2,    '2025-04-10 09:15:00'),
  (4, 'bob.nguyen','$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'Bob Nguyen',    2, 3,    '2025-04-15 08:30:00'),
  (5, 'alice.tran','$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'Alice Tran',    2, 4,    '2025-04-18 10:00:00');

-- 4. Lịch sử hoạt động chi tiết (activity_logs)
INSERT INTO activity_logs (id, user_id, app_name, window_title, url, screenshot_path,
                           start_time, end_time, created_at) VALUES
  (1, 2, 'IntelliJ IDEA', 'WorkPulse - Main.java', NULL, NULL,
       '2025-04-24 08:00:00','2025-04-24 12:00:00','2025-04-24 08:00:00'),
  (2, 2, 'Google Chrome','Dashboard', 'http://localhost:8080/dashboard',
       '/screenshots/2/20250424_120000.png',
       '2025-04-24 13:00:00','2025-04-24 17:00:00','2025-04-24 13:00:00'),
  (3, 3, 'Microsoft Word','Project Specification', NULL, NULL,
       '2025-04-25 09:00:00','2025-04-25 11:30:00','2025-04-25 09:00:00'),
  (4, 3, 'Slack','Team Chat', NULL, NULL,
       '2025-04-25 11:45:00','2025-04-25 17:00:00','2025-04-25 11:45:00');

-- 5. Tóm tắt hàng ngày (daily_summary)
INSERT INTO daily_summary (id, user_id, summary_date, total_active_time, total_inactive_time, most_used_app) VALUES
  (1, 2, '2025-04-24', 28800, 0,      'IntelliJ IDEA'),
  (2, 3, '2025-04-25', 24300, 3600,   'Microsoft Word');

-- HOÀN TẤT GIAO DỊCH
COMMIT;