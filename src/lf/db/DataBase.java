package lf.db;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;

public class DataBase
{
	public Connection conn = null;
	public boolean connected = false;
	private String host;
	private short port;
	private String database;
	private String username;
	private String password;

	public final static int TSELECT = 0;
	public final static int TINSERT = 1;
	public final static int TUPDATE = 2;
	public final static int TDELETE = 3;
	public final static int TTRUNCATE = 4;

	public DataBase(String _host, short _port, String _database, String _username, String _password)
	{
		host = _host;
		port = _port;
		database = _database;
		username = _username;
		password = _password;
		
		connect();
	}

	public boolean connect()
	{
		if(connected)
			return true;
		try{
			System.out.print("¥ Opening connection to DB...");
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database;
			conn = DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e)
		{
			System.out.println("...failed.");
			connected = false;
			e.printStackTrace();
		} catch (SQLException e)
		{
			System.out.println("...failed.");
			connected = false;
			e.printStackTrace();
		}
		finally
		{
			System.out.println("...success.");
			connected = conn != null;
		}
		return connected;
	}

	public void close()
	{
		if(conn != null)
		{
			try
			{
				conn.close();
				System.out.println("¥ Connection to DB closed.");
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			connected = false;
		}
	}

	public Object request(int type, String request, Object... args)
	{
		if(connect())
		{
			try
			{
				PreparedStatement st = conn.prepareStatement(request);
				int i = 1;
				for(Object o : args)
				{
					if(o.getClass().equals(int.class) || o.getClass().equals(Integer.class)) {
						st.setInt(i, (Integer)o);
					} else if (o.getClass().equals(float.class) || o.getClass().equals(Float.class)) {
						st.setFloat(i, (Float)o);
					} else if (o.getClass().equals(double.class) || o.getClass().equals(Double.class)) {
						st.setDouble(i, (Double)o);
					} else if (o.getClass().equals(String.class)) {
						st.setString(i, (String)o);
					} else if (o.getClass().equals(boolean.class) || o.getClass().equals(Boolean.class)) {
						st.setBoolean(i, (Boolean)o);
					} else if (o.getClass().equals(Timestamp.class)) {
						st.setTimestamp(i, (Timestamp)o);
					} else if (o.getClass().equals(Date.class)) {
						st.setDate(i, (Date)o);
					} else {
						System.out.println("bug !!!!! "+i+" "+o.getClass());
					}
					i++;
				}
				
				switch(type)
				{
				case TSELECT :
					return st.executeQuery();
				case TINSERT :
				case TUPDATE :
				case TDELETE :
					return st.executeUpdate();
				case TTRUNCATE :
					return st.execute();
				}

			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public Object request2(int type, String request, Object[] args)
	{
		if(connect())
		{
			try
			{
				PreparedStatement st = conn.prepareStatement(request);
				int i = 1;
				for(Object o : args)
				{
//					System.out.println(o.getClass().toString()+" "+o.getClass().cast(o).toString());
					if(o.getClass().equals(int.class) || o.getClass().equals(Integer.class)) {
						st.setInt(i, (Integer)o);
					} else if (o.getClass().equals(float.class) || o.getClass().equals(Float.class)) {
						st.setFloat(i, (Float)o);
					} else if (o.getClass().equals(double.class) || o.getClass().equals(Double.class)) {
						st.setDouble(i, (Double)o);
					} else if (o.getClass().equals(String.class)) {
						st.setString(i, (String)o);
					} else if (o.getClass().equals(boolean.class) || o.getClass().equals(Boolean.class)) {
						st.setBoolean(i, (Boolean)o);
					} else if (o.getClass().equals(Timestamp.class)) {
						st.setTimestamp(i, (Timestamp)o);
					} else if (o.getClass().equals(Date.class)) {
						st.setDate(i, (Date)o);
					} else {
						System.out.println("bug !!!!! "+i+" "+o.getClass());
					}
					i++;
				}
				
				switch(type)
				{
				case TSELECT :
					return st.executeQuery();
				case TINSERT :
				case TUPDATE :
				case TDELETE :
					return st.executeUpdate();
				case TTRUNCATE :
					return st.execute();
				}

			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public String now() throws SQLException
	{
		ResultSet rs = (ResultSet)request(TSELECT, "select now()");
		if(rs.next())
			return DateFormat.getDateTimeInstance().format(new Date(rs.getTimestamp(1).getTime()));
		return "not now";
	}

	public Date now2() throws SQLException
	{
		ResultSet rs = (ResultSet)request(TSELECT, "select now()");
		if(rs.next())
			return new Date(rs.getTimestamp(1).getTime());
		return null;
	}

	public Timestamp now3() throws SQLException
	{
		ResultSet rs = (ResultSet)request(TSELECT, "select now()");
		if(rs.next())
			return rs.getTimestamp(1);
		return null;
	}
}
