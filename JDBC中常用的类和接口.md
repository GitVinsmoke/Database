# <font color=red>JDBC中常用的类和接口</font>

### <font color=red>Connection接口</font>

Connection接口代表与特定的数据库连接，在连接上下文中执行SQL语句并返回结果。

| 方法 | 功能描述 |
|--------|--------|
|   createStatement()     |    创建一个Statement对象    |
|   createStatement(int resultSetType, int resultSetConcurrency)     |    创建一个Statement对象，该对象生成具有给定类型、并发性、和可保存的ResultSet对象    |
|   PrearedStatement()     |    创建预处理对象PreparedStatement    |
|   isReadOnly()     |    查看当前Connection对象的读写模式是否为只读模式    |
|   setReadOnly()     |    设置当前Connection对象的读写模式，默认是非只读模式    |
|   commit()     |    使上一次提交/回滚之后进行的更改成为持久更改，并释放此Connection对象当前所持有的的所有数据库锁    |
|   setAutoCommit()     |    设置对数据库的更改是否为自动提交，默认是自动提交（true）    |
|   rollback()     |    回滚当前事务中的所有改动并释放当前连接持有的数据库的锁    |
|   close()     |    立即释放连接对象的数据库和JDBC资源    |


### <font color=red>DriverManager类</font>

DriverManager类用来管理数据库中的所有驱动程序。它是JDBC的管理层，作用于用户和驱动程序之间，跟踪可用的驱动程序，并在数据库的驱动程序之间建立连接。如果通过getConnection()方法可以建立连接，则经连接返回，否则抛出SQLException异常。DriverManager类的常用方法如下：

|方法|功能描述|
|--------|--------|
| getConnection(String sql, String user, String password) |指定三个入口参数（依次是连接数据库的URL、用户名和密码）来获取与数据库的连接|
|setLLoginTimeout|获取驱动程序试图登录到某一数据库时可以等待的最长时间，以秒为单位|
|println(String message)|将一条消息打印到当前的JDBC日志流中|




### <font color=red>Statment接口</font>

Statement接口用于在已经建立连接的基础上向数据库发送SQL语句。在JDBC中有3中Statement对象，分别是Statement、PreparedStatement和CallableStatement。Statement对象用于执行不带参数的简单的SQL语句；PreparedStatement继承了Statement，用来执行动态的SQL语句；CallableStatement继承了PreparedStatement，用来执行数据库的存储过程的调用。Statement接口的常用的方法如下：

| 方法 | 功能描述 |
|--------|--------|
|    execute(String sql)    |    执行静态的select语句，该语句可能返回多个结果集    |
| executeQuery(String sql) | 执行给定的SQL语句，该语句返回单个ResultSet对象 |
| clearBatch() | 清空此Statement的当前SQL命令列表 |
|executeBatch()|将一批命令提交给数据库来执行，如果全部命令执行成功则返回更新计数组成的数组。数组元素的排序与SQL语句的添加顺序对应|
|addBatch()|将给定的SQL命令添加到此Statement对象的当前命令列表中。如果驱动程序不支持批量处理，将抛出异常|
|close()|释放Statement实例占用的数据库和JDBC资源|


### <font color=red>PreparedStatment接口</font>

PreparedStatment接口用来动态地执行SQL语句。通过PreparedStatment实例执行的动态SQL语句，将被预编译并保存到PreparedStatment实例中，从而可以重复地执行该SQL语句。PreparedStatment接口的常用方法如下：

