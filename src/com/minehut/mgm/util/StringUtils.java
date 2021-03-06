package com.minehut.mgm.util;

import org.bukkit.ChatColor;
import org.bukkit.util.ChatPaginator;

import java.text.DecimalFormat;

/**
 * Created by luke on 6/1/15.
 */
public class StringUtils {
    public static int timeStringToSeconds(String input) {
        if (input == "oo")
            return (int) Double.POSITIVE_INFINITY;
        if (input == "-oo")
            return (int) Double.NEGATIVE_INFINITY;
        int time = 0;
        String currentUnit = "";
        String current = "";
        boolean negative = false;
        for (int i = 0; i < input.length(); i ++) {
            char c = input.charAt(i);
            if (Character.isDigit(c) && !currentUnit.equals("")) {
                time += convert(NumUtils.parseInt(current) * (negative ? -1 : 1), currentUnit);
                current = "";
                currentUnit = "";
            }
            if (c == '-') {
                negative = true;
            } else if (Character.isDigit(c)) {
                current += NumUtils.parseInt(c + "");
            } else {
                currentUnit += c + "";
            }
        }
        time += convert(NumUtils.parseInt(current) * (negative ? -1 : 1), currentUnit);
        return time;
    }

    private static int convert(int value, String unit) {
        switch (unit) {
            case "y": return value * 365 * 60 * 60 * 24;
            case "mo": return value * 31 * 60 * 60 * 24;
            case "d": return value * 60 * 60 * 24;
            case "h": return value * 60 * 60;
            case "m": return value * 60;
            case "s": return value;
        }
        return value;
    }

    public static String formatTime(double time) {
        boolean negative = false;
        if (time < 0) {
            negative = true;
            time *= -1;
        }
        int hours = (int) time / 3600;
        int minutes = (int) (time - (hours * 3600)) / 60;
        int seconds = (int) time - (hours * 3600) - (minutes * 60);
        String hoursString = hours + "";
        String minutesString = minutes + "";
        String secondsString = seconds + "";
        while (minutesString.length() < 2) {
            minutesString = "0" + minutesString;
        }
        while (secondsString.length() < 2) {
            secondsString = "0" + secondsString;
        }
        return (negative ? "-" : "") + (hours == 0 ? "" : hoursString + ":") + minutesString + ":" + secondsString;
    }

    public static String formatTimeWithMillis(double time) {
        boolean negative = false;
        if (time < 0) {
            negative = true;
            time *= -1;
        }
        int hours = (int) time / 3600;
        int minutes = (int) (time - (hours * 3600)) / 60;
        int seconds = (int) time - (hours * 3600) - (minutes * 60);
        double millis = time - (hours * 3600) - (minutes * 60) - seconds;
        String hoursString = hours + "";
        String minutesString = minutes + "";
        String secondsString = seconds + "";
        String millisString = new DecimalFormat(".000").format(millis);
        millisString = millisString.substring(1);
        while (minutesString.length() < 2) {
            minutesString = "0" + minutesString;
        }
        while (secondsString.length() < 2) {
            secondsString = "0" + secondsString;
        }
        return (negative ? "-" : "") + (hours == 0 ? "" : hoursString + ":") + minutesString + ":" + secondsString + "." + millisString;
    }

    public static String getTechnicalName(String string) {
        return string.trim().toUpperCase().replaceAll(" ", "_");
    }

    /** Repeat character 'c' n times. */
    public static String repeat(String c, int n) {
        assert n >= 0;
        return new String(new char[n]).replace("\0", c);
    }

    public static String padMessage(String message, String c, ChatColor dashColor, ChatColor messageColor) {
        message = " " + message + " ";
        String dashes = StringUtils.repeat(c, (ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH - ChatColor.stripColor(message).length() - 2) / (c.length() * 2));
        return dashColor + dashes + ChatColor.RESET + messageColor + message + ChatColor.RESET + dashColor + dashes;
    }

    public static String trimTo(String string, int start, int end) {
        return string.length() > start ? (string.length() > end ? string.substring(start, end) : string.substring(start)) : "";
    }

    public static String removeLastWord(String string) {
        String word = string;
        boolean reachedWord = false;
        for (int i = word.length() - 1; i >= 0; i --) {
            if (word.charAt(i) == ' ') {
                if (reachedWord) {
                    break;
                } else {
                    word = word.substring(0, i);
                }
            } else {
                if (!reachedWord) {
                    reachedWord = true;
                }
                word = word.substring(0, i);
            }
        }
        return word;
    }

    public static String getCurrentChatColor(String str, int index) {
        String color = "\u00A7r";
        for (int i = index - 1; i > 0; i --) {
            if (str.charAt(i - 1) == '\u00A7') {
                color = "\u00A7" + str.charAt(i);
                break;
            }
        }
        return color;
    }
}
