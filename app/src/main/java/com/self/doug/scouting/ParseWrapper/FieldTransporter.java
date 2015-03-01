package com.self.doug.scouting.ParseWrapper;

import android.os.Parcel;

import com.parse.ParseObject;

import java.util.HashMap;
import java.util.Map;

// See http://codewithchris.com

public class FieldTransporter
        implements IFieldTransport
{
    ParseObject po;
    Parcel p;
    int d = IFieldTransport.DIRECTION_FORWARD;

    Map<String,IFieldTransport> transporterMap;

    public FieldTransporter(ParseObject inpo, Parcel inp, int direction){
        po = inpo;
        p = inp;
        d = direction;

        //Register the all the translators/tranporters
        register();
    }
    private void register()
    {
        transporterMap = new HashMap<String,IFieldTransport>();
        //register integers
        transporterMap.put(
                ValueField.FT_int,
                new IntegerFieldTransport(po,p,d));

        //register string transporter
        transporterMap.put(
                ValueField.FT_string,
                new StringFieldTransport(po,p,d));

        //Other missing transporters
    }
    private IFieldTransport getTransportFor(String fieldType)
    {
        IFieldTransport ift = transporterMap.get(fieldType);
        if (ift == null)
        {
            throw new RuntimeException("Problem with locating the type");
        }
        return ift;
    }

    @Override
    public void transfer(ValueField f)
    {
        IFieldTransport ift = getTransportFor(f.type);
        ift.transfer(f);
    }
}
