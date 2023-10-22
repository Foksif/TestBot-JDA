package net.fworlds;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.fworlds.listeneres.EventListener;

import javax.security.auth.login.LoginException;

public class Main {

    private final Dotenv config;
    private final ShardManager shardManager;
    public Main() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.listening("By Foks_f"));
        shardManager = builder.build();

        shardManager.addEventListener(new EventListener());

    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager(){
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            Main bot = new Main();
        } catch (LoginException e) {
            System.out.println("ERROR: Invalid token");
        }

    }
}