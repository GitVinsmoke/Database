# <font color=red>连接数据库</font>

### <font color=red>添加所需要的jar包</font>

连接数据库需要用到JDBC驱动程序。先下载相关的驱动程序jar包，再在工程目录下创建一个lib文件夹，将jar包复制添加到此文件夹下，再添加构造路径。


### <font color=red>SQLite连接数据库</font>

```
//首先加载数据库驱动（只需要在第一次访问数据库时加载一次）：驱动全类名=包名+类名
//此方法会抛出 ClassNotFoundException异常
Class.forName("org.sqlite.JDBC");

/*
* 使用java.sql包中的Connection接口，并通过DriverMannager类的静态方法 getConnection()创建连接对象
* 写法：c=DriverManager.getConnection(url, user, password);
*/
c = DriverManager.getConnection("jdbc:sqlite:test.db"); // SQLite不需要用户和密码

//此时数据库连接成功，注意需要处理相关异常

例如：
//关闭数据库连接
try {
    c.close();
} catch (SQLException e) {
    e.printStackTrace();
} finally{
    //finally表示捕捉到异常之后需要执行的动作
    //即便是发生了异常，也要考虑到关闭数据库连接
}

```

### <font color=red>MySQL连接数据库</font>

```
//首先加载数据库驱动（只需要在第一次访问数据库时加载一次）：驱动全类名=包名+类名
//此方法会抛出 ClassNotFoundException异常
Class.forName("com.mysql.jdbc.Driver");


/*
* 使用java.sql包中的Connection接口，并通过DriverMannager类的静态方法 getConnection()创建连接对象
* 写法：c=DriverManager.getConnection(url, user, password);
* mysql URL写法："jdbc:mysql://主机名或IP地址:端口号/数据库名称"
* 主机地址：本地主机地址为localhost或者写127.0.0.1
* 其中下面的“?useSSL=true”不是必须的，只是用来处理JDBC和MySQL的版本兼容问题
*/
c = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtest?useSSL=true", "user", "your password");
//需要抛出SQLException异常

//此时数据库连接成功，注意需要处理相关异常
例如：
//关闭数据库连接
try {
    c.close();
} catch (SQLException e) {
    e.printStackTrace();
} finally{
    //finally表示捕捉到异常之后需要执行的动作
    //即便是发生了异常，也要考虑到关闭数据库连接
}

```
#### SQL语句的执行

执行SQL语句采用的是接口java.sql.Statement的成员方法

- int executeUpdate(String sql) throws SQLException

	第一条通常用来执行一些不具有返回结果的SQL语句，如insert，update或delete语句

- ResultSet executeQuery(String sql) throws SQLException

	第二条通常用来执行只返回一个结果集的SQL语句。该成员方法直接返回结果集对象的引用

- boolean execute(String sql) throws SQLException

	第三条通常用来执行一些具有单个或多个返回结果集的SQL语句，如select语句。如有结果集返回，则返回值为true，否则返回false。返回的结果集可以通过接口java.sql.Statement的成员方法getResultSet获得。每取到一个结果集都应当调用接口java.sql.Statement的成员方法getUpdateCount，以更新当前的计数。

	设s为java.sql.Statement类型的变量，而且成员方法execute是通过s调用的，则当下面的布尔表达式

		((s.getMoreResults()==false)&&(s.getUpdateCount()==-1))

	为true时，表明所有返回结果集都已经处理完毕，即不用再取下一个结果集。

在查询信息的例程中，java语句

	ResultSet r=s.executeQuery("select * from 表名");

结果集对象的引用赋给变量r，这样r所指向的实例对象中包含了学生成绩数据库表的所有列信息。

可以通过接口java.sql.ResultSet的成员方法
ResultSetMetaData getMetaData() throws SQLException
提取在结果集中的列信息。此方法返回ResultSetMetaData实例对象的引用，实例对象记录了结果集所包含的列数、列的名称和类型等属性信息。

