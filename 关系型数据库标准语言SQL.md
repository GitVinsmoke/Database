# 数据库

数据库一般分为关系型数据和NoSql数据库， 关系型数据库一般使SQL操作，SQL对大小写不敏感。

### SQL的数据类型

SQL支持许多内置的数据类型，并且允许用户定义新的域（数据）类型。

1. char(n)：定长字符串，长度n由用户指定。省略（n）时，长度为1。全称为character。

2. varchar(n)：变长字符串，最大长度n由用户指定。全称是character varying。定长和变长的区别主要表现在前者需要固定长度的空间，二后者占用的空间在最大长度范围内是可以改变的。

3. bit(n)：定长二进位串，长度n由用户指定。省略n时，长度为1。

4. bit varying(n)：变长二进位串，最大长度n由用户指定。

5. int：整数，其值域依赖于具体的实现，全称是integer。

6. smallint：小整数，其值域依赖于具体的实现，但是小于int的值域。

7. numeric(p, d)：p位有效数字的定点数，其中小数点右边占d位。

8. dec(p, d)：p位有效数字的定点数，其中小数点右边占d位。全称是decimal。

9. float(n)：精度至少为n位数字的浮点数，其值域依赖于实现。

10. real：实数，其值域依赖于实现。

11. double precision：双精度实数，精度依赖于实现，但精度比real高。

12. date：日期，包括年、月、日，格式为YYYY-MM-DD。

13. time：时间，包括时、分、秒，格式为HH：MM：SS。time(n)可以表示比秒更小的单位，秒后取n位

14. timestamp：时间戳，是data和time的结合体，包括年、月、日、时、分、秒。timestamp(n)可以表示比秒更小的单位，秒后取n位。

15. interval：时间间隔。SQL允许对date、time和interval类型的值进行运算。例如，如果x和y都是date类型，则x-y为interval类型，其值为x和y之间的天数。在date或time类型的值上加减一个interval类型的值得到新的date或time类型的值。

### 数据库操作：

- 查看当前数据库服务器有哪些数据库： show databases;

- 使用当前数据库服务器下的某一个数据库：use 数据库名称;

- 创建数据库：create database 数据库名称；

- 删除数据库：drop database 数据库名称

### 数据表操作

 - 创建数据表:

```
create table <表名> (<列定义>...<列定义>   [<表约束定义>...<表约束定义>]);

<表名>：标识符，是对定义的基本表命名

<列定义>：定义每个属性（列）的名称、类型、缺省值和列上的约束条件，格式是：

<列名> <类型> [default<缺省值>] <列约束定义>,...,<列约束定义>

列约束定义格式：

[constraint <约束名>] <列约束>

其中可选短语“constraint <约束名>”为列约束的命名。常用的列约束包括：

1）not null：不允许该列取空值，不加此限制时，该列可以取空值。

2）primary key：知名该列时主码（主键）,其值非空、唯一。

3）unique：该列上的值必须唯一。这相当于说明该列为候选码。

4）check(<条件>)：指明该列的值必须满足的条件，其中<条件>是一个涉及该列的布尔表达式。

表约束定义格式：

[constraint <约束名>] <表约束>

其中可选短语“constraint <约束名>”为表约束的命名。常用的表约束包括：

1）primary key(A1, A2,...Ak)：说明属性列A1...Ak构成该表的主码（主键）。当主码只包含一个属性时，也可以用列定义约束定义主码。

2）unique(A1, A2,...Ak)：说明这些属性列上的值必须唯一，这相当于说明A1，...，Ak构成该表的候选码。当候选码只包含一个属性时，也可以用列约束定义候选码。

3）check(A1,...，Ak)：说明该表上的一个完整性约束条件。通常，<条件>是一个涉及到该表一个或多个列的布尔表达式。

外码比较复杂，它具有如下形式：

foreign key(A1,...,Ak) references <外表名> (<外表主码>)

[<参照触发动作>]

它说明属性时A1,...,Ak是表的外码，<外表名>给出被参照关系的表名，<外表主码>给出被参照关系的主码，而<参照触发动作>说明违反参照完整性时需要采取的措施。

eg：创建教师表Teachers

creat table Teachers
(
Tno      char(7) not null primary key,
Tname    char(10) not null,
Sex      char(2) check(Sex='男' or Sex='女'),
Birthday date,
Title    char(6),
Dno      char(4),
foreign key (Dno) references Departments (Dno)
);

这里，我们定义Tno为Teachers的主码，Tname不能为空，而用check短语限定Sex的值只能是“男”或“女”。最后一行定义Dno为关系Teachers的外码，它参照Departments的主码Dno。

eg：创建选课表SC

create table SC
(
Sno     char(9),
Cno     char(5),
Grade   smallint check(Grade>=0 and Grade<=100),
primary key(Sno, Cno),
foreign key(Sno) references Students (Sno),

foreign key(Cno) references Courses (Cno)

);

```

