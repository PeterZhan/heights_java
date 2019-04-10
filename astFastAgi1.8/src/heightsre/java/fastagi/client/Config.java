package heightsre.java.fastagi.client;
import java.util.*;
import java.io.*;

// this config is to get asterisk connection parameters and mysql database connection
// parameters
public class Config {
	// get parameters from configration file
  public void getParams() throws  IOException
  {
	  // read the property file
	  Properties props = new Properties();
      FileInputStream in = new FileInputStream(cfile);
      props.load(in);
      in.close();
      
      asthost = props.getProperty("asthost");  // get tha asterisk ip
      username = props.getProperty("username");  //get the asterisk lgoin name
      password = props.getProperty("password");  // login password
      astChann = props.getProperty("channlike");  // channel to monitor
      outtrunk = props.getProperty("outtrunk");  // trunk name
      
      
      drivers = props.getProperty("jdbc.drivers");  //jdbc driver name
      url = props.getProperty("jdbc.url");  //jdbc url
      dbusername = props.getProperty("jdbc.username"); // jdbc username
      dbpassword = props.getProperty("jdbc.password");  // jdbc password
      
	  
      
      //apache
      url_apa = props.getProperty("jdbc.url_apa");  //jdbc url
      dbusername_apa = props.getProperty("jdbc.username_apa"); // jdbc username
      dbpassword_apa = props.getProperty("jdbc.password_apa");  // jdbc password
     
  }

  // for asterisk
  public String  asthost, username, password, astChann, outtrunk;
  
  
  String drivers;
  // for mysql
  
  String url;
  String dbusername;
  String dbpassword;

  
  // for apache mysql
  
  
  String url_apa;
  String dbusername_apa;
  String dbpassword_apa;
  
  
  
 // the configuration file 
  public final String cfile = "/var/javalib/heights.properties";

}
