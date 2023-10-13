package com.menitimu.rzs.stuff;

import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.TextRenderer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;
import com.menitimu.rzs.RZSMod;
import com.menitimu.rzs.hud.TabMoreInfo;
import com.menitimu.rzs.util.PlayerStatsCache;
import com.menitimu.rzs.util.RandomStuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ApiManager {
    private static final String[] InvalidedJson = {"§cError§r", "", "§c無効な§r", "", "§cJsonデータ§r", "", "", "", "", ""};
    private static final String[] FailedHttp = {"§cError§r", "", "§cHTTP§r", "", "リクエストに失敗", "", "", "", "", ""};
    private static final String[] NoPlayer = {"§cError§r", "", "§cPlayerの§r", "", "項目が存在しません", "", "", "", "", ""};
    private static final String[] NoStats = {"§cError§r", "", "§cStatsの§r", "", "項目が存在しません", "", "", "", "", ""};
    private static final String[] NoArcade = {"§cError§r", "", "§cArcadeの§r", "", "項目が存在しません", "", "", "", "", ""};
    private static final String[] NoUrl = {"§cError§r", "", "§cAPIの§r", "", "§curlが存在しません§r", "", "", "", "", ""};
    private static final String[] IllegalUrl = {"§cError§r", "", "§cAPIの§r", "", "§curlが不正です§r", "", "", "", "", ""};
    private static final String[] Error400 = {"§cError400§r", "", "§c必要な§r", "", "§c要素の欠落§r", "", "", "", "", ""};
    private static final String[] Error403 = {"§cError403§r", "", "§c無効な§r", "", "§cAPIキー§r", "", "", "", "", ""};
    private static final String[] Error429 = {"§cError429§r", "", "§cAPIの§r", "", "§c要求回数上限§r", "", "", "", "", ""};
    private static final Duration ERROR_CACHE = Duration.ofMinutes(5L);
    private static final Duration NORMAL_CACHE = Duration.ofMinutes(20L);
    private static Map<UUID, PlayerStatsCache> DataMap = new HashMap<>();
    public static void renderStats(UUID uuid, float x, float y, float scale){
        RandomStuff.renderRect(x, y, 0D, 84 * scale, 80 * scale, new OneColor(255, 255, 255, 30));
        String[] texts = getData(uuid);
        for (int i = 0; i < texts.length; i++){
            float x1 = x;
            if(i % 2 == 1) x1 += (42 - (float) Platform.getGLPlatform().getStringWidth(texts[i]) / 2);
            TextRenderer.drawScaledString(texts[i], x1, y + i * 8 * scale, 0xffffff, TextRenderer.TextType.SHADOW, scale);
        }
    }
    public static String[] getData(UUID uuid){
        Instant currentTime = Instant.now();
        if(DataMap.containsKey(uuid) && Duration.between(DataMap.get(uuid).getCreatedTime(), currentTime).compareTo(DataMap.get(uuid).getExpireDuration()) < 0) {
            return DataMap.get(uuid).getStats();
        }
        try {
            String urlStr;
            if (TabMoreInfo.APIurl.isEmpty()) return NoUrl;
            else urlStr = TabMoreInfo.APIurl + uuid.toString();
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                String jsonData = response.toString();
                connection.disconnect();
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(jsonData);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("success") && !jsonObject.get("success").getAsBoolean()) {
                        if (jsonObject.has("cause")) {
                            String cause = jsonObject.get("cause").getAsString();
                            if (cause.contains("Missing")) {
                                DataMap.put(uuid, new PlayerStatsCache(Error400, currentTime, ERROR_CACHE));
                                return Error400;
                            }
                            if (cause.contains("Invalid")) {
                                DataMap.put(uuid, new PlayerStatsCache(Error403, currentTime, ERROR_CACHE));
                                return Error403;
                            }
                            if (cause.contains("Key")) {
                                DataMap.put(uuid, new PlayerStatsCache(Error429, currentTime, ERROR_CACHE));
                                return Error429;
                            }
                        }
                        DataMap.put(uuid, new PlayerStatsCache(InvalidedJson, currentTime, ERROR_CACHE));
                        return InvalidedJson;
                    }
                    if (jsonObject.has("player")) {
                        JsonObject playerObject = jsonObject.getAsJsonObject("player");
                        if (playerObject.has("stats")) {
                            JsonObject statsObject = playerObject.getAsJsonObject("stats");
                            if (statsObject.has("Arcade")) {
                                JsonObject arcadeObject = statsObject.getAsJsonObject("Arcade");
                                String[] result = {"§2§lDE DATA:§r", "§c§lNULL§r", "§4§lBB DATA:§r", "§c§lNULL§r", "§9§lAA DATA:§r", "§c§lNULL§r", "§c§lKills:§r", "§c§lNULL§r", "§c§lK/DR:§r", "§e§lN/A§r"};
                                if (arcadeObject.has("wins_zombies_deadend")) {
                                    int DE_WINS = arcadeObject.get("wins_zombies_deadend").getAsInt();
                                    result[0] = "§2§lDE Wins:§r";
                                    if (DE_WINS >= 200)
                                        result[1] = "§6§l";
                                    else if (DE_WINS >= 100)
                                        result[1] = "§5§l";
                                    else if (DE_WINS >= 50)
                                        result[1] = "§9§l";
                                    else if (DE_WINS >= 20)
                                        result[1] = "§a§l";
                                    else if (DE_WINS >= 10)
                                        result[1] = "§e§l";
                                    else
                                        result[1] = "§c§l";
                                    result[1] += DE_WINS + (DE_WINS == 1 ? " Win §r" : " Wins §r");
                                } else if (arcadeObject.has("best_round_zombies_deadend")) {
                                    int DE_PB = arcadeObject.get("best_round_zombies_deadend").getAsInt();
                                    result[0] = "§2§lDE PB:§r";
                                    result[1] = "§7Round " + DE_PB + "§r";
                                }
                                if (arcadeObject.has("wins_zombies_badblood")) {
                                    int BB_WINS = arcadeObject.get("wins_zombies_badblood").getAsInt();
                                    result[2] = "§4§lBB Wins:§r";
                                    if (BB_WINS >= 200)
                                        result[3] = "§6§l";
                                    else if (BB_WINS >= 100)
                                        result[3] = "§5§l";
                                    else if (BB_WINS >= 50)
                                        result[3] = "§9§l";
                                    else if (BB_WINS >= 20)
                                        result[3] = "§a§l";
                                    else if (BB_WINS >= 10)
                                        result[3] = "§e§l";
                                    else
                                        result[3] = "§c§l";
                                    result[3] += BB_WINS + (BB_WINS == 1 ? " Win §r" : " Wins §r");
                                } else if (arcadeObject.has("best_round_zombies_badblood")) {
                                    int BB_PB = arcadeObject.get("best_round_zombies_badblood").getAsInt();
                                    result[2] = "§4§lBB PB:§r";
                                    result[3] = "§7Round " + BB_PB + "§r";
                                }
                                if (arcadeObject.has("wins_zombies_alienarcadium")) {
                                    int AA_WINS = arcadeObject.get("wins_zombies_alienarcadium").getAsInt();
                                    result[4] = "§9§lAA Wins:§r";
                                    if (AA_WINS >= 50)
                                        result[5] = "§6§l";
                                    else if (AA_WINS >= 20)
                                        result[5] = "§5§l";
                                    else if (AA_WINS >= 10)
                                        result[5] = "§9§l";
                                    else
                                        result[5] = "§a§l";
                                    result[5] += AA_WINS + (AA_WINS == 1 ? " Win§r" : " Wins§r");
                                } else if (arcadeObject.has("best_round_zombies_alienarcadium")) {
                                    int AA_PB = arcadeObject.get("best_round_zombies_alienarcadium").getAsInt();
                                    result[4] = "§9§lAA PB:§r";
                                    if (AA_PB >= 71)
                                        result[5] = "§e§l";
                                    else if (AA_PB >= 61)
                                        result[5] = "§c§l";
                                    else
                                        result[5] = "§7";
                                    result[5] += "Round " + AA_PB + "§r";
                                }
                                int Kills = 0;
                                if (arcadeObject.has("zombie_kills_zombies")) {
                                    Kills = arcadeObject.get("zombie_kills_zombies").getAsInt();
                                }
                                result[7] = "§e§l" + Kills + "§r";
                                int Death;
                                if (arcadeObject.has("deaths_zombies")) {
                                    Death = arcadeObject.get("deaths_zombies").getAsInt();
                                    if (Death == 0) {
                                        result[9] = "§c§lN/A§r";
                                    } else {
                                        DecimalFormat decimalFormat = new DecimalFormat("#.#");
                                        result[9] = "§e§l" + decimalFormat.format(Kills / Death) + "§r";
                                    }
                                }
                                DataMap.put(uuid, new PlayerStatsCache(result, currentTime, NORMAL_CACHE));
                                return result;
                            }
                            DataMap.put(uuid, new PlayerStatsCache(NoArcade, currentTime, ERROR_CACHE));
                            return NoArcade;
                        }
                        DataMap.put(uuid, new PlayerStatsCache(NoStats, currentTime, ERROR_CACHE));
                        return NoStats;
                    }
                    DataMap.put(uuid, new PlayerStatsCache(NoPlayer, currentTime, ERROR_CACHE));
                    return NoPlayer;
                } else {
                    DataMap.put(uuid, new PlayerStatsCache(FailedHttp, currentTime, ERROR_CACHE));
                    return FailedHttp;
                }
            } else if (responseCode == 400) {
                DataMap.put(uuid, new PlayerStatsCache(Error400, currentTime, ERROR_CACHE));
                return Error400;
            } else if (responseCode == 403) {
                DataMap.put(uuid, new PlayerStatsCache(Error403, currentTime, ERROR_CACHE));
                return Error403;
            } else if (responseCode == 429) {
                DataMap.put(uuid, new PlayerStatsCache(Error429, currentTime, ERROR_CACHE));
                return Error429;
            }
        }catch (MalformedURLException e) {
            return IllegalUrl;
        } catch (MalformedJsonException e) {
            DataMap.put(uuid, new PlayerStatsCache(InvalidedJson, currentTime, ERROR_CACHE));
            return InvalidedJson;
        } catch (IOException ignored) {
        }
        DataMap.put(uuid, new PlayerStatsCache(InvalidedJson, currentTime, ERROR_CACHE));
        return InvalidedJson;
    }
}
