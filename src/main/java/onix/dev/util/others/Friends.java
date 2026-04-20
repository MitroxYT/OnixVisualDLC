package onix.dev.util.others;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;


import java.util.ArrayList;
import java.util.List;

@Getter
@UtilityClass
public class Friends {
    @Getter
    public final List<Friend> friends = new ArrayList<>();

    public void addFriend(Player player) {
        addFriend(player.getName().getString());
    }

    public void addFriend(String name) {
        friends.add(new Friend(name));
    }

    public void removeFriend(Player player) {
        removeFriend(player.getName().getString());
    }

    public void removeFriend(String name) {
        friends.removeIf(friend -> friend.getName().equalsIgnoreCase(name));
    }

    public boolean isFriend(Entity entity) {
        if (entity instanceof Player player) return isFriend(player.getName().getString());
        return false;
    }
    public boolean isFriend(String friend) {
        return friends.stream().anyMatch(isFriend -> isFriend.getName().equals(friend));
    }

    public void clear() {
        friends.clear();
    }
}
