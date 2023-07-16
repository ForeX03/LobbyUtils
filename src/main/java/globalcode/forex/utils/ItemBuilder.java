package globalcode.forex.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;

import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {
    private Material mat;

    private int amount;

    private short data;

    private String title;

    private List<String> lore;

    private HashMap<Enchantment, Integer> enchants;

    private Color color;

    private Potion potion;

    private String headUrl;
    private final String url = "http://textures.minecraft.net/texture/";

    public ItemBuilder(Material mat) {
        this(mat, 1);
    }

    public ItemBuilder(Material mat, int amount) {
        this(mat, amount, (short)0);
    }

    public ItemBuilder(Material mat, short data) {
        this(mat, 1, data);
    }

    public ItemBuilder(Material mat, int amount, short data) {
        this.title = null;
        this.lore = new ArrayList<>();
        this.enchants = new HashMap<>();
        this.mat = mat;
        this.amount = amount;
        this.data = data;
    }

    @SneakyThrows
    public ItemBuilder setOwnerFromUrl(String url) {
        this.headUrl = url;
        return this;
    }


    public ItemBuilder setType(Material mat) {
        this.mat = mat;
        return this;
    }

    public ItemBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ItemBuilder addLores(List<String> lores) {
        this.lore.addAll(lores);
        return this;
    }

    public ItemBuilder setLore(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchant, int level) {
        if (this.enchants.containsKey(enchant))
            this.enchants.remove(enchant);
        this.enchants.put(enchant, Integer.valueOf(level));
        return this;
    }

    public ItemBuilder setColor(Color color) {
        if (!this.mat.name().contains("LEATHER_"))
            throw new IllegalArgumentException("Can only dye leather armor!");
        this.color = color;
        return this;
    }

    public ItemBuilder setPotion(Potion potion) {
        if (this.mat != Material.POTION)
            this.mat = Material.POTION;
        this.potion = potion;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    @SneakyThrows
    public ItemStack build() {
        Material mat = this.mat;
        if (mat == null) {
            mat = Material.AIR;
            Bukkit.getLogger().warning("Null material!");
        }

        ItemStack item = new ItemStack(this.mat, this.amount, this.data);
        ItemMeta meta = item.getItemMeta();
        if (headUrl != null) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { this.url+this.headUrl }).getBytes());
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        }
        if (this.title != null) meta.setDisplayName(this.title);
        if (!this.lore.isEmpty()) meta.setLore(this.lore);
        if (meta instanceof LeatherArmorMeta)
            ((LeatherArmorMeta)meta).setColor(this.color);
        item.setItemMeta(meta);
        item.addUnsafeEnchantments(this.enchants);
        if (this.potion != null)
            this.potion.apply(item);
        return item;
    }

    public ItemBuilder clone() {
        ItemBuilder newBuilder = new ItemBuilder(this.mat);
        newBuilder.setTitle(this.title);
        for (String lore : this.lore)
            newBuilder.setLore(lore);
        for (Map.Entry<Enchantment, Integer> entry : this.enchants.entrySet())
            newBuilder.addEnchantment(entry.getKey(), ((Integer)entry.getValue()).intValue());
        newBuilder.setColor(this.color);
        newBuilder.potion = this.potion;
        return newBuilder;
    }

    public Material getType() {
        return this.mat;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean hasEnchantment(Enchantment enchant) {
        return this.enchants.containsKey(enchant);
    }

    public int getEnchantmentLevel(Enchantment enchant) {
        return ((Integer)this.enchants.get(enchant)).intValue();
    }

    public HashMap<Enchantment, Integer> getAllEnchantments() {
        return this.enchants;
    }

    public boolean isItem(ItemStack item) {
        return isItem(item, false);
    }

    public boolean isItem(ItemStack item, boolean strictDataMatch) {
        ItemMeta meta = item.getItemMeta();
        if (item.getType() != getType())
            return false;
        if (!meta.hasDisplayName() && getTitle() != null)
            return false;
        if (!meta.getDisplayName().equals(getTitle()))
            return false;
        if (!meta.hasLore() && !getLore().isEmpty())
            return false;
        if (meta.hasLore())
            for (String lore : meta.getLore()) {
                if (!getLore().contains(lore))
                    return false;
            }
        for (Enchantment enchant : item.getEnchantments().keySet()) {
            if (!hasEnchantment(enchant))
                return false;
        }
        return true;
    }
}