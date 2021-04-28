package net.skyhcf.souppvp.profile;

import com.google.common.collect.Lists;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.kit.Kit;
import net.skyhcf.souppvp.utils.MiscUtils;
import org.bson.Document;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class KitProfileManager {

    private List<KitProfile> profiles = Lists.newArrayList();

    public KitProfileManager(){
        this.refresh();
    }

    public KitProfile createProfile(Player player){
        KitProfile kitProfile = new KitProfile(player.getUniqueId(), 0, 0, 0, 0.0, Lists.newArrayList());
        Document document = new Document("uuid", kitProfile.getUuid().toString())
        .append("kills", kitProfile.getKills())
        .append("killstreak", kitProfile.getKillstreak())
        .append("deaths", kitProfile.getDeaths())
        .append("balance", kitProfile.getBalance())
        .append("allowedKits", MiscUtils.kitsToStrings(kitProfile.getAllowedKits()));
        SoupPvP.getInstance().getMongoManager().getProfiles().insertOne(document);
        return kitProfile;
    }

    public KitProfile get(Player player){
        for(KitProfile kitProfile : profiles){
            if(kitProfile.getUuid().toString().equalsIgnoreCase(player.getUniqueId().toString())) return kitProfile;
        }
        return null;
    }

    public KitProfile get(OfflinePlayer player){
        for(KitProfile kitProfile : profiles){
            if(kitProfile.getUuid().toString().equalsIgnoreCase(player.getUniqueId().toString())) return kitProfile;
        }
        return null;
    }

    public boolean exists(Player player){
        for(KitProfile kitProfile : profiles){
            if(kitProfile.getUuid().toString().equalsIgnoreCase(player.getUniqueId().toString())) return true;
        }
        return false;
    }

    public void refresh(){
        List<KitProfile> profiles = Lists.newArrayList();

        for(Document document : SoupPvP.getInstance().getMongoManager().getProfiles().find()){
            profiles.add(new KitProfile(
                    UUID.fromString(document.getString("uuid")),
                    document.getInteger("kills"),
                    document.getInteger("killstreak"),
                    document.getInteger("deaths"),
                    document.getDouble("balance"),
                    MiscUtils.stringsToKits(((List<String>) document.get("allowedKits")))
            ));
        }
        this.profiles = profiles;
    }


}
