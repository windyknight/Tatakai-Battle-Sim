/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlesim;

import java.util.*;

/**
 *
 * @author Jay Lopez
 */
public class Warrior {
    protected String name;
    protected Type type;
    protected int level;
    //protected base stats//
    protected final double baseHp = 150;
    protected final double baseEnergy = 0;
    protected int baseTough; //hp and regen
    protected int baseDex; //armor and ias
    protected int baseSmart; //energy
    protected int incTough;
    protected int incDex;
    protected int incSmart;
    protected final double baseRegen = 0.25;
    protected double baseAttackTime;
    protected double baseArmor;
    //end protected base stats//
    protected double minDamage;
    protected double maxDamage;
    protected double currentHp;
    protected double maxHp;
    protected double energy;
    protected double trueArmor;
    protected double incAttackSpd;
    protected ArrayList<Item> items;
    //final stats to be used in combat//
    protected int tough; //hp and regen
    protected int dex; //armor and ias
    protected int smart; //energy
    protected double regen;
    protected double damageMitigation;
    protected double attackCooldown;
    protected boolean alive;
    
    Warrior(String name, Type type, int baseTough, int incTough, int baseDex, int incDex, int baseSmart, int incSmart, double baseArmor, double minDamage, double maxDamage, double baseAttackTime) {
        this.name = name;
        this.type = type;
        this.baseTough = baseTough;
        this.incTough = incTough;
        this.baseDex = baseDex;
        this.incDex = incDex;
        this.baseSmart = baseSmart;
        this.incSmart = incSmart;
        this.baseArmor = baseArmor;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.baseAttackTime = baseAttackTime;
        this.items = new ArrayList<>();
        this.alive = true;
        computeNewStats();
    }
    
    
    public void computeNewStats() {
        tough = baseTough + ((level - 1) * incTough);
        for (Item a : items) {
            tough += a.getTough();
        }
        
        smart = baseSmart + ((level - 1) * incSmart);
        for (Item a : items) {
            smart += a.getSmart();
        }
        
        dex = baseDex + ((level - 1) * incDex);
        for (Item a : items) {
            dex += a.getDex();
        }
        
        regen = baseRegen + (tough * 0.03);
        for (Item a : items) {
            regen += a.getRegen();
        }
        
        maxHp = baseHp + (tough * 19);
        for (Item a : items) {
            maxHp += a.getHp();
        }
        
        energy = baseEnergy + (smart * 13);
        for (Item a : items) {
            energy += a.getEnergy();
        }
        
        trueArmor = baseArmor + (0.14 * dex);
        for (Item a : items)  {
            trueArmor += a.getArmor();
        }
        
        incAttackSpd = dex;
        for (Item a : items) {
            incAttackSpd += a.getIas();
        }
        
        damageMitigation = (0.06 * trueArmor) / (1 + (0.06 * trueArmor));
        attackCooldown = ((int)(baseAttackTime / (1 + (incAttackSpd / 100)) * 100)) / 100.00;
    }
    
    public void levelUp() {
        level += 1;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void fullHeal() {
        currentHp = maxHp;
    }
    
    public void partialHeal(double heal) {
        currentHp = currentHp + heal > maxHp ? maxHp : currentHp + heal;
    }
    
    public void takeItem (Item item) {
        items.add(item);
        computeNewStats();
    }
    
    public void dropItem (Item item) {
        if (items.indexOf(item) > -1) {
            items.remove(item);
            computeNewStats();
        }
    }
    
    public Item getItem (int i) {
        return items.get(i);
    }
    
    public void takeDamage(double damage) {
        damage *= 1 - damageMitigation;
        currentHp -= damage;
        if (currentHp <= 0) {
            alive = false;
        }
    }
    
    public void attack (Warrior target) {
        double range = maxDamage - minDamage;
        range *= Math.random() * range + minDamage;
        if (type == Type.TOUGH) {
            range += tough;
        }
        if (type == Type.DEXTEROUS) {
            range += dex;
        }
        if (type == Type.SMART) {
            range += smart;
        }
        target.takeDamage(range);
        System.out.println(type + name + " attacks for " + range + " damage!");
    }
    
    @Override
    public String toString() {
        return type + name + ": " + currentHp + "/" + maxHp;
    }
    
    public double getCurrentHp() {
        return currentHp;
    }
    
    public double getMaxHp() {
        return maxHp;
    }
    
    public double getIas() {
        return incAttackSpd;
    }
    
    public void increaseIas(double value) {
        incAttackSpd += value;
    }
    
    public double getEnergy() {
        return energy;
    }
}