- 修改数据表

```
(1)向数据表中添加新列
alter table <表名> add [column] <列定义>

例：向Courses中增加一个新列Pno，表示课程的先行课程号
alter table Courses add Pno char(5);

(2)对于已经存在的列，只允许修改或删除列的缺省值，语句形式为
alter table <表名> alter [column] <列名> {set default <缺省值> | drop default}

例：在Students的Sex列设置缺省值“女”可以减少大约一半学生性别的输入。可以使用如下语句：

alter table Students alter Sex set default '女';

而删除Sex上的缺省值可以用：

alter table Students alter Sex drop default;

(3)删除已经存在的列

alter table <表名> drop [column] <列名> {cascade | restrict}

其中cascade表示级联，删除将成功，并且依赖于该列的数据库对象（如涉及到该列的视图）也一并删除。restrict表示受限，仅当没有依赖于该列的数据库对象时删除才成功。

例：删除Courses中的Pno列可以用：

alter table Courses drop Pno;



(4)添加表约束

alter table <表名> add <表约束定义>

其中表约束定义与创建基本表相同。



(5)删除表约束

alter table <表名> drop constraint <约束名> {cascade | restrict}

其中被删除的约束名一定是命名的约束，给出约束名。cascade导致删除约束并且同时删除依赖于该约束的数据库对象。而restrict仅当不存在依赖于该约束的数据库对象才可以删除该约束。



```



- 删除基本表



```

当不需要某个基本表时，可以将其删除。语句格式为：

drop table <表名> {cascade | restrict}

其中cascade表示级联删除，依赖于表的数据对象（最常见的是视图）也将一同删除。restrict表示受限删除，如果基于该表定义有视图或者有其他表引用该表（如check，foreign key等约束），或者该表有触发器。存储过程或函数等，则不能删除。

删除基本表将导致存放在表中的数据和表定义都将被彻底删除。

例：

drop table Student restrict;

```

- 添加



```

insert语句有两种使用形式，一种是向基本表中插入单个元组；另一种是将查询的结果（多个元组）插入基本表。



1）插入单个元组

语句格式为：

insert into T[(A1, .... ，Ak)] values (c1,....,ck);

其中T通常是基本表，也可以是视图，Ai是T的属性，ci是常量。

(A1, .... ，Ak)缺省时，values子句必须按基本表属性的定义次序提供新元素每个属性上的值。否则，(A1, .... ，Ak)中属性的次序可以是任意次序，并且可以仅列举基本表的部分属性。此时，value子句中的常量与属性个数是相等的，并且常量对应相对位置上的属性，新元组取缺省值（如果定义了缺省值的话）或空值为null。

eg.

insert 表名 (栏位名称) values (栏位值)

insert stu_info (stu_no, stu_name, gender, birthday)

values('20171217123', '张三', 1, '2017‐12‐14');

insert stu_info (stu_no, stu_name, birthday)

values ('20171217123', '张三', '2017‐12‐14');

2）插入查询结果

语句格式为

insert into T[(A1, .... ，Ak)] <查询表达式>

其中查询表达式通常是一个select语句。该表达式对查询表达式求值，并将结果元组插入到基本表中。

设存放就餐卡登记信息关系Cardinf具有如下模式：

Cardinf(Card-no, Name, Banlance)

其中属性分别是持卡人编号，Name为持卡人姓名，Balance为卡中余额。假设现为教师办理一个就餐卡，直接使用教师号作为持卡人编号，并预存100元。

insert into Cardinf(Card-no, Name, Banlance)

select Tno, Tname, 100.00

from Teachers;

注意：常量100.00出现在select子句中。这使得查询结果的每个元组的第三列均取常量值100.00。

```

