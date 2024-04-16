INSERT INTO sys_role (role_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly,
                      status, del_flag, create_by, create_time, update_by, update_time, remark)
VALUES (1, 'Admin', 'admin', 1, '1', true, false, '0', '0', 'admin', '2024-04-16 08:00:00', 'admin',
        '2024-04-16 08:00:00', 'Superuser role'),
       (2, 'Manager', 'manager', 2, '2', true, false, '0', '0', 'manager', '2024-04-16 08:00:00', 'manager',
        '2024-04-16 08:00:00', 'Manager role'),
       (3, 'User', 'user', 3, '3', true, false, '0', '0', 'user', '2024-04-16 08:00:00', 'user', '2024-04-16 08:00:00',
        'Regular user role');