package com.proryv.alarmnotification.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Ig on 27.06.13.
 */
public class SubscribedServer  implements Serializable, Parcelable {
    public String Name;
    public String EndpointAddress;
    public boolean IsActive;
    public String User;
    public String Password;
    public long id;

    public int Port;


    public SubscribedServer(String name, String endpointAddress, boolean isActive, String user, String password, int port)
    {
        Name = name;
        Port = port;
        EndpointAddress =endpointAddress;
        IsActive = isActive;
        User = user;
        Password = password;

        id = (((long)endpointAddress.hashCode()) << 32) | (name.hashCode() & 0xffffffffL);
    }

    public String getEndpointAddress()
    {
        return "http://" + EndpointAddress + ":" + Port;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(Name);
        parcel.writeString(EndpointAddress);
        parcel.writeString(User);
        parcel.writeString(Password);
        boolean[] b = new boolean[1];
        b[0] = IsActive;
        parcel.writeBooleanArray (b);
        parcel.writeLong(id);
        parcel.writeInt(Port);
    }

    public static final Parcelable.Creator<SubscribedServer> CREATOR = new Parcelable.Creator<SubscribedServer>() {
        // распаковываем объект из Parcel
        public SubscribedServer createFromParcel(Parcel in) {
            return new SubscribedServer(in);
        }

        public SubscribedServer[] newArray(int size) {
            return new SubscribedServer[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private SubscribedServer(Parcel parcel) {
        Name = parcel.readString();
        EndpointAddress =parcel.readString();
        User = parcel.readString();
        Password = parcel.readString();
        boolean[] b = new boolean[1];
        parcel.readBooleanArray(b);
        IsActive = b[0];
        id = parcel.readLong();
        Port = parcel.readInt();
    }
}