- 查询

```

查询在一个或多个关系（基本表或试图）上进行，其结果是一个关系。

select语句个一般形式为：

select [all | distinct] <选择序列>

from <表引用>,...,<表引用>

[where <选择条件>]

[group by <分组列>,...,<分组列> [having <分组选择条件>]]

[order by <排序列> [asc | desc] ,..., <排序列> [asc | desc]]



select语句包含5个子句，其中最基本的结构是select-from-where，并且select子句和from子句是必需得，其他子句都是可选的。

1. select子句

	select子句相当于关系代数的投影运算，用来表示查询结果的诸列。select后可以使用集合量词all或distinct，缺省时为all。all不删除结果重复行，而distinct将删除结果中的重复行。

    <选择序列>可以是“*”，表示查询结果包含from子句指定的基本表或导出表的所有属性。通常，<选择序列>列举查询结果的每个列。结果列的次序可以任意指定，结果列之间用逗号隔开。每个结果列具有如下格式：

    <值表达式> [[as] <列名>]

    其中，<值表达式>是任意可以求值的表达式；可选短语“[as] <列名>”（as可以省略）用<列名>对列重新命名，缺省时结果列名为<值表达式>。<列名>在select-from-where结构中有效。<值表达式>最常见的形式为：

    [<表名.>]<列名>

    其中，<列名>必须出现在from子句指定的表中。在不会引起混淆（即<列名>只出现在一个表中）时，<列名>前的可选前缀“<表名>.”可以缺省。<表名>是<列名>所在表的名字或别名。


2. from子句

	from子句相当于关系代数的笛卡尔积运算，用来列出查询需要扫描的基本表或导出表。from子句中可以有一个或多个<表引用>，中间用逗号隔开，常见形式是：

    <表名>[[as] <表别名>]

    其中，<表名>是合法的表名；可选短语“as <表别名>”（可省略as）用来对表起别名（在select-from-where结构中有效）。当同一个表在select-from-where结构中重复出现时，使用别名可以很好地区分它们的不同出现。当列名前需要带表名前缀时，使用较短的别名也可以简化语句书写。

3. where子句

	where子句相当于关系代数中的选择运算，<查询条件>是作用与from子句中的表和视图的选择谓词。where子句缺省时等价于where true（即查询条件为恒真条件）。

    基本的select语句的执行相当于：首先求from子句指定的基本表或导出表的笛卡尔积，然后根据where子句的查询条件从中个选择满足查询条件的元组，最后投影到select子句的结果列上，得到查询的回答。

4. 不带where子句的简单查询

	最简单的select语句只包含select和from子句。这种语句只能完成对单个表的投影运算。

    例：查询所有课程信息可以用

    select Cno, Cname, Period, Credit

    from Courses;

    或简单地使用

    select *

    from Courses;

    注意：用“*”表示所有的属性列时，得到的查询表的属性次序将和表定义的属性次序一致。然而，如果用第一种形式，属性的次序不必与表属性的定义次序一致，并且允许只显示我们感兴趣的某些属性。如：

    select Cno, Credit

    from Courses;

    select子句中的列可以是表达式，如下：

    假定当前年份为2017年，并且假设我们定义了一个函数year(d)，它返回date类型的参数d中的年份。下面的语句将显示每位学生的年龄：

    select 2017-year(Birthday) as Age

    from Students;

    as Age（as可以省略）用Age对表达式2017-year(Birthday)重新命名，导致结果为单个属性Age的表。

    SQL允许我们用distinct短语强制删除重复元组。下面的语句将显示所有学生的不同年龄：

    select distinct 2017-year(Birthday) Age

    from Students;

5. 带where子句的查询

	一般地，查询条件是一个布尔表达式。布尔表达式由基本布尔表达式用圆括号和逻辑运算符（not，and，or）构成的表达式。其中，基本布尔表达式可以是逻辑常量（true，false）、比较表达式、between表达式、in表达式、like表达式、NULL表达式、量化表达式、存在表达式、唯一表达式、或匹配表达式。

    1. 比较表达式

    	常见形式如下：

        <值表达式1> op <值表达式2>

        其中op是比较运算符（<, <=, >, >=, =, <>或!=），<值表达式1> 和 <值表达式2>都是可求值的表达式。

        eg.

        select distinct Sno

        from SC

        where Grade>60;

	2. between表达式

		between表达式判定一个给定的值是否在给定的闭区间内，常见形式为：

        <值表达式> [not] between <下界> and <上界>

        eg.查询出生年份在1987~1990的学生姓名和专业

        select Sname, Speciality

        from Students

        where year(Birtthday) between 1987 and 1990;

	3. in表达式

		in表达式判定给定的一个元素是否在给定的集合中。常见两种形式有：

        <值表达式> [not] in (<值表达式列表>)

        <元组> | [not] in <子查询>

        eg.查询计算机科学与技术和软件工程专业的学生的学号和姓名

        select Sno, Sname

        from Students

        where Speciality in('计算机科学与技术', '软件工程');

```

