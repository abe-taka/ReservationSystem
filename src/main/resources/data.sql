
INSERT IGNORE INTO m01_student (m01_student_code, m01_student_name, m01_student_password, m01_student_stat, m01_assistant_flag) VALUES ('nhs00000', 'test1', '1234', 0, 0);
INSERT IGNORE INTO m01_student (m01_student_code, m01_student_name, m01_student_password, m01_student_stat, m01_assistant_flag) VALUES ('nhs00001', 'test2', '1234', 0, 0);
INSERT IGNORE INTO m01_student (m01_student_code, m01_student_name, m01_student_password, m01_student_stat, m01_assistant_flag) VALUES ('nhs00002', 'test3', '1234', 0, 0);
INSERT IGNORE INTO m01_student (m01_student_code, m01_student_name, m01_student_password, m01_student_stat, m01_assistant_flag) VALUES ('nhs00003', 'test4', '1234', 0, 0);

INSERT IGNORE INTO v01_floor (`v01_floor_code`) VALUES ('19');
INSERT IGNORE INTO v01_floor (`v01_floor_code`) VALUES ('21');

INSERT IGNORE INTO m03_class (m03_class_code, m03_class_type) VALUES ('IH14A203', '0');

INSERT IGNORE INTO m04_student_regist (m04_student_code, m04_class_no, m04_class_code) VALUES ('nhs00000', 1, 'IH14A203');

INSERT IGNORE INTO m06_machine (`m06_machine_code`,`m06_count`,`m06_machine_name`,`m06_floor_code`) VALUES ('19ABCD1',1,'19ABCD1','19');
INSERT IGNORE INTO m06_machine (`m06_machine_code`,`m06_count`,`m06_machine_name`,`m06_floor_code`) VALUES ('19ABCD2',1,'19ABCD2','19');
INSERT IGNORE INTO m06_machine (`m06_machine_code`,`m06_count`,`m06_machine_name`,`m06_floor_code`) VALUES ('19ABCD3',51,'19ABCD3','19');
INSERT IGNORE INTO m06_machine (`m06_machine_code`,`m06_count`,`m06_machine_name`,`m06_floor_code`) VALUES ('19ABCD4',1,'19ABCD4','19');
INSERT IGNORE INTO m06_machine (`m06_machine_code`,`m06_count`,`m06_machine_name`,`m06_floor_code`) VALUES ('21ABCD1',1,'21ABCD1','21');
INSERT IGNORE INTO m06_machine (`m06_machine_code`,`m06_count`,`m06_machine_name`,`m06_floor_code`) VALUES ('21ABCD2',1,'21ABCD2','21');
INSERT IGNORE INTO m06_machine (`m06_machine_code`,`m06_count`,`m06_machine_name`,`m06_floor_code`) VALUES ('21ABCD3',1,'21ABCD3','21');

INSERT IGNORE INTO m05_class_usable_machine (`m05_class_code`,`m05_machine_code`) VALUES ('IH14A203','19ABCD1');
INSERT IGNORE INTO m05_class_usable_machine (`m05_class_code`,`m05_machine_code`) VALUES ('IH14A203','19ABCD3');
INSERT IGNORE INTO m05_class_usable_machine (`m05_class_code`,`m05_machine_code`) VALUES ('IH14A203','21ABCD1');
INSERT IGNORE INTO m05_class_usable_machine (`m05_class_code`,`m05_machine_code`) VALUES ('IH14A203','21ABCD2');

INSERT IGNORE INTO m07_soft (`m07_soft_code`,`m07_maker`,`m07_soft_name`) VALUES ('AD001','Adobe','Adobe Photoshop');
INSERT IGNORE INTO m07_soft (`m07_soft_code`,`m07_maker`,`m07_soft_name`) VALUES ('AD002','Adobe','Adobe Illustrator');
INSERT IGNORE INTO m07_soft (`m07_soft_code`,`m07_maker`,`m07_soft_name`) VALUES ('AD003','Adobe','Adobe XD');
INSERT IGNORE INTO m07_soft (`m07_soft_code`,`m07_maker`,`m07_soft_name`) VALUES ('AD004','Adobe','Adobe Premier');
INSERT IGNORE INTO m07_soft (`m07_soft_code`,`m07_maker`,`m07_soft_name`) VALUES ('MS001','Microsoft','Microsoft Office');
INSERT IGNORE INTO m07_soft (`m07_soft_code`,`m07_maker`,`m07_soft_name`) VALUES ('MS002','Microsoft','Microsoft Teams');

