/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlesim;

import java.text.DecimalFormat;

/**
 *
 * @author Jay Lopez
 */
public class Brute extends Warrior {
    private final int cursedSpecial = 4;
    private int cursedCount = 0;
    DecimalFormat a = new DecimalFormat("0");
    DecimalFormat b = new DecimalFormat("0.00");
    
    Brute(String name, Type type, int baseTough, int incTough, int baseDex, int incDex, int baseSmart, int incSmart, double baseArmor, double minDamage, double maxDamage, double baseAttackTime) {
        super(name, type, baseTough, incTough, baseDex, incDex, baseSmart, incSmart, baseArmor, minDamage, maxDamage, baseAttackTime);
    }

    public void takeDamage(double range, double armorBoost) {
        double first = trueArmor;
        trueArmor *= armorBoost;
        computeMitigation();
        takeDamage(range);
        trueArmor = first;
        computeMitigation();
    }
    
    public void attack(Cursed target) {
        double range = maxDamage - minDamage;
        range += Math.random() * range + minDamage;
        if (type == Type.TOUGH) {
            range += tough;
        }
        if (type == Type.DEXTEROUS) {
            range += dex;
        }
        if (type == Type.SMART) {
            range += smart;
        }
        if (cursedCount >= cursedSpecial) {
            cursedCount = -1;
            range /= 2;
        }
        target.takeDamage(range);
        cursedCount++;
        System.out.println(type + " " + name + " attacks for " + a.format(range) + " damage!"); 
    }
    
    public void reset() {
        fullHeal();
        timeBack();
        cursedCount = 0;
    }
    
    public String toString() {
        return type + " " + "Brutish" + " " + name + ": " + maxHp + "HP, " + tough + "TOU, " + dex + "DEX, " + smart + "SMR, " + b.format(trueArmor) + "ARM, " + baseAttackTime + "SPD";
    }
}