- 模糊查询

```

like表达式允许我们表示模糊查询，一般格式是：

<匹配值> [not] like <模式> [escape'<换码字符>']

其中<匹配值>和<模式>都是字符串表达式，它们的值是可以比较的。通常，<匹配值>是属性，<模式>是给定的字符串常量。<模式>中允许使用通配符。有两种通配符：“_”（下划线）可以与任意单个字符匹配，而"%"可以与零个或多个任意字符匹配。escape'<换码字符>'通常是escape'\'。它定义‘\’为转义字符，将紧随其后的一个字符转义。如果<模式>中的_或%紧跟在\之后，则这个_或%就失去了通配符的意义，而取其字面意义。

当<匹配值>与<模式>匹配时，则like表达式的值为真，否则，值为假。

eg.

select Sno, Sname
from Student
where Sname like '李__';   /*有两个下划线，因为一个汉字占两个字符位置*/
select *
from Courses
where Cname like 'C\_%' escape '\';        /* 定义"\"为转义字符，“_" 被转义 */
```

- NULL 查询

```
SQL允许元组在某些属性上取空值（NULL）。空值代表未知的值，不能与其他值进行比较。NULL表达式允许我们判断给定的值是否为空值。NULL表达式常见的形式如下：

<值表达式> | <子查询> IS [NOT] NULL

通常<值表达式>是属性。

eg.

select Sno, Cno
from SC
where Grade IS NULL;

```

- 连接查询

```

涉及到多个表的查询通常称为连接查询



eg.查询学号为200605098的学生的各科成绩，对每门课显示课程名和成绩。

select Cname, Grade

from SC, Courses

where SC.Cno=Courses.Cno and Sno='200605098';

其中SC.Cno=Courses.Cno称为连接条件，相同于求SC和Courses的自然连接。而Sno='200605098'则是查询条件。当属性只是from子句中一个表的属性时，前缀可以省略。



eg.查询每个学生的平均成绩，并输出平均成绩大于85的学生学号、姓名和平均成绩。

select Students.Sno, Sname, avg(Grade)

from SC, Students

where Students.Sno=SC.Sno

group by Student.Sno, Sname

having avg(Grade)>85;



eg.查询和小李出生年月相同的学生的姓名

select S2.Sname

from Students S1, Students S2

where S1.Birthday=S2.Birthday and

S1.Sname='小李' and

S2.Sname<>'小李';

同一个表名出现两次，需要重命名（省略as）。

```



