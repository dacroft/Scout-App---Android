package com.self.doug.scouting.ParseWrapper;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParseObjectWrapper
        implements Parcelable
{
    public static String f_createdAt = "createdAt";
    public static String f_createdBy = "createdBy";

    public static String f_updatedAt = "updatedAt";
    public static String f_updatedBy = "updatedBy";

    //The parse object that is being wrapped
    public ParseObject po;

    //Constructors
    //Use this when you are creating a new one from scratch
    public ParseObjectWrapper(String tablename)    {
        po = new ParseObject(tablename);
        po.put(f_createdBy, ParseUser.getCurrentUser());
    }
    //Use this to create proper shell
    //For example you can do this in parcelable
    public ParseObjectWrapper(String tablename, String objectId)
    {
        po = ParseObject.createWithoutData(tablename, objectId);
    }

    //Use this when you are creating from an exsiting parse obejct
    public ParseObjectWrapper(ParseObject in)
    {
        po = in;
    }

    //To create derived objects like Word using the
    //ParseObjectWrapper that is unparceled
    public ParseObjectWrapper(ParseObjectWrapper inPow)
    {
        //Parseobject underneath
        po = inPow.po;

        //parseobject essentials if it has it
        poe = inPow.poe;
    }

    //Accessors
    public ParseObject getParseObject() { return po; }
    String getTablename(){return po.getClassName();    }
    public ParseUser getCreatedBy(){return po.getParseUser(f_createdBy);}
    public void setCreatedBy(ParseUser in){po.put(f_createdBy, in);}
    public void setUpdatedBy(){po.put(f_updatedBy, ParseUser.getCurrentUser());}
    public ParseUser getLastUpdatedBy(){return (ParseUser)po.getParseObject(f_updatedBy);    }

    //Parcelable stuff
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<ParseObjectWrapper> CREATOR
            = new Parcelable.Creator<ParseObjectWrapper>() {
        public ParseObjectWrapper createFromParcel(Parcel in) {
            return create(in);
        }
        public ParseObjectWrapper[] newArray(int size) {
            return new ParseObjectWrapper[size];
        }
    }; //end of creator

    @Override
    public void writeToParcel(Parcel parcel, int flags)
    {
        //Order: tablename, objectId, fieldlist, field values, essentials

        //write the tablename
        parcel.writeString(this.getTablename());

        //write the object id
        parcel.writeString(this.po.getObjectId());

        //write the field list and write the field names
        List<ValueField> fieldList = getFieldList();
        //See how many
        int i = fieldList.size();
        parcel.writeInt(i);

        //write each of the field types
        for(ValueField vf: fieldList)      {
            parcel.writeParcelable(vf, flags);
        }

        //You need to write the field values now
        FieldTransporter ft = new FieldTransporter(this.po,parcel,FieldTransporter.DIRECTION_FORWARD);
        for(ValueField vf: fieldList)      {
            //This will write the field from parse object to the parcel
            ft.transfer(vf);
        }

        //get the essentials and write to the parcel
        ParseObjectEssentials lpoe = this.getEssentials();
        parcel.writeParcelable(lpoe, flags);
    }
    //
    private static ParseObjectWrapper create(Parcel parcel)
    {
        //Order: tablename, objectid, fieldlist, field values, essentials

        String tablename = parcel.readString();
        String objectId = parcel.readString();

        ParseObjectWrapper parseObject =
                new ParseObjectWrapper(tablename, objectId);

        //Read the valuefiled list from parcel
        List<ValueField> fieldList = new ArrayList<ValueField>();
        int size = parcel.readInt();
        for(int i=0;i<size;i++)
        {
            ValueField vf = (ValueField)parcel.readParcelable(ValueField.class.getClassLoader());
            fieldList.add(vf);
        }
        //add the field values
        FieldTransporter ft =
                new FieldTransporter(
                        parseObject.po, parcel, IFieldTransport.DIRECTION_BACKWARD);
        for(ValueField vf: fieldList)
        {
            ft.transfer(vf);
        }
        //read essentials
        ParseObjectEssentials poe =
                (ParseObjectEssentials)parcel.readParcelable(ParseObjectEssentials.class.getClassLoader());

        parseObject.setParseObjectEssentials(poe);
        return parseObject;
    }

    //have the children override this
    public List<ValueField> getFieldList()
    {
        return new ArrayList<ValueField>();
    }

    //To represent createdby and lastupdatedby user objects
    //when parceled. I don't recreate them as ParseObjects but save their
    //essential attributes in separate objects.
    private ParseObjectEssentials poe;
    public void setParseObjectEssentials(ParseObjectEssentials inpoe)   {
        poe = inpoe;
    }
    public ParseObjectEssentials getEssentials()
    {
        if (poe != null) return poe;

        Date cat = po.getCreatedAt();
        Date luat = po.getUpdatedAt();
        ParseUser cby = getCreatedBy();
        ParseUser luby = getLastUpdatedBy();
        return new ParseObjectEssentials(
                cat, User.fromParseUser(cby),
                luat, User.fromParseUser(luby));
    }
    public boolean isParcelled()
    {
        if (poe != null) return true;
        return false;
    }

    //Utility methods that take into account if this
    //object is parceled or not
    public User getCreatedByUser()    {
        if (!isParcelled())
        {
            //it is not parcelled so it is original
            return User.fromParseUser(getCreatedBy());
        }
        //it is parcelled
        return poe.createdBy;
    }
    public Date getCreatedAt()    {
        if (!isParcelled())
        {
            //it is not parcelled so it is original
            return po.getCreatedAt();
        }
        //it is parcelled
        return poe.createdAt;
    }
}