这样可以通过接口java.sql.ResultSetMetaData的成员方法
int getColumnCount() throws SQLException
获得结果集的列数；

通过接口java.sql.ResultSetMetaData的成员方法
String getColumnName(int column) throws SQLException
获得结果集第colum列的列名称；

通过接口java.sql.ResultSetMetaData的成员方法
int getColumnTypeName(int column) throws SQLException
获得结果集第colum列的列数据类型名称；

通过接口java.sql.ResultSetMetaData的成员方法
int getPrecision(int column) throws SQLException
获得结果集第colum列的精度，即表示该列数值的位数或字符数。

**这里需要注意的是，接口java.sql.ResultSetMetaData的成员方法的参数column都是从1开始计数，如果要取第i列的数据，则column的值应当为i，其中i=1,2，……**



* * *

## <font color=red>代码实例</font>

### <font color=red>表的创建</font>

```
// 在数据库中创建一个表Company
	public void createTable(Connection c) {

		System.out.println("打开数据库成功!");
		Statement stmt = null;
		try {
			stmt = c.createStatement();
            //SQL语句
			String sql = "CREATE TABLE students " + 
            			 "(ID INT PRIMARY KEY NOT NULL, " + 
                         "Name TEXT NOT NULL, " + 
                         "Socre INT NOT NULL, "+
                         "Password CHAR(60), ";
			//增删改，都可以使用此方法，返回值是影响数据的条数（int型）
			stmt.executeUpdate(sql);
			
			System.out.println("表创建成功!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//finally表示即便是前面出现异常，仍然要处理的动作
			//出现异常也不要忘记关闭资源
			try {
				// 释放Statement实例占用的数据库和JDBC资源
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	}
```


### <font color=red>连接数据库进行查询操作</font>

```
// SELECT操作
	public void select(Connection c) {
		Statement stmt = null;
		ResultSet rs = null; //结果集的创建以及初始化
		try {
			System.out.println("数据库打开成功");
			stmt = c.createStatement();

			// 临时表，用以存放数据库查询操作所得的结果集
            //参数为String类型，就是SQL语句
			rs = stmt.executeQuery("SELECT * FROM students;");
            
			// 使用迭代器取出数据
			while (rs.next()) {
            	//根据栏位名称或者栏位序号取出相关的数据
				int ID = rs.getInt("ID");
				String Name = rs.getString("Name");
				int Score = rs.getInt("Score");
				String Password = rs.getString("Password");

				System.out.println("ID = " + ID);
				System.out.println("Name = " + Name);
				System.out.println("Score = " + Score);
				System.out.println("Password = " + Password);
				System.out.println();
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {//即便是出现异常也要处理的数据
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("操作成功!");
	}
```

### <font color=red>插入数据操作</font>

```
public void insert(Connection c) {
		Statement stmt = null;
	    try {
	      c.setAutoCommit(false);
	      System.out.println("打开数据库成功!");
	      stmt = c.createStatement();
	      
	      //插入操作
	      String sql = "INSERT INTO Company (ID,NAME,AGE,ADDRESS,SALARY) " +
	                   "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
	      stmt.executeUpdate(sql);

	      sql = "INSERT INTO Company (ID,NAME,AGE,ADDRESS,SALARY) " +
	            "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );"; 
	      stmt.executeUpdate(sql);

	      sql = "INSERT INTO Company (ID,NAME,AGE,ADDRESS,SALARY) " +
	            "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );"; 
	      stmt.executeUpdate(sql);

	      sql = "INSERT INTO Company (ID,NAME,AGE,ADDRESS,SALARY) " +
	            "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"; 
	      stmt.executeUpdate(sql);

	      
	      
	    //使所有上一次提交/回滚后进行的更改成为持久更改，并释放此Connection对象当前持有的所有的数据库锁
	      c.commit();                          
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    } finally{
	    	try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	    
	    System.out.println("成功!");
	}
```

### <font color=red>删除数据操作</font>

