package onix.dev.util.others;


import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

public class FriendUtils {
    private static final Set<String> friends = new HashSet<>();
    
    public static boolean isFriend(Player player) {
        return friends.contains(player.getName().getString().toLowerCase());
    }
    
    public static void addFriend(String name) {
        friends.add(name.toLowerCase());
    }
    
    public static void removeFriend(String name) {
        friends.remove(name.toLowerCase());
    }
    
    public static Set<String> getFriends() {
        return friends;
    }
}