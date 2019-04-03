--创建数据库
create database fuzhong_cms;
--创建User
CREATE USER 'fz'@'%' identified by 'fz123456';
--'%' - 所有情况都能访问
--'localhost' - 本机才能访问
--'111.222.33.44' - 指定 ip 才能访问
--修改密码：update mysql.user set password=password('新密码') where user='fz';

--附权限
GRANT all PRIVILEGES on fuzhong_cms.* to 'fz'@'%' identified by 'fz123456';
--all 可以替换为 select,delete,update,create,drop

--删除用户
Delete FROM mysql.user Where User='fz';

--刷新权限，立即生效
flush privileges;