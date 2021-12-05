INSERT INTO USER ( id, name, email, password,pone_Num ,admin_Yn, id_Use_Yn) VALUES
 (1,'admin', 'admin', 'admin9999', null ,'YES','YES');
INSERT INTO PAGE_SET ( id, graph, dcp_yn, graph_max,graph_min ,graph_day, grap_time,wait_time) VALUES
 (1,'Y', 'Y', 3000, 0 ,7,5,1);
UPDATE PAGE_SET SET WAIT_TIME = 1 WHERE WAIT_TIME IS NULL