- 嵌套查询



```

我们将一个查询嵌套在另一个查询中的查询称为嵌套查询，并称前者为子查询（内层查询），后者为父查询（外层查询）。子查询中不能使用order by子句。



嵌套查询可以分为两类：不相关子查询的子查询的条件不依赖与父查询；而相关子查询的子查询的查询条件依赖于父查询。



1. in引出的子查询

	in表达式的第二种形式可以更一般地判定集合成员资格，其形式如下：

    <元组> [not] in <子查询>

    

    eg.查询和小李在同一个专业学习的女同学的学号的姓名

    select Sno, Sname

    from Students

    where Sex='女' and Speciality in

    (select Speciality

    from Students

    where Sname='小李'

    );

    这是一个不相关的子查询。系统先执行子查询，得到小李所在的专业。如果小李只有一个，则子查询的结果是单个值。此时，可以将“Speciality in”用“Speciality=”替代。

    

2. 集合的比较引出的子查询

	SQL允许将一个元素与子查询的结果集进行比较。这种量化的比较表达式的常用形式为：

    <值表达式> h all | some | any <子查询>

    其中h是比较运算符（=, <>, !=, <, >, >=, <=）。some和any的含义相同。但现在更多地使用some。当<子查询>的结果为单个值时，all，some，any可以省略。

    

    eg.查询比软件工程专业都小的其他专业的学生的学号、姓名、专业和出生日期。

    select Sno, Sname, Speciality, Birthday

    from Students

    where Speciality <> '软件工程' and 

    Birthday > all (

    select Birthday

    from Student

    where Speciality='软件工程'

    );

    

    也可以使用聚集函数实现：

    select Sno, Sname, Speciality, Birthday

    from Students

    where Speciality <> '软件工程' and

    Birthday > (

    select max(Birthday)

    from Students

    where  Speciality='软件工程'

    );

```



- 分组



```

按属性分组，再使用聚集函数

一般形式如下：

group by <分组列> {, <分组列>} [having <分组选择条件>]

其中<分组列>是属性（可以带表名前缀），它所在的表出现在from子句中。可选的having子句是用来过滤掉不满足<分组选择条件>的分组，缺省时等价于having true。<分组选择条件>类似于where子句的查询条件，但其中允许出现聚集函数。

对于group by子句的select语句，select语句中的结果列必须是group by子句中的<分组列>或聚集函数。

带group by子句的select语句的执行效果相当于：首先对from子句中的表计算笛卡尔积，再根据where子句的查询条件从中选择满足查询条件的元组，得到查询的中间结果。然后，暗中group by子句指定的一个或多个列队中间元组进行分组，在这些列上的值相等的元组分为一组；按照having短语中的分组选择条件过滤掉不满足条件的分组。最后，投影到select子句的结果列上，得到查询的回答。



eg.查询每个学生的平均成绩，并输出平均成绩大于85的学生序号和平均成绩

select Sno, avg(Grade)

from SC

group by Sno having avg(Grade)>85;

```





- 删除