INSERT IGNORE INTO m08_machine_soft (`m08_machine_code`,`m08_soft_code`,`m08_floor_code`) VALUES ('19ABCD1','AD001','19');
INSERT IGNORE INTO m08_machine_soft (`m08_machine_code`,`m08_soft_code`,`m08_floor_code`) VALUES ('19ABCD1','AD002','19');
INSERT IGNORE INTO m08_machine_soft (`m08_machine_code`,`m08_soft_code`,`m08_floor_code`) VALUES ('19ABCD1','AD003','19');
INSERT IGNORE INTO m08_machine_soft (`m08_machine_code`,`m08_soft_code`,`m08_floor_code`) VALUES ('19ABCD2','MS001','19');
INSERT IGNORE INTO m08_machine_soft (`m08_machine_code`,`m08_soft_code`,`m08_floor_code`) VALUES ('19ABCD3','AD001','19');
INSERT IGNORE INTO m08_machine_soft (`m08_machine_code`,`m08_soft_code`,`m08_floor_code`) VALUES ('19ABCD3','AD002','19');
INSERT IGNORE INTO m08_machine_soft (`m08_machine_code`,`m08_soft_code`,`m08_floor_code`) VALUES ('21ABCD1','AD001','21');
INSERT IGNORE INTO m08_machine_soft (`m08_machine_code`,`m08_soft_code`,`m08_floor_code`) VALUES ('21ABCD2','AD002','21');
INSERT IGNORE INTO m08_machine_soft (`m08_machine_code`,`m08_soft_code`,`m08_floor_code`) VALUES ('21ABCD3','AD003','21');
INSERT IGNORE INTO m08_machine_soft (`m08_machine_code`,`m08_soft_code`,`m08_floor_code`) VALUES ('21ABCD3','AD004','21');

INSERT IGNORE INTO m10_hour (`m10_hour_code`,`m10_checkin_limit_time`,`m10_checkin_start_time`,`m10_checkout_limit_time`,`m10_continue_time`,`m10_hour_end_time`,`m10_hour_name`,`m10_hour_start_time`) VALUES ('1','1900-01-01 09:45:00.000000','1900-01-01 09:00:00.000000','1900-01-01 11:00:00.000000','1900-01-01 10:45:00.000000','1900-01-01 11:00:00.000000','1限目','1900-01-01 09:30:00.000000');
INSERT IGNORE INTO m10_hour (`m10_hour_code`,`m10_checkin_limit_time`,`m10_checkin_start_time`,`m10_checkout_limit_time`,`m10_continue_time`,`m10_hour_end_time`,`m10_hour_name`,`m10_hour_start_time`) VALUES ('2','1900-01-01 11:30:00.000000','1900-01-01 11:00:00.000000','1900-01-01 12:45:00.000000','1900-01-01 12:30:00.000000','1900-01-01 12:45:00.000000','2限目','1900-01-01 11:15:00.000000');
INSERT IGNORE INTO m10_hour (`m10_hour_code`,`m10_checkin_limit_time`,`m10_checkin_start_time`,`m10_checkout_limit_time`,`m10_continue_time`,`m10_hour_end_time`,`m10_hour_name`,`m10_hour_start_time`) VALUES ('3','1900-01-01 13:15:00.000000','1900-01-01 12:45:00.000000','1900-01-01 14:30:00.000000','1900-01-01 14:15:00.000000','1900-01-01 14:30:00.000000','3限目','1900-01-01 13:00:00.000000');
INSERT IGNORE INTO m10_hour (`m10_hour_code`,`m10_checkin_limit_time`,`m10_checkin_start_time`,`m10_checkout_limit_time`,`m10_continue_time`,`m10_hour_end_time`,`m10_hour_name`,`m10_hour_start_time`) VALUES ('4','1900-01-01 14:55:00.000000','1900-01-01 14:30:00.000000','1900-01-01 16:10:00.000000','1900-01-01 15:55:00.000000','1900-01-01 16:10:00.000000','4限目','1900-01-01 14:40:00.000000');
INSERT IGNORE INTO m10_hour (`m10_hour_code`,`m10_checkin_limit_time`,`m10_checkin_start_time`,`m10_checkout_limit_time`,`m10_continue_time`,`m10_hour_end_time`,`m10_hour_name`,`m10_hour_start_time`) VALUES ('5','1900-01-01 16:35:00.000000','1900-01-01 16:10:00.000000','1900-01-01 17:50:00.000000','1900-01-01 17:35:00.000000','1900-01-01 17:50:00.000000','5限目','1900-01-01 16:20:00.000000');
INSERT IGNORE INTO m10_hour (`m10_hour_code`,`m10_checkin_limit_time`,`m10_checkin_start_time`,`m10_checkout_limit_time`,`m10_continue_time`,`m10_hour_end_time`,`m10_hour_name`,`m10_hour_start_time`) VALUES ('6','1900-01-01 18:15:00.000000','1900-01-01 17:50:00.000000','1900-01-01 19:30:00.000000','1900-01-01 19:15:00.000000','1900-01-01 19:30:00.000000','6限目','1900-01-01 18:00:00.000000');
INSERT IGNORE INTO m10_hour (`m10_hour_code`,`m10_checkin_limit_time`,`m10_checkin_start_time`,`m10_checkout_limit_time`,`m10_continue_time`,`m10_hour_end_time`,`m10_hour_name`,`m10_hour_start_time`) VALUES ('7','1900-01-01 19:45:00.000000','1900-01-01 19:30:00.000000','1900-01-01 21:15:00.000000','1900-01-01 21:00:00.000000','1900-01-01 21:15:00.000000','夜間','1900-01-01 19:30:00.000000');