| 方法 | 功能描述 |
|--------|--------|
|SetInt(int index, int k)|将指定位置的参数设置为int值|
|SetFloat(int index, float f)|将指定位置的参数设置为float值|
|SetLong(int index, long l)|将指定位置的参数设置为long值|
|SetDouble(int index, double d)|将指定位置的参数设置为double值|
|SetBoolean(int index, boolean b)|将指定位置的参数设置为boolean值|
|SetDate(int index, Date date)|将指定位置的参数设置为date值|
|executeQuery()|在此PreparedStatment对象中执行SQL查询，并返回该查询生成的ResultSet对象|
|setString(int index, String s)|将指定位置的参数设置为String值|
|SetNull(int index, int sqlType)|将指定位置的参数设置为SQL NULL|
|executeUpdate()|执行前面包含的参数的动态insert、update或delete语句|
|clearParemeters()|清除当前所有的参数值|


### <font color=red>ResultSet接口</font>

ResultSet接口类似于一个临时表（实际上也是一个缓存区），用来暂时存放数据库查询操作所获得的结果集。ResultSet实例具有指向当前数据行的指针，指针开始的位置在第一条记录的前面，通过next()方法可将指针向下移，进而获取到数据。

Statement对象执行executeQuery实际上并不是一下子将所有查询到的数据全部放入到ResultSet中（想象一下如果是几十万条是不是要蹦了），而是分批次放入进去ResultSet缓存，一次取完了再放入下一批次，直至取完所有查询到的数据。然而ResultSet里的这个缓存区究竟有多大呢？我们可以通过ResultSet里的getFetchSize()方法来获取（一般大小为10个），同时我们也可以通过setFetchSize()方法来设置这个缓存区的大小。

在JDBC 2.0（JDK 1.2）之后，该接口添加了一组更新方法updateXXX()，该方法有两个重载方法，可根据列的索引号和列的名称来更新指定列。但该方法并没有将对数据进行的操作同步到数据库中，需要执行updateRow()或insertRow()方法更新数据库。ResultSet接口的常用方法如下：

| 方法 |功能描述|
|--------|--------|
|getInt()|以int形式获取此ResultSet对象的当前行的指定列值（可以使用栏位名称或者栏位索引值）。如果列值为NULL，则返回值是0|
|getFloat()|以float形式获取此ResultSet对象的当前行的指定列值（可以使用栏位名称或者栏位索引值）。如果列值为NULL，则返回值是0|
|getDate()|以date形式获取此ResultSet对象的当前行的指定列值（可以使用栏位名称或者栏位索引值）。如果列值为NULL，则返回值是null|
|getBoolean()|以boolean形式获取此ResultSet对象的当前行的指定列值（可以使用栏位名称或者栏位索引值）。如果列值为NULL，则返回值是null|
|getString()|以String形式获取此ResultSet对象的当前行的指定列值（可以使用栏位名称或者栏位索引值）。如果列值为NULL，则返回值是null|
|getObject()|以Object形式获取此ResultSet对象的当前行的指定列值（可以使用栏位名称或者栏位索引值）。如果列值为NULL，则返回值是null|
|first()|将指针移到当前记录的第一行|
|last()|将指针移到当前记录的最后一行|
|next()|将指针向下移一行|
|beforeFirst()|将指针移到集合的开头（第一行位置）|
|afterLast()|将指针移到集合的尾部（最后一行位置）|
|absolute(int index)|将指针移到ResultSet给定编号的行|
|isFirst()|判断指针是否位于当前ResultSet集合的第一行。如果是返回true，否则返回false|
|isLast()|判断指针是否位于当前ResultSet集合的最后一行。如果是返回true，否则返回false|
|updateInt()|用int值更新指定列|
|updateFloat()|用float值更新指定列|
|updateLong()|用long值更新指定列|
|updateString()|用String值更新指定列|
|updateObject()|用Object值更新指定列|
|updateNull()|将指定的列值修改为NULL|
|updateDate()|用指定的date值更新指定列|
|updateDouble()|用double值更新指定列|
|getRow()|查看当前行的索引号|
|insertRow()|将插入行的内容插入到数据库|
|updateRow()|将当前行的内容同步到数据库|
|deleteRow()|删除当前行，但是不同步到数据库中，而是在执行close()方法之后同步到数据库中|
