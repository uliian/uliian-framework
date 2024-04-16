CREATE TABLE IF NOT EXISTS sys_role (
                                        role_id BIGINT PRIMARY KEY,
                                        role_name VARCHAR(255),
                                        role_key VARCHAR(255),
                                        role_sort INT,
                                        data_scope VARCHAR(255),
                                        menu_check_strictly BOOLEAN,
                                        dept_check_strictly BOOLEAN,
                                        status VARCHAR(255),
                                        del_flag VARCHAR(255),
                                        create_by VARCHAR(255),
                                        create_time TIMESTAMP,
                                        update_by VARCHAR(255),
                                        update_time TIMESTAMP,
                                        remark VARCHAR(255)
);