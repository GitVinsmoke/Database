# 在MySQL中创建实现自增的序列（Sequence）

### 问题

MySQL中在表的定义中可以对字段名设置为 AUTO_INCREMENT （自动递增），但这有时并不能满足我们的需求，例如，我们在应用中使用JDBC向数据库中添加数据时，我们同时需要将自增的主键作为一个外键去更新其他的关联表，然而我们此时并不能获取这个自增的主键。

所以想到了利用一个独立的自增的sequence来解决该问题。

### 解决办法

当前数据库为：mysql

由于mysql和oracle不太一样，不支持直接的sequence，所以需要创建一张table来模拟sequence的功能，理由sql语句如下：

##### 第一步：创建 sequence 管理表

```sql
DROP TABLE IF EXISTS sequence; 
CREATE TABLE sequence ( 
     name VARCHAR(50) NOT NULL, 
     current_value INT NOT NULL, 
     increment INT NOT NULL DEFAULT 1, 
     PRIMARY KEY (name) 
) ENGINE=InnoDB; 
```

##### 第二步：创建取当前值的函数

```sql
DROP FUNCTION IF EXISTS currval; 
DELIMITER $ 
CREATE FUNCTION currval (seq_name VARCHAR(50)) 
     RETURNS INTEGER
     LANGUAGE SQL 
     DETERMINISTIC 
     CONTAINS SQL 
     SQL SECURITY DEFINER 
     COMMENT ''
BEGIN
     DECLARE value INTEGER; 
     SET value = 0; 
     SELECT current_value INTO value 
          FROM sequence
          WHERE name = seq_name; 
     RETURN value; 
END
$ 
DELIMITER ;
```

通常我们在执行创建过程和函数之前，都会通过"DELIMITER $$"命令将语句的结束符从 ";" 修改成其他符号，我们这里使用的是 "$"，这样在过程和函数中的 ";" 就不会被MySQL解释成语句的结束符而提示错误。在存储过程或者函数创建完毕，通过 "DELIMITER ;"将结束符改成 ";"。

##### 第三步：创建取下一个值的函数

```sql
DROP FUNCTION IF EXISTS nextval; 
DELIMITER $ 
CREATE FUNCTION nextval (seq_name VARCHAR(50)) 
     RETURNS INTEGER
     LANGUAGE SQL 
     DETERMINISTIC 
     CONTAINS SQL 
     SQL SECURITY DEFINER 
     COMMENT ''
BEGIN
     UPDATE sequence
          SET current_value = current_value + increment 
          WHERE name = seq_name; 
     RETURN currval(seq_name); 
END
$ 
DELIMITER ; 
```

实际上就是更新了sequence中一条记录的值，然后调用取当前值的函数，返回新的值。

##### 第四步：创建更新当前值的函数

```sql
DROP FUNCTION IF EXISTS setval; 
DELIMITER $ 
CREATE FUNCTION setval (seq_name VARCHAR(50), value INTEGER) 
     RETURNS INTEGER
     LANGUAGE SQL 
     DETERMINISTIC 
     CONTAINS SQL 
     SQL SECURITY DEFINER 
     COMMENT ''
BEGIN
     UPDATE sequence
          SET current_value = value 
          WHERE name = seq_name; 
     RETURN currval(seq_name); 
END
$ 
DELIMITER ;
```

##### 第五步：测试函数功能

当上述四步完成后，可以用以下数据设置需要创建的sequence名称以及设置初始值和获取当前值和下一个值。（注意varchar类型单引号）

- 添加一个sequence名称和初始值，以及自增幅度：INSERT INTO sequence VALUES ('seq1', 0, 1);

- 设置指定sequence的初始值：SELECT SETVAL('seq1', 10);

- 查询指定sequence某一记录的当前值：SELECT CURRVAL('seq1');

- 查询指定sequence的下一个值：SELECT NEXTVAL('seq1');

