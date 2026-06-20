public abstract class User {
protected String ID;
protected String Name;    
protected String UserName;
protected String Password;
protected String UserType;
public User (String ID ,String Name, String UserName,String Password,String UserType){
 
    this.ID=ID;
    this.Name=Name;
    this.UserName=UserName;
    this.Password=Password;
    this.UserType=UserType;
}
public User(){}
public void setID(String ID){

this.ID=ID;
    
}
public void setName(String Name){
 this.Name=Name;

}
 public void setUserName(String UserName){


    this.UserName=UserName;
 }

public void setPassword(String Password){

 this.Password=Password;

}
public void setUserType(String UserType){

 this.UserType=UserType;

}

public String getID(){
return ID;
}

public String getName(){
return Name;
}
public String getUserName(){
return UserName;
}
public String getPassword(){
return Password;}



public String getUserType(){
return UserType;
}

public abstract void Showmenu();

}