INSERT IGNORE INTO m11_work_pattern (`m11_work_pattern_code`,`m11_work_pattern_name`) VALUES ('00','平日');
INSERT IGNORE INTO m11_work_pattern (`m11_work_pattern_code`,`m11_work_pattern_name`) VALUES ('01','夜間なし');
INSERT IGNORE INTO m11_work_pattern (`m11_work_pattern_code`,`m11_work_pattern_name`) VALUES ('02','夜間のみ');
INSERT IGNORE INTO m11_work_pattern (`m11_work_pattern_code`,`m11_work_pattern_name`) VALUES ('03','休日');

INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','1','00');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','2','00');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','3','00');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','4','00');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','5','00');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','6','00');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','7','00');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','1','01');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','2','01');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','3','01');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','4','01');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','5','01');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','6','01');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','7','01');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','1','02');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','2','02');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','3','02');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','4','02');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','5','02');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','6','02');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('1','7','02');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','1','03');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','2','03');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','3','03');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','4','03');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','5','03');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','6','03');
INSERT IGNORE INTO m12_hour_in_work_pattern (`m12_work_flag`,`m12_hour_code`,`m12_work_pattern_code`) VALUES ('0','7','03');

INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-02 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-03 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-04 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-05 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-06 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-09 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-10 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-13 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-16 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-17 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-18 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-19 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-20 00:00:00.000000','00');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-07 00:00:00.000000','01');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-11 00:00:00.000000','01');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-14 00:00:00.000000','01');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-12 00:00:00.000000','02');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-01 00:00:00.000000','03');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-08 00:00:00.000000','03');
INSERT IGNORE INTO m13_calendar (`m13_date`,`m13_pattern_code`) VALUES ('2021-08-15 00:00:00.000000','03');

INSERT IGNORE INTO m19_admin (`m19_admin_id`,`m19_cancel_wait_timeout`,`m19_first_term`,`m19_first_term_end`,`m19_letter_term`,`m19_letter_term_end`,`m19_max_reservation`,`m19_max_reservation_date`,`m19_popup_time`,`m19_update_date`) VALUES (1,10,'2021-04-01 00:00:00.000000','2021-09-30 00:00:00.000000','2021-10-01 00:00:00.000000','2022-03-30 00:00:00.000000',10,10,10,'2021-04-01 10:56:26.000000');
