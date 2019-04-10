package heightsre.java.prog;
import java.util.*;
import java.io.*;

public class Config {
	
  public void getParams() throws  IOException
  {
	  
	  Properties props = new Properties();
      FileInputStream in = new FileInputStream(cfile);
      props.load(in);
      in.close();
      
      asthost = props.getProperty("asthost");
      username = props.getProperty("username");
      password = props.getProperty("password");
      astChann = props.getProperty("channlike");
      outtrunk = props.getProperty("outtrunk");
      
      
      drivers = props.getProperty("jdbc.drivers");
      url = props.getProperty("jdbc.url");
      dbusername = props.getProperty("jdbc.username");
      dbpassword = props.getProperty("jdbc.password");
      
      //apache
      url_apa = props.getProperty("jdbc.url_apa");  //jdbc url
      dbusername_apa = props.getProperty("jdbc.username_apa"); // jdbc username
      dbpassword_apa = props.getProperty("jdbc.password_apa");  // jdbc password
      
	  
     
  }

  
  public String  asthost, username, password, astChann, outtrunk;
  
  String drivers;
  String url;
  String dbusername;
  String dbpassword;
  
  
  String url_apa;
  String dbusername_apa;
  String dbpassword_apa;

  
  public final String cfile = "/var/javalib/heights.properties";

}
