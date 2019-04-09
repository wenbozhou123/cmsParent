-- 创建数据库时,设置数据库的编码方式
-- CHARACTER SET:指定数据库采用的字符集,utf8不能写成utf-8
-- COLLATE:指定数据库字符集的排序规则,utf8的默认排序规则为utf8_general_ci（通过show character set查看）
create database fuzhong_cms CHARACTER SET utf8 COLLATE utf8_general_ci;
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