package com.self.doug.scouting.ParseWrapper;

//Transfer value from one source to another
public interface IFieldTransport
{
    public static int DIRECTION_FORWARD = 1;
    public static int DIRECTION_BACKWARD= 2;

    //Transfer from one mode to another
    public void transfer(ValueField f);
}