```
//delete操作
public void delete(Connection c){
    Statement stmt=null;
    try {
    	  //设置操作不自动提交，开启事务
          c.setAutoCommit(false);

          System.out.println("数据库打开成功！");
          stmt = c.createStatement();
          String sql = "DELETE from Company where ID=2;";
          stmt.executeUpdate(sql);

          //提交操作结果，操作执行完成
          c.commit();
		  //调用查询操作
          select(c);

          stmt.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    System.out.println("删除成功！");
}
```

### <font color=red>SQL注入</font>

下面一段代码（**<font color=red>注意代码中的单引号和双引号，因为需要查询字符串，需要单引号</font>**）：

```
public void sqlOsmosis(Connection c) {
    Statement st = null;
    ResultSet rs = null;
    String name="LeeH' or '1'='1";
    String password="156' or '1'='1";

    String sql = "select * from stu_info where stuName ='" + name + "' and password = '" + password + "'";
    System.out.println("------>" + sql);
    try {
        st = c.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()) {
            System.out.println("登录成功！");
        } else {
            System.out.println("用户名或密码错误！");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
运行结果：

```
加载数据库驱动成功!
数据库连接成功!
-->select * from stu_info where stuName ='LeeH' or '1'='1' and password = '156' or '1'='1'
登录成功！
```
实际上，数据库中并没有这个记录，但是为什么输出结果却表示查到了这个记录了呢？我们可以 仔细看看上面输出的那个SQL语句，虽然stuName和password都不相符，但是字符'1'等于'1'是正确的，这是由于这个逻辑表达式，使得查找改变，因而结果也改变。

### <font color=red>预防SQL注入——预处理</font>

PreparedStatement接口用来动态地执行SQL语句。通过PreparedStatement实例执行的动态SQL语句，将会被预编译并保存到PreparedStatement实例中。

```
public void prepareStatement(Connection c) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select * from stu_info where password=? and stuName=?";
		try {
			ps = c.prepareStatement(sql);
			/*
			 * 将占位符赋值： 占位在SQL中从左到右下标以1开始依次递增 预处理会将参数中的特殊符号进行转义，可以预防SQL注入
			 */

			ps.setString(1, "1234 or '1'='1'"); //设置第一个问号
			ps.setString(2, "Lucy or '1'='1'"); //设置第二个问号
			
			//ps.setString(1, "123456");
			//ps.setString(2, "Lucy");
			//这样便可以成功查找
			
			System.out.println("------>" + sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("登录成功！");
			} else {
				System.out.println("用户名或密码错误！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
```
采用PreparedStatement接口可以先预编译SQL语句，随后再将指定位置设定指定的值。有关PreparedStatement接口的方法可以参考我的另一篇博客：[JDBC中常用的类和接口](http://blog.csdn.net/sinat_37976731/article/details/79486440)


### <font color=red>将数据库一般常用操作打包成jar包</font>

将相关的连接以及关闭操作设为静态方法，这样一来的好处就是不用每次都写很多相同的操作，只需要传递相应的参数，并且欲修改连接的参数只需在这里修改静态常量。

```
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 连接数据库的一个工具类
 * 
 * @author Vinsmoke
 *
 */
public class DBUtil {

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/dbtest?useSSL=true";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "112358";

	// 获取数据库连接
	public static Connection getDBConnection() {
		Connection c = null;
		try {
			Class.forName(DB_DRIVER);
			c=DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param c
	 */
	public static void close(Connection c) {
		if (c != null) {
			try {
				c.close();
			} catch (SQLException e) {
				System.out.println("Connection关闭异常：" + e);
			}
		}
	}

	/**
	 * 关闭statement
	 * 
	 * @param state
	 */
	public static void close(Statement state) {
		if(state!=null){
			try {
				state.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭PreParedStatement
	 * @param state
	 */
	public static void close(PreparedStatement state) {
		if(state!=null){
			try {
				state.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

```