```

使用delete语句删除表中的某些记录，语句格式为：

delete from T [where <删除条件>]



其中T通常为基本表，但也可以是某些视图，<删除条件>与select语句中的查询条件类似。

delete语句的功能是从指定的表T中删除满足<删除条件>的所有元组。where子句缺省时，则删除表T中全部元组（剩下一个空表T）。



删除所有学生的记录：

delete from Students;



删除学号为200624010的学生记录可以用：

delete from Students where Sno='200624010';



尽管delete语句只能从一个表删除元组，但是删除条件可以涉及到多个表

例如删除计算机系所有学生的选课记录（SC是学生选课关系表）

我们可以先得到计算机系所有学生的学号

delete from SC

where Sno In

(

select Sno

from Students

where Speciality='计算机');





区分：truncate 、delete与drop

相同点：

1.truncate和不带where子句的delete、以及drop都会删除表内的数据。

2.drop、truncate都是DDL语句(数据定义语言),执行后会自动提交。



不同点：

1. truncate 和 delete 只删除数据不删除表的结构(定义)，而drop 语句将删除表的结构以及被依赖的约束(constrain)、触发器(trigger)、索引(index)。

2. delete 语句是数据库操作语言(dml)，这个操作会放到 rollback segement 中，事务提交之后才生效；如果有相应的 trigger，执行的时候将被触发。

truncate、drop 是数据库定义语言(ddl)，操作立即生效，原数据不放到 rollback segment 中，不能回滚，操作不触发 trigger。

3. 速度：一般来说: drop> truncate > delete

4. 安全性：小心使用 drop 和 truncate，尤其没有备份的时候。

5. delete是DML语句,不会自动提交。drop和truncate都是DDL语句,执行后会自动提交。



eg.

第一步，开启事务：

start transaction;

第二步，使用delete：

delete from Students where ID=201385;

第三步，使用rollback：

rollback;

结果：数据回滚



```

事务（Transaction）是并发控制的单位，是用户定义的一个操作序列。这些操作要么都做，要么都不做，是一个不可分割的工作单位。



事务通常以begin transaction开始，以commit或rollback结束。commit表示提交，即提交事务的所有操作。具体地说就是将事务中所有对数据库的更新写会到磁盘上的物理数据库中去，事务正常结束。rollback表示回滚，即在事务运行的过程中发生了某种故障，事务不能运行，系统将事务中对数据库的所有已经完成的操作全部撤销，滚回到事务开始的状态。



事务的特点：



原子性（Atomicity）:操作要么全做，要么全部做。



一致性（Consistency）：事务执行的结果必须是使数据库从一个一致性状态变到另一个一致性状态。



隔离型（Isolation）：一个事务的执行不能被其他事务干扰。



持续性（Durability）：一个事务一旦提交，它对数据库的改变就应该是永久性的，不会被回滚。











- 修改



```

使用update语句可以修改表中某些元组指定属性上的值。格式为：

update T

set A1=e1,...,Ak=ek

[where <修改条件>]



其中T通常为基本表，但也可以是某些视图；A1,...Ak是T的属性，而e1,...ek是表达式；<修改条件>与select语句中的查询条件类似

该语句的功能是：修改表T满足<修改条件>的元组。更具体的说，对于表T中没个满足<修改条件>的元组t，求表达式ei的值，并将它赋予元组t的属性Ai。where子句缺省时，则修改表T中全部元组。



update语句只能修改一个表的元组。但是，修改条件中可以包含涉及其他表的子查询。

例如：将微积分课程成绩低于60分的所有学生的微积分课程成绩提高5分。

先得到微积分课程的课程号，再修改微积分成绩

update SC

set Grade=Grade+5

where Grade < 60 and Cno in

(select Cno

from Courses

where Cname='微积分'

);



```





