package com.proryv.alarmnotification.adapters;

import android.os.Parcel;
import android.os.Parcelable;

public class MultiChoiceElement implements Parcelable
{
    public Boolean IsChecked;
    public int Id;
    public String Text;
    public String Id2;
    public EnumMultiChoiceType MultiChoiceType;

    public MultiChoiceElement(boolean isChecked, int id, String text, EnumMultiChoiceType multiChoiceType)
    {
        IsChecked = isChecked;
        Id = id;
        Text = text;
        MultiChoiceType = multiChoiceType;
    }

    public MultiChoiceElement(boolean isChecked, String id, String text, EnumMultiChoiceType multiChoiceType)
    {
        IsChecked = isChecked;
        Id2 = id;
        Text = text;
        MultiChoiceType = multiChoiceType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        boolean[] b = new boolean[]{IsChecked};
        parcel.writeBooleanArray(b);
        parcel.writeInt(Id);
        parcel.writeString(Text);
        parcel.writeString(Id2);
        parcel.writeInt(MultiChoiceType.getValue());
    }

    public static final Parcelable.Creator<MultiChoiceElement> CREATOR = new Parcelable.Creator<MultiChoiceElement>() {
        // распаковываем объект из Parcel
        public MultiChoiceElement createFromParcel(Parcel in) {
            return new MultiChoiceElement(in);
        }

        public MultiChoiceElement[] newArray(int size) {
            return new MultiChoiceElement[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private MultiChoiceElement(Parcel parcel) {
        boolean[] b = new boolean[1];
        parcel.readBooleanArray(b);
        Id = parcel.readInt();
        Text = parcel.readString();
        Id2 = parcel.readString();
        try
        {
            MultiChoiceType = EnumMultiChoiceType.values()[parcel.readInt()];
        }
        catch (Exception ex)
        {

        }
    }
}

