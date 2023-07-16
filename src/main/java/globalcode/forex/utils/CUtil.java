package globalcode.forex.utils;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CUtil {
    public static String fixColor(String s) {
        if (s == null) {
            return "";
        }
        return IridiumColorAPI.process(s
                .replace("*>>", "»")
                .replace("*<<", "«")
                .replace("{HEART}", "❤")
                .replace("{PI}", "π")
                .replace("{R_ARROW}", "→")
                .replace("{L_ARROW}", "←")
                .replace("*<X>", "✘")
                .replace("*<Y>", "✔")
                .replace("{UNDERLINE}", "۔")
                .replace("{DOT}", "●"));
    }
    public static List<String> fixColor(List<String> list){
        List<String> finallist = new ArrayList<>();
        for(String s : list){
            finallist.add(fixColor(s));
        }
        return finallist;
    }
}
