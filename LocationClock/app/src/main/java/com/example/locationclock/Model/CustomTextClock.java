package com.example.locationclock.Model;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.widget.TextClock;

import java.util.TimeZone;

public class CustomTextClock extends TextClock {

    private String TimeZoneText;

    @Override
    public String getTimeZone() {
        return TimeZoneText;
    }

    public void setTimeZoneText(String timeZoneText) {
        TimeZoneText = timeZoneText;
    }

    public CustomTextClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        TimeZone timeZone = TimeZone.getDefault();
        setTimeZoneText(timeZoneTextConstructor(timeZone.getRawOffset()));
    }

    public String getFormatString() {
        if (getFormat24Hour() ==  null) {
            return "12 Hour Format";
        }
        else {
            return "24 Hour Format";
        }
    }

    public void changeFormat(){
        if (getFormat24Hour() ==  null) {
            setFormat12Hour(null);
            setFormat24Hour("HH:mm");
        } else {
            setFormat24Hour(null);
            setFormat12Hour("hh:mm a");
        }
    }

    private String timeZoneTextConstructor(Integer offset) {
        String tz = "Etc/GMT";

        if (offset < 0) {
            tz += offset.toString();
        } else {
            tz += "+" + offset.toString();
        }
        return tz;
    }

    public void setTimeZone(Location location) {
        double pos = location.getLongitude();
        Integer offset;

        if (pos < -7.5) {
            offset = (int) Math.ceil((pos - 7.5) / 15);
        } else {
            offset = (int) Math.floor((pos + 7.5) / 15);
        }
        Integer milliOffset = offset * 60 * 60 * 1000;
        String[] timezones = TimeZone.getAvailableIDs(milliOffset);

        setTimeZone(timezones[0]);
        setTimeZoneText(timeZoneTextConstructor(offset));
    }
}