- SQL函数

	

    SQL的函数可以单独使用也可以配合group by(分组)子句使用。单独使用时，聚集函数作用于整个查询结果；而与group by子句配合使用时，聚集函数作用于查询结果的每个分组。聚集函数单独使用时，可以认为整个查询结果形成一个分组。

    SQL的聚集函数有如下形式：

    

    	count([all | distinct] *) 或 <聚集函数> ([all | distinct] <值表达式>)

        其中[all | distinct]可选，缺省时为all，使用distinct将删除多重集中的重复元素

        

	- 计数

	```

    返回元组中元素的个数

   格式：select count(参数) from 表名;

   参数可以是栏位（列）的序列号，按照属性定义的次序从零开始，也可以是栏位的名称。最好选择主键作为参数，查询速度快。

   

   eg.

   select count(*) from 表名;

   select count(0) from 表名;

   select count(栏位名) from 表名;

   

    ```

	- 求和函数

	

	```

    将对每个元组中的分组，在某个属性的不同值上求和

		sum(栏位名 or 栏位序号)

    ```

	- 查询最大值最小值平均值

	

    ```

    查询CS302课程成绩最低分、平均分和最高分

    select min(Grade), max(Grade), avg(Grade)

    from SC

    where Cno='CS302';

    

    查询每个学生的平均成绩，并输出平均成绩大于85的学生序号和平均成绩

    select Sno, avg(Grade)

    from sc

    group by Sno having avg(Grade)>85;

	```

    

	- 查询并使用逻辑运算



	```

    select * 

    from user

	user where score >= 80 and score <= 90;

	```

    

	- 数据排序

	

    ```

    order by 栏位名 asc；   (升序（default）,默认情况可以不写asc)

    order by 栏位名 desc;   (降序) 

    

    查询每个学生CS202课程的成绩，并将查询结果按成绩降序排序

    select *

    from SC

    where Cno='CS202'

    order by Grade desc;

    

    查询每位学生的每门课的成绩，并将查询结果按课程号升序、成绩降序排序

    select *

    from SC

    order by Cno, Grader desc;

	```

    

    - 分页函数



	```

    select * from 表名  limit [offset,] rows | rows OFFSET offset

    

    limit子句可以被用于强制 select 语句返回指定的记录数。limit 接受一个或两个数字参数。参数必须是一个整数常量。如果给定两个参数，第一个参数指定第一个返回记录行的偏移量，第二个参数指定返回记录行的最大数目。初始记录行的偏移量是 0(而不是 1)

    

    select * from table limit 5,10;    //检索记录行6-15

    

    //检索从某一个偏移量到记录集的结束所有的记录行，可以指定第二个参数为-1：

	select * from table limit 95,-1; // 检索记录行 96-last.

    

    //如果只给定一个参数，它表示返回最大的记录行数目： 

    SELECT * from table limit 5;     //检索前 5 个记录行

	```

    
    - 加密函数


    ```

    如果我们数据库中涉及到密码，则我们当然不能让记录中的密码干脆的显示出来，可采用下面的函数进行加密。

    password('你要加密的密码');

    例：insert students (ID, Name, Score, Password) values (201500, 'Zhang', 90, password('123456'));

    //注意，进行加密则密码的数据长度至少为60位字符

	```
    
- SQL JOIN

	SQL join 用于根据两个或多个表中的列之间的关系，从这些表中查询数据。

    Join 和 Key

	有时为了得到完整的结果，我们需要从两个或更多的表中获取结果。我们就需要执行 join。

	数据库中的表可通过键将彼此联系起来。主键（Primary Key）是一个列，在这个列中的每一行的值都是唯一的。在表中，每个主键的值都是唯一的。这样做的目的是在不重复每个表中的所有数据的情况下，把表间的数据交叉捆绑在一起。

	- LEFT JOIN	 

	资料来源：[SQL LEFT JOIN 关键字](http://www.w3school.com.cn/sql/sql_join_left.asp)

	![left join.jpg](http://img.blog.csdn.net/20171225135524360?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvc2luYXRfMzc5NzY3MzE=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

    - RIGHT JOIN

    资料来源：[SQL RIGHT JOIN](http://www.w3school.com.cn/sql/sql_join_right.asp)

	![RIGHT JOIN.jpg](http://img.blog.csdn.net/20171225135635367)

    - FULL JOIN

    资料来源：[SQL FULL JOIN 关键字](http://www.w3school.com.cn/sql/sql_join_full.asp)

    ![full join.jpg](http://img.blog.csdn.net/20171225135700925)
