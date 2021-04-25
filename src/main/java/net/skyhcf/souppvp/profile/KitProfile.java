package net.skyhcf.souppvp.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.kit.Kit;
import net.skyhcf.souppvp.utils.MiscUtils;
import org.bson.Document;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class KitProfile {

    private final UUID uuid;
    private int kills;
    private int killstreak;
    private int deaths;
    private double balance;
    private List<Kit> allowedKits;

    public void incrementKill(){
        this.kills += 1;
    }

    public void incrementKill(int kills){
        this.kills += kills;
    }

    public void incrementKillstreak(){
        this.killstreak += 1;
    }

    public void incrementKillstreak(int killstreak){
        this.killstreak += killstreak;
    }

    public void incrementDeath(){
        this.deaths += 1;
    }

    public void incrementDeath(int deaths){
        this.deaths += deaths;
    }

    public void incrementBalance(double balance){
        this.balance += balance;
    }

    public void addAllowedKit(Kit kit){
        allowedKits.add(kit);
    }


    public void save(){
        Document filter = new Document("uuid", uuid.toString());
        Document oldDoc = SoupPvP.getInstance().getMongoManager().getProfiles().find(filter).first();
        if(oldDoc == null) return;
        Document newDoc = new Document("uuid", uuid.toString())
                .append("kills", kills)
                .append("killstreak", killstreak)
                .append("deaths", deaths)
                .append("balance", balance)
                .append("allowedKits", MiscUtils.kitsToStrings(allowedKits));
        SoupPvP.getInstance().getMongoManager().getProfiles().replaceOne(oldDoc, newDoc);
    }

}
